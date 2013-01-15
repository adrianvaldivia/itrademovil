package com.tekton;


import java.util.List;

import com.tekton.model.Alumno;
import com.tekton.model.AlumnoDao;
import com.tekton.model.ElementoLista;
import com.tekton.model.ElementoListaDao;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class ListarAlumnos extends ListActivity {
	
	private SQLiteDatabase db;
    private ElementoListaDao elementoListaDao;
    private AlumnoDao alumnoDao;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	private List<Alumno> listaAlumnos;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumnos);
        
        MyApplication mApplication = (MyApplication)getApplicationContext();
        db=mApplication.getDb();
        elementoListaDao=mApplication.getElementoListaDao();
        alumnoDao=mApplication.getAlumnoDao();
        
        
        //Inicio green Dao Elementos Lista
        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
        String ordenarPor = ElementoListaDao.Properties.Secundario.columnName;
        String orderByElementoLista = ordenarPor + " COLLATE LOCALIZED ASC";
        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName,ElementoListaDao.Properties.Terciario.columnName };
        int[] toElementoLista = { R.id.text1, R.id.text2,R.id.text3 };
        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
        		toElementoLista);    
        //fin green Day de Elementos Lista
        setListAdapter(adapterElementoLista);
        leerDatos();
        mostrarDatos();
    }
    
	private void leerDatos() {
		// TODO Auto-generated method stub
    	this.listaAlumnos=alumnoDao.loadAll();
		
	}
	private void mostrarDatos() {
		elementoListaDao.deleteAll();
        
		for(int i=0;i<listaAlumnos.size();i++){
	        long temp=0;
	        temp=temp+listaAlumnos.get(i).getId();
			ElementoLista elemento = new ElementoLista(null,listaAlumnos.get(i).getNombres(),listaAlumnos.get(i).getApePaterno(),listaAlumnos.get(i).getApeMaterno(),temp);
			elementoListaDao.insert(elemento);
		}
        cursorElementoLista.requery();	
		
	}
	@Override
	protected void onDestroy() {		
		cursorElementoLista.close();
	    super.onDestroy();
	}
}