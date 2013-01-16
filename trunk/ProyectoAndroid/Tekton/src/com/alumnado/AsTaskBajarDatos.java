package com.alumnado;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alumnado.model.Alumno;
import com.alumnado.model.AlumnoDao;
import com.alumnado.model.DaoMaster;
import com.alumnado.model.DaoSession;


import android.app.Activity;
import android.app.ProgressDialog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class AsTaskBajarDatos extends AsyncTask<String, Void, String>
{		

    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AlumnoDao alumnoDao;
    //fin green dao
//    private Cursor cursorElementoLista;       
    
	private ProgressDialog dialog;
	private Activity activity;
	private String response;
	
	public AsTaskBajarDatos(Activity activ) {		 
	        this.activity = activ;		
//	        this.cursorElementoLista=cursorElementoLis;
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
	     if (getResponse().compareTo("error")!=0){
		     leerDatos();
		     actualizarUI();
	     }
//	     db.close();
	}
	private void actualizarUI() {
//		 cursorElementoLista.requery();		
	}
	 
	private void cargarBaseLocal() {
			sincronizarBaseSubida();			               				
			
			/*****ws****/
			List<NameValuePair> param = new ArrayList<NameValuePair>();	
			param.add(new BasicNameValuePair("tag", "getAllAlumnos"));
			String route="/alumnos/";
			try{		       
				WBhelper helper = new WBhelper("http://10.0.2.2/webservicestekton/");
//				WBhelper helper = new WBhelper("http://192.168.0.2/webservicestekton/");
				
				String responseBody=helper.obtainResponse(route,param);			
				
				Log.e("log_tag", responseBody );
				if (responseBody!="error"){					
					setResponse(responseBody);
				}else{
					Log.e("log_tag", "Error in webservice");
					setResponse("error");
				}				
			}catch(Exception e){
			     Log.e("log_tag", "Error in http connection "+e.toString());
//			     pd.dismiss();
			     setResponse("error");
			}
					
			
		    Log.d("TAG","LLEGA SEGUNDO AKI");		    
	}
	private void leerDatos() {
		// TODO Auto-generated method stub
		List<Alumno> listaAlumno = new ArrayList<Alumno>();
		Gson gson = new Gson();
		
		Log.e("log_tag", "se cayo5" );
		listaAlumno	=	gson.fromJson(getResponse(), new TypeToken<List<Alumno>>(){}.getType());
        MyApplication mApplication = (MyApplication)activity.getApplicationContext();
        alumnoDao=mApplication.getAlumnoDao();
        alumnoDao.deleteAll();
		for(int i=0;i<listaAlumno.size();i++){
			alumnoDao.insert(listaAlumno.get(i));
		}
	}
	 
	private void sincronizarBaseSubida() {
		}

	private String getResponse() {
		return response;
	}

	private void setResponse(String response) {
		this.response = response;
	}	 	
	}