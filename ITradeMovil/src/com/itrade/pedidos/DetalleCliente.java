package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Producto;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.ClienteDao.Properties;


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
import android.widget.TextView;
import android.widget.Toast;


public class DetalleCliente extends ListActivity{

	private Button button_verpedidos;
	private Button button_crearpedidos;
	private TextView txt_nombre;
	private TextView txt_ruc;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";
	public String apellidos="";
	public int idcliente;
	public long idusuario;
	int boolVer;
	//green dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private ClienteDao clienteDao;
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
        clienteDao = daoSession.getClienteDao();
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
        
        
	    bundle = getIntent().getExtras();	
		idcliente = bundle.getInt("idcliente");
		idusuario = bundle.getLong("idusuario");
		obtenerDatosCliente();//obtiene los datos del SQLite
		boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Informacion del Cliente");
        //daoCliente = new DAOCliente();
        button_verpedidos = (Button) findViewById(R.id.buttonverpedidos);
        button_crearpedidos = (Button) findViewById(R.id.buttoncrearpedido);
        txt_nombre = (TextView) findViewById(R.id.txtnombrecliente);
        txt_ruc = (TextView) findViewById(R.id.txtruccliente);

        List<String> listaCliente =null;  
//        listaCliente = daoCliente.getCliente(0); //obtiene los clientes
               
        Convierte(listaCliente);

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
			     intent.putExtra("idusuario", idusuario);
			     startActivity(intent);				
	
			}
	 	});
        if (boolVer==0){//es decir si es que ya no necesito mostrar el cliente sino que llamar defrente a crear pedido
        	saltarToCrearPedido();
        }
    }

    private void obtenerDatosCliente() {
		// TODO Auto-generated method stub
		String str="";
		//Producto productoAux=productoDao.loadByRowId(idProducto);
		str=str+idcliente;
        List<Cliente> clientesAux = clienteDao.queryBuilder()
        		.where(Properties.IdCliente.eq(str))
        		.orderAsc(Properties.Id).list();
        
        if (clientesAux!=null){
        	if(clientesAux.size()>0){
        		Cliente clienteTemp=clientesAux.get(0);
            	nombre=clienteTemp.getRazon_Social();
            	apellidos=clienteTemp.getRUC();
        	}
        	else
            	nombre="Error";
        }
    	
		
	}

	private void saltarToCrearPedido() {
//	     Intent intent = new Intent(DetalleCliente.this, CrearPedido.class);
//	     intent.putExtra("nombre", nombre);
//	     intent.putExtra("apellidos", apellidos);
//	     intent.putExtra("idcliente", idcliente);
//	     intent.putExtra("idusuario", idusuario);
//	     startActivity(intent);		
		
	}

    
	public void Convierte(List<String> lis){
		elementoListaDao.deleteAll();
		long i=1;
		this.txt_nombre.setText(this.nombre);
		this.txt_ruc.setText("RUC: "+this.apellidos);
		ElementoLista elemento = new ElementoLista(null,"Direccion:","HardCoded",null,i);
		elementoListaDao.insert(elemento);
		i++;
		ElementoLista elemento2 = new ElementoLista(null,"Credito Disponible:","HardCoded",null,i);
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