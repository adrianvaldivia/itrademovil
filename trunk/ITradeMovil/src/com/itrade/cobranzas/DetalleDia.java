package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.controller.cobranza.SyncNotifications;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.EventoDao.Properties;
import com.itrade.model.EventoDao;

public class DetalleDia extends ListActivity{
	String strFecha;
	private List<Evento> eventos = new ArrayList<Evento>();
	private String idusuario;
	private Date fecha;
	Evento evento= new Evento();
	
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private EventoDao eventoDao;
  //Botones
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
//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
   
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.c_detalle_dia);
		 Bundle bundle=getIntent().getExtras();
		 strFecha=bundle.getString("fecha");
		 Log.d("FEEECHAAAAAA", "fechaaaa="+strFecha);
		 idusuario=bundle.getString("usuario");
//		    Toast.makeText(this, "Fecha: "+strFecha, Toast.LENGTH_LONG).show();
		 Log.d("IDUSUARIOOOOOOO", "IUSARIO="+idusuario);   
		 sqlite();
	     //inicio green Dao
	     DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	     db = helper.getWritableDatabase();
	     daoMaster = new DaoMaster(db);
	     daoSession = daoMaster.newSession();	     
	     elementoListaDao = daoSession.getElementoListaDao();
	     eventoDao = daoSession.getEventoDao();
	     //posible error al borrar
	     elementoListaDao.deleteAll();
	        //Inicio green Dao Elementos Lista
	        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
	        String orderByElementoLista = ElementoListaDao.Properties.Secundario.columnName + " COLLATE LOCALIZED ASC";
	        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
	        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName,ElementoListaDao.Properties.Terciario.columnName };
	        int[] toElementoLista = { R.id.text1, R.id.text2,R.id.text3 };
	        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,toElementoLista);    
	     //fin green Day de Elementos Lista 
	     setListAdapter(adapterElementoLista);
	     guardaListaOriginal();	
	     filtraLista();
	     mostrarLista();
	     /*inicio botonera*/
	     /*BTN clientes*/
			btnClientes= (ImageView)findViewById(R.id.c_det_btnPedidos);
			btnClientes.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DetalleDia.this, ClientesListTask.class); 																				
					intent.putExtra("idusuario", idusuario);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			
			/*BTN clientes*/
			btnDepositar= (ImageView)findViewById(R.id.c_det_btnCalcularMonto);
			btnDepositar.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DetalleDia.this, Amortizacion.class); 																				
					intent.putExtra("idusuario", idusuario);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			/*BTN Mensaje masivo*/
			btnMail= (ImageView)findViewById(R.id.c_det_btnNotificar);
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
												Syncronizar sync = new Syncronizar(DetalleDia.this);
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
												Toast.makeText(DetalleDia.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
												Intent intent = new Intent(DetalleDia.this, DetalleDia.class);
												intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												intent.putExtra("idusuario", idusuario);
												startActivity(intent);
																													
										}else{
											Toast.makeText(DetalleDia.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
										}																																						
									}
						});		
						AlertDialog alertDialog = alertDialogBuilder.create();		 
						alertDialog.show();
					}else{
						Toast.makeText(DetalleDia.this, "Usted ya envió notifaciones el dia de hoy.", Toast.LENGTH_SHORT).show();
					}			
				}
			});
			/*btn buscar clientes*/
			btnBuscar= (ImageView)findViewById(R.id.c_det_btnBuscarClientes);
			btnBuscar.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DetalleDia.this, Buscaclientes.class);		
					intent.putExtra("idusuario", idusuario);
					intent.putExtra("boolVer", 1);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			/**/
			btnCalendario= (ImageView)findViewById(R.id.c_det_btnCalendario);
			btnCalendario.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DetalleDia.this, Calendario.class);
					intent.putExtra("idusuario", idusuario);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});	
			btnDirectorio= (ImageView)findViewById(R.id.c_det_btnDirectorio);
			btnDirectorio.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DetalleDia.this, Directorio.class);
					intent.putExtra("idusuario", idusuario);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});	
			/*btn Mapa Clientes*/
			btnMapaTotal= (ImageView)findViewById(R.id.c_det_btnExplorar);
			btnMapaTotal.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (networkAvailable()){
						Intent intent = new Intent(DetalleDia.this, MapaClientes.class);		
						intent.putExtra("idusuario", idusuario);							
						startActivity(intent);
					}else{
						Toast.makeText(DetalleDia.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
					}				
				}
			});
	     /*Fin botonera*/
	 }
	 

	@Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	     	 
		 this.encuentraEvento(id);//encuentra evento
	     Intent intent = new Intent(DetalleDia.this, DetalleEvento.class); 
	     	     	    
	     intent.putExtra("idusuario",idusuario);
	     intent.putExtra("idevento",evento.getIdEvento().toString()); //hardcode
	     startActivity(intent);	
	     
	    }    
	 
	 	private void encuentraEvento(long id) {
			ElementoLista elementoAux=  elementoListaDao.loadByRowId(id);
			evento=eventoDao.loadByRowId(elementoAux.getIdElemento());
		}
	    private void guardaListaOriginal() {
			// TODO Auto-generated method stub
	    	this.eventos=eventoDao.loadAll();
			
		}
		private void filtraLista() {
				// TODO Auto-generated method stub
			this.eventos=eventoDao.queryBuilder()
			             .where(Properties.Fecha.eq(strFecha))
			        	 .orderAsc(Properties.Id).list();
//			Toast.makeText(this, "filtrado", Toast.LENGTH_LONG).show();
			}
		private void mostrarLista() {
			elementoListaDao.deleteAll();	        
			for(int i=0;i<eventos.size();i++){
		        long temp=0;
		        temp=temp+eventos.get(i).getId();
				ElementoLista elemento = new ElementoLista(null,eventos.get(i).getDescripcion(),"Inicio: "
						+remueveCerosDerecha(eventos.get(i).getHoraInicio()),"Fin: "+remueveCerosDerecha(eventos.get(i).getHoraFin()),temp);
				elementoListaDao.insert(elemento);
		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
			}
	        cursorElementoLista.requery();	
			
		}
		private String remueveCerosDerecha(String horaInicio) {
			// TODO Auto-generated method stub
			int tam=horaInicio.length();
			String resul="";
			resul=resul+horaInicio;
			resul=resul.substring(0, tam-3);	
			return resul;
		}
		
		@Override
		protected void onDestroy() {
//			recuperarOriginal();		
			db.close();
			cursorElementoLista.close();
		    super.onDestroy();
		}
		private void sqlite() {
			// TODO Auto-generated method stub		
			sincNotifications = new SyncNotifications(DetalleDia.this);
//			Integer numreg = syncDetalle.syncBDToSqlite(idpedido);
//			Log.d("RESULTADOS","numeros ="+numreg.toString());	
			
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
