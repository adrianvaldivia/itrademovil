package com.alumnado.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alumnado.model.Alumno;
import com.alumnado.model.AlumnoDao;


import android.app.Activity;
import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.util.Log;

public class AsTaskSubirDatos extends AsyncTask<String, Void, String>
{		

    //green Dao
    private AlumnoDao alumnoDao;	
    //fin green dao
//    private Cursor cursorElementoLista;       
    
//	private ProgressDialog dialog;
	private Activity activity;
	private String response;
	private Alumno alumno;

	private int idAlumno;
	
	public AsTaskSubirDatos(Activity activ,Alumno alu) {		 
	        this.activity = activ;
	        this.alumno = alu;
//	        this.cursorElementoLista=cursorElementoLis;
//	        this.dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
//	            this.dialog.setMessage("Conectando");
//	            this.dialog.show();		    	
	}
	
	@Override
	protected String doInBackground(String... params) {
		String str="Exito";
		subirDatosAlumno();
	    return str;
	}
	
	@Override
	protected void onPostExecute(String result) {
//	     if (this.dialog.isShowing()) {
//	    	    try {
//	    	    	this.dialog.dismiss();
//	    	        dialog = null;
//	    	    } catch (Exception e) {
//	    	        // nothing
//	    	    }	    	 	           
//	     }
	     if (getResponse().compareTo("error")!=0){
		     leerDatos();
		     actualizarUI();
	     }
//	     db.close();
	}
	private void actualizarUI() {
//		 cursorElementoLista.requery();		
	}
	 
	private void subirDatosAlumno() {			               				
			
			/*****ws****/
			List<NameValuePair> param = new ArrayList<NameValuePair>();	
			param.add(new BasicNameValuePair("tag", "registraralumno"));
			param.add(new BasicNameValuePair("nombres", alumno.getNombres()));
			param.add(new BasicNameValuePair("apepaterno", alumno.getApePaterno()));
			param.add(new BasicNameValuePair("apematerno", alumno.getApeMaterno()));
			String route="/alumnos/";
			try{		       
				WBhelper helper = new WBhelper("http://10.0.2.2/webservicestekton/");
				
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
		Gson gson = new Gson();
		
//		idAlumno	=	gson.fromJson(getResponse(), new TypeToken<Integer>(){}.getType());
		Log.e("log_tag", "no se cayo5" );
	}	

	private String getResponse() {
		return response;
	}

	private void setResponse(String response) {
		this.response = response;
	}	 	
	}