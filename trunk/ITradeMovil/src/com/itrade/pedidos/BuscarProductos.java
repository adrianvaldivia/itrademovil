package com.itrade.pedidos;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.itrade.model.Categoria;
import com.itrade.model.CategoriaDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.PedidoLinea;
import com.itrade.model.Producto;
import com.itrade.model.ProductoDao;
import com.itrade.pedidos.R;
import com.itrade.model.ProductoDao.Properties;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.db.DAOProducto;



public class BuscarProductos extends ListActivity{
	DAOProducto daoProducto =null;
	PopupWindow m_pw;
	private Button button_aceptar;
	public Bundle bundle;
	int boolVer,cantidad;
	List<PedidoLinea> listaPedidoLinea =new ArrayList<PedidoLinea>();
	List<Producto> listaProducto =null;
	List<Producto> listaProductoOriginal =null;
	List<Categoria> listaCategoria =null;
	ArrayList <Integer> listaProductoElegido= new ArrayList<Integer>();//arreglo de ids
	ArrayList <Integer> listaProductoCantidad= new ArrayList<Integer>();//arreglo de cantidades
	ArrayList <String> listaProductoNombre= new ArrayList<String>();//lista de arreglo de nombres
	Producto producto= new Producto();
	public EditText textView_Cantidad;
	public int idpedido;
	Spinner spinner_categoria;
	//Green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProductoDao productoDao;
    private CategoriaDao categoriaDao;
    private ElementoListaDao elementoListaDao;

//    private Cursor cursor;
    private Cursor cursorSpinner;
    private Cursor cursorElementoLista;
//    SimpleCursorAdapter adapter;
    SimpleCursorAdapter adapterSpinner;
    SimpleCursorAdapter adapterElementoLista;
    //green Dao
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarproductos);
        setTitle("iTrade - Productos");
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
        categoriaDao = daoSession.getCategoriaDao();
        elementoListaDao = daoSession.getElementoListaDao();
        
        // Segunda parte
