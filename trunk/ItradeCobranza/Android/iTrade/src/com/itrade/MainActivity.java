package com.itrade;
import com.itrade.models.Contact;
import com.itrade.models.Login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.jsonParser.*;

public class MainActivity extends Activity {
	
	private EditText textView_Usuario;
	private EditText textView_Password;
    private Button button_Ingresar;
	private TextView loginErrorMsg;
	private String direccion="http://10.0.2.2/";
	private ArrayList<Login> loginList = new ArrayList<Login>();
	// JSON Response node names	
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Importing all assets like buttons, text fields
	    textView_Usuario  = (EditText) findViewById(R.id.loginUser);
	    textView_Password  = (EditText) findViewById(R.id.loginPassword);
	    button_Ingresar = (Button) findViewById(R.id.btnLogin);
	    loginErrorMsg = (TextView) findViewById(R.id.login_error);
	    
	    
	    //Login button Click Event
	    button_Ingresar.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		//On click
				String user = textView_Usuario.getText().toString();
				String password = textView_Password.getText().toString();				
				Log.d("Button", "Login");
				//JSONObject json = userFunction.loginUser(user, password);
				WBhelper helper = new WBhelper(direccion);				
				List<NameValuePair> params = new ArrayList<NameValuePair>();			
				params.add(new BasicNameValuePair("username", user));
				params.add(new BasicNameValuePair("password", password));
				//Obteniendo el response
				String responseBody=helper.obtainResponse("itrade/Login/get_user_by_username_password/",params);
				Log.d("response=",responseBody);
				if (responseBody!="error"){
					Gson gson = new Gson();	
					loginList = gson.fromJson(responseBody, new TypeToken<List<Login>>(){}.getType());					
					Integer inte=loginList.size();
					Log.d("Cantidad de contactos!!!!!!!=",inte.toString());						
					if (inte>0){						
						//AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()); 
						Intent intent = new Intent(MainActivity.this, Home.class);							
						// Close all views before launching Dashboard
						intent.putExtra("User_Login", loginList.get(0)); 
						startActivity(intent);							
						// Close Login Screen
						finish();
					}else{
						loginErrorMsg.setText("Incorrect username/password");
					}
							    		   														
				}
				
				/*
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						loginErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){						
							JSONObject json_user = json.getJSONObject("user");						
							
				    		Intent intent = new Intent(MainActivity.this, Home.class);	
							
							startActivity(intent);								
							finish();
						}else{							
							loginErrorMsg.setText("Incorrect username/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    		*/
				
				
			}
	 	}); 
	    
    }
}