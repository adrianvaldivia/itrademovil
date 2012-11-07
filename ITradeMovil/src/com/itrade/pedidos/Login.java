package com.itrade.pedidos;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoLineaDao;
import com.itrade.model.Persona;
import com.itrade.model.PersonaDao;
import com.itrade.R;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.UsuarioDao.Properties;
import com.itrade.cobranzas.ClientesListTask;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOCliente;
import com.itrade.db.DAOPedido;
import com.itrade.db.DAOUsuario;
import com.itrade.db.DAOPersona;


import de.greenrobot.dao.Query;


public class Login extends Activity {
	
	DAOCliente daoCliente =null;
	DAOPedido daoPedido =null;
    private DAOUsuario daoUsu= null;
    private DAOPersona daoPerso= null;
    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private PedidoDao pedidoDao;
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
    public ProgressDialog pd;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.c_login);
	    //inicio green DAO 
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        clienteDao = daoSession.getClienteDao();
        pedidoDao = daoSession.getPedidoDao();
        pedidoLineaDao = daoSession.getPedidoLineaDao();
        pedidoLineaDao.deleteAll();
        //fin green dao

	    
	    
	    daoUsu = new DAOUsuario(Login.this);
	    daoPerso = new DAOPersona();
    
	    
	    textView_Usuario  = (EditText) findViewById(R.id.loginUser);
	    textView_Password  = (EditText) findViewById(R.id.loginPassword);
	    button_Ingresar = (Button) findViewById(R.id.btnLogin);
	        
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
	    			lanzarActivitys(usuarioLocal);													    				   
				}
				else{//intentara logearse en la nube
					if (haveNetworkConnection()){
						Usuario usuario = daoUsu.confirmarLogin(nombreUsuario,password);
				    	if (usuario != null){
				    		usuario.setPassword(password);
				    		sincronizarBase(usuario);//Preguntar si quiere borrar los datos del usuario logeado anteriormente
							lanzarActivitys(usuario);			    							   
				    	}
				    	else{
				    		Toast.makeText(getBaseContext(), "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
				    	}						
					}
					else{
						Toast.makeText(getBaseContext(), "Intente nuevamente. No Hay Conexion a Internet!", Toast.LENGTH_LONG).show();
					}
				}
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
    			intent.putExtra("idempleado", usuario.getIdUsuario().toString());
    			intent.putExtra("nombre", nombre);
    			intent.putExtra("apellidos", apellidos);
    			//intent.putExtra("usuario", usuario);					
    			startActivity(intent);					
    			textView_Password.setText("");
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
	

    
	private void sincronizarBase(Usuario usuario) {
		// TODO Auto-generated method stub
		long idUsuario=usuario.getIdUsuario();
		usuario.setNombreReal("BDLOCAL");
		////////////////////////////////////////////////////////////Sincronizacion de Usuarios
		usuarioDao.deleteAll();
		usuarioDao.insert(usuario);
		///////////////////////////////////////////////////Sincronizacion de Clientes		
        daoCliente = new DAOCliente(this);  
        List<Cliente> listaCliente = daoCliente.getAllClientes(idUsuario); //obtiene los clientes
        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
        Double x;
		Double y;
		clienteDao.deleteAll();
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
		}
		//////////////////////////////////////////////sincronizacion de pedidos
		daoPedido = new DAOPedido(Login.this);
		List<Pedido> listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos
		pedidoDao.deleteAll();        
		for(int i=0;i<listaPedido.size();i++){
			//numvoucher = A de antiguo
			Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),"A",listaPedido.get(i).getMontoTotal());
			pedidoDao.insert(pedido);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
		////////////////////////////////////////////////////////Sincronizacion de pedido Linea
//		pedidoLineaDao.deleteAll();
		

 // hardcode de eventos con  las dos lineas de arriba
		
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
	        	Toast.makeText(this, "Cargando DB!", Toast.LENGTH_LONG).show();
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
    private void cargarBaseLocal() {
  
	}
	private Usuario confirmarLoginLocal(String nombreUsuario,String password) {		

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
    
}