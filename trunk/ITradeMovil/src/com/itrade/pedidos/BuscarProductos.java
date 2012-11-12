package com.itrade.pedidos;

import android.app.ListActivity;
import android.app.SearchManager;
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
import com.itrade.R;
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
	long idElemento;
	public boolean boolBarraBusqueda=false;
	public NumberPicker picker_Cantidad;
	public long idpedido;
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
        setContentView(R.layout.buscarproductosfusion);
        setTitle("iTrade - Productos");      
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
        categoriaDao = daoSession.getCategoriaDao();
        elementoListaDao = daoSession.getElementoListaDao();
        
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
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName,ElementoListaDao.Properties.Terciario.columnName };
        int[] toElementoLista = { R.id.text1, R.id.text2,R.id.text3 };
        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
        		toElementoLista);    
        //fin green Day de Elementos Lista 
        
        setListAdapter(adapterElementoLista);
        guardaListaOriginal();
        
        
        bundle = getIntent().getExtras();
        boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity
        idpedido=bundle.getLong("idpedido");

        daoProducto = new DAOProducto(BuscarProductos.this);
        button_aceptar = (Button) findViewById(R.id.buttonaceptar);
        spinner_categoria = (Spinner) findViewById(R.id.spinnercategoria);
        spinner_categoria.setAdapter(adapterSpinner);
        if (categoriaDao.count()==0){
        	cargarCombo();//cargo elementos HardCoded cuando no hay BD
        }
               
	    button_aceptar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(BuscarProductos.this, "Ok", Toast.LENGTH_SHORT).show();
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
        /////////////////////////////////////barra de busqueda
        handleIntent(getIntent());
        //////////////////////////////////////////barra de busqueda  

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
    	 picker_Cantidad  = (NumberPicker) layout.findViewById(R.id.numberpickercantidad);
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
		idElemento=elementoAux.getId();
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
		//Producto productoAux=productoDao.loadByRowId(idProducto);
		str=str+idProducto;
        List<Producto> productosAux = productoDao.queryBuilder()
        		.where(Properties.IdProducto.eq(str))
        		.orderAsc(Properties.Id).list();
        
        if (productosAux!=null){
        	if(productosAux.size()>0){
        		Producto productoTemp=productosAux.get(0);
            	str=productoTemp.getDescripcion();//posible error	
        	}
        	else
        		str="Error Nombre Prod";
        }
        	
        
        
		
		return str;
	}

	public void onButtonInPopup (View target) {
//		String strCantidad = ""+picker_Cantidad.mCurrent;
//		String strCantidad = ""+picker_Cantidad.getCurrent();
		String strCantidad = ""+picker_Cantidad.mText.getText().toString();
//	    String strCantidad = textView_Cantidad.getText().toString();
	    Long idProductoAux=producto.getIdProducto();//cambios chichan antes getId();
	    int intAux= safeLongToInt(idProductoAux);//idProducto
	    if(strCantidad!=null){
	    	if (strCantidad.length()>0) {
	    		cantidad = Integer.parseInt(strCantidad);
	    		registraCantidadLista();
	    		chequeaProductoRepetido(intAux);
	    	}
	    	else
	    		cantidad=0;
	    }
	    else
	    	cantidad=0;
	    	
	    PedidoLinea pedidoLinea = new PedidoLinea();
	    pedidoLinea.setCantidad(cantidad);
	    pedidoLinea.setIdProducto(intAux);
	    pedidoLinea.setIdPedido(idpedido);
	    //pedidoLinea.setNombre(producto.getDescripcion());
	    this.listaPedidoLinea.add(pedidoLinea);
	    //Toast.makeText(this, "C:"+cantidad+"Pr:"+producto.getIdproducto()+"Pe:"+idpedido, Toast.LENGTH_LONG).show();
	    m_pw.dismiss();
	}


	private void registraCantidadLista() {
		// TODO Auto-generated method stub
		ElementoLista elementoAux=  elementoListaDao.loadByRowId(idElemento);
		elementoAux.setTerciario("Cantidad: "+cantidad);
		this.elementoListaDao.deleteByKey(this.idElemento);
		elementoListaDao.insert(elementoAux);
		cursorElementoLista.requery();	
	}

	public void onButtonCancelarPopup (View target) {
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
	        case R.id.opcion2:{
	    		int tam=spinner_categoria.getCount();
	    		spinner_categoria.setSelection(tam-1);
	        	onSearchRequested();  
//	        	mostrarBarraBusqueda();
//	        	Toast.makeText(this, "Presionaste Opcion 2!", Toast.LENGTH_LONG).show();
	        }
	                            break;
	        case R.id.opcion3: Toast.makeText(this, "Presionaste Opcion 3!", Toast.LENGTH_LONG).show();
	                            break;
	    }
	    return true;
	}
	
    private void filtrarLista(long id) {
    	if (!boolBarraBusqueda){// pregunto si hubo una busqueda de texto primero
        	recuperarOriginal();
        	if (id!=0){// cuando el Id es cero muestro todos
//        		String str=String.valueOf(id);
        		Categoria categoriaTemp =categoriaDao.loadByRowId(id);
        		String str=String.valueOf(categoriaTemp.getIdCategoria());
        		
            	
                List<Producto> productosAux = productoDao.queryBuilder()
                		.where(Properties.IdCategoria.eq(str))
                		.orderAsc(Properties.Id).list();

                elementoListaDao.deleteAll();
        	
        		for(int i=0;i<productosAux.size();i++){
        			String strCantidad="";
        			int cantidadAux=encuentraElegido(productosAux.get(i).getId());
        			if (cantidadAux!=-1){
        				strCantidad="Cantidad: "+cantidadAux;
        			}
        			ElementoLista elemento = new ElementoLista(null,productosAux.get(i).getDescripcion(),"Precio: "+productosAux.get(i).getPrecio(),strCantidad,productosAux.get(i).getId());
        			elementoListaDao.insert(elemento);
        	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
        		}
        		cursorElementoLista.requery();	
        	}
        	else{    		        	
                List<Producto> productosAux = productoDao.loadAll();
                elementoListaDao.deleteAll();
                
        		for(int i=0;i<productosAux.size();i++){
        			String strCantidad="";
        			int cantidadAux=encuentraElegido(productosAux.get(i).getId());
        			if (cantidadAux!=-1){//cuando la cantidad es menos uno significa que no fue encontrado
        				strCantidad="Cantidad: "+cantidadAux;
        			}
        			ElementoLista elemento = new ElementoLista(null,productosAux.get(i).getDescripcion(),"Precio: "+productosAux.get(i).getPrecio(),strCantidad,productosAux.get(i).getId());
        			elementoListaDao.insert(elemento);
        		}
        		cursorElementoLista.requery();    		
        	}        	
    	}
    	else{
    		boolBarraBusqueda=false;
    	}    		
	}
	
    private int encuentraElegido(Long id) {
    	int resul=-1;
    	int idAux=safeLongToInt(id);
    	for(int i=0;i<listaPedidoLinea.size();i++){
    		if (listaPedidoLinea.get(i).getIdProducto()==idAux){
    			resul=listaPedidoLinea.get(i).getCantidad();
    			break;
    		}
    	}
    	
		// TODO Auto-generated method stub
		return resul;
	}
	private void chequeaProductoRepetido(int idProductoAux) {
    	int resul=-1;
    	for(int i=0;i<listaPedidoLinea.size();i++){
    		
    		if (listaPedidoLinea.get(i).getIdProducto()==idProductoAux){
    			resul=i;
    			break;
    		}
    	}
    	if (resul!=-1){
    		listaPedidoLinea.remove(resul);
    	}
    		
    	
		// TODO Auto-generated method stub
		
	}

	private void guardaListaOriginal() {
		this.listaProductoOriginal=productoDao.loadAll();
		
	}
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();

		for(int i=0;i<listaProductoOriginal.size();i++){

			ElementoLista elemento = new ElementoLista(null,listaProductoOriginal.get(i).getDescripcion(),"Precio: "+listaProductoOriginal.get(i).getPrecio(),null,listaProductoOriginal.get(i).getId());
			elementoListaDao.insert(elemento);
		}
		cursorElementoLista.requery();
	}
    private void cargarBaseLocal() {
//        daoProducto = new DAOProducto(BuscarProductos.this);
//        listaProducto = daoProducto.getAllProductos(); //obtiene los clientes
//        listaCategoria=daoProducto.getAllCategorias();
//       
//		productoDao.deleteAll();
//		categoriaDao.deleteAll();
//		elementoListaDao.deleteAll();
//		
//        
//		for(int i=0;i<listaProducto.size();i++){
//
//			Producto productoAux = new Producto(null,listaProducto.get(i).getIdProducto(),listaProducto.get(i).getDescripcion(),listaProducto.get(i).getPrecio(),listaProducto.get(i).getStock(),listaProducto.get(i).getActivo(),listaProducto.get(i).getIdCategoria(),listaProducto.get(i).getIdMarca());
//			ElementoLista elemento = new ElementoLista(null,listaProducto.get(i).getDescripcion(),"Precio: "+listaProducto.get(i).getPrecio(),null,listaProducto.get(i).getId());
//			elementoListaDao.insert(elemento);
//			productoDao.insert(productoAux);			
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//		for(int i=0;i<listaCategoria.size();i++){
//			Categoria categoria = new Categoria(null,listaCategoria.get(i).getIdCategoria(),listaCategoria.get(i).getDescripcion());
//			categoriaDao.insert(categoria);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//		guardaListaOriginal();
//		cursorSpinner.requery();
//		cursorElementoLista.requery();
////        cursor.requery();
//    	setListAdapter(adapterElementoLista);
//    	spinner_categoria.setAdapter(adapterSpinner);
	}
    
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            this.boolBarraBusqueda=true;
            BuscaPorNombre(query);
        }
    }
	private void BuscaPorNombre(String query) {
		// TODO Auto-generated method stub
//    	recuperarOriginal();
    	if (query.length()!=0){    		
            List<Producto> productosAux = productoDao.queryBuilder()
            		.where(Properties.Descripcion.like("%"+query+"%"))
            		.orderAsc(Properties.Id).list();

            elementoListaDao.deleteAll();
    	
    		for(int i=0;i<productosAux.size();i++){
    			String strCantidad="";
    			int cantidadAux=encuentraElegido(productosAux.get(i).getId());
    			if (cantidadAux!=-1){
    				strCantidad="Cantidad: "+cantidadAux;
    			}
    			ElementoLista elemento = new ElementoLista(null,productosAux.get(i).getDescripcion(),"Precio: "+productosAux.get(i).getPrecio(),strCantidad,productosAux.get(i).getId());
    			elementoListaDao.insert(elemento);
    		}
    		cursorElementoLista.requery();	
    	}
    	boolBarraBusqueda=false;		
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
