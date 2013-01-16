package com.tekton;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tekton.model.Alumno;
import com.tekton.model.DaoMaster;
import com.tekton.model.DaoSession;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class AsTaskSubirDatos extends AsyncTask<String, Void, String>
{		

    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    //fin green dao
    private Cursor cursorElementoLista;       
    
	private ProgressDialog dialog;
	private Activity activity;
	
	public AsTaskSubirDatos(Activity activ,Cursor cursorElementoLis) {		 
	        this.activity = activ;		
	        this.cursorElementoLista=cursorElementoLis;
	        this.dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
	            this.dialog.setMessage("Conectando");
	            this.dialog.show();		    	
	}
	
	@Override
	protected String doInBackground(String... params) {
		String str="Exito";
		cargarBaseLocal();
	    return str;
	}
	
	@Override
	protected void onPostExecute(String result) {
	     if (this.dialog.isShowing()) {
	    	    try {
	    	    	this.dialog.dismiss();
	    	        dialog = null;
	    	    } catch (Exception e) {
	    	        // nothing
	    	    }	    	 	           
	     }	     
	     actualizarUI();
	     db.close();
	}
	private void actualizarUI() {
		 cursorElementoLista.requery();		
	}
	 
	private void cargarBaseLocal() {
			sincronizarBaseSubida();
			List<Alumno> listaAlumno = new ArrayList<Alumno>();			               				
			
			/*****ws****/
			
			
//			Syncronizar sync = new Syncronizar(window);
			List<NameValuePair> param = new ArrayList<NameValuePair>();	
//			param.add(new BasicNameValuePair("idalumno", idalumno));
			
			String route="/ws/clientes/get_alumnos_by_id_p/";
			
//			sync.conexion(param,route);
			
//			try {
////				sync.getHilo().join();
//				Log.d("TAG","LLEGA PRIMERO AKI");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		    Log.d("TAG","LLEGA SEGUNDO AKI");
		    Gson gson = new Gson();
								
			Log.e("log_tag", "se cayo5" );
//			listaAlumno	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Alumno>>(){}.getType());
	}
	 
	 private void sincronizarBaseSubida() {
		}	 	
	}