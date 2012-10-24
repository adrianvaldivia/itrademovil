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
import com.itrade.controller.pedidos.SyncronizarPedidos;
import com.itrade.model.Cliente;
import com.itrade.model.Credito;
import com.itrade.model.Persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class RegistrarProspecto extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	ViewFlipper vf;
	
	EditText ruc,rzsocial,direcc,telefcliente,dni,nombre,apellido,telefperson,fechanac,cantidad;
	
	Button btnregistrar;
	
	Cliente client;
	Persona person;
	Credito cred;
		
	String idusuario;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprospecto);

        Intent i = getIntent();
        idusuario = (String)i.getSerializableExtra("idempleado");
        
        /*Contacto = Persona ****************************************************************/
    	dni = (EditText) findViewById(R.id.pdni);
    	nombre = (EditText) findViewById(R.id.pnombre);
    	apellido = (EditText) findViewById(R.id.papellido);
    	telefperson =(EditText) findViewById(R.id.ptelefo);
    	fechanac = (EditText) findViewById(R.id.pcumple);

        /*Empresa = Cliente ****************************************************************/
    	ruc = (EditText) findViewById(R.id.pruc);
    	rzsocial = (EditText) findViewById(R.id.prazonsocial);
    	direcc =(EditText) findViewById(R.id.pdirecc);
    	telefcliente = (EditText) findViewById(R.id.ptelefempre);
    
        /*Linea_Credito = Linea Credito ****************************************************************/
        cantidad = (EditText) findViewById(R.id.pcantidad);
        
        /*Btn Registrar*************************************************************************************/
    	btnregistrar = (Button)findViewById(R.id.pregistraprospecto);
        
    	btnregistrar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
                 		
    client = new Cliente(null, null, null, null, null, null, rzsocial.getText().toString(), ruc.getText().toString(), null, null, direcc.getText().toString(), null, null, null); 

    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    String strFecha = fechanac.getText().toString();
    Date fecha = null;
    try {
		fecha = formato.parse(strFecha);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    
  person = new Persona(null, null, nombre.getText().toString(), apellido.getText().toString(), null, dni.getText().toString(), fecha, telefperson.getText().toString(), null, null); 

  cred = new Credito(Integer.parseInt(cantidad.getText().toString())); 

                 Syncronizar sync = new Syncronizar(RegistrarProspecto.this);
                 List<NameValuePair> param = new ArrayList <NameValuePair>();
                 param.add(new BasicNameValuePair("idvendedor", idusuario));
                 
                 param.add(new BasicNameValuePair("dni", person.getDNI()));
                 param.add(new BasicNameValuePair("nombre", person.getNombre()));
                 param.add(new BasicNameValuePair("apepaterno", person.getApePaterno()));
                 param.add(new BasicNameValuePair("telefono",  person.getTelefono()));
                 param.add(new BasicNameValuePair("fechanac",  person.getFechNac().toString()));
            
                 param.add(new BasicNameValuePair("ruc", client.getRUC()));
                 param.add(new BasicNameValuePair("razon_social", client.getRazon_Social()));
                 param.add(new BasicNameValuePair("direccion", client.getDireccion()));
                
                 String monto = cred.getCantidad()+"";
                 
                 param.add(new BasicNameValuePair("montosolicitado", monto));
                 
                 String route = "/ws/clientes/registrar_prospecto/";
			sync.conexion(param, route);
			
	/********EL WEBSERVICE TIENE QUE DEVOLVER ALGO COMO UNA VALIDACION PARA SABER SI SE REGISTRO********/
			   if (sync.getResponse()=="true")
				   
					Toast.makeText(RegistrarProspecto.this, "Se registro el Prospecto Exitosamente", Toast.LENGTH_SHORT).show();    
			   else	
				   Toast.makeText(RegistrarProspecto.this, "No se registro, intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
			}
		});
        
    	
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);

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

//	MainActivity mac = new MainActivity();
	

	public boolean onTouch(View v, MotionEvent event) {

		float init_x=0;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
			init_x=event.getX();
			return true;
		case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
			float distance =init_x-event.getX();

			if(distance>0)
    		{
                 vf.showPrevious();
    		}

    		if(distance<0)
    		{
    		     vf.showNext();
    		}

		default:
			break;
		}
		return false;
	}
}

public class ViewflipperBlogActivity extends Activity implements OnClickListener{
    public float init_x;
	private ViewFlipper vf;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper);

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

    private class ListenerTouchViewFlipper implements View.OnTouchListener{


		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
				init_x=event.getX();
				return true;
			case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
				float distance =init_x-event.getX();

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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

}