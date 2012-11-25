package com.itrade.jsonParser;

import com.itrade.db.DAOUsuario;
import com.itrade.model.Usuario;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsTaskLogin extends AsyncTask<String, Void, String>
{		
    private DAOUsuario daoUsu= null;
              
	 private ProgressDialog dialog;
	 private Activity activity;		 
	 String nusuario="";
	 String pass="";
	 Usuario usuario;
	 AsTaskCargarDatos taskCargar;

	 public AsTaskLogin(Activity activ,String nombreUsu
			 				,String pw) {		 
	        this.activity = activ;		
	        this.dialog = new ProgressDialog(activity);	       
	        this.nusuario=nombreUsu;
	        this.pass=pw;
	    }

	    @Override
	     protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
	            this.dialog.setMessage("Conectando");
	            this.dialog.show();
	            daoUsu = new DAOUsuario(activity);
	        }



	@Override
	protected String doInBackground(String... params) {
		String str="Hola";
		////////////////////////////////////
		usuario = daoUsu.confirmarLogin(nusuario,pass);
	    return str;
	}



	 @Override
	protected void onPostExecute(String result) {
	     if (this.dialog.isShowing()) {
	           this.dialog.dismiss();
	     }
	     if (usuario != null){
//	     if (true){
	    	 	usuario.setPassword(pass);
//	    	 	Usuario usuTemp= new Usuario();
//	    	 	usuTemp.setNombre("lalala");
//	    	 	long l=0;
//	    	 	usuTemp.setIdUsuario(l+6);
//	    	 	usuTemp.setIdPerfil(2);
//	    	 	usuTemp.setUsername("chichan");
//	    	 	usuTemp.setPassword(pass);
	    	 	
	    		
	    		taskCargar= new AsTaskCargarDatos(activity,usuario);
				taskCargar.execute();
	    							   
	    	}
	    	else{
	    		Toast.makeText(activity, "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
	    	}	
	}
	
	}