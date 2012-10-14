package com.itrade.pedidos;


import com.itrade.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MiMeta extends Activity {
    private Button button_aceptar;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mimeta);
        button_aceptar = (Button) findViewById(R.id.buttonaceptarmeta);
        setTitle("iTrade - Mi Meta");    
        button_aceptar.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
//    			Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
    			MiMeta.this.finish();
    		}
     	});
    }

}