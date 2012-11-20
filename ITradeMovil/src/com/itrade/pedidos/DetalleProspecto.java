package com.itrade.pedidos;

import java.util.List;

import com.itrade.R;
import com.itrade.R.id;
import com.itrade.R.layout;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Prospecto;
import com.itrade.model.ProspectoDao;
import com.itrade.model.ProspectoDao.Properties;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DetalleProspecto extends ListActivity{

	private Button button_verpedidos;
	private Button button_aceptar;
	private ImageButton  button_clientes;
	private ImageButton  button_pedidos;
	private TextView txt_nombre;
	private TextView txt_ruc;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";	
	public String apellidos="";
	public String direccion="";
	public Double montoCredito=0.0;
	public int idcliente;
	public long idusuario;
	int boolVer;
	//green dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private ProspectoDao prospectoDao;
    //fin green dao

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalleprospecto2);
        
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();        
        elementoListaDao = daoSession.getElementoListaDao();
        prospectoDao = daoSession.getProspectoDao();
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
		Log.d("***idprospecto", idcliente+"");
		idusuario = bundle.getLong("idusuario");
		
		obtenerDatosCliente();//obtiene los datos del SQLite
		boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity

//        Bundle bundle=getIntent().getExtras();
        setTitle("iTrade - Informacion del Prospecto");
        //daoCliente = new DAOCliente();
        button_verpedidos = (Button) findViewById(R.id.buttonverpedidos);
        button_aceptar = (Button) findViewById(R.id.buttoncrearpedido);
        button_clientes = (ImageButton) findViewById(R.id.btnBuscarClientes);
        button_pedidos = (ImageButton) findViewById(R.id.btnBuscarPedidos);
        txt_nombre = (TextView) findViewById(R.id.txtnombrecliente);
        txt_ruc = (TextView) findViewById(R.id.txtruccliente);

        List<String> listaCliente =null;  
//        listaCliente = daoCliente.getCliente(0); //obtiene los clientes
               
        Convierte(listaCliente);

//	    button_verpedidos.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				//Toast.makeText(DetalleCliente.this, "pruebaa", Toast.LENGTH_LONG).show();
////				Bundle bundle = getIntent().getExtras();
//				
//				//Intent intent = new Intent(DetalleCliente.this, VerMapaActivity.class);
//				
////				startActivity(intent);		
//			}
//	 	});
        button_aceptar.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
//    			Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
    			DetalleProspecto.this.finish();
    		}
     	});
//	    button_clientes.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {				
//				Intent intent = new Intent(DetalleProspecto.this,  BuscarClientesGreenDao.class);
//				intent.putExtra("idusuario", idusuario);
//				startActivity(intent);		
//			}
//	 	});
//	    button_pedidos.setOnClickListener(new android.view.View.OnClickListener() {
//			public void onClick(View v) {				
//				Intent intent = new Intent(DetalleProspecto.this,  BuscarPedidos.class);
//				intent.putExtra("idusuario", idusuario);
//				startActivity(intent);		
//			}
//	 	});
    }

    private void obtenerDatosCliente() {
		// TODO Auto-generated method stub
		String str="";
		//Producto productoAux=productoDao.loadByRowId(idProducto);
		str=str+idcliente;
        List<Prospecto> clientesAux = prospectoDao.queryBuilder()
        		.where(Properties.IdProspecto.eq(str))
        		.orderAsc(Properties.Id).list();
        
        if (clientesAux!=null){
        	Log.d("***PROSPECTOYA", clientesAux.size()+"");
        	if(clientesAux.size()>0){
        		Prospecto clienteTemp=clientesAux.get(0);
            	nombre=clienteTemp.getRazon_Social();
            	apellidos=clienteTemp.getRUC();
            	montoCredito=clienteTemp.getMontoActual();
            	direccion=clienteTemp.getDireccion();
        	}
        	else
            	nombre="Error";
        }
    	
		
	}


    
	public void Convierte(List<String> lis){
		elementoListaDao.deleteAll();
		long i=1;
		this.txt_nombre.setText(this.nombre);
		this.txt_ruc.setText("RUC: "+this.apellidos);
		ElementoLista elemento = new ElementoLista(null,"Direccion:",""+direccion,null,i);
		elementoListaDao.insert(elemento);
		i++;
		ElementoLista elemento2 = new ElementoLista(null,"Credito Solicitado:",""+montoCredito,null,i);
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