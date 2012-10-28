package com.itrade.pedidos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;
import com.itrade.model.Credito;
import com.itrade.model.Persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class RegistrarProspecto extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	ViewFlipper vf;
	
	EditText ruc,rzsocial,direcc,telefcliente,dni,nombre,apellidopater,apellidomater,telefperson,fechanac,correo,cantidad;
	
	Button btnregistrar;
	
	Cliente client;
	Persona person;
	Credito cred;
		
	String idusuario;
	String idu;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprospecto);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        
 /****************************************************/
        Bundle bundle=getIntent().getExtras();
        //int idu = bundle.getInt("idempleado");		
        idu = bundle.getString("idempleado");
        //idusuario= String.valueOf(idu);
  /*************************************************************/
        
        /*Contacto = Persona ****************************************************************/
    	dni = (EditText) findViewById(R.id.pdni);
    	nombre = (EditText) findViewById(R.id.pnombre);
    	apellidopater = (EditText) findViewById(R.id.papellidopat);
    	apellidomater = (EditText) findViewById(R.id.papellidomat);
    	telefperson =(EditText) findViewById(R.id.telefono);
    	fechanac = (EditText) findViewById(R.id.pcumple);
    	correo = (EditText) findViewById(R.id.correo);

        /*Empresa = Cliente ****************************************************************/
    	ruc = (EditText) findViewById(R.id.pruc);
    	rzsocial = (EditText) findViewById(R.id.prazonsocial);
    	direcc =(EditText) findViewById(R.id.pdirecc);
    	//telefcliente = (EditText) findViewById(R.id.ptelefempre);
    
        /*Linea_Credito = Linea Credito ****************************************************************/
        cantidad = (EditText) findViewById(R.id.pcantidad);
        
        /*Btn Registrar*************************************************************************************/
    	btnregistrar = (Button)findViewById(R.id.pregistraprospecto);
        
    	btnregistrar.setOnClickListener(new OnClickListener() {
			
		
    		public void onClick(View v) {
				// TODO Auto-generated method stub

    			if ((!ruc.getText().toString().trim().equals("")) &&
                  (!rzsocial.getText().toString().trim().equals("")) &&
                  (!direcc.getText().toString().trim().equals("")) &&
                  (!telefcliente.getText().toString().trim().equals("")) &&
                  (!dni.getText().toString().trim().equals("")) &&
                  (!nombre.getText().toString().trim().equals("")) &&
                  (!apellidopater.getText().toString().trim().equals("")) &&
                  (!apellidomater.getText().toString().trim().equals("")) &&
                  (!telefperson.getText().toString().trim().equals("")) &&
                  (!fechanac.getText().toString().trim().equals("")) &&
                  (!correo.getText().toString().trim().equals("")) &&
                  (!cantidad.getText().toString().trim().equals(""))) 
            		  
         {           		
    
    				if ((ruc.length()!=11) || (dni.length()!=8) || (Integer.parseInt(cantidad.getText().toString().trim()) < 1))
    				
    				Toast.makeText(RegistrarProspecto.this, "Revisar valores de DNI, RUC y Cantidad", Toast.LENGTH_SHORT).show();
    		
    				else 
    					
            	  client = new Cliente(null, null, null, null, null, null, rzsocial.getText().toString().trim(), ruc.getText().toString().trim(), null, null, direcc.getText().toString().trim(), null, null, null); 

    SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
    String strFecha = fechanac.getText().toString();
    
    Date fecha = null;
    
    try {
   	      
    	fecha = formato.parse(strFecha);
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
	    e.printStackTrace();
    }

   
  person = new Persona(null, null, nombre.getText().toString().trim(), apellidopater.getText().toString().trim(), apellidomater.getText().toString().trim(), dni.getText().toString().trim(), fecha, telefperson.getText().toString().trim(), correo.getText().toString().trim(), null); 

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
                 param.add(new BasicNameValuePair("correo",  person.getEmail()));
                 param.add(new BasicNameValuePair("fechnac",  fecha.toString())); // Revisar Nombre de FechaNac
            
                 param.add(new BasicNameValuePair("ruc", client.getRUC()));
                 param.add(new BasicNameValuePair("razon_social", client.getRazon_Social()));
                 param.add(new BasicNameValuePair("direccion", client.getDireccion()));
                
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
			     String valor1 = sync.getResponse().trim();
			     int valor2 = Integer.parseInt(valor1);
			    
			     if (valor2>0)
				
			    	 Toast.makeText(RegistrarProspecto.this, "Se registro el Prospecto Exitosamente", Toast.LENGTH_SHORT).show();    
			   else	
				   Toast.makeText(RegistrarProspecto.this, "No se registro, intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
			
         
         }// fin de mi IF true
      
    		else 
    		{
    			Toast.makeText(RegistrarProspecto.this, "Todos los Campos son obligatorios", Toast.LENGTH_SHORT).show();
    			
    		}
    	
    	 	} // fin del evento Onclick del Guardar	
    		
    	
    	}); 
    	
    	 
        
    	

//        Button bt1 = (Button) findViewById(R.id.buttonUno);
//        bt1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showNext();
//			}
//		});
//
//        Button bt2 = (Button) findViewById(R.id.buttondos);
//        bt2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showPrevious();
//			}
//		});

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

//public class ViewflipperBlogActivity extends Activity implements OnClickListener{
//    public float init_x;
//	private ViewFlipper vf;
//
//	/** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
//
////        Button bt1 = (Button) findViewById(R.id.buttonUno);
////        bt1.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////
////				vf.showNext();
////			}
////		});
////
////        Button bt2 = (Button) findViewById(R.id.buttondos);
////        bt2.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////
////				vf.showPrevious();
////			}
////		});
//
//        vf.setOnTouchListener(new ListenerTouchViewFlipper());
//
//    }


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

	
}

