package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.R;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.db.DAOPedido;
 
public class BuscarPedidos extends ListActivity{
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PedidoDao pedidoDao;
    private ElementoListaDao elementoListaDao;
    private ClienteDao clienteDao;

//    private Cursor cursor;
    private Cursor cursorElementoLista;
//    SimpleCursorAdapter adapter;
    SimpleCursorAdapter adapterElementoLista;
	
    List<Pedido> listaPedido;  
    List<Pedido> listaPedidoOriginal;  
    Pedido pedido= new Pedido();
	List<String> lista = new ArrayList<String>();
	DAOPedido daoPedido =null;
//	private Button button_cearpedido;
	long idUsuario;
	private EditText editText;
	private Button button_buscar;
	private ImageButton  button_clientes;
	InputMethodManager imm;
	Cliente cliente= new Cliente();
	String razonsocial="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscarpedidosfusion);
		imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
		
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pedidoDao = daoSession.getPedidoDao();
        elementoListaDao= daoSession.getElementoListaDao();
        clienteDao= daoSession.getClienteDao();
                
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
        int[] toElementoLista = { R.id.text1, R.id.text2 };
        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
        		toElementoLista);
        setListAdapter(adapterElementoLista);
        //fin green Day de Elementos Lista
//        this.guardaListaOriginal();
//        this.recuperarOriginal();
 
		Bundle bundle = getIntent().getExtras();
		idUsuario = bundle.getLong("idusuario");
		this.razonsocial=bundle.getString("razonsocial");
        setTitle("iTrade - Pedidos");
        editText = (EditText) findViewById(R.id.editTextCliente);
        button_buscar = (Button) findViewById(R.id.buttonbuscar);
        button_clientes = (ImageButton) findViewById(R.id.btnBuscarClientes);
        if (razonsocial!=null){
            if (!(razonsocial.length()==0)){
            	editText.setText(razonsocial);
            	buscarPedido2(razonsocial);
            }
        	
        }
	    button_buscar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(BuscarClientesGreenDao.this, "Buscar", Toast.LENGTH_LONG).show();
				buscarPedido();
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); 
			}
	 	});
	    button_clientes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(BuscarPedidos.this,  BuscarClientesGreenDao.class);
				intent.putExtra("idusuario", idUsuario);
				startActivity(intent);		
			}
	 	});
	    addUiListeners();
 
	}
	
    protected void addUiListeners() {
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	buscarPedido();
                	imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //oculto el teclado
                    return true;
                }
                return false;
            }
        });

        final View button = findViewById(R.id.buttonbuscar);
        button.setEnabled(false);
        
        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;//habilita o deshabilita el boton segun sea el caso
                button.setEnabled(enable);
                if (enable==false){
                    if (razonsocial!=null){
                        if(razonsocial.length()==0){
                        	recuperarOriginal();
                        }        
                    }
                    else
                    	recuperarOriginal();
//                	recuperarOriginal();
                }
                else
                	buscarPedido();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            	Log.d("Before", "Before: ");
            	//Toast.makeText(NoteActivity.this, "Before", Toast.LENGTH_LONG).show();
            }

            public void afterTextChanged(Editable s) {
//            	Log.d("After", "After: ");
            	//Toast.makeText(NoteActivity.this, "After", Toast.LENGTH_LONG).show();
            }
        });        
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	this.encuentraPedido(id);
    	long longTemp=0;
    	Cliente clienteTemp=null;
    	if (pedido!=null){
    		longTemp=longTemp+pedido.getIdCliente();
    		clienteTemp= this.encuentraCliente(longTemp);
    	}		
    	Intent intent = new Intent(BuscarPedidos.this, DetallePedido.class);
    	if (clienteTemp!=null && pedido!=null){
    		intent.putExtra("nombre", clienteTemp.getRazon_Social());
   	     	intent.putExtra("apellidos", clienteTemp.getRUC());
   	     	intent.putExtra("idcliente", clienteTemp.getIdCliente());
   	     	intent.putExtra("idusuario", idUsuario );
   	     	intent.putExtra("idpedidolocal", pedido.getId() );
   	     	intent.putExtra("monto", pedido.getMontoSinIGV() );
   	     	startActivity(intent);
    	}
    }
	private void encuentraPedido(long id) {
		ElementoLista elementoAux=  elementoListaDao.loadByRowId(id);
		pedido=pedidoDao.loadByRowId(elementoAux.getIdElemento());
		// TODO Auto-generated method stub
		
	}
	private void buscarPedido2(String texto) {
        ArrayList<String> listaGenerica = encuentraIdCliente(texto);//lista con los Ids de los clientes encontrados
        elementoListaDao.deleteAll();
        if(listaGenerica.size()>0){
        	for(int k=0;k<listaGenerica.size();k++){
        		String strIdCliente=listaGenerica.get(k);
        		List<Pedido> pedidosAux = pedidoDao.queryBuilder()
              		.where(com.itrade.model.PedidoDao.Properties.IdCliente.eq(strIdCliente))
              		.orderAsc(com.itrade.model.PedidoDao.Properties.Id).list();
      		              
        		for(int i=0;i<pedidosAux.size();i++){
        			long longTemp=0;
        			longTemp=longTemp+pedidosAux.get(i).getIdCliente();
        			Cliente clienteTemp= this.encuentraCliente(longTemp);
        			ElementoLista elemento = new ElementoLista(null,clienteTemp.getRazon_Social(),"Monto Total: "+pedidosAux.get(i).getMontoSinIGV(),null,pedidosAux.get(i).getId());
      						
        			elementoListaDao.insert(elemento);
        			//Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
        		}
            }
        }                
        cursorElementoLista.requery();	                
    }

	private void buscarPedido() {
        String texto = editText.getText().toString();
        ArrayList<String> listaGenerica = encuentraIdCliente(texto);//lista con los Ids de los clientes encontrados
        elementoListaDao.deleteAll();
        if(listaGenerica.size()>0){
        	for(int k=0;k<listaGenerica.size();k++){
        		String strIdCliente=listaGenerica.get(k);
        		List<Pedido> pedidosAux = pedidoDao.queryBuilder()
              		.where(com.itrade.model.PedidoDao.Properties.IdCliente.eq(strIdCliente))
              		.orderAsc(com.itrade.model.PedidoDao.Properties.Id).list();
      		              
        		for(int i=0;i<pedidosAux.size();i++){
        			long longTemp=0;
        			longTemp=longTemp+pedidosAux.get(i).getIdCliente();
        			Cliente clienteTemp= this.encuentraCliente(longTemp);
        			ElementoLista elemento = new ElementoLista(null,clienteTemp.getRazon_Social(),"Monto Total: "+pedidosAux.get(i).getMontoSinIGV(),null,pedidosAux.get(i).getId());
      						
        			elementoListaDao.insert(elemento);
        			//Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
        		}
            }
        }                
        cursorElementoLista.requery();	                
    }

    private ArrayList<String> encuentraIdCliente(String razonSocial) {
    	ArrayList<String> listaGenerica = new ArrayList<String>();
		// TODO Auto-generated method stub
    	String str="";
        List<Cliente> clientesAux = clienteDao.queryBuilder()
			.where(com.itrade.model.ClienteDao.Properties.Razon_Social.like("%"+razonSocial+"%"))
			.orderAsc(com.itrade.model.ClienteDao.Properties.Id).list();
    	
        if (clientesAux!=null){
        	if(clientesAux.size()>0){
        		for(int i=0;i<clientesAux.size();i++){
        			Cliente clienteTemp=clientesAux.get(i);
                	str=""+clienteTemp.getIdCliente();//posible error
                	listaGenerica.add(str);
        		}
        			
        	}
        	else
        		str="-1";
        }
    	
    	return listaGenerica;		
	}
    private Cliente encuentraCliente(long idCliente) {
    	Cliente clienteTemp = new Cliente();
		// TODO Auto-generated method stub
    	String strIdCliente=""+idCliente;
        List<Cliente> clientesAux = clienteDao.queryBuilder()
			.where(com.itrade.model.ClienteDao.Properties.IdCliente.eq(strIdCliente))
			.orderAsc(com.itrade.model.ClienteDao.Properties.Id).list();
    	
        if (clientesAux!=null){
        	if(clientesAux.size()>0){
        		clienteTemp=clientesAux.get(0);            		
        	}
        	else
        		clienteTemp=null;;
        }
    	
    	return clienteTemp;		
	}

	private void cargarBaseLocal() {
//    	daoPedido = new DAOPedido(BuscarPedidos.this);
//    	listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos
//
//		pedidoDao.deleteAll();
//		elementoListaDao.deleteAll();
//        
//		for(int i=0;i<listaPedido.size();i++){
//			Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),listaPedido.get(i).getNumVoucher(),listaPedido.get(i).getMontoTotal());
//			pedidoDao.insert(pedido);
//			long longTemp=0;
//			longTemp=longTemp+listaPedido.get(i).getIdCliente();
//			Cliente clienteTemp= this.encuentraCliente(longTemp);
//			ElementoLista elemento = new ElementoLista(null,clienteTemp.getRazon_Social(),"Monto Total: "+listaPedido.get(i).getMontoSinIGV(),null,listaPedido.get(i).getId());
//			elementoListaDao.insert(elemento);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//        cursorElementoLista.requery();
	}
    
    private void guardaListaOriginal() {
		// TODO Auto-generated method stub
    	this.listaPedidoOriginal=pedidoDao.loadAll();
		
	}
    
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaPedidoOriginal.size();i++){
//			Cliente cliente = clienteDao.loadByRowId(listaPedidoOriginal.get(i).getIdCliente());
			long longTemp=0;
			longTemp=longTemp+listaPedidoOriginal.get(i).getIdCliente();
			Cliente clienteTemp= this.encuentraCliente(longTemp);
			if (clienteTemp!=null){
				ElementoLista elemento = new ElementoLista(null,clienteTemp.getRazon_Social(),"Monto Total: "+listaPedidoOriginal.get(i).getMontoSinIGV(),null,listaPedidoOriginal.get(i).getId());
				elementoListaDao.insert(elemento);
			}			
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuagenda, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	Toast.makeText(this, "Sincronizando!", Toast.LENGTH_LONG).show();
	        	cargarBaseLocal();	        	
	                            break;
	                           }	      
	    }
	    return true;
	}
	@Override
	public void onResume() {
        
        if (razonsocial!=null){
            if(razonsocial.length()==0){
            	this.guardaListaOriginal();
            	recuperarOriginal();
            }
            else{
            	buscarPedido2(razonsocial);//cuando lo llama la ventana de un cliente especifico
            }
        }
        else{
        	this.guardaListaOriginal();
        	recuperarOriginal();
        }
        	

		super.onResume();
	}
	@Override
	protected void onDestroy() {
		db.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}
}