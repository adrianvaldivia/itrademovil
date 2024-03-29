package com.itrade.pedidos;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.jsonParser.AsTaskLogin;
import com.itrade.model.Categoria;
import com.itrade.model.CategoriaDao;
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.Meta;
import com.itrade.model.MetaDao;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoLineaDao;
import com.itrade.model.Persona;
import com.itrade.model.PersonaDao;
import com.itrade.model.Producto;
import com.itrade.model.Prospecto;
import com.itrade.model.ProspectoDao;
import com.itrade.R;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.UsuarioDao.Properties;
import com.itrade.cobranzas.ClientesListTask;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOCliente;
import com.itrade.db.DAOEvento;
import com.itrade.db.DAOPedido;
import com.itrade.db.DAOProducto;
import com.itrade.db.DAOProspecto;
import com.itrade.db.DAOUsuario;
import com.itrade.db.DAOPersona;


import de.greenrobot.dao.Query;


public class Login extends Activity {
	
	DAOCliente daoCliente =null;
	DAOPedido daoPedido =null;
	DAOEvento daoEvento =null;
	DAOProducto daoProducto =null;
	DAOProspecto daoProspecto =null;
    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UsuarioDao usuarioDao;
    private PedidoLineaDao pedidoLineaDao;
    //fin green dao
    List<Usuario> listaUsuario;
    List<Persona> listaPersona;
    Usuario ultimoUsuario = new  Usuario();
    

	private EditText textView_Usuario;
	private EditText textView_Password;
    private Button button_Ingresar;
 
    public Persona datEmpleado = null;
    public Boolean boolBaseLocalUsuariosVacia=false;
    public Boolean boolBaseLocalPersonasVacia=false;
//    public ProgressDialog pd;
    AsTaskLogin  taskLogin;
    String nombreUsuRecordado="";
    boolean sePuedeBorrar=false;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.c_login);
	    //inicio preferencias
	    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	    final boolean boolRecordarUsuario = sharedPref.getBoolean(PreferencePedidos.KEY_PREF_CHECK_USUARIO, true);	    
	    if (boolRecordarUsuario){
	    	final String nombUsuPreferences = sharedPref.getString(PreferencePedidos.KEY_PREF_USUARIO,"");
	    	nombreUsuRecordado=""+nombUsuPreferences;
	    }
	    else
	    	nombreUsuRecordado="";
	    //fin preferencias
        
        
	    
	    //inicio green DAO 
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
//        clienteDao = daoSession.getClienteDao();
//        pedidoDao = daoSession.getPedidoDao();
//        pedidoLineaDao = daoSession.getPedidoLineaDao();
//        productoDao = daoSession.getProductoDao();
//        categoriaDao = daoSession.getCategoriaDao();
//        eventoDao = daoSession.getEventoDao();
//        metaDao = daoSession.getMetaDao();
//        prospectoDao = daoSession.getProspectoDao();
//        contactoDao = daoSession.getContactoDao();
        
//        pedidoLineaDao.deleteAll();
        //fin green dao

	    
	    
