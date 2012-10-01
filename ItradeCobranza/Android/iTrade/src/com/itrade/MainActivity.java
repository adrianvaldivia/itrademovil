package com.itrade;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	//Los declaro aki porque pueden ser accesibles en todo el activity
	private EditText textView_Usuario;//input nombre usuario
	private EditText textView_Password;//input password
    private Button button_Ingresar;//boton ingresar
	private TextView loginErrorMsg;
	// JSON Response node names	
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//Aca se le indica que va a utilizar el layout main
        
        // Importing all assets like buttons, text fields
	    textView_Usuario  = (EditText) findViewById(R.id.loginUser);// Este es el input del Nombre de usuario
	    textView_Password  = (EditText) findViewById(R.id.loginPassword);// Este el input del password
	    button_Ingresar = (Button) findViewById(R.id.btnLogin); //El boton que dice incgresar
	    loginErrorMsg = (TextView) findViewById(R.id.login_error); //Mensaje de error que debería aparecer
	    
	    
	    //Login button Click Event. Listener para el boton
	    button_Ingresar.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		//Definido en el evento onlick	    		
				String user = textView_Usuario.getText().toString(); //Con esto se obtiene lo que se escriba en el input de nombre usuario
				String password = textView_Password.getText().toString();//Con esto se obtiene lo que se escriba en el input para el password				
				Log.d("Button", "Login"); //Este es un mensaje que sale en el logcat, cuanto es log.d sale un mensaje de color AZUL				
				//Declaro un nueo intent. Como primer parametro tiene este activity y como segundo el que yo quiero iniciar
				Intent intent = new Intent(MainActivity.this, LoginTask.class); 													
				intent.putExtra("username", user); //Estoy agregando como parámetro a "username" al intent 
				intent.putExtra("password", password); //Estoy agregando como parámetro a "password" al intent
				startActivity(intent); //Comienza el intent
				finish(); //Cierra esta actividad														
			}
	 	}); 
	    
    }
}