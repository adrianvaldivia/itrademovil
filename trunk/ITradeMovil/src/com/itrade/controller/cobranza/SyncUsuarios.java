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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;

public class SyncUsuarios {

	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    
    private UsuarioDao usuarioDao;    
    private Usuario usuarioLogged;
    
    private String username;
    private String password;
    
    
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
    
    public SyncUsuarios(Activity activ) {
		super();	
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        sync=new Syncronizar(activity);
        gson = new Gson();  
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
    
    
    public int cargarUsuarios(String username, String password){
		//Consulta al Webservice		
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("username", username));
		param.add(new BasicNameValuePair("password", password));	
		sync.conexion(param,"/ws/login/get_user_by_username_password/");
		try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}	    	  
		ArrayList<Usuario> listausuario = new ArrayList<Usuario>();			 
		listausuario=gson.fromJson(sync.getResponse(), new TypeToken<List<Usuario>>(){}.getType());	  
		usuarioDao.deleteAll();
		for(Usuario usuario: listausuario ){
			usuario.setNombreReal(usuario.getApePaterno()+" "+usuario.getApeMaterno()+" "+usuario.getNombre());
			usuarioDao.insert(usuario);
		}
		return listausuario.size();							   			
	}    
    public void closeDB(){
		db.close();
	}
    
    public boolean syncSqliteToBD(){
		return true;
	}
    
    
    public Usuario getUsuarioLogged() {
    	List<Usuario> listaUsuario=usuarioDao.loadAll();
		return listaUsuario.get(0);
	}

	public Integer syncBDToSqlite(){    	
		Integer registros=0;
		//Compruebo si tengo conexión a internet;    			
		if (networkAvailable()){
    		//Primero intentar sincronizar la BD SQLite antes de limpiar la BD
			if (syncSqliteToBD()){			    	
	    		//Si tengo conexion llamo al webservice				
	    		//Si obtuve todos los registros limpio la base de datos SQLite
				registros +=cargarUsuarios(username,password);				
			}else{
    			//Enviar mensaje que primero debe obtener cobertura, de otra manera se perderan los pedidos registrados				
			}    							        		    
		}else{
    		//Obtener los clientes del vendedor 
			Log.d("TAAAAAG","NO hay internet");
			if (password=="1234"){
				registros=usuarioDao.loadAll().size();
			}
		}      	    	    
    	return registros;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}    
	
	
}

