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
import com.itrade.model.Cliente;
import com.itrade.model.Credito;
import com.itrade.model.Persona;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;
import android.widget.ViewFlipper;


public class RegistrarProspecto extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	EmailValidator validador = new EmailValidator();
	
	ViewFlipper vf;
	
	EditText ruc,rzsocial,direcc,telefcliente,dni,nombre,apellidopater,apellidomater,telefperson,fechanac,correo,cantidad;
	
	Button btnregistrar;
	
	Cliente client;
	Persona person;
	Credito cred;
	
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
	
	static final int DATE_DIALOG_ID = 999;
/******************************************************/
	Button bclientes, dprospectos, hpedidos, vdirectorio, aagenda, rmetas;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprospecto);

  /*LOS LINKS DE LOS BOTONES*******************************************************************/      
//        bclientes = (Button) findViewById(R.id.btnCrearPedido);
//        bclientes.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				Intent a = new Intent(RegistrarProspecto.this, BuscarClientesGreenDao.class); //CAMBIAR
////				/*********ENVIAR INFO A LA VENTANA***********/
////				startActivity(a);
//			}
//		});
//        
//        dprospectos = (Button) findViewById(R.id.btnBuscarPedido);
//        dprospectos.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				limpiarProspecto();
//				Intent b = new Intent(RegistrarProspecto.this, BuscarProspectos.class);
//				/*********ENVIAR INFO A LA VENTANA***********/
//				b.putExtra("idusuario", idusuario);
//				startActivity(b);
//			}
//		});
//        
//        hpedidos = (Button) findViewById(R.id.btnProspectos); 
//        hpedidos.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Intent c = new Intent(RegistrarProspecto.this, CrearPedido.class);
////				/*********ENVIAR INFO A LA VENTANA***********/
////				startActivity(c);
//			}
//		});
//        
//        vdirectorio = (Button) findViewById(R.id.btnDirectorio);
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
//        aagenda = (Button) findViewById(R.id.btnCalendario);
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
//        rmetas = (Button) findViewById(R.id.btnMeta);
//        rmetas.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Intent f = new Intent(RegistrarProspecto.this, MiMeta.class);
////				/*********ENVIAR INFO A LA VENTANA***********/
////				startActivity(f);
//			}
//		});
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
				
			//	Date b1 = new Date(yearactual, monthactual, dayactual); // fecha hoy
			//	Date b2 = new Date(year, month, day);	// fecha ingresada
				Calendar date1 = Calendar.getInstance();
		        Calendar date2 = Calendar.getInstance();

		        date1.clear();
		        date1.set(yearactual, monthactual, dayactual);
		        date2.clear();
		        date2.set(year, month, day);

		        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

		        anhos = (float) diff / (24 * 60 * 60 * 1000 * 365);
		        Log.d("XXXX", "Cuantos son "+anhos+" "+date1.getTime()+" "+date2.getTime());
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
        				{
        					if (anhos == 1000)
        	        		{
        	        				Toast.makeText(RegistrarProspecto.this, "Su posible cliente debe ser mayor a 18 años", Toast.LENGTH_SHORT).show();
        	        		}
        	        				else 
        	        				{
    			
    			client = new Cliente(null, null, null, null, null, null, rzsocial.getText().toString().trim(), ruc.getText().toString().trim(), latitud, longitud, direcc.getText().toString().trim(), null, null, null); 

            	  
   String  strFecha = fechanac.getText().toString();
//    SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
//    String strFecha = fechanac.getText().toString();
//    
//    Date fecha = null; 
//    
//    try {
//   	      
//    	fecha = formato.parse(strFecha);
//		
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//	    e.printStackTrace();
//    }
//
//   strFecha = fecha;
   
   Log.v("XXXX", "aa "+strFecha+" aaa");
    
    person = new Persona(null, null, nombre.getText().toString().trim(), apellidopater.getText().toString().trim(), apellidomater.getText().toString().trim(), dni.getText().toString().trim(), strFecha, telefperson.getText().toString().trim(), correo.getText().toString().trim(), null); 

  String valor = cantidad.getText().toString().trim();
  cred = new Credito(Integer.parseInt(valor)); 

   /***************Falta Ingresar los idsssss de las tablas **************/
  				
  
                 Syncronizar sync = new Syncronizar(RegistrarProspecto.this);
                 List<NameValuePair> param = new ArrayList <NameValuePair>();
                 param.add(new BasicNameValuePair("idvendedor", idu));
                 
                 param.add(new BasicNameValuePair("dni", person.getDNI()));
                 param.add(new BasicNameValuePair("nombre", person.getNombre()));
                 param.add(new BasicNameValuePair("apepaterno", person.getApePaterno()));
                 param.add(new BasicNameValuePair("apematerno", person.getApeMaterno()));
                 param.add(new BasicNameValuePair("telefono",  person.getTelefono()));
                 param.add(new BasicNameValuePair("email",  person.getEmail()));
                 param.add(new BasicNameValuePair("fechanac", strFecha)); // Revisar Nombre de FechaNac
            
                 param.add(new BasicNameValuePair("ruc", client.getRUC()));
                 param.add(new BasicNameValuePair("razon_social", client.getRazon_Social()));
                 param.add(new BasicNameValuePair("direccion", client.getDireccion()));
                 param.add(new BasicNameValuePair("latitud", client.getLatitud().toString() ));
                 param.add(new BasicNameValuePair("longitud", client.getLongitud().toString()));
                
                 //String monto = cred.getCantidad()+"";
                // int valor = Integer.parseInt(cantidad.getText().toString());
                
                 
                 param.add(new BasicNameValuePair("montosolicitado", Integer.toString(cred.getCantidad()) ));

                 String route = "/ws/clientes/registrar_prospecto/";
			     sync.conexion(param, route);
	             
			     try {
					sync.getHilo().join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	
			     /********EL WEBSERVICE TIENE QUE DEVOLVER ALGO COMO UNA VALIDACION PARA SABER SI SE REGISTRO********/
			    
			     if (!sync.getResponse().equals("0"))
			     {
			    	 
			    	 Toast.makeText(RegistrarProspecto.this, "Se registro el Prospecto Exitosamente", Toast.LENGTH_SHORT).show();    
			    	 
			    	 limpiarProspecto();
			    	 
			    	 Intent a = new Intent(RegistrarProspecto.this, BuscarProspectos.class);
			    	 a.putExtra("idusuario", idusuario);
			    	 startActivity(a);
			     
			     } 
			     
			     else	
				   Toast.makeText(RegistrarProspecto.this, "No se registro, intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
			
         
         }// fin de mi IF true
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
			if ((month<10) && (day<10)) 
			{
			fechanac.setText(new StringBuilder().append(year)
					   .append("-0").append(month + 1).append("-0").append(day)
					   .append(" "));
			}
			if ((month>9) && (day>9))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-").append(month + 1).append("-").append(day)
						   .append(" "));
			}
			if ((month>9) && (day<10))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-").append(month + 1).append("-0").append(day)
						   .append(" "));
			}
			if ((month<10) && (day>9))
			{
				fechanac.setText(new StringBuilder().append(year)
						   .append("-0").append(month + 1).append("-").append(day)
						   .append(" "));
			}
		 
		}
	};
	
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
	
}

