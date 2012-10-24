package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.controller.pedidos.SyncronizarPedidos;
import com.itrade.db.DAOCliente;
import com.itrade.model.Cliente;
import com.itrade.pedidos.RegistrarProspecto;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



import com.itrade.R;


public class BuscarProspectos extends Activity {
	private DAOCliente daoProspecto;
	private EditText textView_razonSocial;
	private Button button_buscar;
	private Button button_agregar;
	private ListView list_prospectos;
	//adapter aqui
	//private ItemProspectoAdapter adapter;
	ArrayAdapter<Cliente> adapter;
	private ArrayList<Cliente> listaProspectos;
	String idusuario;
	
		
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buscarprospectos);
		
		Intent i = getIntent();                
		idusuario=(String)i.getSerializableExtra("idempleado");		
		
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		textView_razonSocial= (EditText) findViewById(R.id.editTextCliente);
		button_buscar = (Button) findViewById(R.id.buttonbuscar);
		button_agregar = (Button) findViewById(R.id.buttonvermapa);
		list_prospectos = (ListView) findViewById(R.id.list);
		
		//lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
		
 //WEBSERVICE LLENA ARREGLO DE CLIENTES
        
        SyncronizarPedidos sync = new SyncronizarPedidos(BuscarProspectos.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", idusuario));		
		
		//http://200.16.7.111/dp2/itrade/ws/clientes/get_prospecto_by_vendedor/
		String route="/ws/clientes/get_prospecto_by_vendedor/";
	    sync.conexion(param,route);
	    try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
	    Gson gson = new Gson();
									
		listaProspectos	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());						
//	    ArrayList<String> idProspectos = new ArrayList<String>();	    	    	    	  
//		for(Cliente prosp: prospectoList){
//			idProspectos.add(prosp.getIdCliente().toString());
//		}
		
		//adapter = new ItemProspectoAdapter(this, prospectoList);
        adapter =new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, listaProspectos); 
        		//new ExpandableListAdapter(getBaseContext(), idClientes, new ArrayList<ArrayList<Pedido>>());
        list_prospectos.setAdapter(adapter);
        
        
        button_agregar.setOnClickListener(evento);
    
	}
	
	
	OnClickListener evento = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(BuscarProspectos.this, "Ok", Toast.LENGTH_LONG).show();
			Intent i = new Intent(BuscarProspectos.this, RegistrarProspecto.class);
			startActivity(i);
			BuscarProspectos.this.finish();
		}
	};
}


