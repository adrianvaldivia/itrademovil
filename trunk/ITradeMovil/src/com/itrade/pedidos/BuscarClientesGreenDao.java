package com.itrade.pedidos;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.ListActivity;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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


import com.itrade.db.DAOCliente;
import com.itrade.db.DAOPedido;

import com.itrade.model.Cliente;

import com.itrade.model.ClienteDao.Properties;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;

import com.itrade.model.DaoSession;
import com.itrade.model.ClienteDao;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoLinea;
import com.itrade.model.PedidoLineaDao;
import com.itrade.R;


public class BuscarClientesGreenDao extends ListActivity{
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ClienteDao clienteDao;
    private ElementoListaDao elementoListaDao;
    private PedidoDao pedidoDao;
    private PedidoLineaDao pedidoLineaDao;

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapter;
    SimpleCursorAdapter adapterElementoLista;
    
    private EditText editText;
	DAOCliente daoCliente =null;
	DAOPedido daoPedido =null;
	private Button button_vermapa;
	private Button button_buscar;
	private ImageButton  button_pedidos;
	private ImageButton  button_prospectos;
	Cliente cliente= new Cliente();
	List<Cliente> listaCliente;
	List<Cliente> listaClienteOriginal;
	
	public long idUsuario;
	String nombre, apellidos;
	InputMethodManager imm;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(BuscarClientesGreenDao.this, "Createando", Toast.LENGTH_LONG).show();
        setContentView(R.layout.buscarclientesfusion);
        imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);

        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        clienteDao = daoSession.getClienteDao();
        elementoListaDao = daoSession.getElementoListaDao();
        pedidoDao = daoSession.getPedidoDao();
        pedidoLineaDao=daoSession.getPedidoLineaDao();
        //posible error al borrar
        elementoListaDao.deleteAll();
        
        // Fin green Day  
        
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
        int[] toElementoLista = { R.id.text1, R.id.text2 };
        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
        		toElementoLista);    
        //fin green Day de Elementos Lista
        
        setListAdapter(adapterElementoLista);
        guardaListaOriginal();
        recuperarOriginal();
        
        Bundle bundle=getIntent().getExtras();
        idUsuario = bundle.getLong("idusuario");
		nombre =bundle.getString("nombre");
		apellidos=bundle.getString("apellidos");
		Toast.makeText(BuscarClientesGreenDao.this, "Bienvenido, "+nombre+" "+ apellidos, Toast.LENGTH_LONG).show();
        setTitle("Clientes");
        
        button_vermapa = (Button) findViewById(R.id.buttonvermapa);
        button_buscar = (Button) findViewById(R.id.buttonbuscar);
        button_pedidos = (ImageButton) findViewById(R.id.btnBuscarPedidos);
        button_prospectos = (ImageButton) findViewById(R.id.btnBuscarProspectos);
        editText = (EditText) findViewById(R.id.editTextCliente);

	    button_vermapa.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(BuscarClientesGreenDao.this, UbicacionCheckInActivity.class);
				intent.putExtra("idusuario", idUsuario);
				startActivity(intent);		
			}
	 	});
	    button_pedidos.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(BuscarClientesGreenDao.this,  BuscarPedidos.class);
				intent.putExtra("idusuario", idUsuario);
				startActivity(intent);		
			}
	 	});
	    button_prospectos.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(BuscarClientesGreenDao.this,  BuscarProspectos.class);
				intent.putExtra("idusuario", idUsuario);
				startActivity(intent);		
			}
	 	});
	    button_buscar.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(BuscarClientesGreenDao.this, "Buscar", Toast.LENGTH_LONG).show();
				buscarCliente();
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); 
			}
	 	});
	    addUiListeners();
	    
    }

    protected void addUiListeners() {
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	buscarCliente();
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
                	recuperarOriginal();
                }
                else
                	buscarCliente();
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
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
//     String selection = l.getItemAtPosition(position).toString();
     
     //inicio cambios chichan
//	 int debug=0;
//	 debug++;
     this.encuentraCliente(id);
     int temp=cliente.getIdCliente();
