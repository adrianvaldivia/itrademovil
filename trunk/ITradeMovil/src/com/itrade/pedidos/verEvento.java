package com.itrade.pedidos;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.itrade.R;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.DaoMaster.DevOpenHelper;

public class verEvento extends Activity{
 private String fecha;
 private Evento evento;
 
 private SQLiteDatabase db;

 private DaoMaster daoMaster;
 private DaoSession daoSession;
 private ElementoListaDao elementoListaDao;
 private EventoDao eventoDao;

// private Cursor cursor;
 private Cursor cursorElementoLista;
 SimpleCursorAdapter adapterElementoLista;
 
 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);

	 setContentView(R.layout.detalle_evento);
	 
	 DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
     db = helper.getWritableDatabase();
     daoMaster = new DaoMaster(db);
     daoSession = daoMaster.newSession();	     
     elementoListaDao = daoSession.getElementoListaDao();
     eventoDao = daoSession.getEventoDao();
     //posible error al borrar
     elementoListaDao.deleteAll();
 }
}
