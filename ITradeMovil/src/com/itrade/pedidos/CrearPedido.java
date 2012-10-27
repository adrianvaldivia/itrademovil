package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoLinea;
import com.itrade.model.Producto;
import com.itrade.model.ProductoDao;
import com.itrade.R;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.db.DAOPedido;


import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
	public int idpedido=0;
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
	
	Double montoTotal=0.0;
	
	//Green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProductoDao productoDao;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
//    	Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        
        //Inicio configuracion green dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
      //Fin configuracion green dao
        
        setContentView(R.layout.crearpedidofusion);
	    bundle = getIntent().getExtras();	
		nombre = bundle.getString("nombre");
		apellidos = bundle.getString("apellidos");
		idcliente = bundle.getInt("idcliente");
		idempleado = bundle.getInt("idempleado");
//		guardarPedido();
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
				guardarPedido();
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
		lista.add("RUC: "+this.apellidos);
		lista.add("Lista de Productos:");
		return lista;
	}
	public List<String> ConvierteAlVolver(List<String> lis){
		List<String> lista=new ArrayList<String>();;
		lista.add("Nombre: "+this.nombre);
		lista.add("RUC: "+this.apellidos);
		lista.add("Lista de Productos:");
		for(int i=0;i<listaProductoNombre.size();i++){
			lista.add(listaProductoNombre.get(i)+"  Cantidad:"+this.listaProductoCantidad.get(i));
		    PedidoLinea pedidoLinea = new PedidoLinea();
		    pedidoLinea.setCantidad(listaProductoCantidad.get(i));
		    pedidoLinea.setIdProducto(listaProductoElegido.get(i));
		    pedidoLinea.setIdPedido(idpedido);
		    pedidoLinea.setMontoLinea(obtenerPrecio(pedidoLinea.getIdProducto())*pedidoLinea.getCantidad());
		    this.listaPedidoLinea.add(pedidoLinea);
		}
		return lista;
	}
	private Double obtenerPrecio(Integer idProducto) {
		// TODO Auto-generated method stubaa
		Double precioAux=0.0;
		Producto productoAux= this.productoDao.loadByRowId(idProducto);
		precioAux=productoAux.getPrecio();
		return precioAux;
	}

	public void guardarPedido(){
        daoPedido = new DAOPedido();
        Pedido pedido= new Pedido();
        pedido.setIdCliente(idcliente);
        procesaPedido();//obtiene el monto Total
        pedido.setMontoTotal(montoTotal);
        idpedido=daoPedido.registrarPedido(pedido);
	}
	private void procesaPedido() {//acumulador del monto Total
        for(int i=0;i<listaPedidoLinea.size();i++){
        	montoTotal=montoTotal+listaPedidoLinea.get(i).getMontoLinea();
        }
		// TODO Auto-generated method stub
		
	}

	public void guardarDetallePedido(){
		procesaPedidoLinea();
        daoPedido = new DAOPedido();
        for(int i=0;i<listaPedidoLinea.size();i++){
        	daoPedido.registrarPedidoLinea(listaPedidoLinea.get(i));
        	
        }
	}
 
	private void procesaPedidoLinea() {
        for(int i=0;i<listaPedidoLinea.size();i++){
        	listaPedidoLinea.get(i).setIdPedido(idpedido);        	
        }
	}

	@Override
	public void onRestart(){
//		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
		super.onRestart();
		//setTitle("Volviendo");
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
	@Override
	protected void onDestroy() { 		
		db.close();
//		cursor.close();
	    super.onDestroy();
	}
}