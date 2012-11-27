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
import com.itrade.model.Cliente;
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
	
	
	
	public void getParamsIntent(){
		Intent i = getIntent();   		
		
   //     this.idpedido = (String)i.getSerializableExtra("idpedido");
        this.idcliente = (String)i.getSerializableExtra("idcliente");
        this.idempleado=(String)i.getSerializableExtra("idempleado");
        this.idusuario=(String)i.getSerializableExtra("idusuario");
	}
}
