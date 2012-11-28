package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.SyncNotifications;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuscarPedidoCliente extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	final Context context = this;
	private SyncNotifications sincNotifications;
	private ImageView btnClientes;
	private ImageView btnMail;
	private ImageView btnBuscar;
	private ImageView btnDepositar;
	private ImageView btnDirectorio;
	private ImageView btnCalendario;
	private ImageView btnMapa;
	private ImageView btnMapaTotal;
	
	
	
	private String idcliente;
	private String idempleado;
	private String idusuario;
	private ListView list_pedidos;
	private Gson gson;
	private Syncronizar sync;
	private SyncPedidos syncPedidos;
	private List<Pedido> pedList = new ArrayList<Pedido>();
	private List<String> pedListS = new ArrayList<String>();
	//private ArrayAdapter<String> adapter;
	private TextView txtcliente;
	private List<Pedido> pedListSQL = new ArrayList<Pedido>();
	private String idPedidoActual;
	private Cliente clienteActual;
	private List<com.itrade.model.Pedido> pedidos = new ArrayList<com.itrade.model.Pedido>();
	PedidoAdapter adapter;
	
	
	public PedidoAdapter getPedidosAdapter() {
		return adapter;
	}
	public void setAdapter(PedidoAdapter adapter) {
		this.adapter = adapter;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.c_pedidos_cliente);
		getParamsIntent();
		
		
		Botones();
		

		txtcliente = (TextView) findViewById(R.id.txtCliente);
		
		//buscarPedido(idpedido);
		syncPedidos= new SyncPedidos(BuscarPedidoCliente.this);
		
		clienteActual = syncPedidos.buscarCliente(idcliente);
		
		txtcliente.setText(clienteActual.getNombre() + " " + clienteActual.getApePaterno() + " " + clienteActual.getApeMaterno());
		
		
		
		
		pedListSQL = syncPedidos.getPedidosSemana(idcliente);
		for(int i=0; i< pedListSQL.size();i++){
			
			pedidos.add(pedListSQL.get(i));
			
		}
					    	
		list_pedidos = (ListView) findViewById(R.id.listPedido);
        adapter = new PedidoAdapter(this,R.layout.c_pedido_row,pedidos);
        
        System.out.println("Antes de Setear PedidoAdapter");
        
        list_pedidos.setAdapter(adapter);
        System.out.println("Despues de Setear PedidoAdapter");
        
        syncPedidos.closeDB();
    	list_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
		 public void onItemSelected(AdapterView parentView, View childView, int position, long id) 
		 {
		    //obtengo el id del cliente
		 }
		 public void onNothingSelected(AdapterView parentView) 
		 {
		 }
		 
		 String idCliente = " ";
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					
			Pedido pedSel = (Pedido)list_pedidos.getItemAtPosition(arg2);
			idPedidoActual  = (Long.toBinaryString(pedSel.getIdPedido()));
			System.out.println("idpedidoActual="+idPedidoActual);			
			
//			for (int i=0;i<pedidos.size();i++){
//				if (Long.toString(pedidos.get(i).getIdPedido()).compareTo(idPedidoActual) ==0){
//					idPedidoActual = Long.toString(pedidos.get(i).getIdPedido());
//					break;
//				}
//				
//			}
			
			
			
			Intent intent = new Intent(BuscarPedidoCliente.this, RequestDetailTask.class);		                
			intent.putExtra("idpedido", pedSel.getIdPedido().toString());
			intent.putExtra("idcliente", pedSel.getIdCliente().toString());
			intent.putExtra("idempleado", idempleado);	
			intent.putExtra("idusuario", idusuario);					
				startActivity(intent);
//			
//			Intent intent = new Intent(BuscarPedidoCliente.this, BuscarPedidoCliente.class); 																				
////			intent.putExtra("idcliente", idCliente);
//			startActivity(intent);
//			
//			
		}
//		
//		
		});
    	
    	
    	
	}
	
	
	
	private void Botones() {
	
		
		btnClientes= (ImageView)findViewById(R.id.c_ped_btnListaClientes);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuscarPedidoCliente.this, ClientesListTask.class); 																				
				intent.putExtra("idempleado", idusuario);
				startActivity(intent);
			}
		});
		
		/*BTN clientes*/
		btnDepositar= (ImageView)findViewById(R.id.c_ped_btnDepositarBanco);
		btnDepositar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuscarPedidoCliente.this, Amortizacion.class); 																				
				intent.putExtra("idusuario", idusuario);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.c_ped_btnMailMasivo);
		btnMail.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String titulo="Notificar"; 
				String mensaje="¿Deseas Notificar ahora a todos tus clientes ?"; 				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 				
				alertDialogBuilder.setTitle(titulo);		 			
				alertDialogBuilder
						.setMessage(mensaje)
						.setCancelable(true)
						.setNegativeButton("Cancelar", null)
						.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {														
								dialog.cancel();
								//verificar si tiene internet o no <--------------------------
								
								if (networkAvailable()){
									if (!sincNotifications.sendNotification(idusuario)){
										Syncronizar sync = new Syncronizar(BuscarPedidoCliente.this);
										List<NameValuePair> param = new ArrayList<NameValuePair>();
										param.add(new BasicNameValuePair("idcobrador", idusuario));
										String route2="/ws/cobranza/send_notifications/";
										sync.conexion(param,route2);
										try {
											sync.getHilo().join();
										} catch (InterruptedException e) {
											  // TODO Auto-generated catch block
											e.printStackTrace();
										}									
										sincNotifications.saveNotification(idusuario);
										Toast.makeText(BuscarPedidoCliente.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(BuscarPedidoCliente.this, "Usted ya envió notifaciones el dia de hoy.", Toast.LENGTH_SHORT).show();
									}																		
								}else{
									Toast.makeText(BuscarPedidoCliente.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
								}																														
								Intent intent = new Intent(BuscarPedidoCliente.this, ClientesListTask.class); 													
								intent.putExtra("idempleado", idusuario);
								startActivity(intent);
							}
				});		
				AlertDialog alertDialog = alertDialogBuilder.create();		 
				alertDialog.show();	
				
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.c_ped_btnBuscarCliente);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuscarPedidoCliente.this, Buscaclientes.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				startActivity(intent);
			}
		});
		/**/
		btnCalendario= (ImageView)findViewById(R.id.c_ped_btnCalendario);
		btnCalendario.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuscarPedidoCliente.this, Calendario.class);
				intent.putExtra("idusuario", idusuario);				
				startActivity(intent);
			}
		});	
		btnDirectorio= (ImageView)findViewById(R.id.c_ped_btnDirectorio);
		btnDirectorio.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuscarPedidoCliente.this, Directorio.class);
				intent.putExtra("idusuario", idusuario);				
				startActivity(intent);
			}
		});	
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.c_ped_btnMapa);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(BuscarPedidoCliente.this, MapaClientes.class);		
					intent.putExtra("idempleado", idusuario);				
					startActivity(intent);
				}else{
					Toast.makeText(BuscarPedidoCliente.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});	
		
		
		
	}
	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
		
   //     this.idpedido = (String)i.getSerializableExtra("idpedido");
        this.idcliente = (String)i.getSerializableExtra("idcliente");
        this.idempleado=(String)i.getSerializableExtra("idempleado");
        this.idusuario=(String)i.getSerializableExtra("idusuario");
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
