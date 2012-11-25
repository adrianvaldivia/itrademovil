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
import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;

public class SyncContactos {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ContactoDao contactoDao;    
    private List<Contacto> listaContacto;   
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
	
    public SyncContactos(Activity activ){
    	super();
    	listaContacto=new ArrayList<Contacto>();
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        contactoDao = daoSession.getContactoDao();
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

	public Integer syncBDToSqlite(String idubigeo) {
		// TODO Auto-generated method stub
		Integer registros=0;		
		if (networkAvailable()){
			try{
				Log.d("SqlLite","Entro a cargar");
				registros +=cargarContactos(idubigeo);
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

	public Integer cargarContactos(String idubigeo) {
		// TODO Auto-generated method stub				
		List<Contacto> listaContactTemp=contactoDao.loadAll();
		if (networkAvailable()){
			if (listaContactTemp.isEmpty() || listaContactTemp.size()==0){							
				ArrayList<Contacto> linContactoList = getWebServiceList(idubigeo);				
				if (linContactoList.size()>0){
					contactoDao.deleteAll();
					for(Contacto contacto: linContactoList){				
						contactoDao.insert(contacto);
					}			
				}else{
					Log.d("cargarContactos", "No hay datos");
				}		
			}
			else{
				Log.d("ACTUALIZAR CONTACTOS", "Ya se encuentra en la bd");
				ArrayList<Contacto> linContactoList = getWebServiceList(idubigeo);
				if (listaContactTemp.size()!=linContactoList.size()){
					contactoDao.deleteAll();
					for(Contacto contacto: linContactoList){				
						contactoDao.insert(contacto);
					}
				}
			}		
		}		
		return contactoDao.loadAll().size();
	}

	private ArrayList<Contacto> getWebServiceList(String idubigeo) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idubigeo", idubigeo));				
		String route="/ws/pedido/get_contactos_by_user_id/";
		sync.conexion(param,route);
		try {
			sync.getHilo().join();
			Log.d("TAG","LLEGA PRIMERO AKI");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    		   
		ArrayList<Contacto> linContactoList = new ArrayList<Contacto>();			
		linContactoList=gson.fromJson(sync.getResponse(), new TypeToken<List<Contacto>>(){}.getType());
		return linContactoList;
	}

	public List<Contacto> buscarContactos() {		
		return contactoDao.loadAll();
	}          
}
