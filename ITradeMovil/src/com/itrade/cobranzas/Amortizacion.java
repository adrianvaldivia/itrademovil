package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.itrade.R;
import com.itrade.controller.cobranza.SyncDeposito;
import com.itrade.controller.cobranza.SyncDetallePedido;
import com.itrade.controller.cobranza.SyncNotifications;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Amortizacion extends Activity {
	private TextView txtVwMontoCobrado;
	private TextView txtVwMontoDepositado;
	private EditText editMontoDeposito;
	private EditText nroTransaccion;
	private Button btnDeposito;
	private String idusuario;
	private SyncPedidos syncPedido;
	private SyncDeposito syncDeposito;
	private Double montoMaximoDeposito;
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
	//Syncronization
	private SyncNotifications sincNotifications;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.c_deposito);	
		
        getParamsIntent();
        Log.d("ID: ","Cobranzaa"+this.idusuario);
        sqlite();
        fillValues();               
        setValues();
        //txtVwMontoCobrado.setText(sync.getResponse().toString());	
       //syncPedido.closeDB();
        /*BTN clientes*/
		btnClientes= (ImageView)findViewById(R.id.btnListaClientes);
		btnClientes.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Amortizacion.this, ClientesListTask.class); 																				
				intent.putExtra("idempleado", idusuario);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		/*BTN clientes*/
		btnDepositar= (ImageView)findViewById(R.id.btnDepositarBanco);
		btnDepositar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Amortizacion.this, Amortizacion.class); 																				
				intent.putExtra("idusuario", idusuario);
				startActivity(intent);
			}
		});
		/*BTN Mensaje masivo*/
		btnMail= (ImageView)findViewById(R.id.btnMailMasivo);
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
											Syncronizar sync = new Syncronizar(Amortizacion.this);
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
											Toast.makeText(Amortizacion.this, "Se notificó exitosamente a los clientes.", Toast.LENGTH_SHORT).show();
											Intent intent = new Intent(Amortizacion.this, ClientesListTask.class); 													
											intent.putExtra("idempleado", idusuario);
											startActivity(intent);
																												
									}else{
										Toast.makeText(Amortizacion.this, "Necesita conexión a internet para nofiticar", Toast.LENGTH_SHORT).show();
									}																																						
								}
					});		
					AlertDialog alertDialog = alertDialogBuilder.create();		 
					alertDialog.show();
				}else{
					Toast.makeText(Amortizacion.this, "Usted ya envió notifaciones el dia de hoy.", Toast.LENGTH_SHORT).show();
				}			
			}
		});
		/*btn buscar clientes*/
		btnBuscar= (ImageView)findViewById(R.id.btnBuscarCliente);
		btnBuscar.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Amortizacion.this, Buscaclientes.class);		
				intent.putExtra("idusuario", idusuario);
				intent.putExtra("boolVer", 1);
				startActivity(intent);
			}
		});
		/**/
		btnCalendario= (ImageView)findViewById(R.id.btnCalendario);
		btnCalendario.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Amortizacion.this, Calendario.class);
				intent.putExtra("idusuario", idusuario);				
				startActivity(intent);
			}
		});	
		btnDirectorio= (ImageView)findViewById(R.id.btnDirectorio);
		btnDirectorio.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Amortizacion.this, Directorio.class);
				intent.putExtra("idusuario", idusuario);				
				startActivity(intent);
			}
		});	
		/*btn Mapa Clientes*/
		btnMapaTotal= (ImageView)findViewById(R.id.btnMapa);
		btnMapaTotal.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (networkAvailable()){
					Intent intent = new Intent(Amortizacion.this, MapaClientes.class);		
					intent.putExtra("idempleado", idusuario);				
					startActivity(intent);
				}else{
					Toast.makeText(Amortizacion.this, "Necesita conexion a internet para ver el mapa", Toast.LENGTH_SHORT).show();
				}				
			}
		});	
		
        
        
	}
	private void sqlite() {
		// TODO Auto-generated method stub		
		sincNotifications = new SyncNotifications(Amortizacion.this);
//		Integer numreg = syncDetalle.syncBDToSqlite(idpedido);
//		Log.d("RESULTADOS","numeros ="+numreg.toString());	
		
	}
	public void setValues(){		
		editMontoDeposito = (EditText) findViewById(R.id.montoDeposito);//Monto a depositar		
		nroTransaccion = (EditText) findViewById(R.id.numTransaccion);	//NRo transaccion
		btnDeposito = (Button) findViewById(R.id.btnDeposito);						
		btnDeposito.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {	    		
	    		String monto = editMontoDeposito.getText().toString();
	    		if (monto.trim().equals("")){
	    			Toast.makeText(Amortizacion.this, "Debe ingresar un monto", Toast.LENGTH_SHORT).show();
	    		}else{
	    			if(Integer.parseInt(monto)>montoMaximoDeposito){
		    			Toast.makeText(Amortizacion.this, "No puede registrar mas del monto cobrado", Toast.LENGTH_SHORT).show();
		    		}else{
		    			String numVoucher = nroTransaccion.getText().toString();
			    		syncDeposito.cargarDeposito(idusuario, monto, numVoucher);	
			    		syncDeposito.closeDB();
			    		Toast.makeText(Amortizacion.this, "Se registro el Depósito", Toast.LENGTH_SHORT).show();			    	
			    		Intent intent = new Intent(Amortizacion.this, Amortizacion.class); 																				
						intent.putExtra("idusuario", idusuario);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
		    		}	
	    		}	    		    	
			}
	 	});
        
	}
	public void getParamsIntent(){		
		Intent i = getIntent(); 		
		idusuario = (String)i.getSerializableExtra("idusuario");
		//idcliente=(String)i.getSerializableExtra("idcliente");
		//idempleado=(String)i.getSerializableExtra("idempleado");
	}
	public void fillValues(){
		//Get Cliente
		Log.d("tag", "llenando valores");	
		syncPedido=new SyncPedidos(Amortizacion.this);
		syncDeposito= new SyncDeposito(Amortizacion.this);
		Log.d("tag", "VALOR"+syncPedido.getMontoTotalCobrado(idusuario).toString());
		txtVwMontoCobrado=(TextView) findViewById(R.id.montoCobrado);	
		txtVwMontoCobrado.setText(syncPedido.getMontoTotalCobrado(idusuario).toString());
		txtVwMontoDepositado=(TextView) findViewById(R.id.montoDepositado);	
		txtVwMontoDepositado.setText(syncDeposito.totalDepositado(idusuario).toString());
		montoMaximoDeposito=syncPedido.getMontoTotalCobrado(idusuario)-syncDeposito.totalDepositado(idusuario);	
		montoMaximoDeposito=Math.round(montoMaximoDeposito*100.0)/100.0;
		btnClientes=(ImageView)findViewById(R.id.btnListaClientes);
		btnMail=(ImageView)findViewById(R.id.btnMailMasivo);
		btnBuscar=(ImageView)findViewById(R.id.btnBuscarCliente);
		btnDepositar=(ImageView)findViewById(R.id.btnDepositarBanco);
		btnDirectorio=(ImageView)findViewById(R.id.btnDirectorio);
		btnCalendario=(ImageView)findViewById(R.id.btnCalendario);
		btnMapaTotal=(ImageView)findViewById(R.id.btnMapa);
		
		
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
