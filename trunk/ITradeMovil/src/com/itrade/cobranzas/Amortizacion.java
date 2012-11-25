package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.itrade.R;
import com.itrade.controller.cobranza.SyncDeposito;
import com.itrade.controller.cobranza.SyncDetallePedido;
import com.itrade.controller.cobranza.SyncPedidos;
import com.itrade.controller.cobranza.Syncronizar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        
        
	}
	private void sqlite() {
		// TODO Auto-generated method stub		
		
//		Integer numreg = syncDetalle.syncBDToSqlite(idpedido);
//		Log.d("RESULTADOS","numeros ="+numreg.toString());	
		
	}
	public void setValues(){
		Log.d("tag", "LLEGO1");
		editMontoDeposito = (EditText) findViewById(R.id.montoDeposito);//Monto a depositar		
		nroTransaccion = (EditText) findViewById(R.id.numTransaccion);	//NRo transaccion
		btnDeposito = (Button) findViewById(R.id.btnDeposito);						
		btnDeposito.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {	    		
	    		String monto = editMontoDeposito.getText().toString();
	    		if(Integer.parseInt(monto)>montoMaximoDeposito){
	    			Toast.makeText(Amortizacion.this, "No puede registrar mas del monto cobrado", Toast.LENGTH_SHORT).show();
	    		}else{
	    			String numVoucher = nroTransaccion.getText().toString();
		    		syncDeposito.cargarDeposito(idusuario, monto, numVoucher);	
		    		syncDeposito.closeDB();
		    		Toast.makeText(Amortizacion.this, "Se registro el Depósito", Toast.LENGTH_SHORT).show();
		    		Intent intent = new Intent(Amortizacion.this, Amortizacion.class); 																				
					intent.putExtra("idusuario", idusuario);
					startActivity(intent);
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
	}	
}
