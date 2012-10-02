package com.itrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.jsonParser.WBhelper;
import com.itrade.models.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PaymentTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */	
	
	private TextView textView;
	private String direccion="http://10.0.2.2/"; 
	private ArrayList<Login> loginList = new ArrayList<Login>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here		
		setContentView(R.layout.cobrar_pedido);//utiliza el layout home   
        Intent i = getIntent(); //Se obtiene el intent
        // se obtienen los parametros que se pasaron como extras en el intent anterior
        String userStr = (String)i.getSerializableExtra("username"); //Se obtiene el nombre de usuario
        String passStr = (String)i.getSerializableExtra("password"); //Se obtiene el password       	
        textView = (TextView) findViewById(R.id.textView1);// Se obtiene el textview de home        
      //Se llama a un método que a su vez ejecutará el hilo asyncrono
        executeLoginTask(userStr, passStr);//le paso los parámetros user y password
	}
	public void executeLoginTask(String user, String password) {
		PaymentBackGroundTask task = new PaymentBackGroundTask();//declaro una tarea como una nueva clase
	    task.execute(new String[] { user,password });// le digo que se ejecute y le paso los parámetros user y pass

	}
	/*CLASE ASINCRONA*/
	private class PaymentBackGroundTask extends AsyncTask<String, Void, String>{
		@Override
		/*PRIMERO SE METE AKI*/
		protected String doInBackground(String... parameters) {//primero se ejecutará esto y en parameters están user y password
			// TODO Auto-generated method stub
			String response="";
			//Llamada al webservice
			WBhelper helper = new WBhelper(direccion);//Esta es una clase que yo cree que me ayuda con los webservices				
			List<NameValuePair> params = new ArrayList<NameValuePair>();// declaro un arreglo para pasarle parámetros a mi webservice			
			params.add(new BasicNameValuePair("username", parameters[0]));// Declaro un parámetro "username" que tiene el valor user que le pasé como atributo
			params.add(new BasicNameValuePair("password", parameters[1]));// mismo caso para el password
			//Obteniendo el response
			String responseBody=helper.obtainResponse("itrade/Login/get_user_by_username_password/",params);// hago la llamada al webservice			
			Log.d("response login task=",responseBody);//Escribo en el logcat para saber que cosa es lo que esta llegando							
			return responseBody;//retorno todo el string de respuesta
		}				
		@Override
		/*SEGUNDO SE METE AKI*/
		protected void onPostExecute(String result) {
			Log.d("POST!!!!!!=",result); //lo que yo retorne del bakground lo obtengo en la variable result, por siacaso lo escribo en logcat con esta linea						
			
			Gson gson = new Gson();//un helper para el parseo del texto a objeto 		
			//Esta linea automaticamente le das el texto y te convierte a objeto, lo regresa en una lista de objetos
			loginList = gson.fromJson(result, new TypeToken<List<Login>>(){}.getType());//Es necesario que la clase login tenga los mismos campos del webservice un ejemplo es el siguiente
			//[{"Username":"adg","Nombre":"Joel","ApePaterno":"Prada","ApeMaterno":"Licla"}]			
			Integer inte=loginList.size();//Si es que el parseo lo hizo bien y hubo algun resultado el tamaño de la lista será mayor que 0
			Log.d("Cantidad de contactos!!!!!!!=",inte.toString());// por siacaso escribo el resultado en el logcat
			if (inte>0){//hago la validación		
				String cont = loginList.get(0).toString();// obtengo el elemento de la lista y lo transformo a string
				textView.setText(cont);// seteo el valor en la vista
			}else{
				//loginErrorMsg.setText("Incorrect username/password");
				textView.setText("No existe usuario con ese nombre");// No existe o hubo un error.
				//Aca es donde pondremos la alerta de que no existe usuario o algo similar. Código de henry				
			}
			
		}
		
	}
}
