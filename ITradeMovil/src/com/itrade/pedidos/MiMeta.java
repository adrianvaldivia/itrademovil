package com.itrade.pedidos;


import com.itrade.R;
import com.itrade.db.DAOMeta;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Meta;
import com.itrade.model.MetaDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.MetaDao.Properties;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;

public class MiMeta extends Activity {
	//green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private MetaDao metaDao;
    //fin green dao
	
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
    
    boolean exito;
    DAOMeta daometa;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mimetafusion);
      //inicio green DAO 
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        
        
        
        button_aceptar = (Button) findViewById(R.id.btn_aceptarmeta);
        tv_avancemeta = (TextView) findViewById(R.id.txt_avancemeta);
        tv_metareal = (TextView) findViewById(R.id.txt_metareal);
        tv_periodo = (TextView) findViewById(R.id.txt_periodometa);
        tv_porcentaje = (TextView) findViewById(R.id.textView7);
        pb= (ProgressBar) findViewById(R.id.progressBar1);
        
        Bundle bundle=getIntent().getExtras();
        long idu = bundle.getLong("idusuario");		
        idusuario= String.valueOf(idu);
        
//        obtenerMetaVendedor();
//        imprimirMeta();
        
//        boolean exito=bundle.getBoolean("exito");
//        periodo = bundle.getString("periodo");
//        metareal= bundle.getDouble("meta");
//        avance= bundle.getDouble("avance");
        
//        if(exito){
//        	Double decimal;
//        	if (avance!=null){
//        	  decimal=avance;
//              decimal = decimal*(Math.pow(10, 2));
//              decimal = (double) Math.round(decimal);
//              decimal = decimal/Math.pow(10, 2);
//              
//              tv_avancemeta.setText(String.valueOf(decimal));
//        	}
//        	else {
//        		tv_avancemeta.setText("0");
//        	}
//        	if (metareal!=null){
//              decimal=metareal;
//              decimal = decimal*(Math.pow(10, 2));
//              decimal = (double) Math.round(decimal);
//              decimal = decimal/Math.pow(10, 2);
//              
//              tv_metareal.setText(String.valueOf(decimal));
//        	}
//        	else{
//        		tv_metareal.setText("No hay meta asignada");
//        		
//        	}
//        	if(periodo!=null){
//              tv_periodo.setText(periodo);
//
//              decimal=avance*100/metareal;
//              decimal = decimal*(Math.pow(10, 2));
//              decimal = (double) Math.round(decimal);
//              decimal = decimal/Math.pow(10, 2);
//              
//              tv_porcentaje.setText(String.valueOf(decimal)+"%");
//        	}
//        	else {
//        		tv_periodo.setText("No tiene periodo asignado");
//        		
//        	}
//        	
//        	if(avance!=null && metareal!=null){
//              if (avance*100/metareal>=100){
//              	pb.setProgress(100);
//              	
//              }
//              else {
//              	
//              	int v =(int) Math.round(avance*100/metareal);
//              	pb.setProgress(v);
//              }
//        	}
//        	else{
//        		pb.setProgress(0);
//        		
//        	}
//        	
//        }
//        else{
//        	tv_periodo.setText("");
//        	tv_metareal.setText("");
//        	tv_avancemeta.setText("");
//        	tv_porcentaje.setText("No hay meta asignada");
//        	pb.setProgress(0);
//        	
//        }
        setTitle("iTrade - Mi Meta");    
        button_aceptar.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
//    			Toast.makeText(MenuLista.this, "Salir", Toast.LENGTH_LONG).show();
    			MiMeta.this.finish();
    		}
     	});
    }
    
    private void imprimirMeta() {
		// TODO Auto-generated method stub
        if(exito){
        	Double decimal;
        	if (avance!=null){
        	  decimal=avance;
              decimal = decimal*(Math.pow(10, 2));
              decimal = (double) Math.round(decimal);
              decimal = decimal/Math.pow(10, 2);
              
              tv_avancemeta.setText(String.valueOf(decimal));
        	}
        	else {
        		tv_avancemeta.setText("0");
        	}
        	if (metareal!=null){
              decimal=metareal;
              decimal = decimal*(Math.pow(10, 2));
              decimal = (double) Math.round(decimal);
              decimal = decimal/Math.pow(10, 2);
              
              tv_metareal.setText(String.valueOf(decimal));
        	}
        	else{
        		tv_metareal.setText("No hay meta asignada");
        		
        	}
        	if(periodo!=null){
              tv_periodo.setText(periodo);

              decimal=avance*100/metareal;
              decimal = decimal*(Math.pow(10, 2));
              decimal = (double) Math.round(decimal);
              decimal = decimal/Math.pow(10, 2);
              
              tv_porcentaje.setText(String.valueOf(decimal)+"%");
        	}
        	else {
        		tv_periodo.setText("No tiene periodo asignado");
        		
        	}
        	
        	if(avance!=null && metareal!=null){
              if (avance*100/metareal>=100){
              	pb.setProgress(100);
              	
              }
              else {
              	
              	int v =(int) Math.round(avance*100/metareal);
              	pb.setProgress(v);
              }
        	}
        	else{
        		pb.setProgress(0);
        		
        	}
        	
        }
        else{
        	tv_periodo.setText("");
        	tv_metareal.setText("");
        	tv_avancemeta.setText("");
        	tv_porcentaje.setText("No hay meta asignada");
        	pb.setProgress(0);
        	
        }
		
	}

	private void obtenerMetaVendedor() {
		// TODO Auto-generated method stub
		String str="";
		//Producto productoAux=productoDao.loadByRowId(idProducto);
		str=str+idusuario;
        Meta metav= metaDao.loadByRowId(1);
        if (metav==null) exito=false;
        else exito=true;
        periodo= metav.getNombre();
        avance=metav.getSuma();
        metareal=metav.getMeta();
    	
		
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menuagenda, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	Toast.makeText(this, "Sincronizando!", Toast.LENGTH_LONG).show();
	        	cargarBaseLocal();	        	

	        }
            break;

	    }
	    return true;
	}

	private void cargarBaseLocal() {
		// TODO Auto-generated method stub
		metaDao.deleteAll();
		daometa= new DAOMeta(this);
		Log.d("IDUSU META", idusuario);
		Meta metaAux	=	daometa.buscarMetaxVendedor(idusuario);
		metaDao.insert(metaAux);
		obtenerMetaVendedor();
		imprimirMeta();
	}
	
	@Override
	protected void onDestroy() {	
		db.close();
	    super.onDestroy();
	}
	
	@Override
	protected void onResume() {	
		daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        metaDao = daoSession.getMetaDao();
        obtenerMetaVendedor();
        imprimirMeta();
		
	    super.onResume();
	}
}