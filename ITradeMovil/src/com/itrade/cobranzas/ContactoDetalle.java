package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Producto;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.ContactoDao.Properties;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ContactoDetalle extends ListActivity{

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
	public String idusuario;
	public long idpersona;//id interno sqlite
	//green dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private ContactoDao contactoDao;
    final Context context = this;
    //fin green dao
  //Botones
  		private ImageView btnClientes;
  		private ImageView btnMail;
  		private ImageView btnBuscar;
  		private ImageView btnDepositar;
  		private ImageView btnDirectorio;
  		private ImageView btnCalendario;  	
  		private ImageView btnMapaTotal;
  		//fin Botones
//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_detalle_contacto);
	    bundle = getIntent().getExtras();	
		idusuario = bundle.getString("idusuario");
		Log.d("TAGGGGG", idusuario);
		idpersona=bundle.getLong("idpersona");
        
		/*BOTONERA INICIO*/
		/*BTN clientes*/
		btnClientes= (ImageView)findViewById(R.id.c_con_btnPedidos);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContactoDetalle.this, ClientesListTask.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.c_con_btnNotificar);
		btnMail.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String titulo="Notificar"; 
				String mensaje="¿Deseas Notificar ahora a todos tus clientes ?"; 				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);		 				
				alertDialogBuilder.setTitle(titulo);		 			
				alertDialogBuilder
						.setMessage(mensaje)
						.setCancelable(true)
						.setNegativeButton("Cancelar", null)
						.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {														
								dialog.cancel();
								//verificar si tiene internet o no <--------------------------
								
								if (networkAvailable()){
									Syncronizar sync = new Syncronizar(ContactoDetalle.this);
									List<NameValuePair> param = new ArrayList<NameValuePair>();
									param.add(new BasicNameValuePair("idcobrador", idusuario));
									String route2="/ws/cobranza/send_notifications/";
									sync.conexion(param,route2);
									try {
										sync.getHilo().join();
									} catch (InterruptedException e) {
										  // TODO Auto-generated catch block
										e.printStackTrace();
									}									
									Toast.makeText(ContactoDetalle.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(ContactoDetalle.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
								}																														
								Intent intent = new Intent(ContactoDetalle.this, ContactoDetalle.class); 													
								intent.putExtra("idusuario", idusuario);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
				});		
				AlertDialog alertDialog = alertDialogBuilder.create();		 
				alertDialog.show();	
				
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.c_con_btnBuscarClientes);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {				
				Intent intent = new Intent(ContactoDetalle.this, Buscaclientes.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);				
			}
		});
		btnDepositar= (ImageView)findViewById(R.id.c_con_btnCalcularMonto);
		btnDepositar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContactoDetalle.this, Amortizacion.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/**/
		btnCalendario= (ImageView)findViewById(R.id.c_con_btnCalendario);
		btnCalendario.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContactoDetalle.this, Calendario.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});	
		btnDirectorio= (ImageView)findViewById(R.id.c_con_btnDirectorio);
		btnDirectorio.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContactoDetalle.this, Directorio.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});	
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.c_con_btnExplorar);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(ContactoDetalle.this, MapaClientes.class);		
					intent.putExtra("idusuario", idusuario);				
					startActivity(intent);
				}else{
					Toast.makeText(ContactoDetalle.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});
		/*BOTONERA FIN*/
		
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
        button_crearpedidos = (Button) findViewById(R.id.c_con_llamar);
        button_clientes = (ImageButton) findViewById(R.id.btnBuscarClientes);
        button_pedidos = (ImageButton) findViewById(R.id.btnBuscarPedidos);
        txt_nombre = (TextView) findViewById(R.id.c_con_txtnombrecliente);
        txt_ruc = (TextView) findViewById(R.id.c_con_txtruccliente);
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
	public boolean networkAvailable() {    	
     	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
     	if (connectMgr != null) {
     		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
     		if (netInfo != null) {
     			for (NetworkInfo net : netInfo) {
     				if (net.getState() == NetworkInfo.State.CONNECTED) {
     					return true;
     				}
     			}
     		}
     	} 
     	else {
     		Log.d("NETWORK", "No network available");
     	}
     	return false;
     }
}