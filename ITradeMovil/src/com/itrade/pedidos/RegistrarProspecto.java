package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOProspecto;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Prospecto;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.ProspectoDao;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;



public class RegistrarProspecto extends Activity implements OnClickListener{
  
	/** Called when the activity is first created. */
	private static final int REQUEST_CODE=10;
	
	EmailValidator validador = new EmailValidator();
	
	ViewFlipper vf;
	
	EditText ruc,rzsocial,direcc,telefcliente,dni,nombre,apellidopater,apellidomater,telefperson,fechanac,correo,cantidad;
	
	Button btnregistrar;
	
	Prospecto client;
// cambios chichan	
//	Persona person;
//	Credito cred;
	
	Button ubic;
	
	boolean bandera=false;
	String idusuario;
	String idu;
	
/*****************HARDCODEANDOLOS*******************/	
	Double latitud=-17.3879;
	Double longitud=-66.1051;
/****************************************************/
/******************************************************/
	Calendar cal;

	int year, yearactual;
	int month, monthactual;
	int day, dayactual;
 
	int ingresarfecha=0;
	float anhos=0;
	int days=0;
	
	static final int DATE_DIALOG_ID = 999;
/******************************************************/
	
	//Green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProspectoDao prospectoDao;
    //fin green dao
	ImageButton bclientes, dprospectos, hpedidos, vdirectorio, aagenda, rmetas;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprospecto);
      //Inicio configuracion green dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        prospectoDao = daoSession.getProspectoDao();
        //fin green dao

  /*LOS LINKS DE LOS BOTONES*******************************************************************/      
        bclientes = (ImageButton) findViewById(R.id.btnCrearPedido);
        bclientes.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent a = new Intent(RegistrarProspecto.this, BuscarClientesGreenDao.class); //CAMBIAR
//				/*********ENVIAR INFO A LA VENTANA***********/
				a.putExtra("idusuario", idusuario);
        			startActivity(a);
			}
		});
//        
        dprospectos = (ImageButton) findViewById(R.id.btnBuscarPedido);
        dprospectos.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				limpiarProspecto();
				Intent b = new Intent(RegistrarProspecto.this, BuscarProspectos.class);
				/*********ENVIAR INFO A LA VENTANA***********/
				b.putExtra("idusuario", idusuario);
				startActivity(b);
			}
		});
//        
        hpedidos = (ImageButton) findViewById(R.id.btnProspectos); 
        hpedidos.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent c = new Intent(RegistrarProspecto.this, CrearPedido.class);
				/*********ENVIAR INFO A LA VENTANA***********/
				c.putExtra("idusuario", idusuario);
        			startActivity(c);
			}
		});
