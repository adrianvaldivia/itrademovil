package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BuscarPedidoCliente extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	
	private String idcliente;
	private ListView list_pedidos;
	private Gson gson;
	private Syncronizar sync;
	private SyncPedidos sincPedidos;
	private List<Pedido> pedList = new ArrayList<Pedido>();
	private List<String> pedListS = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private TextView txtcliente;
	private List<Pedido> pedListSQL = new ArrayList<Pedido>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.c_pedidos_cliente);
		getParamsIntent();
		
		list_pedidos = (ListView) findViewById(R.id.listDetalle);

		txtcliente = (TextView) findViewById(R.id.txtCliente);
		txtcliente.setText(idcliente);
		
//		gson = new Gson(); 
		
		sincPedidos= new SyncPedidos(BuscarPedidoCliente.this); 
		pedListSQL = sincPedidos.getPedidosSemana(idcliente);
		
						//webservice
				//		sync= new Syncronizar(this);
				//		List<NameValuePair> param = new ArrayList<NameValuePair>();
				//		
				//		
				//		param.add(new BasicNameValuePair("idcliente",idcliente));
				//		
				//		sync.conexion(param,"/ws/pedido/get_proximos_pedidos/");
				//		
				//		try {
				//			sync.getHilo().join();			
				//		} catch (InterruptedException e) {
				//			//TODO Auto-generated catch block
				//			e.printStackTrace();
				//		}
				//		
				//		//ws/pedido/get_proximos_pedidos/2001
				//		this.pedList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());
				//		
						
						//webservice
		
		
		for (int i=0; i< pedListSQL.size();i++){
			pedListS.add(Long.toString(pedListSQL.get(i).getIdPedido()));
		}
		
		adapter =new ArrayAdapter<String>(BuscarPedidoCliente.this, R.layout.itemsimplelinea, pedListS);
    	list_pedidos.setAdapter(adapter);
    	
    	
    	
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
					
			String razon =(String) (list_pedidos.getItemAtPosition(arg2));
			System.out.println("cliente:"+ razon);
			
			for (int i=0;i<pedListSQL.size();i++){
				if (Long.toString(pedListSQL.get(i).getIdPedido()).compareTo(razon) ==0){
					idCliente = Long.toString(pedListSQL.get(i).getIdCliente());
					break;
				}
				
			}
						
			Intent intent = new Intent(BuscarPedidoCliente.this, BuscarPedidoCliente.class); 																				
			intent.putExtra("idcliente", idCliente);
			startActivity(intent);
			
			
		}
		
		
		});
    	
    	
    	
	}
	
	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
		
   //     this.idpedido = (String)i.getSerializableExtra("idpedido");
        this.idcliente = (String)i.getSerializableExtra("idcliente");
   //     this.idusuario = (String)i.getSerializableExtra("idempleado");
		
 
	}
}
