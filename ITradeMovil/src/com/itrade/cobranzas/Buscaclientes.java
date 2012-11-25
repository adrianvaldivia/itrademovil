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
import android.app.ListActivity;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class Buscaclientes extends Activity {
	
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
		ArrayList<String> lclientes=new ArrayList<String>();;
		ArrayList<Cliente> lclientes2;
		ArrayList<String> lpedidos;
		ArrayList<String> lclientesA;
		
		ArrayList datos;
		private String[] songsArray ;
		Cliente cliActual;
		
		private List<Cliente> cliListSQL;
		private SyncPedidos sincPedidos;
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
    		
    	}
    	

    	
    	adapter =new ArrayAdapter<String>(Buscaclientes.this, R.layout.itemsimplelinea, lclientes);
    	list_clientes.setAdapter(adapter);
    	   	
		button_buscar.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	String tvrazon="";
		    	tvrazon = textView_razonSocial.getText().toString();
		    	String cad;
		    	
			    for(int i=0;i<cliListSQL.size();i++){	
			    	
	
			    	if (cliListSQL.get(i).getRazon_Social().regionMatches(0,tvrazon ,0, tvrazon.length())){
	 		
		    			cliActual = cliListSQL.get(i);
		    			refrescarLista(cliActual);
		    			break;
		    			
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
			startActivity(intent);
			
			
		}
		
		
		});


		
	} 
	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
		
   //     this.idpedido = (String)i.getSerializableExtra("idpedido");
   //     this.idcliente = (String)i.getSerializableExtra("idcliente");
        this.idusuario = (String)i.getSerializableExtra("idusuario");
 
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
	

}
