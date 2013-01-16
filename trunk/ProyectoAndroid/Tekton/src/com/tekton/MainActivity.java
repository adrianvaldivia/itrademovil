package com.tekton;


import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		        	Intent i= new Intent(MainActivity.this, AgregarAlumno.class);
		        	MainActivity.this.startActivity(i);
		        	MainActivity.this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);		           
	        	}	        		        		                  
	        }	      
	        break;
	        case R.id.opcion2:{
	        	Intent i= new Intent(MainActivity.this, ListarAlumnos.class);
	        	MainActivity.this.startActivity(i);
	        	MainActivity.this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	        }	      
	        break;
	        case R.id.opcion3:{
	        	Intent i= new Intent(MainActivity.this, VisualizarProblemas.class);
	        	MainActivity.this.startActivity(i);
	        	MainActivity.this.overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
	        }	      
	        break;
	        case R.id.opcion4:{
	        	Toast.makeText(this, "Eliminando Datos Guardados!", Toast.LENGTH_SHORT).show();
	        	limpiarBaseLocal();
                break;
	        }
	    }
	    return true;
	}
	
    private void limpiarBaseLocal() {
		// TODO Auto-generated method stub  	
    	 File cache = getCacheDir();
    	    File appDir = new File(cache.getParent());
    	    if (appDir.exists()) {
    	        String[] children = appDir.list();
    	        for (String s : children) {
    	            if (!s.equals("lib")) {
    	                deleteDir(new File(appDir, s));Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
    	            }
    	        }
    	    }
    	 MyApplication mApplication = (MyApplication)getApplicationContext();
    	 mApplication.inicializarBdLocal();
	}
	
    public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    MainActivity.this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	    return;
	}
}