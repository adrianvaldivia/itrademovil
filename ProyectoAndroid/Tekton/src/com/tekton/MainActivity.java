package com.tekton;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //comenzamos
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	if (true){
		        	Intent i= new Intent(getBaseContext(), AgregarAlumno.class);
		            startActivity(i);	         
	        	}
	        	else
	        		Toast.makeText(this, "No hay conexion a Internet!", Toast.LENGTH_SHORT).show();	        		                  
	        }	      
	        break;
	        case R.id.opcion2:{
	        	Intent i= new Intent(getBaseContext(), ListarAlumnos.class);
	            startActivity(i);	                  
	        }	      
	        break;
	        case R.id.opcion3:{
	        	Intent i= new Intent(getBaseContext(), VisualizarProblemas.class);
	            startActivity(i);	                  
	        }	      
	        break;
	    }
	    return true;
	}
}