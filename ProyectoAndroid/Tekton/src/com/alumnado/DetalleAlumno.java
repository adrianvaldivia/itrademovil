package com.alumnado;

import com.alumnado.R;
import com.alumnado.model.Alumno;
import com.alumnado.model.AlumnoDao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class DetalleAlumno extends Activity {
	
	private AlumnoDao alumnoDao;
	private EditText txt_nombres;
	private EditText txt_apepaterno;
	private EditText txt_apematerno;
	private Bundle bundle;
	private long idAlumno=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallealumno);
    	txt_nombres = (EditText) findViewById(R.id.nombres);
    	txt_apepaterno = (EditText) findViewById(R.id.apepaterno);
    	txt_apematerno= (EditText) findViewById(R.id.apematerno);  
	    bundle = getIntent().getExtras();	
		idAlumno = bundle.getLong("idAlumno");
		obtenerDatosAlumno();//obtiene los datos del SQLite              
    }
	private void obtenerDatosAlumno() {
		// TODO Auto-generated method stub
        MyApplication mApplication = (MyApplication)getApplicationContext();
        alumnoDao=mApplication.getAlumnoDao();
		Alumno alumnoTemp= alumnoDao.loadByRowId(idAlumno);
    	txt_nombres.setText(alumnoTemp.getNombres());
    	txt_apepaterno.setText(alumnoTemp.getApePaterno());
    	txt_apematerno.setText(alumnoTemp.getApeMaterno());
	}
	protected void guardarAlumno() {
		// TODO Auto-generated method stub
		String nomb,apepate,apemate;
		nomb=txt_nombres.getText().toString();
		apepate=txt_apepaterno.getText().toString();
		apemate=txt_apematerno.getText().toString();
        Alumno alu =new Alumno(null,null,nomb,apepate,apemate);
        alumnoDao.insert(alu);		
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}
	
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    DetalleAlumno.this.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
	    super.onBackPressed();
	}
}