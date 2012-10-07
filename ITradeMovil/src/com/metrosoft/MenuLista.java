package com.metrosoft;

import java.util.ArrayList;

import com.metrosoft.model.ItemMenu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;


public class MenuLista extends ListActivity{

	private Button button_salirmenu;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";
	public String apellidos="";
	public int idempleado;
	private TextView textView_NombreUsuario;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulista);
        textView_NombreUsuario  = (TextView) findViewById(R.id.nombreusuario);
//inicio cambios
        bundle = getIntent().getExtras();
		idempleado=bundle.getInt("idempleado");
		nombre =bundle.getString("nombre");
		apellidos=bundle.getString("apellidos");
		setTitle("iTrade - Bienvenido");
		textView_NombreUsuario.setText(nombre+" "+apellidos);
//fin cambios        
        button_salirmenu = (Button) findViewById(R.id.salirmenu);
        
        ListView lv = getListView();
        ArrayList<ItemMenu> itemsCompra = obtenerItems();        
        ItemMenuAdapter adapter = new ItemMenuAdapter(this, itemsCompra);        
        lv.setAdapter(adapter);

//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 

	    button_salirmenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
				MenuLista.this.finish();
	
			}
	 	});

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
//     Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
     if (selection.compareTo("Clientes")==0){
			Intent intent = new Intent(MenuLista.this, BuscarClientes.class);		
			intent.putExtra("idempleado", idempleado);
			intent.putExtra("boolVer", 1);
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Productos")==0){
    	 	Intent intent = new Intent(MenuLista.this, BuscarProductos.class);
    	 	intent.putExtra("boolVer", 0);
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Pedidos")==0){
			Intent intent = new Intent(MenuLista.this, BuscarPedidos.class);
			intent.putExtra("idruta", 0);
			intent.putExtra("idunidad", 1);
			intent.putExtra("idempleado", idempleado);
			
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Mi Meta")==0){
			Intent intent = new Intent(MenuLista.this, MiMeta.class);
			startActivity(intent);		
     }
     if (selection.compareTo("Mi Agenda")==0){
			Intent intent = new Intent(MenuLista.this, SimpleCalendarViewActivity.class);
			startActivity(intent);		
     }
    }    
    
	
    private ArrayList<ItemMenu> obtenerItems() {
    	ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
    	
    	items.add(new ItemMenu(1, "Clientes", "Ver Listado de Clientes", "drawable/clientes"));
    	items.add(new ItemMenu(2, "Pedidos", "Gestiona los pedidos", "drawable/pedidos"));
    	items.add(new ItemMenu(3, "Mi Agenda", "Organizador y eventos", "drawable/agenda"));
    	items.add(new ItemMenu(4, "Mi Meta", "Evolucion de mis ventas", "drawable/excelencia"));
    	
    	
    	return items;
    }
}