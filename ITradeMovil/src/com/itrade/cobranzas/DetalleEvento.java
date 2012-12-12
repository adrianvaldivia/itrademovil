package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.controller.cobranza.SyncNotifications;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.EventoDao.Properties;

public class DetalleEvento extends Activity{
	private String fecha;
	Evento evento= new Evento();
	 
	private SQLiteDatabase db;
	
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private ElementoListaDao elementoListaDao;
	private EventoDao eventoDao;
	
	// private Cursor cursor;
	private Cursor cursorElementoLista;
	SimpleCursorAdapter adapterElementoLista;
	int idEvento=0;
	private String idusuario;
	private TextView txt_Hora;
	private EditText editTextDesc;
	private EditText editTextLugar; //Botones
	private ImageView btnClientes;
	private ImageView btnMail;
	private ImageView btnBuscar;
	private ImageView btnDepositar;
	private ImageView btnDirectorio;
	private ImageView btnCalendario;  	
	private ImageView btnMapaTotal;
	//Context
	final Context context = this;
	private SyncNotifications sincNotifications;


 
 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);

	 setContentView(R.layout.c_detalle_evento);
	 txt_Hora = (TextView) findViewById(R.id.c_txtHora);
	 editTextDesc = (EditText) findViewById(R.id.c_txtDesc);
	 editTextLugar = (EditText) findViewById(R.id.c_txtLugar);
	 Intent i = getIntent();  
	 String str=(String)i.getSerializableExtra("idevento");
	 idusuario=(String)i.getSerializableExtra("idusuario");
	 idEvento=Integer.parseInt(str);
	 sincNotifications= new SyncNotifications(DetalleEvento.this);
	 /*Cmbio*/
	 DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
     db = helper.getWritableDatabase();
     daoMaster = new DaoMaster(db);
     daoSession = daoMaster.newSession();	     
     elementoListaDao = daoSession.getElementoListaDao();
     eventoDao = daoSession.getEventoDao();
     //fin green day
     encontrarEvento();
     String strHoraIni="";
     String strHoraFi="";
     strHoraIni=remueveCerosDerecha(evento.getHoraInicio());
     strHoraFi=remueveCerosDerecha(evento.getHoraFin());
     txt_Hora.setText("De:"+strHoraIni+" a "+strHoraFi);
     editTextDesc.append(evento.getDescripcion());
     editTextLugar.append("Headquarters");
//     Toast.makeText(this, "H: "+evento.getDescripcion(), Toast.LENGTH_LONG).show();
     /*inicio botonera*/
     /*BTN clientes*/
		btnClientes= (ImageView)findViewById(R.id.c_eve_btnPedidos);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalleEvento.this, ClientesListTask.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		/*BTN clientes*/
		btnDepositar= (ImageView)findViewById(R.id.c_eve_btnCalcularMonto);
		btnDepositar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalleEvento.this, Amortizacion.class); 																				
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.c_eve_btnNotificar);
		btnMail.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!sincNotifications.sendNotification(idusuario)){
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
											Syncronizar sync = new Syncronizar(DetalleEvento.this);
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
											sincNotifications.saveNotification(idusuario);
											Toast.makeText(DetalleEvento.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
											Intent intent = new Intent(DetalleEvento.this, DetalleEvento.class);
											intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											intent.putExtra("idusuario", idusuario);
											startActivity(intent);
																												
									}else{
										Toast.makeText(DetalleEvento.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
									}																																						
								}
					});		
					AlertDialog alertDialog = alertDialogBuilder.create();		 
					alertDialog.show();
				}else{
					Toast.makeText(DetalleEvento.this, "Usted ya envió notifaciones el dia de hoy.", Toast.LENGTH_SHORT).show();
				}			
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.c_eve_btnBuscarClientes);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalleEvento.this, Buscaclientes.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		/**/
		btnCalendario= (ImageView)findViewById(R.id.c_eve_btnCalendario);
		btnCalendario.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalleEvento.this, Calendario.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});	
		btnDirectorio= (ImageView)findViewById(R.id.c_eve_btnDirectorio);
		btnDirectorio.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalleEvento.this, Directorio.class);
				intent.putExtra("idusuario", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});	
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.c_eve_btnExplorar);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(DetalleEvento.this, MapaClientes.class);		
					intent.putExtra("idusuario", idusuario);							
					startActivity(intent);
				}else{
					Toast.makeText(DetalleEvento.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});
     /*Fin botonera*/
     
 	 }
	private String remueveCerosDerecha(String horaInicio) {
		// TODO Auto-generated method stub
		int tam=horaInicio.length();
		String resul="";
		resul=resul+horaInicio;
		resul=resul.substring(0, tam-3);	
		return resul;
	}
	private void encontrarEvento() {
		// TODO Auto-generated method stub
		String strIdEvento =String.valueOf(idEvento);
		Log.d("IDEVENTO===", "idevento"+strIdEvento);
		List<Evento> eventos=eventoDao.queryBuilder()
	             .where(Properties.IdEvento.eq(strIdEvento))
	        	 .orderAsc(Properties.Id).list();
		if (eventos.size()>0){			
			evento=eventos.get(0);			
		}
		else
			evento.setDescripcion("ERROR");
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
