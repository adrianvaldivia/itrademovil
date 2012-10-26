package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.DaoMaster.DevOpenHelper;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class DetalleCliente extends ListActivity{

	private Button button_verpedidos;
	private Button button_crearpedidos;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";
	public String apellidos="";
	public int idcliente;
	public int idempleado;
	int boolVer;
	//green dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    //fin green dao

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalleclientefusion);
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();        
        elementoListaDao = daoSession.getElementoListaDao();
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
        int[] toElementoLista = { android.R.id.text1, android.R.id.text2 };
        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
        		toElementoLista);    
        //fin green Day de Elementos Lista
        
        setListAdapter(adapterElementoLista);
        
        
	    bundle = getIntent().getExtras();	
		nombre = bundle.getString("nombre");
		apellidos = bundle.getString("apellidos");
		idcliente = bundle.getInt("idcliente");
		idempleado = bundle.getInt("idempleado");
		boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Informacion del Cliente");
        //daoCliente = new DAOCliente();
        button_verpedidos = (Button) findViewById(R.id.buttonverpedidos);
        button_crearpedidos = (Button) findViewById(R.id.buttoncrearpedido);

        List<String> listaCliente =null;  
//        listaCliente = daoCliente.getCliente(0); //obtiene los clientes

        List<String> lista =null;
        
        lista= this.Convierte(listaCliente);
//        ListView lv = getListView(); 
//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 

	    button_verpedidos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(DetalleCliente.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
				
				//Intent intent = new Intent(DetalleCliente.this, VerMapaActivity.class);
				
//				startActivity(intent);		
			}
	 	});
	    button_crearpedidos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(DetalleCliente.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
			     Intent intent = new Intent(DetalleCliente.this, CrearPedido.class);
			     intent.putExtra("nombre", nombre);
			     intent.putExtra("apellidos", apellidos);
			     intent.putExtra("idcliente", idcliente);
			     intent.putExtra("idempleado", idempleado);
			     startActivity(intent);				
	
			}
	 	});
        if (boolVer==0){//es decir si es que ya no necesito mostrar el cliente sino que llamar defrente a crear pedido
        	saltarToCrearPedido();
        }
    }

    private void saltarToCrearPedido() {
//	     Intent intent = new Intent(DetalleCliente.this, CrearPedido.class);
//	     intent.putExtra("nombre", nombre);
//	     intent.putExtra("apellidos", apellidos);
//	     intent.putExtra("idcliente", idcliente);
//	     intent.putExtra("idempleado", idempleado);
//	     startActivity(intent);		
		
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
     //Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
    }    
    
	public List<String> Convierte(List<String> lis){
		elementoListaDao.deleteAll();
		List<String> lista=new ArrayList<String>();
		lista.add("Nombre: "+this.nombre);
		lista.add("RUC: "+this.apellidos);
		long i=0;
		ElementoLista elemento = new ElementoLista(null,"Razon Social:",this.nombre,i);
		elementoListaDao.insert(elemento);
		
		ElementoLista elemento2 = new ElementoLista(null,"RUC:",this.apellidos,i);
		elementoListaDao.insert(elemento2);
		cursorElementoLista.requery();
//		lista.add("ID: "+this.idcliente);
		return lista;
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