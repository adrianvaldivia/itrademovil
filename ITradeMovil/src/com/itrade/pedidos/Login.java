package com.itrade.pedidos;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Persona;
import com.itrade.model.PersonaDao;
import com.itrade.R;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.UsuarioDao.Properties;
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

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.login2);
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
    
	    
	    textView_Usuario  = (EditText) findViewById(R.id.autoCompleteTextView1);
	    textView_Password  = (EditText) findViewById(R.id.editText1);
	    button_Ingresar = (Button) findViewById(R.id.button1);
	        
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
				int resul=-1;
				if (boolBaseLocalUsuariosVacia){
					resul= daoUsu.confirmarLogin(nombreUsuario, password);
				}
				else{
					resul=confirmarLoginLocal(nombreUsuario, password);
				}
				
				
				if (resul!=-1) {
			    	
					if(boolBaseLocalPersonasVacia){
						datEmpleado = daoPerso.buscarDatosPersona(resul);
					}
					else{
						datEmpleado = buscarDatosPersonaLocal(resul);
					}
//				    datEmpleado = daoPerso.buscarDatosPersona(resul);
				    
					Intent intent = new Intent(Login.this, MenuLista.class);
					
					String nombre= datEmpleado.getNombre();
					String apellidos=datEmpleado.getApePaterno();

					
//					Toast.makeText(getBaseContext(), "Ruta: "+idruta, Toast.LENGTH_SHORT).show();
//					Toast.makeText(getBaseContext(), "Unidad: "+idunidad, Toast.LENGTH_SHORT).show();
					
					intent.putExtra("idempleado", resul);
					intent.putExtra("nombre", nombre);
					intent.putExtra("apellidos", apellidos);
					
					startActivity(intent);
					
					textView_Password.setText("");
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
			Usuario cliente = new Usuario(null,listaUsuario.get(i).getNombre(),listaUsuario.get(i).getPassword(),listaUsuario.get(i).getIdPerfil(),listaUsuario.get(i).getIdPersona(),listaUsuario.get(i).getActivo(),listaUsuario.get(i).getIdJerarquia(),listaUsuario.get(i).getIdZona(),listaUsuario.get(i).getIdDistrito(),listaUsuario.get(i).getIdCiudad(),listaUsuario.get(i).getIdPais());
			usuarioDao.insert(cliente);
	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
		}	
		for(int i=0;i<listaPersona.size();i++){
			Persona persona = new Persona(null,listaPersona.get(i).getNombre(),listaPersona.get(i).getApePaterno(),listaPersona.get(i).getApeMaterno(),listaPersona.get(i).getDNI(),listaPersona.get(i).getFechNac(),listaPersona.get(i).getTelefono(),listaPersona.get(i).getEmail(),listaPersona.get(i).getActivo());
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