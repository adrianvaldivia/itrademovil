package com.itrade.pedidos;


import com.itrade.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.lang.Math;

public class MiMeta extends Activity {
    private Button button_aceptar;
    private TextView tv_avancemeta;
    private TextView tv_metareal;
    private TextView tv_periodo;
    private TextView tv_porcentaje;
    private String idusuario;
    private String periodo;
    private Double metareal;
    private Double avance;
    private ProgressBar pb;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mimetafusion);
        button_aceptar = (Button) findViewById(R.id.btn_aceptarmeta);
        tv_avancemeta = (TextView) findViewById(R.id.txt_avancemeta);
        tv_metareal = (TextView) findViewById(R.id.txt_metareal);
        tv_periodo = (TextView) findViewById(R.id.txt_periodometa);
        tv_porcentaje = (TextView) findViewById(R.id.textView7);
        pb= (ProgressBar) findViewById(R.id.progressBar1);
        Bundle bundle=getIntent().getExtras();
        long idu = bundle.getLong("idusuario");		
        idusuario= String.valueOf(idu);
        periodo = bundle.getString("periodo");
        metareal= bundle.getDouble("meta");
        avance= bundle.getDouble("avance");
        
      Double decimal=avance;
      decimal = decimal*(Math.pow(10, 2));
      decimal = (double) Math.round(decimal);
      decimal = decimal/Math.pow(10, 2);
        
        tv_avancemeta.setText(String.valueOf(decimal));
        decimal=metareal;
        decimal = decimal*(Math.pow(10, 2));
        decimal = (double) Math.round(decimal);
        decimal = decimal/Math.pow(10, 2);
        
        tv_metareal.setText(String.valueOf(decimal));
        tv_periodo.setText(periodo);

        decimal=avance*100/metareal;
        decimal = decimal*(Math.pow(10, 2));
        decimal = (double) Math.round(decimal);
        decimal = decimal/Math.pow(10, 2);
        
        tv_porcentaje.setText(String.valueOf(decimal)+"%");
        
        if (avance*100/metareal>=100){
        	pb.setProgress(100);
        	
        }
        else {
        	
        	int v =(int) Math.round(avance*100/metareal);
        	pb.setProgress(v);
        }
        
        setTitle("iTrade - Mi Meta");    
        button_aceptar.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
//    			Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
    			MiMeta.this.finish();
    		}
     	});
    }

}