//        
//        vdirectorio = (ImageButton) findViewById(R.id.btnDirectorio);
//        vdirectorio.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(RegistrarProspecto.this, "Funcionalidad Pendiente", Toast.LENGTH_SHORT).show();
////				Intent d = new Intent(RegistrarProspecto.this, .class);
////				/*********ENVIAR INFO A LA VENTANA***********/
////				startActivity(d);
//			}
//		});
//        
//        aagenda = (ImageButton) findViewById(R.id.btnCalendario);
//        aagenda.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(RegistrarProspecto.this, "Funcionalidad Pendiente", Toast.LENGTH_SHORT).show();
////				Intent e = new Intent(RegistrarProspecto.this, x.class);
////				/*********ENVIAR INFO A LA VENTANA***********/
////				startActivity(e);
//			}
//		});
//        
        rmetas = (ImageButton) findViewById(R.id.btnMeta);
        rmetas.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent f = new Intent(RegistrarProspecto.this, MiMeta.class);
				/*********ENVIAR INFO A LA VENTANA***********/
				f.putExtra("idusuario", idusuario);
        			startActivity(f);
			}
		});
  /********************************************************************************************/      
        cal = Calendar.getInstance();

    	year=cal.get(Calendar.YEAR);
    	month=cal.get(Calendar.MONTH);
    	day=cal.get(Calendar.DAY_OF_MONTH);

    	yearactual=cal.get(Calendar.YEAR);
    	monthactual=cal.get(Calendar.MONTH);
    	dayactual =cal.get(Calendar.DAY_OF_MONTH);
        
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        
 /****************************************************/
        Bundle bundle=getIntent().getExtras();
        //int idu = bundle.getInt("idusuario");		
        idu = bundle.getString("idusuario");
        //idusuario= String.valueOf(idu);
  /*************************************************************/
        
        ubic = (Button)findViewById(R.id.ubicar);
        ubic.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegistrarProspecto.this, UbicacionProspectoActivity.class);
				intent.putExtra("idusuario", idusuario);
				startActivityForResult(intent,REQUEST_CODE);
			}
		});
        
        /*Contacto = Persona ****************************************************************/
    	dni = (EditText) findViewById(R.id.pdni);
    	nombre = (EditText) findViewById(R.id.pnombre);
    	apellidopater = (EditText) findViewById(R.id.papellidopat);
    	apellidomater = (EditText) findViewById(R.id.papellidomat);
    	telefperson =(EditText) findViewById(R.id.telefono);
    	fechanac = (EditText) findViewById(R.id.pcumple);
    	
    	fechanac.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 int inType = fechanac.getInputType(); // backup the input type
				    fechanac.setInputType(InputType.TYPE_NULL); // disable soft input
				    fechanac.onTouchEvent(event); // call native handler
				    fechanac.setInputType(inType); // restore input type
				    return true; // consume touch even
			}
		});
    	
    	fechanac.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ingresarfecha=1;
				
				showDialog(DATE_DIALOG_ID);
				
				
			}
		});
    	
    	correo = (EditText) findViewById(R.id.correo);

        /*Empresa = Cliente ****************************************************************/
    	ruc = (EditText) findViewById(R.id.pruc);
    	rzsocial = (EditText) findViewById(R.id.prazonsocial);
    	direcc =(EditText) findViewById(R.id.pdireccion);
    	//telefcliente = (EditText) findViewById(R.id.ptelefempre);
    
        /*Linea_Credito = Linea Credito ****************************************************************/
        cantidad = (EditText) findViewById(R.id.pcantidad);
        
        /*Btn Registrar*************************************************************************************/
    	btnregistrar = (Button)findViewById(R.id.pregistraprospecto);
        
    	btnregistrar.setOnClickListener(new OnClickListener() {
			
	/****************************EL CLICK PARA REGISTRAR PROSPECTO*****************************/	
    		
    		public void onClick(View v) {
				// TODO Auto-generated method stub

    			bandera = validador.validate(correo.getText().toString());
    			
    			
    			if ((!ruc.getText().toString().trim().equals("")) &&
                  (!rzsocial.getText().toString().trim().equals("")) &&
                  (!direcc.getText().toString().trim().equals("")) &&
                  (!telefperson.getText().toString().trim().equals("")) &&
                  (!dni.getText().toString().trim().equals("")) &&
                  (!nombre.getText().toString().trim().equals("")) &&
                  (!apellidopater.getText().toString().trim().equals("")) &&
                  (!fechanac.getText().toString().trim().equals("")) &&
                  (!correo.getText().toString().trim().equals("")) &&
                  (!apellidomater.getText().toString().trim().equals("")) &&
                  (!cantidad.getText().toString().trim().equals(""))) 
            		  
         {             
                    		
    
    		if ((ruc.length()!=11) || (dni.length()!=8) || (Integer.parseInt(cantidad.getText().toString().trim()) < 1))
    		{
    				Toast.makeText(RegistrarProspecto.this, "Revisar valores de DNI, RUC y Cantidad", Toast.LENGTH_SHORT).show();
    		}
    				else 
    				{
    		if (bandera==false)
    		    			Toast.makeText(RegistrarProspecto.this, "Por favor Revisar el Correo ingresado", Toast.LENGTH_SHORT).show();
    		else	
    		{	
    			if (ingresarfecha==0)
        		{
        				Toast.makeText(RegistrarProspecto.this, "Por Favor Ingrese una Fecha Válida", Toast.LENGTH_SHORT).show();
        		}
        				else 
        				{ /***********************************************/
        					Date b1 = new Date(yearactual, monthactual, dayactual); // fecha hoy
        					Date b2 = new Date(year, month, day);	// fecha ingresada
        					
        					long diferencia= (b1.getTime() - b2.getTime());

        			        String dias = String.valueOf(diferencia/(1000*60*60*24));
        				    anhos = ((int)(Integer.parseInt(dias)/365));
        			        Log.d("DDD", "Los dias son "+dias+" "+anhos);
        			        
        			       /**************************************************/ 
        					if (anhos < 17)
        	        		{
        	        				Toast.makeText(RegistrarProspecto.this, "Su posible cliente debe ser mayor a 18 años", Toast.LENGTH_SHORT).show();
        	        		}
        	        				else 
        	        				{//aca empieza el caso feliz
        	        					
        	    String  strFecha = fechanac.getText().toString();
    			client = new Prospecto(null, null, null, nombre.getText().toString().trim(), 
    					apellidopater.getText().toString().trim(),
    					apellidomater.getText().toString().trim(), 
    					rzsocial.getText().toString().trim(), ruc.getText().toString().trim(), 
    					latitud, longitud, direcc.getText().toString().trim(),
    					null, null, null,0.0,dni.getText().toString().trim(),
    					strFecha,telefperson.getText().toString().trim(),
    					correo.getText().toString().trim());//aca el cliente ya contiene todos los campos necesarios 

            	  
//   String  strFecha = fechanac.getText().toString();
   
   Log.v("XXXX", "aa "+strFecha+" aaa");
    //cambio chichan
//    person = new Persona(null, null, nombre.getText().toString().trim(), apellidopater.getText().toString().trim(), apellidomater.getText().toString().trim(), dni.getText().toString().trim(), strFecha, telefperson.getText().toString().trim(), correo.getText().toString().trim(), null); 

  String valor = cantidad.getText().toString().trim();
//  cred = new Credito(Integer.parseInt(valor));
  client.setMontoActual(0.0+Integer.parseInt(valor));
  client.setActivo("N");//N de nuevo que me sirve para la sincronizacion  de SQLite
  //inicio insert  Green DAo
	prospectoDao.insert(client);//aca hago en insert al SQLite 
  //fin insert Green DAo
  				
//			DAOProspecto daoprospect = new DAOProspecto(RegistrarProspecto.this);
//			long idu3;
//			idu3 = Long.parseLong(idu);
//			daoprospect.registrarProspecto(client, idu3);
			if (haveNetworkConnection()){
				sincronizarBaseSubida();
			}
//                 Syncronizar sync = new Syncronizar(RegistrarProspecto.this);
//                 List<NameValuePair> param = new ArrayList <NameValuePair>();
//                 param.add(new BasicNameValuePair("idvendedor", idu));
//                 
//                 param.add(new BasicNameValuePair("dni", client.getDNI()));
//                 param.add(new BasicNameValuePair("nombre", client.getNombre()));
//                 param.add(new BasicNameValuePair("apepaterno", client.getApePaterno()));
//                 param.add(new BasicNameValuePair("apematerno", client.getApeMaterno()));
//                 param.add(new BasicNameValuePair("telefono",  client.getTelefono()));
//                 param.add(new BasicNameValuePair("email",  client.getEmail()));
//                 param.add(new BasicNameValuePair("fechanac", strFecha)); // Revisar Nombre de FechaNac
//            
//                 param.add(new BasicNameValuePair("ruc", client.getRUC()));
//                 param.add(new BasicNameValuePair("razon_social", client.getRazon_Social()));
//                 param.add(new BasicNameValuePair("direccion", client.getDireccion()));
//                 param.add(new BasicNameValuePair("latitud", client.getLatitud().toString() ));
//                 param.add(new BasicNameValuePair("longitud", client.getLongitud().toString()));
//                
//                 //String monto = cred.getCantidad()+"";
//                // int valor = Integer.parseInt(cantidad.getText().toString());
//                
//                 
//                 param.add(new BasicNameValuePair("montosolicitado", ""+client.getMontoActual() ));
//
//            Log.d("Jorge", ""+idu+", "+client.getDNI()+", "+client.getNombre()+","+client.getApePaterno()+", "+client.getApeMaterno()+", "+client.getTelefono()+", "+client.getEmail()+", "+strFecha+", "+client.getRUC()+", "+client.getRazon_Social()+", "+client.getDireccion()+","+client.getLatitud()+", "+client.getLongitud());     
//                 
//                 String route = "/ws/clientes/registrar_prospecto/";
//			     sync.conexion(param, route);
//	             
//			     try {
//					sync.getHilo().join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}		
	
			     /********EL WEBSERVICE TIENE QUE DEVOLVER ALGO COMO UNA VALIDACION PARA SABER SI SE REGISTRO********/
			    
//			     if (!sync.getResponse().equals("0"))
			     if (true)
			     {
			    	 
			    	 Toast.makeText(RegistrarProspecto.this, "Se registro el Prospecto Exitosamente", Toast.LENGTH_SHORT).show();    
			    	 RegistrarProspecto.super.onBackPressed();
//			    	 limpiarProspecto();
//			    	 
//			    	 Intent a = new Intent(RegistrarProspecto.this, BuscarProspectos.class);
//			    	 a.putExtra("idusuario", idusuario);ss
//			    	 startActivity(a);
			     
			     } 
			     
//			     else	
//				   Toast.makeText(RegistrarProspecto.this, "No se registro, intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
			
         
         }// fin de mi IF true ... termina el caso feliz
    			}
         }
         	}
         }
    		else 
    		{
    			Toast.makeText(RegistrarProspecto.this, "Todos los Campos son obligatorios", Toast.LENGTH_SHORT).show();
    			
    		}
    	
    	 	} // fin del evento Onclick del Guardar	
    		
    	
    	}); 
    	
    	 
        
        vf.setOnTouchListener(new ListenerTouchViewFlipper());

    }


	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


