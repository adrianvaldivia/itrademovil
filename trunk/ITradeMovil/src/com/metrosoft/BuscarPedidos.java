package com.metrosoft;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.metrosoft.db.DAOPedido;
import com.metrosoft.model.Pedido;
 
public class BuscarPedidos extends ListActivity{
 
	List<String> lista = new ArrayList<String>();
	DAOPedido daoPedido =null;
	private Button button_cearpedido;
	private Button button_regresar;
	int idempleado;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verpedidos);
 
		Bundle bundle = getIntent().getExtras();
		idempleado = bundle.getInt("idempleado");
        setTitle("iTrade - Pedidos");
        daoPedido = new DAOPedido();
        button_cearpedido = (Button) findViewById(R.id.buttoncrearpedido);
        button_regresar = (Button) findViewById(R.id.buttonregresar);
        List<Pedido> listaIncidencia = null;  
        listaIncidencia = daoPedido.getAllPedidos(idempleado); //obtiene las incidencias de una unidad
        
        lista= Convierte(listaIncidencia);
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        button_cearpedido.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(BuscarPedidos.this, "pruebaa", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(BuscarPedidos.this, BuscarClientes.class);		
				intent.putExtra("idempleado", idempleado);
				intent.putExtra("boolVer", 0);//boolean que indica quien llamo a la ventana
				startActivity(intent);
			}
	 	});
        button_regresar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(BuscarPedidos.this, "pruebaa", Toast.LENGTH_LONG).show();
				BuscarPedidos.this.finish();
			}
	 	});
 
	}

	public List<String> Convierte(List<Pedido> lis) {
		List<String> lista = new ArrayList<String>();
		
//		lista.add("PEDIDO");
//		lista.add("FECHA");
//		lista.add("CLIENTE");
		
		for(int i=0;i<lis.size();i++) {
			lista.add("Fecha:"+lis.get(i).getFecha().toString()+" Cliente:"+String.valueOf(lis.get(i).getNombrecliente()));
//			lista.add(lis.get(i).getFecha().toString());
//			lista.add(String.valueOf(lis.get(i).getIdcliente()));
		}
		return lista;
	}
	
}