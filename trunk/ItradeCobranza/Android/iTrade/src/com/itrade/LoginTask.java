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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	final Context context = this;
	//Declarando elementos
	private TextView textView;//El textview que se va mostrar
	private TextView textPedido;//El textview del pedido que se va solicitar
	private Button button_Aceptar;//boton de aceptar
	private String direccion="http://10.0.2.2/";//la direccion de localhost para el consumo de webservices 
	private ArrayList<Login> loginList = new ArrayList<Login>();//Lista de ayuda para contener los elementos consultados
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here		
		setContentView(R.layout.home);//utiliza el layout home   
        Intent i = getIntent(); //Se obtiene el intent
        // se obtienen los parametros que se pasaron como extras en el intent anterior
        String userStr = (String)i.getSerializableExtra("username"); //Se obtiene el nombre de usuario
        String passStr = (String)i.getSerializableExtra("password"); //Se obtiene el password    
        
        textView = (TextView) findViewById(R.id.textView1);// Se obtiene el textview de home
        textPedido = (TextView) findViewById(R.id.txtVwPedido);// textview de pedido
        button_Aceptar= (Button)findViewById(R.id.Aceptar);//boton Aceptar
        
      //Se llama a un método que a su vez ejecutará el hilo asyncrono
        executeLoginTask(userStr, passStr);//le paso los parámetros user y password
        
        button_Aceptar.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		//Definido en el evento onlick    		
				String pedido = textPedido.getText().toString(); //Con esto se obtiene lo que se escriba en el input de nombre usuario
				Log.d("Button", "Pedido"); //Este es un mensaje que sale en el logcat, cuanto es log.d sale un mensaje de color AZUL				
				//Declaro un nueo intent. Como primer parametro tiene este activity y como segundo el que yo quiero iniciar
				Intent intent = new Intent(LoginTask.this, PaymentTask.class); 													
				intent.putExtra("pedido", pedido); //Estoy agregando como parámetro a "pedido" en el intent 
				Log.d("codigo de pedido !!=",pedido); 
				startActivity(intent); //Comienza el intent
				//finish(); //Cierra esta actividad														
			}
	 	});
        
	}
	public void executeLoginTask(String user, String password) {
		LoginBackGroundTask task = new LoginBackGroundTask();//declaro una tarea como una nueva clase
	    task.execute(new String[] { user,password });// le digo que se ejecute y le paso los parámetros user y pass

	}
	/*CLASE ASINCRONA*/
	private class LoginBackGroundTask extends AsyncTask<String, Void, String>{
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
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 
					// set title
				alertDialogBuilder.setTitle("Usuario incorrecto");		 
					// set dialog message
				alertDialogBuilder
						.setMessage("El usuario no es válido!")
						.setCancelable(false)						
						.setNegativeButton("Aceptar",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing								
								dialog.cancel();
								finish();
							}
						});
		 
						// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();		 
						// show it
				alertDialog.show();
			}

		}
			
	}
		
	

}
