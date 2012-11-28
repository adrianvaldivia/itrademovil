package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.SyncContactos;
import com.itrade.controller.cobranza.SyncDetallePedido;
import com.itrade.controller.cobranza.SyncNotifications;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.SyncUsuarios;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOCliente;
import com.itrade.db.DAOProspecto;
import com.itrade.model.Cliente;
import com.itrade.model.Meta;
import com.itrade.model.Pedido;
import com.itrade.model.Prospecto;
import com.itrade.model.Usuario;
import com.itrade.pedidos.BuscarProspectos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
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
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class Buscaclientes extends Activity {
	
	
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
	
	
	ArrayAdapter<String> adaptador;
	private Gson gson;
	//private DAOCliente daoProspecto;
		private EditText textView_razonSocial;
		private Button button_buscar;
		Syncronizar sync;
		
		private List<com.itrade.model.Cliente> detalleCliente;
		private ListView list_clientes;
		
		ArrayList<Cliente> cliList;
		ArrayList<Pedido>pedList;
		DAOCliente daocliente;
		
		//ArrayAdapter<Cliente> adapter;
		//ItemProspectoAdapter adapter2;
		ArrayAdapter<String> adapter;
		private ArrayList<Pedido> listaPedidos;
		private List<Cliente> listaClientes;
		//private ArrayList<Meta> listameta;
		private Meta mimeta;
		String idusuario;
		String razonSocial;			
		ArrayList<String> lclientes = new ArrayList<String>();
		ArrayList<String> lclientesAux = new ArrayList<String>();
		ArrayList<Cliente> lclientes2;
		ArrayList<String> lpedidos;
		ArrayList<String> lclientesA;
		
		ArrayList datos;
		private String[] songsArray ;
		Cliente cliActual;
		
		private List<Cliente> cliListSQL;
		private SyncPedidos sincPedidos;
		private String idempleado;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 
		
		setContentView(R.layout.c_buscar_clientes);
		
			
		getParamsIntent();
		sqlite();
        fillValues();
		
        Botones();
		list_clientes = (ListView) findViewById(R.id.list);
		if (list_clientes == null) { Log.w("", "list_pedidos es NULL"); }
		
		
		button_buscar = (Button) findViewById(R.id.buttonbuscar);
		if (button_buscar == null) { Log.w("", "button_buscar es NULL"); }
		
		
		
		
		
		textView_razonSocial = (EditText) findViewById(R.id.editTextCliente);
		
		
		//gson = new Gson(); 
		
		//long idUsuario = 3;
			
		/// carga clientes sqlLite
			
		cliListSQL = sincPedidos.getListaCliente(idusuario);
		
		
		///
		
		/// web service
//		sync= new Syncronizar(this);
//		List<NameValuePair> param = new ArrayList<NameValuePair>();
//				
//										
//		
//		param.add(new BasicNameValuePair("idvendedor",idusuario));	
//		sync.conexion(param,"/ws/clientes/get_clientes_by_vendedor/");
//		try {
//			sync.getHilo().join();			
//		} catch (InterruptedException e) {
//			//TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		cliList= new ArrayList<Cliente>();
//		
//		cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());
		/// web service
		
		
		
		
		lpedidos = new ArrayList<String>();
    	lclientes2=new ArrayList<Cliente>();
    	lclientesA = new ArrayList<String>();
    	

 //   	detalleCliente = new ArrayList<Cliente>();
    	for(int i=0;i<cliListSQL.size();i++) {
 //   		detalleCliente.add(cliListSQL.get(i));

    		lclientes.add(cliListSQL.get(i).getRazon_Social());
    		lclientesAux.add(cliListSQL.get(i).getRazon_Social());
    	}
    	

    	
    	adapter =new ArrayAdapter<String>(Buscaclientes.this, R.layout.itemsimplelinea, lclientes);
    	list_clientes.setAdapter(adapter);
    	   	
		button_buscar.setOnClickListener(new OnClickListener() {
			int n=0;
		    public void onClick(View v) {
		    	String tvrazon="";
		    	tvrazon = textView_razonSocial.getText().toString();
		    	String cad;
		    	if (tvrazon.length() == 0){
		    		lclientes.clear();
		    		for(int j=0;j<lclientesAux.size();j++){
		    			lclientes.add(lclientesAux.get(j));
		    		}
		    		adapter.notifyDataSetChanged();
			    	list_clientes.setAdapter(adapter);
		    	}
		    	else{
		    		
				    for(int i=0;i<cliListSQL.size();i++){	
				    	
		
				    	if (cliListSQL.get(i).getRazon_Social().regionMatches(0,tvrazon ,0, tvrazon.length())){
		 		
			    			cliActual = cliListSQL.get(i);
			    			refrescarLista(cliActual);
			    			
			    			break;
			    			
				    	}
				    	n = i;
				    }

			    	if(n==cliListSQL.size()-1){ //no lo encontro
			    		lclientes.clear();
						adapter.notifyDataSetChanged();
				    	list_clientes.setAdapter(adapter);
			    	}
			    	
				    
		    	}
		    	
		    }

			private void refrescarLista(Cliente cliActual) {
				
				//adapter =new ArrayAdapter<String>(Buscaclientes.this, R.layout.itemsimplelinea, lclientes);
		    	//list_clientes.setAdapter(adapter);
				lclientes.clear();
				lclientes.add(cliActual.getRazon_Social());
		    	adapter.notifyDataSetChanged();
		    	list_clientes.setAdapter(adapter);
		    	
			}
   });
		
	
		
		list_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() 
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
					
			String razon =(String) (list_clientes.getItemAtPosition(arg2));
			System.out.println("cliente:"+ razon);
			
			for (int i=0;i<cliListSQL.size();i++){
				if (cliListSQL.get(i).getRazon_Social().compareTo(razon) ==0){
					idCliente = Long.toString(cliListSQL.get(i).getIdCliente());
					break;
				}
				
			}
						
			Intent intent = new Intent(Buscaclientes.this, BuscarPedidoCliente.class); 																				
			intent.putExtra("idcliente", idCliente);
			intent.putExtra("idempleado", idempleado);
			intent.putExtra("idusuario", idusuario);
			startActivity(intent);
			
			
		}
		
		
		});


		
	} 
	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
		
   //     this.idpedido = (String)i.getSerializableExtra("idpedido");
   //     this.idcliente = (String)i.getSerializableExtra("idcliente");
        this.idusuario = (String)i.getSerializableExtra("idusuario");
        this.idempleado = (String)i.getSerializableExtra("idusuario");
 
	}
	
	
	
	   private void sqlite(){
			sincPedidos= new SyncPedidos(Buscaclientes.this); 
//			sincDetallePedido= new SyncDetallePedido(ClientesListTask.this);
//			sincContacto= new SyncContactos(ClientesListTask.this);
//			sincUsuario = new SyncUsuarios(ClientesListTask.this);
//			sincNotifications = new SyncNotifications(ClientesListTask.this);
//			//Integer numePed = sincPedidos.cargarClientes(idusuario);
//			//Integer numeCli = sincPedidos.cargarPedidos(idusuario);
//			Integer numreg = sincPedidos.syncBDToSqlite(idusuario);
//			Integer numerito = sincDetallePedido.syncBDToSqlite(idusuario);
//			Usuario usuario = sincUsuario.getUsuarioById(idusuario);
//			Log.d("USUARIIO","USUARIIO ="+usuario.getNombre());						
//			Integer numerazo = sincContacto.cargarContactos(usuario.getIdUbigeo().toString());
//			Log.d("ClienteListaTask","pedidos ="+numreg.toString());		
//			Log.d("ClienteListaTask","detallesPedidos ="+numerito.toString());
//			Log.d("ClienteListaTask","detallesPedidos ="+numerazo.toString());
//		   	//Log.d("RESULTADOS","fecha ="+today.toString());
//		   	//List<Pedido> listaPedi =sincPedidos.getPedidos(Integer.parseInt(idusuario));
//			//Log.d("pEDIDOS","cantidad ="+listaPedi.size());
	     }
	
	
