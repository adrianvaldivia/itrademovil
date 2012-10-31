package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
	List<String> lista = new ArrayList<String>();
	DAOPedido daoPedido =null;
//	private Button button_cearpedido;
	private Button button_regresar;
	long idUsuario;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verpedidos);
		
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pedidoDao = daoSession.getPedidoDao();
        elementoListaDao= daoSession.getElementoListaDao();
        clienteDao= daoSession.getClienteDao();
        
//        // Segunda parte
//        String textColumn = PedidoDao.Properties.IdCliente.columnName;
//        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
//        cursor = db.query(pedidoDao.getTablename(), pedidoDao.getAllColumns(), null, null, null, null, orderBy);
//        String[] from = { textColumn, PedidoDao.Properties.MontoTotal.columnName };
//        int[] to = { android.R.id.text1, android.R.id.text2 };
//        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
//                to);
//        setListAdapter(adapter);
        // Fin green Day
        
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
        int[] toElementoLista = { android.R.id.text1, android.R.id.text2 };
        adapterElementoLista = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursorElementoLista, fromElementoLista,
        		toElementoLista);
        setListAdapter(adapterElementoLista);
        //fin green Day de Elementos Lista
        this.guardaListaOriginal();
        this.recuperarOriginal();
 
		Bundle bundle = getIntent().getExtras();
		idUsuario = bundle.getLong("idusuario");
        setTitle("iTrade - Pedidos");
        
//        button_cearpedido = (Button) findViewById(R.id.buttoncrearpedido);
//        button_regresar = (Button) findViewById(R.id.buttonregresar);
        
     
        
//        lista= Convierte(listaIncidencia);
//        ListView lv = getListView(); 
//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
//        button_cearpedido.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				//Toast.makeText(BuscarPedidos.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(BuscarPedidos.this, BuscarClientesGreenDao.class);		
//				intent.putExtra("idusuario", idusuario);
//				intent.putExtra("boolVer", 0);//boolean que indica quien llamo a la ventana
//				startActivity(intent);
//			}
//	 	});
//        button_regresar.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				//Toast.makeText(BuscarPedidos.this, "pruebaa", Toast.LENGTH_LONG).show();
//				BuscarPedidos.this.finish();
//			}
//	 	});
 
	}


    private void cargarBaseLocal() {
    	daoPedido = new DAOPedido(BuscarPedidos.this);
    	listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos

		pedidoDao.deleteAll();
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaPedido.size();i++){
			Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),listaPedido.get(i).getNumVoucher(),listaPedido.get(i).getMontoTotal());
			pedidoDao.insert(pedido);
			Cliente cliente = clienteDao.loadByRowId(listaPedido.get(i).getIdCliente());
			ElementoLista elemento = new ElementoLista(null,cliente.getRazon_Social(),"Monto Total: "+listaPedido.get(i).getMontoTotal(),null,listaPedido.get(i).getId());
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();		
	}
    
    private void guardaListaOriginal() {
		// TODO Auto-generated method stub
    	this.listaPedidoOriginal=pedidoDao.loadAll();
		
	}
    
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaPedidoOriginal.size();i++){
			Cliente cliente = clienteDao.loadByRowId(listaPedidoOriginal.get(i).getIdCliente());
			ElementoLista elemento = new ElementoLista(null,cliente.getRazon_Social(),"Monto Total: "+listaPedidoOriginal.get(i).getMontoTotal(),null,listaPedidoOriginal.get(i).getId());
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	
		
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
	@Override
	protected void onDestroy() {
		db.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}
}