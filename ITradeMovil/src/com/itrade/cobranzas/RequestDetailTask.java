package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.SyncDetallePedido;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoLinea;
import com.itrade.modelo.Login;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDetailTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	final Context context = this;
	private String idpedido;
	private String idcliente;	
	private String idusuario;
	private Cliente clienteSelected;
	private Pedido pedidoSelected;
	private List<com.itrade.model.PedidoLinea> detallePedido;
	private PedidoLineaAdapter pedidosAdapter;
	private Button buttonCobrar;
	private Button buttonRuta;
	//Botones
	private ImageView btnClientes;
	private ImageView btnMail;
	private ImageView btnBuscar;
	private ImageView btnDepositar;
	private ImageView btnDirectorio;
	private ImageView btnCalendario;
	//Botones  

  	private ImageView btnMapaTotal;
	private SyncDetallePedido syncDetalle;
	private SyncPedidos syncPedido;
	
	public PedidoLineaAdapter getPedidosAdapter() {
		return pedidosAdapter;
	}
	public void setPedidosAdapter(PedidoLineaAdapter pedidosAdapter) {
		this.pedidosAdapter = pedidosAdapter;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
        setContentView(R.layout.c_detalle_pedido);        
        getParamsIntent();
        sqlite();
        fillValues();               
        setValues();
        syncDetalle.closeDB();
        syncPedido.closeDB();
        
	}
	private void sqlite() {
		// TODO Auto-generated method stub
		syncDetalle=new SyncDetallePedido(RequestDetailTask.this);
		syncPedido=new SyncPedidos(RequestDetailTask.this);
//		Integer numreg = syncDetalle.syncBDToSqlite(idpedido);
//		Log.d("RESULTADOS","numeros ="+numreg.toString());	
		
	}
	public void setValues(){
		Log.d("tag", "LLEGO1");
		TextView txtNombre = (TextView) findViewById(R.id.textVwCliente);
		TextView txtTotal = (TextView) findViewById(R.id.textVwTotal);	
		TextView txtEstado = (TextView) findViewById(R.id.TextVwEstado);
		txtNombre.setText(this.clienteSelected.getApePaterno()+" "+this.clienteSelected.getNombre());
		txtTotal.setText(this.pedidoSelected.getMontoTotalPedido().toString());
		Log.d("tag", "LLEGO2");
		buttonCobrar= (Button)findViewById(R.id.btnCobrar);
		if(pedidoSelected.getIdEstadoPedido()==4){
			txtEstado.setText("COBRADO");
			buttonCobrar.setText("Entregar");
		}
		
		//Set Adapter
		ListView listView = (ListView) findViewById(R.id.listDetalle);
        PedidoLineaAdapter adapter = new PedidoLineaAdapter(this,R.layout.c_detalle_row, detallePedido);
        listView.setAdapter(adapter);
        //Set button cobrar Event
        Log.d("tag", "LLEGO3");
        if(pedidoSelected.getIdEstadoPedido()==4){
        	buttonCobrar.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
        			syncPedido=new SyncPedidos(RequestDetailTask.this);
        			if (syncPedido.networkAvailable()){
    					Syncronizar sync = new Syncronizar(RequestDetailTask.this);
    					List<NameValuePair> param = new ArrayList<NameValuePair>();	
    					
    					param.add(new BasicNameValuePair("idpedido", idpedido));
    					
    					String route2="/ws/pedido/entregar_pedido/";
    					sync.conexion(param,route2);
    					try {
    						sync.getHilo().join();			
    					} catch (InterruptedException e) {
    						  // TODO Auto-generated catch block
    						e.printStackTrace();
    					}	    	  
//    					Gson gson = new Gson();  
//    					ArrayList<Pedido> listaPayments = new ArrayList<Pedido>();
//    					listaPayments = gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());
    				}
        			
		        	Integer numreg = syncPedido.entregarPedido(idpedido);
					Log.d("SQL","Se entrego ="+numreg.toString()+" pedido");
					
					String titulo="";
					String mensaje=""; 
					if (numreg>0){
						Pedido pedido = syncPedido.buscarPedido(idpedido);
						titulo="Entrega exitosa"; 
						mensaje="Se registro la entrega del pedido Nro: "+pedido.getIdPedido().toString(); 
					}else{				
						titulo="No se registro la entrega";
						mensaje="No se realizo la entrega del pedido"; 							
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
									Intent intent = new Intent(RequestDetailTask.this, ClientesListTask.class); 													
		//							intent.putExtra("idpedido", idpedido);
		//							intent.putExtra("idcliente", idcliente);	
									intent.putExtra("idusuario", idusuario);
									intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
									startActivity(intent);
								}
					});		
					AlertDialog alertDialog = alertDialogBuilder.create();		 
					alertDialog.show();
					syncPedido.closeDB();
        		}
        	});
        }else{
        	buttonCobrar.setOnClickListener(new OnClickListener() {
		    	public void onClick(View v) {
		    		//Definido en el evento onlick    						 						
					Intent intent = new Intent(RequestDetailTask.this, PaymentTask.class); 													
					intent.putExtra("idpedido", idpedido);
					intent.putExtra("idcliente", idcliente); 	
					intent.putExtra("idusuario", idusuario);
					startActivity(intent);									
				}
		 	});
        }
        buttonRuta= (Button)findViewById(R.id.btnRuta);
        buttonRuta.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    		//Definido en el evento onlick   
	    		
	    		
		    		//Definido en el evento onlick    			
		    	if (networkAvailable()){
		    		Intent intent = new Intent(RequestDetailTask.this, RutaCliente.class); 													
					/*intent.putExtra("idpedido", idpedido);
					intent.putExtra("idcliente", idcliente); 	
					intent.putExtra("idusuario", idusuario);			*/
		    		intent.putExtra("idcliente", idcliente); 
					startActivity(intent);
		    	}else{
		    		Toast.makeText(RequestDetailTask.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
		    	}												
													
			}
	 	});
       
        /*BOTONERA INICIO*/
		/*BTN clientes*/
		btnClientes= (ImageView)findViewById(R.id.c_detped_btnPedidos);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestDetailTask.this, ClientesListTask.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.c_detped_btnNotificar);
		btnMail.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String titulo="Notificar"; 
				String mensaje="¿Deseas Enviar mensaje de texto a: "+clienteSelected.getNombre()+" ?"; 				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 				
				alertDialogBuilder.setTitle(titulo);		 			
				alertDialogBuilder
						.setMessage(mensaje)
						.setCancelable(true)
						.setNegativeButton("Cancelar", null)
						.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {																						
								dialog.cancel();
								sendSms();								
							}
				});		
				AlertDialog alertDialog = alertDialogBuilder.create();		 
				alertDialog.show();					
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.c_detped_btnBuscarClientes);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestDetailTask.this, Buscaclientes.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/**/
		btnDepositar= (ImageView)findViewById(R.id.c_detped_btnCalcularMonto);
		btnDepositar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestDetailTask.this, Amortizacion.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		btnDirectorio= (ImageView)findViewById(R.id.c_detped_btnDirectorio);
		btnDirectorio.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestDetailTask.this, Directorio.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		btnCalendario= (ImageView)findViewById(R.id.c_detped_btnCalendario);
		btnCalendario.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestDetailTask.this, Calendario.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});	
		
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.c_detped_btnExplorar);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(RequestDetailTask.this, MapaClientes.class);		
					intent.putExtra("idusuario", idusuario);				
					startActivity(intent);
				}else{
					Toast.makeText(RequestDetailTask.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});
		/*BOTONERA FIN*/
		
		
	}
	public void getParamsIntent(){		
		Intent i = getIntent();                
		idpedido=(String)i.getSerializableExtra("idpedido");
		idcliente=(String)i.getSerializableExtra("idcliente");		
		idusuario=(String)i.getSerializableExtra("idusuario");
	}
	public void fillValues(){
		//Get Cliente
		Log.d("tag", "llenando valores");
		
		clienteSelected=syncPedido.buscarCliente(idcliente);
		pedidoSelected=syncPedido.buscarPedido(idpedido);
		detallePedido=syncDetalle.buscarLineaPedidos(idpedido);
		
//		Syncronizar sync2 = new Syncronizar(RequestDetailTask.this);
//		List<NameValuePair> param2 = new ArrayList<NameValuePair>();								
//		param2.add(new BasicNameValuePair("idcliente", idcliente));		
//		String route2="/ws/clientes/get_cliente_by_id/";
//		sync2.conexion(param2,route2);
//		try {
//			sync2.getHilo().join();			
//		} catch (InterruptedException e) {
//			  // TODO Auto-generated catch block
//			e.printStackTrace();
//		}	    	  
//		Log.d("tag", "LLEGO7");
//		Gson gson = new Gson();  
//		ArrayList<Cliente> cliList = new ArrayList<Cliente>();			
//		cliList=gson.fromJson(sync2.getResponse(), new TypeToken<List<Cliente>>(){}.getType());		
//		if (cliList.size()>0){
//			this.clienteSelected=cliList.get(0);
//		}else{
//			finish();
//		}
		//GET Pedido Linea
//		Log.d("tag", "LLEGO8");
//		List<NameValuePair> param = new ArrayList<NameValuePair>();								
//		param.add(new BasicNameValuePair("idpedido", idpedido));				
//		sync2.conexion(param,"/ws/pedido/get_detail_by_idpedido/");
//		try {
//			sync2.getHilo().join();			
//		} catch (InterruptedException e) {
//			  // TODO Auto-generated catch block
//			e.printStackTrace();
//		}	    	  		
//		Log.d("tag", "LLEGO9");
//		ArrayList<com.itrade.cobranzas.PedidoLinea> linPedList = new ArrayList<com.itrade.cobranzas.PedidoLinea>();			
//		linPedList=gson.fromJson(sync2.getResponse(), new TypeToken<List<com.itrade.cobranzas.PedidoLinea>>(){}.getType());		
//		if (cliList.size()>0){
//			this.detallePedido=linPedList;
//			Log.d("size","elementos="+this.detallePedido.get(0).getMarca());
//		}else{
//			finish();
//		}
		
//		//GET Pedido
//		Log.d("tag", "LLEGO10");
//		List<NameValuePair> param3 = new ArrayList<NameValuePair>();								
//		param3.add(new BasicNameValuePair("idpedido", idpedido));				
//		sync2.conexion(param3,"/ws/pedido/consultar_pedido/");
//		try {
//			sync2.getHilo().join();			
//		} catch (InterruptedException e) {
//			  // TODO Auto-generated catch block
//			e.printStackTrace();
//		}	    	  		
//		ArrayList<Pedido> pedList = new ArrayList<Pedido>();			
//		pedList=gson.fromJson(sync2.getResponse(), new TypeToken<List<Pedido>>(){}.getType());		
//		if (cliList.size()>0){
//			this.pedidoSelected=pedList.get(0);			
//		}else{
//			finish();
//		}			
		Log.d("tag", "LLEGO10");
	}
	public void sendSms(){
		Intent intent = new Intent(RequestDetailTask.this, RequestDetailTask.class); 																					
		intent.putExtra("idpedido", idpedido);
		intent.putExtra("idcliente",idcliente);
		intent.putExtra("idusuario", idusuario);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);                
        SmsManager sms = SmsManager.getDefault();        
        sms.sendTextMessage(this.clienteSelected.getTelefono(), null, "El cobrador de Itrade se estara acercando durante el dia.", pi, null);								
    }
	 public boolean networkAvailable() {    	
	     	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	     	if (connectMgr != null) {
	     		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
	     		if (netInfo != null) {
	     			for (NetworkInfo net : netInfo) {
	     				if (net.getState() == NetworkInfo.State.CONNECTED) {
	     					return true;
	     				}
	     			}
	     		}
	     	} 
	     	else {
	     		Log.d("NETWORK", "No network available");
	     	}
	     	return false;
	  }
}
