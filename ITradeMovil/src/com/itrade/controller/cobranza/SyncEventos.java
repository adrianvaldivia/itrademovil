package com.itrade.controller.cobranza;

import java.util.ArrayList;
import java.util.List;

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
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;

public class SyncEventos {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private EventoDao eventoDao;    
    private List<Evento> listaEvento;   
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
	
    public SyncEventos(Activity activ){
    	super();
    	listaEvento=new ArrayList<Evento>();
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        eventoDao = daoSession.getEventoDao();
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
				registros +=cargarEventos(idusuario,fecha);
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

	private Integer cargarEventos(String idusuario,String fecha) {
		// TODO Auto-generated method stub
		List<Evento> listaEventTemp=eventoDao.loadAll();	
		if (listaEventTemp.isEmpty() || listaEventTemp.size()==0 ){					
			ArrayList<Evento> linEvenList = getWebServiceList(idusuario, fecha);				
			if (linEvenList.size()>0){
				for(Evento evento: linEvenList){				
					eventoDao.insert(evento);
				}
			}else{
				Log.d("cargarDetallePedido", "No hay datos");
			}
		
		}
		else{
			ArrayList<Evento> linEvenList = getWebServiceList(idusuario, fecha);			
			if (listaEventTemp.size()!=linEvenList.size()){
				eventoDao.deleteAll();
				for(Evento evento: linEvenList){				
					eventoDao.insert(evento);
				}
			}
			Log.d("cargarDetallePedido", "Ya se encuentra en la bd");
		}
		
		return eventoDao.loadAll().size();
	}

	private ArrayList<Evento> getWebServiceList(String idusuario, String fecha) {
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
		ArrayList<Evento> linEvenList = new ArrayList<Evento>();			
		linEvenList=gson.fromJson(sync.getResponse(), new TypeToken<List<Evento>>(){}.getType());
		return linEvenList;
	}

	public List<Evento> buscarEventos(String idusuario,String fecha) {
		return eventoDao.loadAll();
	}
    
    
    
}
