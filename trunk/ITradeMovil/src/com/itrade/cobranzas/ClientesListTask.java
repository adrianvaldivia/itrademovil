package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.Parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;
import com.itrade.model.Usuario;
import com.itrade.pedidos.BuscarClientesGreenDao;
import com.itrade.pedidos.Login;
import com.itrade.pedidos.MenuLista;
import com.itrade.pedidos.RegistrarProspecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ClientesListTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	final Context context = this;
	private ExpandableListAdapter adapter;
	public Bundle bundle;
	public String nombre="";
	public String apellidos="";
	public String idusuario;
	public String idpedido;
	//Botones
	private ImageView btnClientes;
	private ImageView btnMail;
	private ImageView btnBuscar;
	private ImageView btnDepositar;
	private ImageView btnDirectorio;
	private ImageView btnCalendario;
	private ImageView btnMapa;
	private ImageView btnMapaTotal;
	private SyncPedidos sincPedidos;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lista_clientes);                       
        Intent i = getIntent();                       
		idusuario=(String)i.getSerializableExtra("idempleado");
		sqlite();
		/*BTN clientes*/
		btnClientes= (ImageView)findViewById(R.id.btnListaClientes);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClientesListTask.this, ClientesListTask.class); 																				
				intent.putExtra("idempleado", idusuario);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.btnMailMasivo);
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
									Syncronizar sync = new Syncronizar(ClientesListTask.this);
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
									Toast.makeText(ClientesListTask.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(ClientesListTask.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
								}																														
								Intent intent = new Intent(ClientesListTask.this, ClientesListTask.class); 													
								intent.putExtra("idempleado", idusuario);
								startActivity(intent);
							}
				});		
				AlertDialog alertDialog = alertDialogBuilder.create();		 
				alertDialog.show();	
				
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.btnBuscarCliente);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClientesListTask.this, BuscarClientesGreenDao.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				startActivity(intent);
			}
		});		
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.btnMapa);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(ClientesListTask.this, MapaClientes.class);		
					intent.putExtra("idempleado", idusuario);				
					startActivity(intent);
				}else{
					Toast.makeText(ClientesListTask.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});	
		
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);                       
        listView.setOnChildClickListener(new OnChildClickListener()
        {
                        
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id)
            {
            	Pedido pedido= new Pedido();
                
                pedido = (Pedido)adapter.getChild(groupPosition, childPosition);
                /*Intent para ver el detalle del pedido*/
                Intent intent = new Intent(ClientesListTask.this, RequestDetailTask.class);		                
				intent.putExtra("idpedido", pedido.getIdPedido().toString());
				intent.putExtra("idcliente", pedido.getIdCliente().toString());
				intent.putExtra("idempleado", idusuario);	
				//intent.putExtra("usuario", usuario);					
				startActivity(intent);									
                //Toast.makeText(getBaseContext(), "PEDIDO = "+ pedido.getIdPedido().toString(), Toast.LENGTH_LONG).show();
                return true;
                //CLICK EN ALGUN PEDIDO
            }            
        });
               
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {        				
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long id) {
				// TODO Auto-generated method stub
				Pedido pedido= new Pedido();
				int groupPosition = ExpandableListView.getPackedPositionGroup(id);
				int childPosition = ExpandableListView.getPackedPositionChild(id);
				if (childPosition>=0){
					pedido=(Pedido)adapter.getChild(groupPosition, childPosition);
//					
					idpedido= pedido.getIdPedido().toString();
//					
//					Gson gson = new Gson();  
//					ArrayList<Pedido> lista = new ArrayList<Pedido>();
//					lista = gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());
										
//					Pedido pedidito = lista.get(0);
					String titulo="Cancelar pedido"; 
					String mensaje="¿Deseas cancelar el pedido N°: "+ idpedido + " ?"; 
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 				
					alertDialogBuilder.setTitle(titulo);		 			
					alertDialogBuilder
							.setMessage(mensaje)
							.setCancelable(true)
							.setNegativeButton("Cancelar", null)
							.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {														
									dialog.cancel();
									
									// AQUI SI NO TiENE CONEXION
									sincPedidos= new SyncPedidos(ClientesListTask.this);
									if (!sincPedidos.networkAvailable()){									
										Integer numreg = sincPedidos.eliminarPedido(idpedido);
										Log.d("LONGCLICK","Se elimino ="+numreg.toString());		
									}else{//Si es que tiene conexion
										Syncronizar sync = new Syncronizar(ClientesListTask.this);
										List<NameValuePair> param = new ArrayList<NameValuePair>();
										param.add(new BasicNameValuePair("idpedido", idpedido));
										String route2="/ws/pedido/cancelar_pedido/";
										sync.conexion(param,route2);
										try {
											sync.getHilo().join();
										} catch (InterruptedException e) {
											  // TODO Auto-generated catch block
											e.printStackTrace();
										}
									}	
									
									Intent intent = new Intent(ClientesListTask.this, ClientesListTask.class); 													
									intent.putExtra("idempleado", idusuario);
									startActivity(intent);
									finish();
								}
					});		
					AlertDialog alertDialog = alertDialogBuilder.create();		 
					alertDialog.show();	
					