//    Toast.makeText(this, "IdCliente: "+temp, Toast.LENGTH_LONG).show();
     Intent intent = new Intent(BuscarClientesGreenDao.this, DetalleCliente.class);
     intent.putExtra("idcliente", temp);
     intent.putExtra("idusuario", idUsuario);
     startActivity(intent);
     
    }    
    
	private void encuentraCliente(long id) {
		ElementoLista elementoAux=  elementoListaDao.loadByRowId(id);
		cliente=clienteDao.loadByRowId(elementoAux.getIdElemento());
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
	        case R.id.opcion3: {
//	        	Toast.makeText(this, "Cerrando Sesion!", Toast.LENGTH_LONG).show();
	        	cerrarSesion();
	        }
	                            break;
	    }
	    return true;
	}


	private void cargarBaseLocal() {
        daoCliente = new DAOCliente(this);  
        listaCliente = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
        Double x;
		Double y;
		clienteDao.deleteAll();
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaCliente.size();i++){
			x=listaCliente.get(i).getLatitud();
			y=listaCliente.get(i).getLongitud();
			Cliente cliente2 = new Cliente(null,listaCliente.get(i).getIdPersona(),listaCliente.get(i).getIdCliente(),
					listaCliente.get(i).getNombre(),listaCliente.get(i).getApePaterno(),
					listaCliente.get(i).getRazon_Social(),listaCliente.get(i).getRazon_Social(),
					listaCliente.get(i).getRUC(),x,y,listaCliente.get(i).getDireccion(),
					listaCliente.get(i).getIdCobrador(),listaCliente.get(i).getIdUsuario(),
					listaCliente.get(i).getActivo());
			cliente2.setActivo("A");//util para el checkin del mapa
	        clienteDao.insert(cliente2);
	        long temp=0;
//	        temp=temp+listaCliente.get(i).getIdCliente();//aqui estaba el error
	        temp=temp+i+1;//aca tambien habia error
			ElementoLista elemento = new ElementoLista(null,listaCliente.get(i).getRazon_Social(),"RUC: "+listaCliente.get(i).getRUC(),null,temp);
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();		
        guardaListaOriginal();
	}

	private void buscarCliente() {
        String texto = editText.getText().toString();
//        editText.setText("");
        List<Cliente> clientesAux = clienteDao.queryBuilder()
        		.where(Properties.Razon_Social.like("%"+texto+"%"))
        		.orderAsc(Properties.Id).list();
//        Double x;
//		Double y;
//		clienteDao.deleteAll();
		elementoListaDao.deleteAll();
        
		for(int i=0;i<clientesAux.size();i++){
			ElementoLista elemento = new ElementoLista(null,clientesAux.get(i).getRazon_Social(),"RUC: "+clientesAux.get(i).getRUC(),null,clientesAux.get(i).getId());
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	                
    }
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}


    private void guardaListaOriginal() {
		// TODO Auto-generated method stub
    	this.listaClienteOriginal=clienteDao.loadAll();
		
	}
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaClienteOriginal.size();i++){
	        long temp=0;
	        temp=temp+listaClienteOriginal.get(i).getId();
			ElementoLista elemento = new ElementoLista(null,listaClienteOriginal.get(i).getRazon_Social(),"RUC: "+listaClienteOriginal.get(i).getRUC(),null,temp);
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	
		
	}
	
    private void cerrarSesion() {
		// TODO Auto-generated method stub
	    new AlertDialog.Builder(this)
        .setTitle("Cerrar Sesion")
        .setMessage("Debe tener conexion, para sincroninzar" +
        		"los datos. Realmente Desea Cerrar la Sesion?")
        .setNegativeButton("No", null)
        .setNeutralButton("Solo Salir", new OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
//            	Toast.makeText(MenuLista.this, "Yaaaa", Toast.LENGTH_SHORT).show();
        		BuscarClientesGreenDao.super.onBackPressed();
            	
            }
        })
        .setPositiveButton("Si", new OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            		sincronizarBaseSubida();
            		BuscarClientesGreenDao.super.onBackPressed();
            	
            }
        }).create().show();	
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
//		Toast.makeText(BuscarClientesGreenDao.this, "Restarteando", Toast.LENGTH_LONG).show();
		recuperarOriginal();	
        imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //oculto el teclado
	}
	
	@Override
	protected void onDestroy() {
		recuperarOriginal();		
		db.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		Minimizar();
	}
	
	@Override
	public void finish(){
		super.finish();		
	}
	private void Minimizar() {
		// TODO Auto-generated method stub
		this.moveTaskToBack(true);		
	}
	private void sincronizarBaseSubida() {
		long idPedido=0;
		long idPedidoLocal;
		int tam=0;
        daoPedido = new DAOPedido(BuscarClientesGreenDao.this);
        List<Pedido> pedidosAux = pedidoDao.queryBuilder()
        		.where(com.itrade.model.PedidoDao.Properties.NumVoucher.eq("N"))
        		.orderAsc(com.itrade.model.PedidoDao.Properties.Id).list();
        tam=pedidosAux.size();
        for(int i=0;i<tam;i++){
        	Pedido pedidoTemp=pedidosAux.get(i);
        	pedidoTemp.setNumVoucher("A");;
        	pedidoDao.deleteByKey(pedidoTemp.getId());
        	pedidoDao.insert(pedidoTemp);
        	idPedidoLocal=pedidoTemp.getId();
        	idPedido=daoPedido.registrarPedido(pedidoTemp);//id de bd externa
            List<PedidoLinea> pedidosLineaAux = pedidoLineaDao.queryBuilder()
            		.where(com.itrade.model.PedidoLineaDao.Properties.IdPedido.eq(idPedidoLocal))
            		.orderAsc(com.itrade.model.PedidoLineaDao.Properties.Id).list();
            for(int j=0;j<pedidosLineaAux.size();j++){
            	PedidoLinea pedidoLineaTemp=pedidosLineaAux.get(j);
            	pedidoLineaTemp.setIdPedido(idPedido);//lo setteo con el Id de BD externa
            	daoPedido.registrarPedidoLinea(pedidoLineaTemp);
            }
        }
	}
}