//        String textColumn = ProductoDao.Properties.Descripcion.columnName;
//        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
//        cursor = db.query(productoDao.getTablename(), productoDao.getAllColumns(), null, null, null, null, orderBy);
//        String[] from = { textColumn, ProductoDao.Properties.Precio.columnName };
//        int[] to = { android.R.id.text1, android.R.id.text2 };
//        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
//                to);        
        // Fin green Day de Productos      
        
        
        
        //Inicio green Dao Spinner
        String textColumnSpinner = CategoriaDao.Properties.Descripcion.columnName;
        String orderBySpinner = textColumnSpinner + " COLLATE LOCALIZED ASC";
        cursorSpinner = db.query(categoriaDao.getTablename(), categoriaDao.getAllColumns(), null, null, null, null, orderBySpinner);
        String[] fromSpinner = { textColumnSpinner };
        int[] toSpiner = new int[]{android.R.id.text1};
        startManagingCursor(cursorSpinner); 
        adapterSpinner = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorSpinner, fromSpinner, toSpiner );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        //fin green Day del Spinner
        
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
        int[] toElementoLista = { android.R.id.text1, android.R.id.text2 };
        adapterElementoLista = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursorElementoLista, fromElementoLista,
        		toElementoLista);    
        //fin green Day de Elementos Lista 
        
        setListAdapter(adapterElementoLista);
        guardaListaOriginal();
        
        
        bundle = getIntent().getExtras();
        boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity
        idpedido=bundle.getInt("idpedido");

        daoProducto = new DAOProducto();
        button_aceptar = (Button) findViewById(R.id.buttonaceptar);
        spinner_categoria = (Spinner) findViewById(R.id.spinnercategoria);
        spinner_categoria.setAdapter(adapterSpinner);
        if (categoriaDao.count()==0){
        	cargarCombo();//cargo elementos HardCoded cuando no hay BD
        }
               
	    button_aceptar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(BuscarProductos.this, "Ok", Toast.LENGTH_LONG).show();
				BuscarProductos.this.finish();
			}
	 	});
	    spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            //Toast.makeText(BuscarProductos.this, "Ok:"+id, Toast.LENGTH_LONG).show();
	        	String nombreCategoria=encuentraNombreCategoria(id);
	        	//Toast.makeText(BuscarProductos.this, "Ok:"+nombreCategoria, Toast.LENGTH_LONG).show();
	            if (nombreCategoria.compareToIgnoreCase( "Todos" )==0){	            	
	            	filtrarLista(0);//Muestra todos los productos
	            }
	            else{
	            	filtrarLista(id);//muestra la lista filtrada
	            }
	        }
			public void onNothingSelected(AdapterView<?> parent) {
	        }
	    });

    }

	private String encuentraNombreCategoria(long id) {
		String str="";
		Categoria categ = categoriaDao.loadByRowId(id);
		if (categ!=null)
			str=categ.getDescripcion();
		return str;
	}

	private void cargarCombo() {//cargo elementos hardcoded
    	List<String> listaCategoria =new ArrayList<String>();
    	listaCategoria.add("Lista Vacia");
    	this.spinner_categoria.setAdapter(null);
    	
    	ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategoria);
    	//Añadimos el layout para el menú y se lo damos al spinner
    	spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner_categoria.setAdapter(spinner_adapter);
		
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

     this.encuentraProducto(id);
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
			lista.add(lis.get(i).getDescripcion().toString());
		}
		return lista;
	}
	private void encuentraProducto(Long  id) {
//		for(int i=0;i<listaProducto.size();i++){
//			if (selection.compareTo(listaProducto.get(i).getDescripcion()) == 0){
//				producto=listaProducto.get(i);
//				break;
//			}
//		}
		ElementoLista elementoAux=  elementoListaDao.loadByRowId(id);
		
		producto=productoDao.loadByRowId(elementoAux.getIdElemento());
	}
	public void PreparaArreglos(){		
		for(int i=0;i<listaPedidoLinea.size();i++){
			listaProductoElegido.add(listaPedidoLinea.get(i).getIdProducto());
			listaProductoCantidad.add(listaPedidoLinea.get(i).getCantidad());
			listaProductoNombre.add(encuentraNombreProducto(listaPedidoLinea.get(i).getIdProducto()));
		}
	}
	private String encuentraNombreProducto(Integer idProducto) {
		String str="";		
		Producto productoAux=productoDao.loadByRowId(idProducto);
		str=productoAux.getDescripcion();
		return str;
	}

	public void onButtonInPopup (View target) {
	    String strCantidad = textView_Cantidad.getText().toString();	    
	    cantidad = Integer.parseInt(strCantidad);	
	    Long idProductoAux=producto.getId();
	    int intAux= safeLongToInt(idProductoAux);
	    PedidoLinea pedidoLinea = new PedidoLinea();
	    pedidoLinea.setCantidad(cantidad);
	    pedidoLinea.setIdProducto(intAux);
	    pedidoLinea.setIdPedido(idpedido);
	    //pedidoLinea.setNombre(producto.getDescripcion());
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
		intent.putExtras(extras);
		setResult(RESULT_OK,intent);
		super.finish();		
	}
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	Toast.makeText(this, "Cargando BD!", Toast.LENGTH_LONG).show();
	        	cargarBaseLocal();	        	
	                            break;
	                           }
	        case R.id.opcion2:     Toast.makeText(this, "Presionaste Opcion 2!", Toast.LENGTH_LONG).show();
	                            break;
	        case R.id.opcion3: Toast.makeText(this, "Presionaste Opcion 3!", Toast.LENGTH_LONG).show();
	                            break;
	    }
	    return true;
	}
	
    private void filtrarLista(long id) {
    	recuperarOriginal();
    	if (id!=0){
    		String str=String.valueOf(id);
        	
            List<Producto> productosAux = productoDao.queryBuilder()
            		.where(Properties.IdCategoria.eq(str))
            		.orderAsc(Properties.Id).list();

            elementoListaDao.deleteAll();
    	
    		for(int i=0;i<productosAux.size();i++){
    			ElementoLista elemento = new ElementoLista(null,productosAux.get(i).getDescripcion(),"Precio: "+productosAux.get(i).getPrecio(),productosAux.get(i).getId());
    			elementoListaDao.insert(elemento);
    	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
    		}
    		cursorElementoLista.requery();	
    	}
	}
	
    private void guardaListaOriginal() {
		this.listaProductoOriginal=productoDao.loadAll();
		
	}
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();
//		productoDao.deleteAll();
		for(int i=0;i<listaProductoOriginal.size();i++){

//			Producto productoAux = new Producto(null,listaProductoOriginal.get(i).getDescripcion(),listaProductoOriginal.get(i).getPrecio(),listaProductoOriginal.get(i).getStock(),listaProductoOriginal.get(i).getActivo(),listaProductoOriginal.get(i).getIdCategoria(),listaProductoOriginal.get(i).getIdMarca());
			ElementoLista elemento = new ElementoLista(null,listaProductoOriginal.get(i).getDescripcion(),"Precio: "+listaProductoOriginal.get(i).getPrecio(),listaProductoOriginal.get(i).getId());
			elementoListaDao.insert(elemento);
//			productoDao.insert(productoAux);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
		cursorElementoLista.requery();
	}
    private void cargarBaseLocal() {
        daoProducto = new DAOProducto();
        listaProducto = daoProducto.getAllProductos(); //obtiene los clientes
        listaCategoria=daoProducto.getAllCategorias();
       
		productoDao.deleteAll();
		categoriaDao.deleteAll();
		elementoListaDao.deleteAll();
		
        
		for(int i=0;i<listaProducto.size();i++){

			Producto productoAux = new Producto(null,listaProducto.get(i).getDescripcion(),listaProducto.get(i).getPrecio(),listaProducto.get(i).getStock(),listaProducto.get(i).getActivo(),listaProducto.get(i).getIdCategoria(),listaProducto.get(i).getIdMarca());
			ElementoLista elemento = new ElementoLista(null,listaProducto.get(i).getDescripcion(),"Precio: "+listaProducto.get(i).getPrecio(),listaProducto.get(i).getId());
			elementoListaDao.insert(elemento);
			productoDao.insert(productoAux);			
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
		for(int i=0;i<listaCategoria.size();i++){
			Categoria categoria = new Categoria(null,listaCategoria.get(i).getDescripcion());
			categoriaDao.insert(categoria);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
		guardaListaOriginal();
		cursorSpinner.requery();
		cursorElementoLista.requery();
//        cursor.requery();
    	setListAdapter(adapterElementoLista);
    	spinner_categoria.setAdapter(adapterSpinner);
	}
	@Override
	protected void onDestroy() { 
		recuperarOriginal();
		db.close();
//		cursor.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}
}