public class ListenerTouchViewFlipper extends Activity implements View.OnTouchListener{

	public boolean onTouch(View v, MotionEvent event) {

		float init_x=0;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
			init_x=event.getX();
			return true;
		case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
			float distance =init_x - event.getX();

			if(distance>0)
    		{
    			 vf.setInAnimation(inFromRightAnimation());
                 vf.setOutAnimation(outToLeftAnimation());
                 vf.showPrevious();
    		}

    		if(distance<0)
    		{
    		     vf.setInAnimation(inFromLeftAnimation());
    		     vf.setOutAnimation(outToRightAnimation());
    		     vf.showNext();
    		}

		default:
			break;
		}

		return false;
	}


	
	
}


    private Animation inFromRightAnimation() {

    	Animation inFromRight = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f );

    	inFromRight.setDuration(500);
    	inFromRight.setInterpolator(new AccelerateInterpolator());

    	return inFromRight;

    }

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	public boolean isEmailValid(String email)
    {
         String regExpn =
             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

     CharSequence inputStr = email;

     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
     Matcher matcher = pattern.matcher(inputStr);

     if(matcher.matches())
        return true;
     else
        return false;
}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(RegistrarProspecto.this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			if ((month<9) && (day<10)) 
			{
			fechanac.setText(new StringBuilder().append(year)
					   .append("-0").append(month + 1).append("-0").append(day)
					   .append(" "));
			}
			if ((month>8) && (day>9))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-").append(month + 1).append("-").append(day)
						   .append(" "));
			}
			if ((month>8) && (day<10))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-").append(month + 1).append("-0").append(day)
						   .append(" "));
			}
			if ((month<9) && (day>9))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-0").append(month + 1).append("-").append(day)
						   .append(" "));
			}
		 
		}
	};
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
	
