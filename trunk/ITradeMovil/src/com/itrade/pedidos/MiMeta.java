package com.itrade.pedidos;


import com.itrade.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MiMeta extends Activity {
    private Button button_aceptar;
    private TextView tv_avancemeta;
    private TextView tv_metareal;
    private TextView tv_periodo;
    private String idusuario;
    private String periodo;
    private Double metareal;
    private Double avance;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mimetafusion);
        button_aceptar = (Button) findViewById(R.id.btn_aceptarmeta);
        tv_avancemeta = (TextView) findViewById(R.id.txt_avancemeta);
        tv_metareal = (TextView) findViewById(R.id.txt_metareal);
        tv_periodo = (TextView) findViewById(R.id.txt_periodometa);
        
        Bundle bundle=getIntent().getExtras();
        long idu = bundle.getLong("idusuario");		
        idusuario= String.valueOf(idu);
        periodo = bundle.getString("periodo");
        metareal= bundle.getDouble("meta");
        avance= bundle.getDouble("avance");
        
        tv_avancemeta.setText(String.valueOf(avance));
        tv_metareal.setText(String.valueOf(metareal));
        tv_periodo.setText(periodo);
        
        setTitle("iTrade - Mi Meta");    
        button_aceptar.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
//    			Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
    			MiMeta.this.finish();
    		}
     	});
    }

}