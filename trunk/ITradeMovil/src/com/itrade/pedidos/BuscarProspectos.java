package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.controller.pedidos.SyncronizarPedidos;
import com.itrade.db.DAOCliente;
import com.itrade.model.Cliente;
import com.itrade.model.Meta;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;



import com.itrade.R;


public class BuscarProspectos extends Activity {
	//private DAOCliente daoProspecto;
	private EditText textView_razonSocial;
	private Button button_buscar;
	private ImageButton button_meta;
	private Button button_agregar;
	private ListView list_prospectos;
	
	//ArrayAdapter<Cliente> adapter;
	//ItemProspectoAdapter adapter2;
	ArrayAdapter<String> adapter;
	private ArrayList<Cliente> listaProspectos;
	private ArrayList<Meta> listameta;
	String idusuario;
	String razonSocial;			
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buscarprospectos);
		
		Bundle bundle=getIntent().getExtras();
        long idu = bundle.getLong("idusuario");		
        idusuario= String.valueOf(idu);        
        
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		textView_razonSocial= (EditText) findViewById(R.id.editTextCliente);
		button_buscar = (Button) findViewById(R.id.buttonbuscar);
		button_agregar = (Button) findViewById(R.id.buttonvermapa);
		button_meta = (ImageButton) findViewById(R.id.btnMeta);
		list_prospectos = (ListView) findViewById(R.id.list);
		
		razonSocial= textView_razonSocial.getText().toString();		
		
		
		button_meta.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//WEBSERVICE LLENA ARREGLO DE meta
    	        
		    	Syncronizar sync = new Syncronizar(BuscarProspectos.this);
		    	List<NameValuePair> param = new ArrayList<NameValuePair>();								
		    	param.add(new BasicNameValuePair("idvendedor", idusuario));	
		    	
		    	
		    	//poner ruta real para meta
		    	String route="/ws/clientes/get_meta_by_vendedor/";
		    	sync.conexion(param,route);
		    	try {
		    		sync.getHilo().join();			
		    	} catch (InterruptedException e) {
		    		// TODO Auto-generated catch block
		    		e.printStackTrace();
		    	}	   
		    	Gson gson = new Gson();
		    										
		    	listameta	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Meta>>(){}.getType());			
				
		    	Toast.makeText(BuscarProspectos.this, "Ok", Toast.LENGTH_LONG).show();
				Intent i = new Intent(BuscarProspectos.this, MiMeta.class);
				i.putExtra("idusuario", idusuario);
				i.putExtra("periodo", listameta.get(0).getPeriodo());
				i.putExtra("meta", listameta.get(0).getMetareal());
				i.putExtra("avance", listameta.get(0).getAvance());
				startActivity(i);
				BuscarProspectos.this.finish();
		    	
			}
			
		});
		
		
		button_buscar.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {

	    	//WEBSERVICE LLENA ARREGLO DE CLIENTES
	    	        
	    	Syncronizar sync = new Syncronizar(BuscarProspectos.this);
	    	List<NameValuePair> param = new ArrayList<NameValuePair>();								
	    	param.add(new BasicNameValuePair("idvendedor", idusuario));	
	    	param.add(new BasicNameValuePair("razon_social", razonSocial));
	    	
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
	    	ArrayList<String> lprospectos=new ArrayList<String>();
	    	for(int i=0;i<listaProspectos.size();i++){
	        	lprospectos.add(listaProspectos.get(i).getRazon_Social());	        	
	        }	
	    	//adapter =new ArrayAdapter<Cliente>(BuscarProspectos.this, android.R.layout.simple_list_item_1, listaProspectos); 
	    	adapter =new ArrayAdapter<String>(BuscarProspectos.this, android.R.layout.simple_list_item_1, lprospectos);
	    	
	    	//adapter2 =new ItemProspectoAdapter(BuscarProspectos.this, listaProspectos);
	    	
	    	list_prospectos.setAdapter(adapter);
	    	//list_prospectos.setAdapter(adapter2);
	    }
	    });
		                                  
        button_agregar.setOnClickListener(evento);
    
	}		
	
	OnClickListener evento = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(BuscarProspectos.this, "Ok", Toast.LENGTH_LONG).show();
			Intent i = new Intent(BuscarProspectos.this, RegistrarProspecto.class);
			i.putExtra("idusuario", idusuario);
			startActivity(i);
			BuscarProspectos.this.finish();
		}
	};
}


