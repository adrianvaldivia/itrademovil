package com.metrosoft;

import java.util.ArrayList;
import java.util.List;

import com.metrosoft.db.DAOPedido;
import com.metrosoft.model.Pedido;
import com.metrosoft.model.PedidoLinea;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class CrearPedido extends ListActivity{
	DAOPedido daoPedido =null;
	private Button button_agregarproducto;
	private Button button_registrarpedido;
	public Bundle bundle;
	public int idpedido;
	public String nombre="";
	public String apellidos="";
	public int idcliente;
	public int idempleado;
	public String pruebaPaso;
	private static final int REQUEST_CODE=10;
	List<PedidoLinea> listaPedidoLinea=new ArrayList<PedidoLinea>();
	ArrayList <Integer> listaProductoElegido= new ArrayList<Integer>();//arreglo de ids
	ArrayList <Integer> listaProductoCantidad= new ArrayList<Integer>();//arreglo de cantidades
	ArrayList <String> listaProductoNombre= new ArrayList<String>();//lista de arreglo de nombres
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
//    	Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearpedido);
	    bundle = getIntent().getExtras();	
		nombre = bundle.getString("nombre");
		apellidos = bundle.getString("apellidos");
		idcliente = bundle.getInt("idcliente");
		idempleado = bundle.getInt("idempleado");
		guardarPedido();
        setTitle("iTrade - Crear Pedido");

        button_agregarproducto = (Button) findViewById(R.id.buttonagregarproducto);
        button_registrarpedido = (Button) findViewById(R.id.buttonregistrarpedido);

        List<String> listaGenerica =null;  

        List<String> lista =null;
        
        lista= this.Convierte(listaGenerica);
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 
	    button_agregarproducto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(CrearPedido.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
				
				Intent intent = new Intent(CrearPedido.this, BuscarProductos.class);
				intent.putExtra("boolVer", 1);//booleano para ver o no ver el popup
				intent.putExtra("idpedido", idpedido);
				startActivityForResult(intent,REQUEST_CODE);	
			}
	 	});
	    button_registrarpedido.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(CrearPedido.this, "Pedido Registrado Exitosamente", Toast.LENGTH_LONG).show();
				guardarDetallePedido();
				CrearPedido.this.finish();
			}
	 	});

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
		lista.add("Apellidos: "+this.apellidos);
		lista.add("Lista de Productos:");
		return lista;
	}
	public List<String> ConvierteAlVolver(List<String> lis){
		List<String> lista=new ArrayList<String>();;
		lista.add("Nombre: "+this.nombre);
		lista.add("Apellidos: "+this.apellidos);
		lista.add("Lista de Productos:");
		for(int i=0;i<listaProductoNombre.size();i++){
			lista.add(listaProductoNombre.get(i)+"  Cantidad:"+this.listaProductoCantidad.get(i));
		    PedidoLinea pedidoLinea = new PedidoLinea();
		    pedidoLinea.setCantidad(listaProductoCantidad.get(i));
		    pedidoLinea.setIdproducto(listaProductoElegido.get(i));
		    pedidoLinea.setIdpedido(idpedido);
		    pedidoLinea.setNombre(listaProductoNombre.get(i));
		    this.listaPedidoLinea.add(pedidoLinea);
		}
		return lista;
	}
	public void guardarPedido(){
        daoPedido = new DAOPedido();
        Pedido pedido= new Pedido();
        pedido.setIdcliente(idcliente);
        pedido.setIdempleado(idempleado);
        idpedido=daoPedido.insertaPedido(pedido);
	}
	public void guardarDetallePedido(){
        daoPedido = new DAOPedido();
        for(int i=0;i<listaPedidoLinea.size();i++){
        	daoPedido.insertaPedidoLinea(listaPedidoLinea.get(i));
        }
	}
 
	@Override
	public void onRestart(){
//		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
		super.onRestart();
		setTitle("Volviendo");
		List<String> lista =null;
		List<String> listaGenerica =null; 
		lista= this.ConvierteAlVolver(listaGenerica);
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
 
	}
 
	@Override
	public void onStop(){
//		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show(); 
		super.onStop(); 
	}
	@Override
    protected void onActivityResult(int requestCode,int resultCode, Intent pData)           
    {
        if ( requestCode == REQUEST_CODE )//Si el código de respuesta es igual al requestCode
            {
            if (resultCode == RESULT_OK )//Si resultCode es igual a ok
                {
//            		pruebaPaso = pData.getExtras().getString("lista" );//Obtengo el string de la subactividad
            		listaProductoElegido=pData.getExtras().getIntegerArrayList("listaProductoElegido");//Obtengo el string de la subactividad
            		listaProductoCantidad=pData.getExtras().getIntegerArrayList("listaProductoCantidad");
            		listaProductoNombre=pData.getExtras().getStringArrayList("listaProductoNombre");
                    //Aquí se hara lo que se desee con el valor recuperado                    
                }
            }
    }
}