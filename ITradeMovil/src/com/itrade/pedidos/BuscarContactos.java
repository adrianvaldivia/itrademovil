package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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



import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.R;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.ContactoDao.Properties;

public class BuscarContactos extends ListActivity{
	InputMethodManager imm;
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ContactoDao contactoDao;
    private ElementoListaDao elementoListaDao;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;

    private Cursor cursor;
    SimpleCursorAdapter adapter;
    
    private EditText editText;
//    DAOContacto daoContacto=null;
	
	private Button button_buscar;	
	Contacto contacto= new Contacto();
	List<Contacto> listaContacto;
	
	public long idusuario;
	List<Contacto> listaContactoOriginal;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directorioadministrativo);
        imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        
        Bundle bundle=getIntent().getExtras();
        idusuario = bundle.getLong("idusuario");
        setTitle("I Trade - Contactos");
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        contactoDao = daoSession.getContactoDao();
        elementoListaDao = daoSession.getElementoListaDao();
        
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
        
                          
        button_buscar = (Button) findViewById(R.id.buttonbuscar);
        editText = (EditText) findViewById(R.id.editTextCliente);

	    button_buscar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buscarContacto();
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); 
			}


	 	});
	    addUiListeners();
    }
    
    protected void addUiListeners() {
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	buscarContacto();
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
                	buscarContacto();
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
    
	private void buscarContacto() {
        String texto = editText.getText().toString();
//        editText.setText("");
        List<Contacto> contactosAux = contactoDao.queryBuilder()
        		.where(Properties.Nombre.like("%"+texto+"%"))
        		.orderAsc(Properties.Id).list();
//        Double x;
//		Double y;
//		clienteDao.deleteAll();
		elementoListaDao.deleteAll();
        
		for(int i=0;i<contactosAux.size();i++){
			ElementoLista elemento = new ElementoLista(null,contactosAux.get(i).getNombre(),"Telefono: "+contactosAux.get(i).getTelefono(),null,contactosAux.get(i).getId());
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	                
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     //inicio cambios chichan
     Intent intent = new Intent(BuscarContactos.this, DetalleContacto.class);
     intent.putExtra("idusuario", idusuario);
     intent.putExtra("idcontacto", id);
     startActivity(intent);
     
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
	        	Toast.makeText(this, "Sincronizando!", Toast.LENGTH_SHORT).show();
	        	cargarBaseLocal();	        	
	        
	        }
            break;
	    }
	    return true;
	}
    private void cargarBaseLocal() {
//    	daoProspecto = new DAOProspecto(this);
//    	listaProspecto = daoProspecto.buscarProspectosxVendedor(""+idusuario,"");
//        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
//        Double x;
//		Double y;
//		prospectoDao.deleteAll();
//		elementoListaDao.deleteAll();
//        
//		for(int i=0;i<listaProspecto.size();i++){
//			x=listaProspecto.get(i).getLatitud();
//			y=listaProspecto.get(i).getLongitud();
//			Prospecto prospecto2 = new Prospecto(null,listaProspecto.get(i).getIdPersona(),listaProspecto.get(i).getIdProspecto(),
//					listaProspecto.get(i).getNombre(),listaProspecto.get(i).getApePaterno(),
//					listaProspecto.get(i).getRazon_Social(),listaProspecto.get(i).getRazon_Social(),
//					listaProspecto.get(i).getRUC(),x,y,listaProspecto.get(i).getDireccion(),
//					listaProspecto.get(i).getIdCobrador(),listaProspecto.get(i).getIdUsuario(),
//					listaProspecto.get(i).getActivo(),listaProspecto.get(i).getMontoActual(),
//					listaProspecto.get(i).getDNI(),listaProspecto.get(i).getFechNac(),
//					listaProspecto.get(i).getTelefono(),listaProspecto.get(i).getEmail()
//					);
//			prospecto2.setActivo("A");//util para el checkin del mapa
//	        prospectoDao.insert(prospecto2);
//	        long temp=0;
////	        temp=temp+listaCliente.get(i).getIdCliente();//aqui estaba el error
//	        temp=temp+i+1;//aca tambien habia error
//			ElementoLista elemento = new ElementoLista(null,listaProspecto.get(i).getRazon_Social(),"RUC: "+listaProspecto.get(i).getRUC(),null,temp);
//			elementoListaDao.insert(elemento);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//        cursorElementoLista.requery();		
//        guardaListaOriginal();
//		
	}
    private void guardaListaOriginal() {
		// TODO Auto-generated method stub
    	this.listaContactoOriginal=contactoDao.loadAll();
		
	}
	private void recuperarOriginal() {
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaContactoOriginal.size();i++){
	        long temp=0;
	        temp=temp+listaContactoOriginal.get(i).getId();
			ElementoLista elemento = new ElementoLista(null,listaContactoOriginal.get(i).getNombre(),"Telefono: "+listaContactoOriginal.get(i).getTelefono(),null,temp);
			elementoListaDao.insert(elemento);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
        cursorElementoLista.requery();	
		
	}

	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}
	@Override
	protected void onDestroy() { 
		db.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}


}