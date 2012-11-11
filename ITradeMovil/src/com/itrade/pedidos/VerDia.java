package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
import com.itrade.model.EventoDao.Properties;
import com.itrade.model.EventoDao;

public class VerDia extends ListActivity{
	String strFecha;
	private List<Evento> eventos = new ArrayList<Evento>();
	private String idUsuario;
	private Date fecha;
	Evento evento= new Evento();
	
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private EventoDao eventoDao;

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.detalle_dia);
		 Bundle bundle=getIntent().getExtras();
		 strFecha=bundle.getString("fecha");
		    Toast.makeText(this, "Fecha: "+strFecha, Toast.LENGTH_LONG).show();
		    
		 
	     //inicio green Dao
	     DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	     db = helper.getWritableDatabase();
	     daoMaster = new DaoMaster(db);
	     daoSession = daoMaster.newSession();	     
	     elementoListaDao = daoSession.getElementoListaDao();
	     eventoDao = daoSession.getEventoDao();
	     //posible error al borrar
	     elementoListaDao.deleteAll();
	        //Inicio green Dao Elementos Lista
	        String textColumnElementoLista = ElementoListaDao.Properties.Principal.columnName;
//	        String orderByElementoLista = textColumnElementoLista + " COLLATE LOCALIZED ASC";
	        String orderByElementoLista = ElementoListaDao.Properties.Secundario.columnName + " COLLATE LOCALIZED ASC";
	        cursorElementoLista = db.query(elementoListaDao.getTablename(), elementoListaDao.getAllColumns(), null, null, null, null, orderByElementoLista);
	        String[] fromElementoLista = { textColumnElementoLista, ElementoListaDao.Properties.Secundario.columnName,ElementoListaDao.Properties.Terciario.columnName };
	        int[] toElementoLista = { R.id.text1, R.id.text2,R.id.text3 };
	        adapterElementoLista = new SimpleCursorAdapter(this, R.layout.itemdoblelinea, cursorElementoLista, fromElementoLista,
	        		toElementoLista);    
	        //fin green Day de Elementos Lista 
	     setListAdapter(adapterElementoLista);
	     guardaListaOriginal();
	     filtraLista();
	     recuperarOriginal();	    		
	 }
	 

	@Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	     // TODO Auto-generated method stub
	     //super.onListItemClick(l, v, position, id);
		 String selection="";
	     selection = l.getItemAtPosition(position).toString();
	     
	    //Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show();
	     
	     Intent intent = new Intent(VerDia.this, verEvento.class); 
	     intent.putExtra("fecha", selection);
	     
	     //encuentra evento
	     this.encuentraEvento(id);
	     //intent.putExtra("IdEvento", evento.getIdEvento());
	     intent.putExtra("IdEvento", 1); //hardcode
	     startActivity(intent);	
	     
	    }    
	 
	 	private void encuentraEvento(long id) {
			ElementoLista elementoAux=  elementoListaDao.loadByRowId(id);
			evento=eventoDao.loadByRowId(elementoAux.getIdElemento());
		}
	    private void guardaListaOriginal() {
			// TODO Auto-generated method stub
	    	this.eventos=eventoDao.loadAll();
			
		}
		private void filtraLista() {
				// TODO Auto-generated method stub
			this.eventos=eventoDao.queryBuilder()
			             .where(Properties.Fecha.eq(strFecha))
			        	 .orderAsc(Properties.Id).list();
			}
		private void recuperarOriginal() {
			elementoListaDao.deleteAll();	        
			for(int i=0;i<eventos.size();i++){
		        long temp=0;
		        temp=temp+eventos.get(i).getId();
				ElementoLista elemento = new ElementoLista(null,eventos.get(i).getAsunto(),"Inicio: "
						+eventos.get(i).getHoraInicio(),"Fin: "+eventos.get(i).getHoraFin(),temp);
				elementoListaDao.insert(elemento);
		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
			}
	        cursorElementoLista.requery();	
			
		}
		
		@Override
		protected void onDestroy() {
			recuperarOriginal();		
			db.close();
			cursorElementoLista.close();
		    super.onDestroy();
		}
	
}
