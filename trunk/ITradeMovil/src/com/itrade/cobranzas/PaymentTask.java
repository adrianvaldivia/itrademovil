package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.jsonParser.WBhelper;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */	
	final Context context = this;
	private TextView txtVwPedido;
	private TextView txtVwCliente;
	private TextView txtVwMonto;
	private TextView txtVwTransaccion;
	private EditText editNumVoucher;
	private Button btnPagar;
	private Button btnDetalle;
	private Button btnRuta;
	private ImageView btnClientes;
	private Spinner spinTipo;
	private String direccion="http://10.0.2.2/"; 
	private String idpedido;
	private String idcliente;
	private String idempleado;
	private Pedido pedidoSelected;
	private Cliente clienteSelected;
	private ArrayList<Pedido> requestList = new ArrayList<Pedido>();
	private ArrayList<Pedido> payReqtList = new ArrayList<Pedido>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cobrar_pedido);		
		
		txtVwPedido = (TextView) findViewById(R.id.txtVwPedido);//Pedido
        txtVwCliente = (TextView) findViewById(R.id.txtVwCliente);//CLIENTE
        txtVwMonto = (TextView) findViewById(R.id.txtVwMonto);//monto
        txtVwTransaccion = (TextView)findViewById(R.id.textVwTransaccion);
        spinTipo = (Spinner)findViewById(R.id.spin);
        btnPagar = (Button)findViewById(R.id.btnPagar);
        btnDetalle = (Button)findViewById(R.id.btnDetalle);        
        editNumVoucher = (EditText)findViewById(R.id.editTxtTransaccion);
        
		getParamsIntent();
        fillValues();          
        setValues(); 
				    
        //Elementos del XML DE COBRANZA       
       
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipoPago,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinTipo.setAdapter(adapter);                       
        spinTipo.setOnItemSelectedListener(new OnItemSelectedListener() {                        
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "item="+arg1+"item2"+arg2,Toast.LENGTH_LONG).show();
				if(arg2==0){
					//Ocultar input
					txtVwTransaccion.setVisibility(View.INVISIBLE);
					editNumVoucher.setVisibility(View.INVISIBLE);
					editNumVoucher.setText("");
				}else{
					//Mostrar input y textviewView.VISIBLE
					txtVwTransaccion.setVisibility(View.VISIBLE);
					editNumVoucher.setVisibility(View.VISIBLE);
				}
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				//Ocultar Inicialmente
				txtVwTransaccion.setVisibility(View.INVISIBLE);
				editNumVoucher.setVisibility(View.INVISIBLE);
				editNumVoucher.setText("");
			}

        });
        btnPagar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Syncronizar sync = new Syncronizar(PaymentTask.this);
				List<NameValuePair> param = new ArrayList<NameValuePair>();	
				String numVoucher= editNumVoucher.getText().toString();
				param.add(new BasicNameValuePair("idpedido", idpedido));
				param.add(new BasicNameValuePair("montocobrado", pedidoSelected.getMontoTotalPedido().toString()));			
				if (spinTipo.getSelectedItem().toString().toLowerCase()!="efectivo"){
					param.add(new BasicNameValuePair("numVoucher", numVoucher));
				}else{
					param.add(new BasicNameValuePair("numVoucher", ""));
				}
				String route2="/ws/pedido/pagar_pedido/";
				sync.conexion(param,route2);
				try {
					sync.getHilo().join();			
				} catch (InterruptedException e) {
					  // TODO Auto-generated catch block
					e.printStackTrace();
				}	    	  
				Gson gson = new Gson();  
				ArrayList<Pedido> listaPayments = new ArrayList<Pedido>();
				listaPayments = gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());
				String titulo="";
				String mensaje=""; 
				if (listaPayments.size()>0){
					Pedido pedido = listaPayments.get(0);
					titulo="Pago exitoso"; 
					mensaje="Se registro el pago del pedido Nro: "+pedido.getIdPedido().toString()+
							" La fecha del registro fue: "+pedido.getFechaCobranza(); 
				}else{
					
					titulo="No se registro el pago";
					mensaje="No se realizo el registro del pago"; 							
				}				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 				
				alertDialogBuilder.setTitle(titulo);		 			
				alertDialogBuilder
						.setMessage(mensaje)
						.setCancelable(false)						
						.setNegativeButton("Aceptar",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {														
								dialog.cancel();
								//finish();
								Intent intent = new Intent(PaymentTask.this, ClientesListTask.class); 													
								intent.putExtra("idpedido", idpedido);
								intent.putExtra("idcliente", idcliente);	
								intent.putExtra("idempleado", idempleado);
								startActivity(intent);
							}
				});		
				AlertDialog alertDialog = alertDialogBuilder.create();		 
				alertDialog.show();	
			}
	 	});
        btnDetalle.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PaymentTask.this, RequestDetailTask.class); 													
				intent.putExtra("idpedido", idpedido);
				intent.putExtra("idcliente", idcliente);
				intent.putExtra("idempleado", idempleado);
				startActivity(intent);
			}
		});
        btnClientes= (ImageView)findViewById(R.id.ImageButton04);
        btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PaymentTask.this, ClientesListTask.class); 																				
				intent.putExtra("idempleado", idempleado);
				startActivity(intent);
			}
		});
       
        
	}	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
        this.idpedido = (String)i.getSerializableExtra("idpedido");
        this.idcliente = (String)i.getSerializableExtra("idcliente");
        this.idempleado = (String)i.getSerializableExtra("idempleado");
        Log.d("tag","pedido"+this.idpedido);
	}
	
	public void setValues(){
		
        txtVwPedido.setText("Pedido#"+pedidoSelected.getIdPedido());		
	    txtVwCliente.setText(clienteSelected.getApePaterno()+" "+clienteSelected.getApeMaterno()+" "+clienteSelected.getNombre());
	    txtVwMonto.setText("S/."+pedidoSelected.getMontoTotalPedido().toString());		
	}
	
	public void fillValues(){
		//Get Pedido Data
		Syncronizar sync = new Syncronizar(PaymentTask.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idpedido", this.idpedido));		
		String route2="/ws/pedido/consultar_pedido/";
		sync.conexion(param,route2);
		try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			  // TODO Auto-generated catch block
			e.printStackTrace();
		}	    	  
		Gson gson = new Gson();  			
		requestList = gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());	
		if (requestList.size()>0){
			pedidoSelected = requestList.get(0);
			Log.d("selecionado1","PEDIDO1"+pedidoSelected.getIdPedido().toString());
		}else{
			finish();
		}									
		//Get Cliente Data
		Syncronizar sync2 = new Syncronizar(PaymentTask.this);
		List<NameValuePair> param2 = new ArrayList<NameValuePair>();								
		param2.add(new BasicNameValuePair("idcliente", this.idcliente));		
		String route="/ws/clientes/get_cliente_by_id/";
		sync2.conexion(param2,route);
		try {
			sync2.getHilo().join();			
		} catch (InterruptedException e) {
			  // TODO Auto-generated catch block
			e.printStackTrace();
		}	    	  		
		ArrayList<Cliente> cliList = new ArrayList<Cliente>();			
		cliList=gson.fromJson(sync2.getResponse(), new TypeToken<List<Cliente>>(){}.getType());		
		if (cliList.size()>0){
			this.clienteSelected=cliList.get(0);
		}else{
			finish();
		}
	}
}