public void fillValues(){
		
//		pedidoSelected=sincPedidos.buscarPedido(idpedido);
//		clienteSelected=sincPedidos.buscarCliente(idcliente);
		Log.d("sqlite","esta jalando de bd movil");
	
	}

private void Botones() {
	
	
	btnClientes= (ImageView)findViewById(R.id.c_bus_btnListaClientes);
	btnClientes.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Buscaclientes.this, ClientesListTask.class); 																				
			intent.putExtra("idempleado", idusuario);
			startActivity(intent);
		}
	});
	
	/*BTN clientes*/
	btnDepositar= (ImageView)findViewById(R.id.c_bus_btnDepositarBanco);
	btnDepositar.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Buscaclientes.this, Amortizacion.class); 																				
			intent.putExtra("idusuario", idusuario);
			startActivity(intent);
		}
	});
	/*BTN Mensaje masivo*/
	btnMail= (ImageView)findViewById(R.id.c_bus_btnMailMasivo);
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
									Syncronizar sync = new Syncronizar(Buscaclientes.this);
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
									Toast.makeText(Buscaclientes.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(Buscaclientes.this, "Usted ya envió notifaciones el dia de hoy.", Toast.LENGTH_SHORT).show();
								}																		
							}else{
								Toast.makeText(Buscaclientes.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
							}																														
							Intent intent = new Intent(Buscaclientes.this, ClientesListTask.class); 													
							intent.putExtra("idempleado", idusuario);
							startActivity(intent);
						}
			});		
			AlertDialog alertDialog = alertDialogBuilder.create();		 
			alertDialog.show();	
			
		}
	});
	/*btn buscar clientes*/
	btnBuscar= (ImageView)findViewById(R.id.c_bus_btnBuscarCliente);
	btnBuscar.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Buscaclientes.this, Buscaclientes.class);		
			intent.putExtra("idusuario", idusuario);
			intent.putExtra("boolVer", 1);
			startActivity(intent);
		}
	});
	/**/
	btnCalendario= (ImageView)findViewById(R.id.c_bus_btnCalendario);
	btnCalendario.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Buscaclientes.this, Calendario.class);
			intent.putExtra("idusuario", idusuario);				
			startActivity(intent);
		}
	});	
	btnDirectorio= (ImageView)findViewById(R.id.c_bus_btnDirectorio);
	btnDirectorio.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Buscaclientes.this, Directorio.class);
			intent.putExtra("idusuario", idusuario);				
			startActivity(intent);
		}
	});	
	/*btn Mapa Clientes*/
	btnMapaTotal= (ImageView)findViewById(R.id.c_bus_btnMapa);
	btnMapaTotal.setOnClickListener(new OnClickListener() {			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (networkAvailable()){
				Intent intent = new Intent(Buscaclientes.this, MapaClientes.class);		
				intent.putExtra("idempleado", idusuario);				
				startActivity(intent);
			}else{
				Toast.makeText(Buscaclientes.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
			}				
		}
	});	
	
	
	
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
