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
    		//Primero intentar sincronizar la BD SQLite antes de limpiar la BD
			//if (syncSqliteToBD()){
			try{
	    		//Si tengo conexion llamo al webservice				
	    		//Si obtuve todos los registros limpio la base de datos SQLite
				
				Log.d("SqlLite","Entro a cargar");
				//registros +=cargarPedido(idpedido);
				registros +=cargarEventos(idusuario,fecha);
				//WEB SERVICE SAQUE TODO EL DETALLE X PEDIDO
			}catch(Exception e){
			//}else{
				Log.d("SqlLite","No tiene cobertura");
				Log.d("Error", "SE CAYO" + " "+e.getMessage());
    			//Enviar mensaje que primero debe obtener cobertura, de otra manera se perderan los pedidos registrados				
			//}    	
			}
		}
		else{
			Toast.makeText(context, "No Hay Conexion a Internet", Toast.LENGTH_LONG).show();
		}
		
		return registros;
	}

	private Integer cargarEventos(String idusuario,String fecha) {
		// TODO Auto-generated method stub
		Log.d("cargarDetallePedido", "LLEGO");
		Log.d("LOG", idusuario);
		/*
		List<Evento> listaEventTemp=eventoDao.queryBuilder()
				.where(Properties.IdPedido.eq(idusuario))
				.where(Properties.IdPedido.eq(fecha))
				.list();
		*/
		Log.d("AHHHHHHH", "entra shit!!!87930189203");
		if (/*listaPedTemp.isEmpty() || listaPedTemp.size()==0*/ true){
					
			Log.d("AHHHHHHH", "entra shit!!! 1");
			List<NameValuePair> param = new ArrayList<NameValuePair>();								
			param.add(new BasicNameValuePair("idusuario", idusuario));
			param.add(new BasicNameValuePair("fecha", fecha));			
			String route="/ws/eventos/get_eventos_idusuario_month/";
			Log.d("AHHHHHHH", "entra shit!!! 2");
			
					
		    sync.conexion(param,route);
		    try {
				sync.getHilo().join();
				Log.d("TAG","LLEGA PRIMERO AKI");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    Log.d("AHHHHHHH", "entra shit237812937128973891");
			ArrayList<Evento> linEvenList = new ArrayList<Evento>();			
			linEvenList=gson.fromJson(sync.getResponse(), new TypeToken<List<Evento>>(){}.getType());	
			Log.d("AHHHHHH7d8s9a7d8sa9H", sync.getResponse());
			if (linEvenList.size()>0){
				for(Evento evento: linEvenList){				
					//adapter.addItem(ped);
					//iNSERT EN LA bd
					eventoDao.insert(evento);
				}
			}else{
				Log.d("cargarDetallePedido", "No hay datos");
			}
		
		}
		else{
			Log.d("cargarDetallePedido", "Ya se encuentra en la bd");
		}
		
		return eventoDao.loadAll().size();
	}

	public List<Evento> buscarEventos() {
		// TODO Auto-generated method stub
		/*
		List<PedidoLinea> listaPedTemp=pedidoLineaDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		return listaPedTemp;
		*/
		return eventoDao.loadAll();
	}
    
    
    
}
