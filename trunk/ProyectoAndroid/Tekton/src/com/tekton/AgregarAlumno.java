package com.tekton;

import com.tekton.model.Alumno;
import com.tekton.model.AlumnoDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarAlumno extends Activity {
	
	private AlumnoDao alumnoDao;
	private EditText txt_nombres;
	private EditText txt_apepaterno;
	private EditText txt_apematerno;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaralumno);
        MyApplication mApplication = (MyApplication)getApplicationContext();
        alumnoDao=mApplication.getAlumnoDao();
        
    	txt_nombres = (EditText) findViewById(R.id.nombres);
    	txt_apepaterno = (EditText) findViewById(R.id.apepaterno);
    	txt_apematerno= (EditText) findViewById(R.id.apematerno);
        Button button_guardar = (Button) findViewById(R.id.buttonguardar);
	    button_guardar.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {				
				guardarAlumno();
				Toast.makeText(AgregarAlumno.this, "Alumno Registrado Exitosamente", Toast.LENGTH_SHORT).show();
				AgregarAlumno.this.finish();	
			}
	 	});

        
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
	    AgregarAlumno.this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	    super.onBackPressed();
	}
}