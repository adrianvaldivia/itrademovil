package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.pedidos.R;
import com.itrade.jsonParser.WBhelper;
import com.itrade.modelo.Login;
import com.itrade.modelo.Pedido;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class PaymentTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */	
	final Context context = this;
	private TextView txtVwPedido;
	private TextView txtVwCliente;
	private TextView txtVwMonto;
	private Button btnPagar;//boton ingresar
	private Spinner spinTipo;
	private String direccion="http://10.0.2.2/"; 
	private ArrayList<Pedido> requestList = new ArrayList<Pedido>();
	private ArrayList<Pedido> payReqtList = new ArrayList<Pedido>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here		
		setContentView(R.layout.cobrar_pedido);//utiliza el layout home   
        Intent i = getIntent(); //Se obtiene el intent
        // se obtienen los parametros que se pasaron como extras en el intent anterior
        String pedido = (String)i.getSerializableExtra("pedido"); //Se obtiene el nombre de usuario      
        //Elementos del XML DE COBRANZA       
        txtVwPedido = (TextView) findViewById(R.id.txtVwPedido);//Pedido
        txtVwCliente = (TextView) findViewById(R.id.txtVwCliente);//CLIENTE
        txtVwMonto = (TextView) findViewById(R.id.txtVwMonto);//monto
        spinTipo = (Spinner)findViewById(R.id.spin);
        btnPagar = (Button)findViewById(R.id.btnPagar);
        //Creamos el adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipoPago,android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //Le indicamos al spinner el adaptador a usar
        spinTipo.setAdapter(adapter);        
        //Se llama a un método que a su vez ejecutará el hilo asyncrono
        executePaymentTask(pedido);//le paso los parámetros user y password
        
        btnPagar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	    		paymentRequestTask();												
			}
	 	});
        
	}
	
	public void paymentRequestTask(){
		PayRequest task = new PayRequest();//declaro una tarea como una nueva clase
	    task.execute(new String[] { requestList.get(0).getIdPedido().toString() });//Solo le pasamos el IDPEDIDO
	}
	
	private class PayRequest extends AsyncTask<String, Void, String>{
		protected String doInBackground(String... parameters) {//primero se ejecutará esto y en parameters están user y password
			// TODO Auto-generated method stub
			String response="";
			//Llamada al webservice
			WBhelper helper = new WBhelper(direccion);//Esta es una clase que yo cree que me ayuda con los webservices				
			List<NameValuePair> params = new ArrayList<NameValuePair>();// declaro un arreglo para pasarle parámetros a mi webservice			
			Log.d("idpedido=",parameters[0]);
			params.add(new BasicNameValuePair("idpedido", parameters[0]));// Declaro un parámetro "username" que tiene el valor user que le pasé como atributo
			//Obteniendo el response
			String responseBody=helper.obtainResponse("itrade/Pedido/pagar_pedido/",params);// hago la llamada al webservice			
			Log.d("response PAGAR PEDIDO Task=",responseBody);//Escribo en el logcat para saber que cosa es lo que esta llegando							
			return responseBody;//retorno todo el string de respuesta
		}				
		@Override
		/*SEGUNDO SE METE AKI*/
		protected void onPostExecute(String result) {
			Log.d("POST payment=",result); //lo que yo retorne del bakground lo obtengo en la variable result, por siacaso lo escribo en logcat con esta linea									
			Gson gson = new Gson();//un helper para el parseo del texto a objeto 		
			//Esta linea automaticamente le das el texto y te convierte a objeto, lo regresa en una lista de objetos																
			payReqtList = gson.fromJson(result, new TypeToken<List<Pedido>>(){}.getType());//Es necesario que la clase login tenga los mismos campos del webservice un ejemplo es el siguiente
			//[{"Username":"adg","Nombre":"Joel","ApePaterno":"Prada","ApeMaterno":"Licla"}]			
			Integer inte=payReqtList.size();//Si es que el parseo lo hizo bien y hubo algun resultado el tamaño de la lista será mayor que 0
			Log.d("Cantidad de pedidos!!!!!!!=",inte.toString());// por siacaso escribo el resultado en el logcat
			String titulo="";
			String mensaje=""; 
			if (inte>0){//hago la validación		
				Pedido pedido = payReqtList.get(0);// obtengo el elemento de la lista y lo transformo a string
				titulo="Pago exitoso"; 
				mensaje="Se registro el pago del pedido Nro: "+pedido.getIdPedido().toString()+
						" La fecha del registro fue: "+pedido.getFechaCobranza(); 
			}else{
				//loginErrorMsg.setText("Incorrect username/password");
				titulo="No se registro el pago";
				mensaje="No se realizo el registro del pago"; 							
			}
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 
			// set title
			alertDialogBuilder.setTitle(titulo);		 
				// set dialog message
			alertDialogBuilder
					.setMessage(mensaje)
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
	
	public void executePaymentTask(String idpedido) {
		PaymentBackGroundTask task = new PaymentBackGroundTask();//declaro una tarea como una nueva clase
	    task.execute(new String[] { idpedido });// le digo que se ejecute y le paso los parámetros user y pass

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
			Log.d("idpedido=",parameters[0]);
			params.add(new BasicNameValuePair("idpedido", parameters[0]));// Declaro un parámetro "username" que tiene el valor user que le pasé como atributo
			//Obteniendo el response
			String responseBody=helper.obtainResponse("itrade/Pedido/consultar_pedido/",params);// hago la llamada al webservice			
			Log.d("response pAYMENT Task=",responseBody);//Escribo en el logcat para saber que cosa es lo que esta llegando							
			return responseBody;//retorno todo el string de respuesta
		}				
		@Override
		/*SEGUNDO SE METE AKI*/
		protected void onPostExecute(String result) {
			Log.d("POST payment=",result); //lo que yo retorne del bakground lo obtengo en la variable result, por siacaso lo escribo en logcat con esta linea						
			
			Gson gson = new Gson();//un helper para el parseo del texto a objeto 		
			//Esta linea automaticamente le das el texto y te convierte a objeto, lo regresa en una lista de objetos
			requestList = gson.fromJson(result, new TypeToken<List<Pedido>>(){}.getType());//Es necesario que la clase login tenga los mismos campos del webservice un ejemplo es el siguiente
			//[{"Username":"adg","Nombre":"Joel","ApePaterno":"Prada","ApeMaterno":"Licla"}]			
			Integer inte=requestList.size();//Si es que el parseo lo hizo bien y hubo algun resultado el tamaño de la lista será mayor que 0
			Log.d("Cantidad de pedidos!!!!!!!=",inte.toString());// por siacaso escribo el resultado en el logcat
			if (inte>0){//hago la validación		
				Pedido pedido = requestList.get(0);// obtengo el elemento de la lista y lo transformo a string
				txtVwPedido.setText("Pedido#"+pedido.getIdPedido());// seteo el valor en la vista				
		        txtVwCliente.setText(pedido.getApePaterno()+" "+pedido.getApeMaterno()+" "+pedido.getNombre());// seteo el valor en la vista
		        txtVwMonto.setText("S/."+pedido.getMontoTotal().toString());
			}else{
				//loginErrorMsg.setText("Incorrect username/password");
				txtVwPedido.setText("No existe usuario con ese nombre");// No existe o hubo un error.
				//Aca es donde pondremos la alerta de que no existe usuario o algo similar. Código de henry				
			}
			
		}
		
	}
}
