package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class DetalleCliente extends ListActivity{

	private Button button_verpedidos;
	private Button button_crearpedidos;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";
	public String apellidos="";
	public int idcliente;
	public int idempleado;
	int boolVer;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallecliente);
	    bundle = getIntent().getExtras();	
		nombre = bundle.getString("nombre");
		apellidos = bundle.getString("apellidos");
		idcliente = bundle.getInt("idcliente");
		idempleado = bundle.getInt("idempleado");
		boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Informacion del Cliente");
        //daoCliente = new DAOCliente();
        button_verpedidos = (Button) findViewById(R.id.buttonverpedidos);
        button_crearpedidos = (Button) findViewById(R.id.buttoncrearpedido);

        List<String> listaCliente =null;  
//        listaCliente = daoCliente.getCliente(0); //obtiene los clientes

        List<String> lista =null;
        
        lista= this.Convierte(listaCliente);
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 

	    button_verpedidos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(DetalleCliente.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
				
				//Intent intent = new Intent(DetalleCliente.this, VerMapaActivity.class);
				
//				startActivity(intent);		
			}
	 	});
	    button_crearpedidos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(DetalleCliente.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
			     Intent intent = new Intent(DetalleCliente.this, CrearPedido.class);
			     intent.putExtra("nombre", nombre);
			     intent.putExtra("apellidos", apellidos);
			     intent.putExtra("idcliente", idcliente);
			     intent.putExtra("idempleado", idempleado);
			     startActivity(intent);				
	
			}
	 	});
        if (boolVer==0){//es decir si es que ya no necesito mostrar el cliente sino que llamar defrente a crear pedido
        	saltarToCrearPedido();
        }
    }

    private void saltarToCrearPedido() {
	     Intent intent = new Intent(DetalleCliente.this, CrearPedido.class);
	     intent.putExtra("nombre", nombre);
	     intent.putExtra("apellidos", apellidos);
	     intent.putExtra("idcliente", idcliente);
	     intent.putExtra("idempleado", idempleado);
	     startActivity(intent);		
		// TODO Auto-generated method stub
		
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
     Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
    }    
    
	public List<String> Convierte(List<String> lis){
		List<String> lista=new ArrayList<String>();;
		lista.add("Nombre: "+this.nombre);
		lista.add("RUC: "+this.apellidos);
//		lista.add("ID: "+this.idcliente);
		return lista;
	}
}