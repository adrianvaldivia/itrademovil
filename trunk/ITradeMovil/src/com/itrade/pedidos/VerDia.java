package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.Date;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.DaoMaster.DevOpenHelper;

public class VerDia extends ListActivity{
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	private String idUsuario;
	private Date fecha;
	
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);

		 setContentView(R.layout.detalle_dia);
		 
	     //inicio green Dao
	     DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	     db = helper.getWritableDatabase();
	     daoMaster = new DaoMaster(db);
	     daoSession = daoMaster.newSession();	     
	     elementoListaDao = daoSession.getElementoListaDao();
	     //posible error al borrar
	     elementoListaDao.deleteAll();
		        //Inicio green Dao Elementos Lista
	     String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
	     String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
	     cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
	     String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName };
	     int[] toElementoLista = { R.id.text1, R.id.text2 };
	     adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
	      		toElementoLista);    
	        //fin green Day de Elementos Lista
	     setListAdapter(adapterElementoLista);
	     recuperarOriginal();
		 
	 }
	 
	 @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	     // TODO Auto-generated method stub
	     //super.onListItemClick(l, v, position, id);
//	     String selection = l.getItemAtPosition(position).toString();
	     
	    Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show();
	     
	    }    
	 
		private void recuperarOriginal() {
			elementoListaDao.deleteAll();
	        long temp2=1;
			ElementoLista elemento2 = new ElementoLista(null,"HARDCODED","HARDCODED",null,temp2);
			elementoListaDao.insert(elemento2);
	        
			for(int i=0;i<eventos.size();i++){
		        long temp=0;
		        temp=temp+eventos.get(i).getId();
				ElementoLista elemento = new ElementoLista(null,eventos.get(i).getAsunto(),"RUC: "+eventos.get(i).getHoraInicio(),null,temp);
				elementoListaDao.insert(elemento);
		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
			}
	        cursorElementoLista.requery();	
			
		}
	
}
