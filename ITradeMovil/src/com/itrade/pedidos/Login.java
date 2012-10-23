package com.itrade.pedidos;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Persona;
import com.itrade.model.PersonaDao;
import com.itrade.R;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.UsuarioDao.Properties;
import com.itrade.cobranzas.ClientesListTask;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOUsuario;
import com.itrade.db.DAOPersona;


import de.greenrobot.dao.Query;


public class Login extends Activity {
	
    private DAOUsuario daoUsu= null;
    private DAOPersona daoPerso= null;
    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UsuarioDao usuarioDao;
    private PersonaDao personaDao;
    //fin green dao
    List<Usuario> listaUsuario;
    List<Persona> listaPersona;
    

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
        personaDao = daoSession.getPersonaDao();
        //fin green dao

	    
	    
	    daoUsu = new DAOUsuario();
	    daoPerso = new DAOPersona();
    
	    
	    textView_Usuario  = (EditText) findViewById(R.id.loginUser);
	    textView_Password  = (EditText) findViewById(R.id.loginPassword);
	    button_Ingresar = (Button) findViewById(R.id.btnLogin);
	        
	    //M�todo click etn Boton Ingresar

	    button_Ingresar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				boolBaseLocalUsuariosVacia=false;
				boolBaseLocalPersonasVacia=false;
		        if (usuarioDao.count()==0){
		        	boolBaseLocalUsuariosVacia=true;
		        }	
		        if (personaDao.count()==0){
		        	boolBaseLocalPersonasVacia=true;
		        }	
				String nombreUsuario = textView_Usuario.getText().toString();
				String password = textView_Password.getText().toString();
				
				int resul=1;
				/*
				if (boolBaseLocalUsuariosVacia){
					resul= daoUsu.confirmarLogin(nombreUsuario, password);
				}
				else{
					resul=confirmarLoginLocal(nombreUsuario, password);
				}
				*/
				
				
				
				if (resul!=-1) {
			    	/*
					if(boolBaseLocalPersonasVacia){
						datEmpleado = daoPerso.buscarDatosPersona(resul);
					}
					else{
						datEmpleado = buscarDatosPersonaLocal(resul);
					}
					*/
					
//				    datEmpleado = daoPerso.buscarDatosPersona(resul);
					
					/**************************WEBSERVICE BEGIN******************************/
					
					Syncronizar sync = new Syncronizar(Login.this);
					List<NameValuePair> param = new ArrayList<NameValuePair>();								
					param.add(new BasicNameValuePair("username", nombreUsuario));
					param.add(new BasicNameValuePair("password", password));
					//String route="dp2/itrade/ws/login/get_user_by_username_password/";
					String route="/ws/login/get_user_by_username_password/";
				    sync.conexion(param,route);
				    try {
						sync.getHilo().join();
						Log.d("TAG","LLEGA PRIMERO AKI");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    Log.d("TAG","LLEGA SEGUNDO AKI");
				    Gson gson = new Gson();
					ArrayList<Usuario> logList = new ArrayList<Usuario>();					
					Log.e("log_tag", "se cayo5" );
					logList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Usuario>>(){}.getType());				    
					if (logList.size()>0){
						Usuario usuario = logList.get(0);					    					   
					    /*****************************WEBSERVICE END**********************************/
					    
						if (usuario.getIdPerfil()==2){//PEDIDOS
							Intent intent = new Intent(Login.this, MenuLista.class);					
						    String nombre= usuario.getNombre();
							String apellidos=usuario.getApePaterno()+" "+usuario.getApeMaterno();				
							intent.putExtra("idempleado", resul);
							intent.putExtra("nombre", nombre);
							intent.putExtra("apellidos", apellidos);
							//intent.putExtra("usuario", usuario);					
							startActivity(intent);					
							textView_Password.setText("");
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
					}else{
						Toast.makeText(getBaseContext(), "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
					}														    				   
				}
				else{
					Toast.makeText(getBaseContext(), "Password o Usuario incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
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
//        daoCliente = new DAOCliente();  
        listaUsuario = daoUsu.getAllUsuarios(); //obtiene los usuarios
        listaPersona = daoPerso.getAllPersonas(); //obtiene los usuarios
        usuarioDao.deleteAll();
        personaDao.deleteAll();
        
		for(int i=0;i<listaUsuario.size();i++){
			//Usuario cliente = new Usuario(null,listaUsuario.get(i).getNombre(),listaUsuario.get(i).getPassword(),listaUsuario.get(i).getIdPerfil(),listaUsuario.get(i).getIdPersona(),listaUsuario.get(i).getActivo(),listaUsuario.get(i).getIdJerarquia(),listaUsuario.get(i).getIdZona(),listaUsuario.get(i).getIdDistrito(),listaUsuario.get(i).getIdCiudad(),listaUsuario.get(i).getIdPais());
			//usuarioDao.insert(cliente);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}	
		for(int i=0;i<listaPersona.size();i++){
			Persona persona = new Persona(null,listaPersona.get(i).getIdPersona(),listaPersona.get(i).getNombre(),listaPersona.get(i).getApePaterno(),listaPersona.get(i).getApeMaterno(),listaPersona.get(i).getDNI(),listaPersona.get(i).getFechNac(),listaPersona.get(i).getTelefono(),listaPersona.get(i).getEmail(),listaPersona.get(i).getActivo());
			personaDao.insert(persona);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}
	}
	private int confirmarLoginLocal(String nombreUsuario,String password) {
		Query<Usuario> query = usuarioDao.queryBuilder().where(
				Properties.Nombre.eq(nombreUsuario), Properties.Password.eq(password))
				.build();
		List <Usuario> listaUsuarioAux = query.list();
		if (listaUsuarioAux.size()>=1){
			return safeLongToInt(listaUsuarioAux.get(0).getId());
		}
		else
			return -1;
	}
	private Persona buscarDatosPersonaLocal(int idusuario) {
		Persona persona= new Persona();
		persona.setNombre("HardCoded");
		Long idUsuarioTemp,idPersonaTemp;
		idUsuarioTemp=Long.valueOf(idusuario);
		Usuario usuario;
		if(!boolBaseLocalUsuariosVacia){
			usuario=usuarioDao.loadByRowId(idUsuarioTemp);
			idPersonaTemp=Long.valueOf(usuario.getIdPersona());
			persona=personaDao.loadByRowId(idPersonaTemp);
		}
		
		return persona;
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