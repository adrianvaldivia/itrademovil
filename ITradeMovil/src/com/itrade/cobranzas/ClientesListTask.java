package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;
import com.itrade.model.Usuario;
import com.itrade.pedidos.Login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ClientesListTask extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	private ExpandableListAdapter adapter;
	public Bundle bundle;
	public String nombre="";
	public String apellidos="";
	public int idusuario;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lista_clientes);
     // Retrive the ExpandableListView from the layout
        bundle = getIntent().getExtras();
		idusuario=bundle.getInt("idempleado");
		nombre =bundle.getString("nombre");
		apellidos=bundle.getString("apellidos");
        
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);               
        Log.d("Llega", "setchild");
        listView.setOnChildClickListener(new OnChildClickListener()
        {
                        
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id)
            {
            	Pedido pedido= new Pedido();
                
                pedido = (Pedido)adapter.getChild(groupPosition, childPosition);
                Toast.makeText(getBaseContext(), "PEDIDO = "+ pedido.getFechaPedido().toString(), Toast.LENGTH_LONG).show();
                return false;
                //CLICK EN ALGUN PEDIDO
            }
        });        
        listView.setOnGroupClickListener(new OnGroupClickListener(){           
	        public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
		    // TODO Auto-generated method stub
			    Toast.makeText(getBaseContext(), "CLIENTE", Toast.LENGTH_LONG).show();
			    return false;
			}
        });

        // Inicializa el adaptador
        //ArrayList<String> clientes = new ArrayList<String>();     
        //ArrayList<String> clientes;// = new ArrayList<Cliente>();
        //WEBSERVICE LLENA ARREGLO DE CLIENTES
        Log.d("Llega2", "antes del web cliente");
        Syncronizar sync = new Syncronizar(ClientesListTask.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", "3"));		
		//String route="dp2/itrade/ws/clientes/get_clientes_by_vendedor/";
		String route="itrade_web/ws/clientes/get_clientes_by_vendedor/";
	    sync.conexion(param,route);
	    try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    Log.d("Llega3", "parsero cliente");
	    Gson gson = new Gson();
		ArrayList<Cliente> cliList = new ArrayList<Cliente>();							
		cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());				    		
	    ArrayList<String> idClientes = new ArrayList<String>();
		for(Cliente cli: cliList){
			idClientes.add(cli.getIdCliente().toString());
		}
        adapter = new ExpandableListAdapter(getBaseContext(), idClientes, new ArrayList<ArrayList<Pedido>>());
        CargarLista();
        // Set this blank adapter to the list view
        listView.setAdapter(adapter); 
        Log.d("Llega5", "set adapter");
        
        
    }
    
  
  private void CargarLista() {
	 
		  	 
	  ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
	  
	  //CONSULTAR WEBSERVICE Y LLENAR ARREGLO DE PEDIDOS
	 	  
	  Log.d("Llega4", "antes del web pedido");
	  Syncronizar sync2 = new Syncronizar(ClientesListTask.this);
	  List<NameValuePair> param2 = new ArrayList<NameValuePair>();								
	  param2.add(new BasicNameValuePair("idvendedor", "3"));		
	  //String route="dp2/itrade/ws/pedido/get_pedidos_by_idvendedor/";
	  String route2="itrade_web/ws/pedido/get_pedidos_by_idvendedor/";
	  sync2.conexion(param2,route2);
	  try {
		  sync2.getHilo().join();			
	  } catch (InterruptedException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }	    	  
	  Gson gson = new Gson();  
	  ArrayList<Pedido> pedList = new ArrayList<Pedido>();	
	  Log.d("Pedido", sync2.getResponse());
	  pedList=	gson.fromJson(sync2.getResponse(), new TypeToken<List<Pedido>>(){}.getType());
	  
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
}

