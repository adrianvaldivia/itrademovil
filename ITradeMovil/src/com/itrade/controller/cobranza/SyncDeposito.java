package com.itrade.controller.cobranza;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;
import com.itrade.model.Deposito;
import com.itrade.model.DepositoDao;
import com.itrade.model.Pedido;
import com.itrade.model.DepositoDao.Properties;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;

public class SyncDeposito {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DepositoDao depositoDao;        
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
	
    public SyncDeposito(Activity activ){
    	super();    	
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        depositoDao = daoSession.getDepositoDao();
        sync=new Syncronizar(activity);
        gson = new Gson();  
    }
    
    public void closeDB(){
		db.close();
	}
    
    public boolean networkAvailable() {    	
    	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if (connectMgr != null) {
    		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
    		if (netInfo != null) {
    			for (NetworkInfo net : netInfo) {
    				if (net.getState() == NetworkInfo.State.CONNECTED) {
    					return true;
    				}
    			}
    		}
    	} 
    	else {
    		Log.d("NETWORK", "No network available");
    	}
    	return false;
    }

	public Integer syncBDToSqlite(String idusuario,String fecha) {
		// TODO Auto-generated method stub
		Integer registros=0;
		
		if (networkAvailable()){
			try{
				//registros +=cargarDeposito(idusuario,fecha);
			}catch(Exception e){
				Log.d("SqlLite","No tiene cobertura");
				Log.d("Error", "SE CAYO" + " "+e.getMessage());
			}
		}
		else{
			Toast.makeText(context, "No Hay Conexion a Internet", Toast.LENGTH_LONG).show();
		}
		
		return registros;
	}
	/*
	public Integer registrarDeposito(){
		
	}
*/
		
	public Integer cargarDeposito(String idusuario,String monto, String numVoucher ) {
		
		Deposito deposito = new Deposito(null, null, Integer.parseInt(idusuario), Double.parseDouble(monto), getFechaActual(), numVoucher);
		depositoDao.insert(deposito);		
		return 1;
	}

	public Double totalDepositado(String idusuario){
		Double sumaTotal = 0.0;
		List<Deposito> depTemp = depositoDao.queryBuilder()
				.where(Properties.IdUsuario.eq(Integer.parseInt(idusuario)))
				.where(Properties.Fecha.eq(getFechaActual()))																							
				.list();	
		Log.d("DEPOSITOS", "cantidad"+depTemp.size());
		for (Deposito deposito : depTemp)  {
			sumaTotal+=deposito.getMonto();
		}
		return Math.round(sumaTotal*100.0)/100.0;
	}
	private ArrayList<Deposito> getWebServiceList(String idusuario, String fecha) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idusuario", idusuario));
		param.add(new BasicNameValuePair("fecha", fecha));			
		String route="/ws/eventos/get_eventos_idusuario_month/";					
		sync.conexion(param,route);
		try {
			sync.getHilo().join();
			Log.d("TAG","LLEGA PRIMERO AKI");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		ArrayList<Deposito> linEvenList = new ArrayList<Deposito>();			
		linEvenList=gson.fromJson(sync.getResponse(), new TypeToken<List<Deposito>>(){}.getType());
		return linEvenList;
	}
	

	 private String getFechaActual() {    	
	    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());		
		String today = form.format(calendar.getTime());	
		return today;
	 }      
}