//					Toast.makeText(getBaseContext(), "item="+pedido.getIdPedido(),Toast.LENGTH_LONG).show();
				}
				return true;
			}			
        });
                
        listView.setOnGroupClickListener(new OnGroupClickListener(){           
	        public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
		    // TODO Auto-generated method stub
			    //Toast.makeText(getBaseContext(), "CLIENTE", Toast.LENGTH_LONG).show();
			    return false;
			}
        });
        //WEBSERVICE LLENA ARREGLO DE CLIENTES
/*        antes de
        Syncronizar sync = new Syncronizar(ClientesListTask.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", idusuario));		
		String route="/ws/clientes/get_clientes_by_vendedor/";
	    sync.conexion(param,route);
	    try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	    	    
	    Gson gson = new Gson();
		ArrayList<Cliente> cliList = new ArrayList<Cliente>();							

		cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());	
	*/		
        List<Cliente> cliList = sincPedidos.getListaCliente(idusuario);
	    ArrayList<String> idClientes = new ArrayList<String>();
	    ArrayList<String> nombresClientes = new ArrayList<String>();
		for(Cliente cli: cliList){
			idClientes.add(cli.getIdCliente().toString());
			nombresClientes.add(cli.getApePaterno()+" "+cli.getNombre());
		}
        adapter = new ExpandableListAdapter(getBaseContext(), idClientes,nombresClientes, new ArrayList<ArrayList<Pedido>>());
        CargarLista();
        // Set this blank adapter to the list view
        listView.setAdapter(adapter); 
        //No olvidar de esto en todos los activities
        sincPedidos.closeDB();
    }
    

  private void CargarLista() {	 		  	 	  	
	  //CONSULTAR WEBSERVICE Y LLENAR ARREGLO DE PEDIDOS
	  /*Antes de
	  Syncronizar sync2 = new Syncronizar(ClientesListTask.this);
	  List<NameValuePair> param2 = new ArrayList<NameValuePair>();								
	  param2.add(new BasicNameValuePair("idvendedor", idusuario));		
	  String route2="/ws/pedido/get_pedidos_by_idvendedor/";
	  sync2.conexion(param2,route2);
	  try {
		  sync2.getHilo().join();			
	  } catch (InterruptedException e) {
		  e.printStackTrace();
	  }	    	  
	  Gson gson = new Gson();  
	  ArrayList<Pedido> pedList = new ArrayList<Pedido>();	
	  Log.d("Pedido", sync2.getResponse());
	  pedList=	gson.fromJson(sync2.getResponse(), new TypeToken<List<Pedido>>(){}.getType());	  
	  for(Pedido ped: pedList ){
		  adapter.addItem(ped);
	  }
	  */
	  //Esta jalando los pedidos de Hoy
	  List<Pedido> pedList= sincPedidos.getPedidosHoy();
	  for(Pedido ped: pedList ){
		  adapter.addItem(ped);
	  }
	  
	  
	  
	}

  	private Handler handler = new Handler()
     {
         @Override
         public void handleMessage(Message msg)
         {
             adapter.notifyDataSetChanged();
             super.handleMessage(msg);
         }
     };
     
     private void sqlite(){
		sincPedidos= new SyncPedidos(ClientesListTask.this); 
		
		//Integer numePed = sincPedidos.cargarClientes(idusuario);
		//Integer numeCli = sincPedidos.cargarPedidos(idusuario);
		Integer numreg = sincPedidos.syncBDToSqlite(idusuario);
		Log.d("RESULTADOS","numeros ="+numreg.toString());		
	   	//Log.d("RESULTADOS","fecha ="+today.toString());
	   	//List<Pedido> listaPedi =sincPedidos.getPedidos(Integer.parseInt(idusuario));
		//Log.d("pEDIDOS","cantidad ="+listaPedi.size());
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