//	    daoUsu = new DAOUsuario(Login.this);
    
	    
	    textView_Usuario  = (EditText) findViewById(R.id.loginUser);
	    textView_Password  = (EditText) findViewById(R.id.loginPassword);
	    button_Ingresar = (Button) findViewById(R.id.btnLogin);
	    
	    
	    if(boolRecordarUsuario){
	    	textView_Usuario.setText(this.nombreUsuRecordado);
	    }	    
        if(boolRecordarUsuario&&nombreUsuRecordado.length()==0){//solo se ejecuta la primera vez
            if (usuarioDao.count()==0){//cuando no hay usuario
        	    
            }
            else{//cuando hay un usuario anterior
            	ultimoUsuario=usuarioDao.loadByRowId(1);//
            	textView_Usuario.setText(ultimoUsuario.getUsername());

            }      	
        }
	    
	    
	    
	        
	    //M?todo click etn Boton Ingresar

	    button_Ingresar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				Usuario usuarioLocal=null;
				boolBaseLocalUsuariosVacia=false;
					String nombreUsuario = textView_Usuario.getText().toString();
					String password = textView_Password.getText().toString();
		        if (usuarioDao.count()==0){//cuando esta vacia
		        	boolBaseLocalUsuariosVacia=true;
		        }
		        else{//cuando hay un usuario anterior
		        	ultimoUsuario=usuarioDao.loadByRowId(1);//
		        	usuarioLocal=confirmarLoginLocal(nombreUsuario,password);//intento logearme localmente			        	
		        }
																
				
				if (usuarioLocal!=null) {//cuando se logeo localmente de manera correcta
					usuarioDao.deleteAll();
					usuarioDao.insert(usuarioLocal);										
	    			lanzarActivitys(usuarioLocal);													    				   
				}
				else{//intentara logearse en la nube
					if (haveNetworkConnection()){
//						Usuario usuario = daoUsu.confirmarLogin(nombreUsuario,password);
//				    	if (usuario != null){
//				    		usuario.setPassword(password);
//				    		sincronizarBase(usuario);//Preguntar si quiere borrar los datos del usuario logeado anteriormente
//							lanzarActivitys(usuario);			    							   
//				    	}
//				    	else{
//				    		Toast.makeText(getBaseContext(), "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
//				    	}		
						taskLogin= new AsTaskLogin(Login.this,nombreUsuario,password);
						taskLogin.execute();
					}
					else{
						Toast.makeText(getBaseContext(), "Intente nuevamente. No Hay Conexion a Internet!", Toast.LENGTH_LONG).show();
					}
				}
				textView_Password.setText("");
			}





	 	});  
	    
	    
	    textView_Usuario.setOnKeyListener(new OnKeyListener() {
	    	
		 public boolean onKey(View v, int keyCode, KeyEvent event) {
			 if (keyCode == KeyEvent.KEYCODE_APOSTROPHE){
				 textView_Usuario.getText().subSequence(0, textView_Usuario.length());
		         return true;
		     }
		     return false;		     
		     }
		  });
	    
	    textView_Password.setOnKeyListener(new OnKeyListener() {
	    	
			 public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if (keyCode == KeyEvent.KEYCODE_APOSTROPHE){
					 textView_Password.getText().subSequence(0, textView_Password.length());
			         return true;
			     }
			     return false;		     
			     }
			  });

	    
	 }
    private void lanzarActivitys(Usuario usuario) {
    	//Preguntar si quiere borrar los datos del usuario logeado anteriormente	   
    		if (usuario.getIdPerfil()==2){//PEDIDOS
    			lanzarPedidos(usuario);
    		}
    		if (usuario.getIdPerfil()==3){//Cobranza
    			Intent intent = new Intent(Login.this, ClientesListTask.class);					
    		    String nombre= usuario.getNombre();
    			String apellidos=usuario.getApePaterno()+" "+usuario.getApeMaterno();							
    			intent.putExtra("idusuario", usuario.getIdUsuario().toString());    			
    			intent.putExtra("nombre", nombre);
    			intent.putExtra("apellidos", apellidos);
    			//intent.putExtra("usuario", usuario);					
    			startActivity(intent);					
    			textView_Password.setText("");
    		} 
    		if (usuario.getIdPerfil()!=2&&usuario.getIdPerfil()!=3){//Administrador u otros
    			Toast.makeText(Login.this, usuario.getNombre()+" "+usuario.getApePaterno() +", No tiene los permisos necesarios", Toast.LENGTH_SHORT).show();
    		}
    }
		
	private void lanzarPedidos(Usuario usuario) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Login.this, BuscarClientesGreenDao.class);					
	    String nombre= usuario.getNombre();
		String apellidos=usuario.getApePaterno()+" "+usuario.getApeMaterno();				
		intent.putExtra("idusuario", usuario.getIdUsuario());
		intent.putExtra("nombre", nombre);
		intent.putExtra("apellidos", apellidos);
		//intent.putExtra("usuario", usuario);					
		startActivity(intent);					
		textView_Password.setText("");
	}
	

    