public void limpiarProspecto(){
		ruc.setText("");
		rzsocial.setText("");
		direcc.setText("");
		dni.setText("");
		nombre.setText("");
		apellidopater.setText("");
		apellidomater.setText("");
		telefperson.setText("");
		fechanac.setText("");
		correo.setText("");
		cantidad.setText("");}	

private void sincronizarBaseSubida() {	
	int tam=0;
	DAOProspecto daoprospect = new DAOProspecto(RegistrarProspecto.this);
    List<Prospecto> prospectosAux = prospectoDao.queryBuilder()
    		.where(com.itrade.model.ProspectoDao.Properties.Activo.eq("N"))
    		.orderAsc(com.itrade.model.ProspectoDao.Properties.Id).list();
    tam=prospectosAux.size();
    for(int i=0;i<tam;i++){
    	Prospecto prospectoTemp=prospectosAux.get(i);
    	prospectoTemp.setActivo("A");;
    	prospectoDao.deleteByKey(prospectoTemp.getId());
    	prospectoDao.insert(prospectoTemp);
		long idu3;
		idu3 = Long.parseLong(idu);
		daoprospect.registrarProspecto(prospectoTemp, idu3);
    }
}



	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent pData)           
	{
    if ( requestCode == REQUEST_CODE )//Si el código de respuesta es igual al requestCode
        {
        if (resultCode == RESULT_OK )//Si resultCode es igual a ok
            {
//        		pruebaPaso = pData.getExtras().getString("lista" );//Obtengo el string de la subactividad
        		this.latitud=pData.getExtras().getDouble("latitud");//Obtengo el string de la subactividad
        		this.longitud=pData.getExtras().getDouble("longitud");
//        		Toast.makeText(RegistrarProspecto.this,""+latitud+" "+longitud, Toast.LENGTH_LONG).show();
                //Aquí se hara lo que se desee con el valor recuperado                    
            }
        }
}
	
	
	@Override
	protected void onDestroy() { 
		db.close();
	    super.onDestroy();
	}
}


