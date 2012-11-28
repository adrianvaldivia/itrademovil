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
import com.itrade.model.PedidoLinea;
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
    
    
    public Integer cargarRegPrueba(){
    	Deposito dep1 = new Deposito(null, null, 2, 11.1, "2012-11-11", "123456");
    	Deposito dep2 = new Deposito(null, null, 2, 11.1, "2012-11-05", "654321");
    	Deposito dep3 = new Deposito(null, null, 2, 110.1, "2012-11-26", "ASDHAKJ");
    	depositoDao.insert(dep1);
    	depositoDao.insert(dep2);
    	depositoDao.insert(dep3);
    	return 1;
    }

	public Integer syncBDToSqlite(String idusuario) {
		// TODO Auto-generated method stub
		Integer registros=0;		
		if (networkAvailable()){
			//Obtener todos los depositos del usuario
			//Registra los pendientes
			List<Deposito> depositosPendientes=depositosByUser(idusuario);
			if (depositosPendientes.size()>0){
				for (Deposito dep :depositosPendientes){
					Log.d("IDDEPOSITO", "DEPO="+dep.getId().toString()+"=======");
					Long id=dep.getId();
					Integer a= registrarDeposito(dep.getIdUsuario().toString(), dep.getMonto(), dep.getFecha(), dep.getNumVoucher());
					Log.d("AHHHHHHHREGISTRAA", "REGISTRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+a);				
					List<Deposito> depTemp=depositoDao.queryBuilder()
							.where(Properties.IdDeposito.eq(0))
							.list();
					Deposito depo=depTemp.get(0);
					depo.setIdDeposito(a);
					depositoDao.update(depo);
					registros++;
				}	
			}
			//Obten los pedidos del servidor
			ArrayList<Deposito> listDepsWeb = consultarDepositos(idusuario,getFechaActual());
			if (listDepsWeb.size()>0){
				for(Deposito dep:listDepsWeb){
					if (!estaEnLocal(dep.getIdDeposito())){
						//Registrar en la BD local
						depositoDao.insert(dep);
					}
				}
			}					
		}
		else{
			Toast.makeText(context, "No Hay Conexion a Internet", Toast.LENGTH_LONG).show();
		}		
		return registros;
	}
	
	
	public boolean estaEnLocal(Integer iddeposito){
		List<Deposito> depTemp=depositoDao.queryBuilder()
				.where(Properties.IdDeposito.eq(iddeposito))
				.list();
		if (depTemp.size()>0){
			return true;
		}
		return false;
	}
	
	public ArrayList<Deposito> consultarDepositos(String idusuario, String fecha){
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idusuario", idusuario));		
		param.add(new BasicNameValuePair("fecha", fecha));		
		sync.conexion(param,"/ws/cobranza/get_deposito_by_user/");
		try {
			sync.getHilo().join();
		} catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}					
		//Log.d("Pedido", sync.getResponse());
		ArrayList<Deposito> listDep = new ArrayList<Deposito>();
		listDep=gson.fromJson(sync.getResponse(), new TypeToken<List<Deposito>>(){}.getType());		
		return listDep;		
	}
	
	public Integer registrarDeposito(String idusuario, Double monto, String fecha, String numvoucher){
			
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idusuario", idusuario));
		param.add(new BasicNameValuePair("monto", monto.toString()));	
		param.add(new BasicNameValuePair("fecha", fecha));	
		param.add(new BasicNameValuePair("numvoucher", numvoucher));	
		sync.conexion(param,"/ws/cobranza/registro_deposito/");
		try {
			sync.getHilo().join();
		} catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}					
		//Log.d("Pedido", sync.getResponse());
		String iddeposito=sync.getResponse();
		//Log.d("IDDEPOSITO", "DEPOSITO"+iddeposito);
		//bORRAR TODOS LOS PEDIDOS
		return Integer.parseInt(iddeposito);
	}

	public List<Deposito> depositosByUser(String idusuario){		
		List<Deposito> depTemp = depositoDao.queryBuilder()
				.where(Properties.IdUsuario.eq(Integer.parseInt(idusuario)))	
				.where(Properties.IdDeposito.eq(0))		
				.list();			
		return depTemp;
	}
		
	public Integer cargarDeposito(String idusuario,String monto, String numVoucher ) {
		
		Deposito deposito = new Deposito(null, 0, Integer.parseInt(idusuario), Double.parseDouble(monto), getFechaActual(), numVoucher);
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



