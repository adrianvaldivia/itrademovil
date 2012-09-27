package com.metrosoft;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.metrosoft.db.DAOProducto;
import com.metrosoft.model.PedidoLinea;
import com.metrosoft.model.Producto;

public class BuscarProductos extends ListActivity{
	DAOProducto daoProducto =null;
	PopupWindow m_pw;
	private Button button_aceptar;
	public Bundle bundle;
	int boolVer,cantidad;
	List<PedidoLinea> listaPedidoLinea =new ArrayList<PedidoLinea>();
	List<Producto> listaProducto =null;
	ArrayList <Integer> listaProductoElegido= new ArrayList<Integer>();//arreglo de ids
	ArrayList <Integer> listaProductoCantidad= new ArrayList<Integer>();//arreglo de cantidades
	ArrayList <String> listaProductoNombre= new ArrayList<String>();//lista de arreglo de nombres
	Producto producto= new Producto();
	public EditText textView_Cantidad;
	public int idpedido;
	Spinner spinner_categoria;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarproductos);
        bundle = getIntent().getExtras();
        boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity
        idpedido=bundle.getInt("idpedido");

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Productos");
        daoProducto = new DAOProducto();
        button_aceptar = (Button) findViewById(R.id.buttonaceptar);
        spinner_categoria = (Spinner) findViewById(R.id.spinnercategoria);
        cargarCombo();       
        listaProducto = daoProducto.getAllProductos(0); //obtiene los productos

        List<String> lista =null;
        
        lista= this.Convierte(listaProducto);
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 
	    button_aceptar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(BuscarProductos.this, "Ok", Toast.LENGTH_LONG).show();
				BuscarProductos.this.finish();
			}
	 	});
    }

    private void cargarCombo() {
    	List<String> listaCategoria =new ArrayList<String>();
    	listaCategoria.add("Enlatados");
    	listaCategoria.add("Bebidas");
    	listaCategoria.add("Menestras");
    	this.spinner_categoria.setAdapter(null);
    	
    	ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategoria);
    	//Añadimos el layout para el menú y se lo damos al spinner
    	spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner_categoria.setAdapter(spinner_adapter);
		
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
     //producto = (Producto)l.getItemAtPosition(position);//error listview de Strings no de Productos
//     Toast.makeText(this, selection, Toast.LENGTH_LONG).show();  
     this.encuentraProducto(selection);
     if (boolVer==1){
    	 // Inicio del popup
    	 LayoutInflater inflater = (LayoutInflater)
         this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	 View layout = inflater.inflate(R.layout.mypopup,
    	 (ViewGroup) findViewById(R.id.MyLinearLayout));
    	 textView_Cantidad  = (EditText) layout.findViewById(R.id.editTextCantidad);
    	 m_pw = new PopupWindow( layout,  350,  250,  true);
    	 m_pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    	 //fin del popup    	 
     }     
    }    
    
	public List<String> Convierte(List<Producto> lis){
		List<String> lista=new ArrayList<String>();;
		for(int i=0;i<lis.size();i++){
			lista.add(lis.get(i).getNombre().toString());
		}
		return lista;
	}
	private void encuentraProducto(String selection) {
		for(int i=0;i<listaProducto.size();i++){
			if (selection.compareTo(listaProducto.get(i).getNombre().toString()) == 0){
				producto=listaProducto.get(i);
				break;
			}
		}
	}
	public void PreparaArreglos(){		
		for(int i=0;i<listaPedidoLinea.size();i++){
			listaProductoElegido.add(listaPedidoLinea.get(i).getIdproducto());
			listaProductoCantidad.add(listaPedidoLinea.get(i).getCantidad());
			listaProductoNombre.add(listaPedidoLinea.get(i).getNombre());
		}
	}
	public void onButtonInPopup (View target) {
	    String strCantidad = textView_Cantidad.getText().toString();	    
	    cantidad = Integer.parseInt(strCantidad);	 
	    PedidoLinea pedidoLinea = new PedidoLinea();
	    pedidoLinea.setCantidad(cantidad);
	    pedidoLinea.setIdproducto(producto.getIdproducto());
	    pedidoLinea.setIdpedido(idpedido);
	    pedidoLinea.setNombre(producto.getNombre());
	    this.listaPedidoLinea.add(pedidoLinea);
	    //Toast.makeText(this, "C:"+cantidad+"Pr:"+producto.getIdproducto()+"Pe:"+idpedido, Toast.LENGTH_LONG).show();
	    m_pw.dismiss();
	}
	@Override
	public void onBackPressed() {		
		BuscarProductos.this.finish();
	return;
	}
	@Override
	public void finish(){
		PreparaArreglos();
		Bundle extras = new Bundle();
		extras.putIntegerArrayList("listaProductoElegido", listaProductoElegido);
		extras.putIntegerArrayList("listaProductoCantidad", listaProductoCantidad);
		extras.putStringArrayList("listaProductoNombre", listaProductoNombre);
		Intent intent = new Intent();
//		intent.putExtra("lista", "A Dormir");
		intent.putExtras(extras);
		setResult(RESULT_OK,intent);
		super.finish();		
	}
}