//	private void sincronizarBase(Usuario usuario) {
//		// TODO Auto-generated method stub
//		long idUsuario=usuario.getIdUsuario();
//		usuario.setNombreReal("BDLOCAL");
//		////////////////////////////////////////////////////////////Sincronizacion de Usuarios
//		usuarioDao.deleteAll();
//		usuarioDao.insert(usuario);
//		////////////////////////////////////////////////////////Sincronizacion de Productos
//		daoProducto = new DAOProducto(Login.this);
//		List<Producto> listaProducto = daoProducto.getAllProductos(); //obtiene los productos
//		List<Categoria> listaCategoria=daoProducto.getAllCategorias();
//       
//		productoDao.deleteAll();
//		categoriaDao.deleteAll();
//		
//        
//		for(int i=0;i<listaProducto.size();i++){
//			Producto productoAux = new Producto(null,listaProducto.get(i).getIdProducto(),listaProducto.get(i).getDescripcion(),listaProducto.get(i).getPrecio(),listaProducto.get(i).getStock(),listaProducto.get(i).getActivo(),listaProducto.get(i).getIdCategoria(),listaProducto.get(i).getIdMarca());		
//			productoDao.insert(productoAux);				    
//		}
//		for(int i=0;i<listaCategoria.size();i++){
//			Categoria categoria = new Categoria(null,listaCategoria.get(i).getIdCategoria(),listaCategoria.get(i).getDescripcion());
//			categoriaDao.insert(categoria);
//		}
//
//		
//		///////////////////////////////////////////////////Sincronizacion de Clientes		
//        daoCliente = new DAOCliente(this);  
//        List<Cliente> listaCliente = daoCliente.getAllClientes(idUsuario); //obtiene los clientes
//        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
//        Double x;
//		Double y;
//		clienteDao.deleteAll();
//		for(int i=0;i<listaCliente.size();i++){
//			x=listaCliente.get(i).getLatitud();
//			y=listaCliente.get(i).getLongitud();
//			Cliente cliente2 = new Cliente(null,listaCliente.get(i).getIdPersona(),listaCliente.get(i).getIdCliente(),
//					listaCliente.get(i).getNombre(),listaCliente.get(i).getApePaterno(),
//					listaCliente.get(i).getRazon_Social(),listaCliente.get(i).getRazon_Social(),
//					listaCliente.get(i).getRUC(),x,y,listaCliente.get(i).getDireccion(),
//					listaCliente.get(i).getIdCobrador(),listaCliente.get(i).getIdUsuario(),
//					listaCliente.get(i).getActivo(),listaCliente.get(i).getMontoActual());
//			cliente2.setActivo("A");//util para el checkin del mapa
//	        clienteDao.insert(cliente2);
//		}
//		//////////////////////////////////////////////sincronizacion de pedidos
//		daoPedido = new DAOPedido(Login.this);
//		List<Pedido> listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos
//		pedidoDao.deleteAll();        
//		for(int i=0;i<listaPedido.size();i++){
//			//numvoucher = A de antiguo
//			Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),"A",listaPedido.get(i).getMontoTotal());
//			pedidoDao.insert(pedido);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//		////////////////////////////////////////////////////////Sincronizacion de pedido Linea
////		pedidoLineaDao.deleteAll();
//		/////////////////////////////////////////////Sincronizacion eventos
//		eventoDao.deleteAll();
//		daoEvento = new DAOEvento(Login.this);
////		String fechaEvento="2012-10-12";
//		String fechaEvento=getFechaActual();
//		List<Evento> listaEvento = daoEvento.getAllEventos(idUsuario,fechaEvento); //obtiene los eventos        
//		for(int i=0;i<listaEvento.size();i++){
//			//numvoucher = A de antiguo
//			Evento evento = new Evento(null, listaEvento.get(i).getIdEvento(),listaEvento.get(i).getCreador(),listaEvento.get(i).getAsunto(),listaEvento.get(i).getLugar(),listaEvento.get(i).getDescripcion(),listaEvento.get(i).getFecha(),listaEvento.get(i).getHoraInicio(),listaEvento.get(i).getHoraFin());
//			eventoDao.insert(evento);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}		
//		//sincronizacion de metas
//		metaDao.deleteAll();
//		Meta meta= new Meta(null,null,100.0,"2012-11-10","2012-12-10",200.0,"Octubre 2012");
//		metaDao.insert(meta);
//		///////////////////////////////////////////////////sincronizacion de prospectos
//		prospectoDao.deleteAll();   	
//		daoProspecto = new DAOProspecto(this);
//		List<Prospecto> listaProspecto = daoProspecto.buscarProspectosxVendedor(""+idUsuario,"");
//        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
//        Double xx;
//		Double yy;
//
//        
//		for(int i=0;i<listaProspecto.size();i++){
//			xx=listaProspecto.get(i).getLatitud();
//			yy=listaProspecto.get(i).getLongitud();
//			Prospecto prospecto2 = new Prospecto(null,listaProspecto.get(i).getIdPersona(),listaProspecto.get(i).getIdProspecto(),
//					listaProspecto.get(i).getNombre(),listaProspecto.get(i).getApePaterno(),
//					listaProspecto.get(i).getRazon_Social(),listaProspecto.get(i).getRazon_Social(),
//					listaProspecto.get(i).getRUC(),xx,yy,listaProspecto.get(i).getDireccion(),
//					listaProspecto.get(i).getIdCobrador(),listaProspecto.get(i).getIdUsuario(),
//					listaProspecto.get(i).getActivo(),listaProspecto.get(i).getMontoActual(),
//					listaProspecto.get(i).getDNI(),listaProspecto.get(i).getFechNac(),
//					listaProspecto.get(i).getTelefono(),listaProspecto.get(i).getEmail());
//			prospecto2.setActivo("A");//util para la sincronizacion de prospectos
//	        prospectoDao.insert(prospecto2);	        	        
//		}
//		//sincronizacion de contactos
//		contactoDao.deleteAll();
//		long temp=0;
//		temp++;
//		Contacto contacto= new Contacto(null,temp,null,"Benito","Leon","Cordova","A","997565670","benito@corp.com");
//		contactoDao.insert(contacto);
//		temp++;
//		Contacto contacto2= new Contacto(null,temp,null,"Ushpa","Leon","Co","A","976755699","ushpa@corp.com");
//		contactoDao.insert(contacto2);
//		temp++;
//		Contacto contacto3= new Contacto(null,temp,null,"Andres","Godinez","Co","A","971199644","anna@corp.com");
//		contactoDao.insert(contacto3);
//
//		
//	}
//    private String getFechaActual() {
//    	String resul;
//		Calendar _calendar;
//		int month, year;
//    	_calendar = Calendar.getInstance(Locale.getDefault());
//    	year = _calendar.get(Calendar.YEAR);
//		month = _calendar.get(Calendar.MONTH) + 1;
//		String strMonth=""+month;
//		strMonth=agregaCeroMes(strMonth);		
//		resul=""+year+"-"+strMonth+"-01";		
//		return resul;
//	}
//	private String agregaCeroMes(String themonth) {
//		String resul="";
//		if (themonth.length()==1)
//			resul="0"+themonth;
//		else
//			resul=""+themonth;
//			
//		return resul;
//		
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menulogin, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	Toast.makeText(this, "Sincronizando!", Toast.LENGTH_SHORT).show();
	        	cargarBaseLocal();	        	
	            break;
	        }
	        case R.id.opcion2:{
	        	Toast.makeText(this, "Eliminando Datos Guardados!", Toast.LENGTH_SHORT).show();
	        	limpiarBaseLocal();
                break;
	        }
	        	
	            
	    }
	    return true;
	}
    private void limpiarBaseLocal() {
		// TODO Auto-generated method stub
    	if (sePuedeBorrar){
    		usuarioDao.deleteAll();
    	}    	
    	this.textView_Usuario.setText("");
    	 File cache = getCacheDir();
    	    File appDir = new File(cache.getParent());
    	    if (appDir.exists()) {
    	        String[] children = appDir.list();
    	        for (String s : children) {
    	            if (!s.equals("lib")) {
    	                deleteDir(new File(appDir, s));Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
    	            }
    	        }
    	    }
		sePuedeBorrar=false;
	}
    public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}
	private void cargarBaseLocal() {
  
	}
	private Usuario confirmarLoginLocal(String nombreUsuario,String password) {		
		long tam=usuarioDao.count();
		Query<Usuario> query = usuarioDao.queryBuilder().where(
				Properties.Username.eq(nombreUsuario), Properties.Password.eq(password))
				.build();
		List <Usuario> listaUsuarioAux = query.list();
		if (listaUsuarioAux.size()>=1){
			return listaUsuarioAux.get(0);
		}
		else
			return null;
	}
	private boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}

	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}
	@Override
	protected void onDestroy() { 
		db.close();
	    super.onDestroy();
	}
	@Override
	protected void onResume() {
	    //inicio green DAO
		//inicio cambio reciente
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();	
        //Fin cambio reciente
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        pedidoLineaDao = daoSession.getPedidoLineaDao();
        sePuedeBorrar=true;
//        pedidoLineaDao.deleteAll();
		 SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		    final boolean boolRecordarUsu = sharedPref.getBoolean(PreferencePedidos.KEY_PREF_CHECK_USUARIO, true);	    
		    if (!boolRecordarUsu){
		    	textView_Usuario.setText("");//limpio cuando ya no se quiere recordar el nombre
		    }
		
	    super.onResume();
	}
    
}