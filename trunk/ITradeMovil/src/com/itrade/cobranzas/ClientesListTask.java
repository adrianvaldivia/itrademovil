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
import com.itrade.pedidos.MenuLista;

import android.app.Activity;
import android.content.Intent;
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
	private ExpandableListAdapter adapter;
	public Bundle bundle;
	public String nombre="";
	public String apellidos="";
	public String idusuario;
	private ImageView btnClientes;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lista_clientes);                       
        Intent i = getIntent();                
		idusuario=(String)i.getSerializableExtra("idempleado");
		btnClientes= (ImageView)findViewById(R.id.ImageButton04);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClientesListTask.this, ClientesListTask.class); 																				
				intent.putExtra("idempleado", idusuario);
				startActivity(intent);
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
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "item="+arg2,Toast.LENGTH_LONG).show();
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
        
        Syncronizar sync = new Syncronizar(ClientesListTask.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", idusuario));		
		//String route="dp2/itrade/ws/clientes/get_clientes_by_vendedor/";
		String route="/ws/clientes/get_clientes_by_vendedor/";
	    sync.conexion(param,route);
	    try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	    
	    Gson gson = new Gson();
		ArrayList<Cliente> cliList = new ArrayList<Cliente>();							
		cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());						
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
    }
    
  
  private void CargarLista() {	 		  	 	  	
	  //CONSULTAR WEBSERVICE Y LLENAR ARREGLO DE PEDIDOS	 	  	 
	  Syncronizar sync2 = new Syncronizar(ClientesListTask.this);
	  List<NameValuePair> param2 = new ArrayList<NameValuePair>();								
	  param2.add(new BasicNameValuePair("idvendedor", idusuario));		
	  //String route="dp2/itrade/ws/pedido/get_pedidos_by_idvendedor/";
	  String route2="/ws/pedido/get_pedidos_by_idvendedor/";
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

