package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;
import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Producto;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.ContactoDao.Properties;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class DetalleContacto extends ListActivity{

	private Button button_crearpedidos;
	private ImageButton  button_clientes;
	private ImageButton  button_pedidos;
	private TextView txt_nombre;
	private TextView txt_ruc;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";	
	public String apellidos="";
	public String telefono="";
	public String email="";
	public long idusuario;
	public long idpersona;//id interno sqlite
	//green dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private ContactoDao contactoDao;
    //fin green dao

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallecontactofusion);
	    bundle = getIntent().getExtras();	
		idusuario = bundle.getLong("idusuario");
		idpersona=bundle.getLong("idpersona");
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();        
        elementoListaDao = daoSession.getElementoListaDao();
        contactoDao = daoSession.getContactoDao();
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
        
        

		

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Informacion del Cliente");
        //daoCliente = new DAOCliente();
        button_crearpedidos = (Button) findViewById(R.id.buttoncrearpedido);
        button_clientes = (ImageButton) findViewById(R.id.btnBuscarClientes);
        button_pedidos = (ImageButton) findViewById(R.id.btnBuscarPedidos);
        txt_nombre = (TextView) findViewById(R.id.txtnombrecliente);
        txt_ruc = (TextView) findViewById(R.id.txtruccliente);
	    obtenerDatosContacto();//obtiene los datos del SQLite
	    Convierte();
	    button_crearpedidos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+telefono.toString()+""));
				startActivity(callIntent);
				
	
			}
	 	});

    }

    private void obtenerDatosContacto() {
		// TODO Auto-generated method stub
		String str="";
		//Producto productoAux=productoDao.loadByRowId(idProducto);
		str=str+idpersona;
        List<Contacto> contactosAux = contactoDao.queryBuilder()
        		.where(Properties.IdPersona.eq(str))
        		.orderAsc(Properties.Id).list();
        
        if (contactosAux!=null){
        	if(contactosAux.size()>0){
        		Contacto contactoTemp=contactosAux.get(0);
            	nombre = contactoTemp.getNombre();
            	apellidos = contactoTemp.getApePaterno() + " " + contactoTemp.getApeMaterno(); 
            	telefono =  contactoTemp.getTelefono();
            	email = contactoTemp.getEmail();
        	}
        	else
            	nombre="Error";
        }
    	           			
	}
	public void Convierte(){
		elementoListaDao.deleteAll();
		long i=1;
		this.txt_nombre.setText(this.nombre);
		this.txt_ruc.setText(""+this.apellidos);
		ElementoLista elemento = new ElementoLista(null,"Telefono:",""+telefono,null,i);
		elementoListaDao.insert(elemento);
		i++;
		ElementoLista elemento2 = new ElementoLista(null,"Email:",""+email,null,i);
		elementoListaDao.insert(elemento2);
		cursorElementoLista.requery();
//		lista.add("ID: "+this.idcliente);
	}


 
	@Override
	protected void onDestroy() {
		//Toast.makeText(DetalleCliente.this, "destruyendo", Toast.LENGTH_LONG).show();
		//elementoListaDao.deleteAll();
		//cursorElementoLista.requery();
		db.close();
		cursorElementoLista.close();
	    super.onDestroy();
	}
}