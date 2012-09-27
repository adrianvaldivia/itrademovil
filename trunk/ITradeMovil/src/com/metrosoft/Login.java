package com.metrosoft;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.metrosoft.db.DAOUsuario;
import com.metrosoft.model.DatosEmpleado;

public class Login extends Activity {
	
    private DAOUsuario daoUsu= null;

	private EditText textView_Usuario;
	private EditText textView_Password;
    private Button button_Ingresar;
 
    public DatosEmpleado datEmpleado = null;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.login2);
	    
	    daoUsu = new DAOUsuario();
    
	    
	    textView_Usuario  = (EditText) findViewById(R.id.autoCompleteTextView1);
	    textView_Password  = (EditText) findViewById(R.id.editText1);
	    button_Ingresar = (Button) findViewById(R.id.button1);
	        
	    //M�todo click etn Boton Ingresar

	    button_Ingresar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				String us = textView_Usuario.getText().toString();
				
				String pw = textView_Password.getText().toString();
				
				int resul= daoUsu.confirmarLogin(us, pw);
				
				if (resul!=-1) {
			    		
//				    datEmpleado = daoUsu.buscarDatosEmpleado(resul);
				    
					Intent intent = new Intent(Login.this, MenuLista.class);
					
					String nombre= "hardcoded";//datEmpleado.getNombre();
					String apellidos="hardcoded";//datEmpleado.getApellidos();

					
					//Toast.makeText(getBaseContext(), "Ruta: "+idruta, Toast.LENGTH_SHORT).show();
					//Toast.makeText(getBaseContext(), "Unidad: "+idunidad, Toast.LENGTH_SHORT).show();
					
					intent.putExtra("idempleado", resul);
					intent.putExtra("nombre", nombre);
					intent.putExtra("apellidos", apellidos);
					
					startActivity(intent);
					
					textView_Password.setText("");
				}
				else{
					Toast.makeText(getBaseContext(), "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
				}
			}
	 	});  
	    
	    
	    textView_Usuario.setOnKeyListener(new OnKeyListener() {
	    	
		 public boolean onKey(View v, int keyCode, KeyEvent event) {
			 if (keyCode == KeyEvent.KEYCODE_APOSTROPHE){
				 textView_Usuario.getText().subSequence(0, textView_Usuario.length());
		         return true;
		     }
		     return false;		     
		     }
		  });
	    
	    textView_Password.setOnKeyListener(new OnKeyListener() {
	    	
			 public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if (keyCode == KeyEvent.KEYCODE_APOSTROPHE){
					 textView_Password.getText().subSequence(0, textView_Password.length());
			         return true;
			     }
			     return false;		     
			     }
			  });
	    
	 }
    
}