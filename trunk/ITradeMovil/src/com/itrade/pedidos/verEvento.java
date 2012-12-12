package com.itrade.pedidos;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
//import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.EventoDao.Properties;

public class verEvento extends Activity{
 Evento evento= new Evento();
 
 private SQLiteDatabase db;

 private DaoMaster daoMaster;
 private DaoSession daoSession;
// private ElementoListaDao elementoListaDao;
 private EventoDao eventoDao;

// private Cursor cursor;
// private Cursor cursorElementoLista;
 SimpleCursorAdapter adapterElementoLista;
 long idEvento=0;
 private TextView txt_Hora;
 private EditText editTextDesc;
 private EditText editTextLugar;
 private EditText editTextAsunto;


 
 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);

	 setContentView(R.layout.detalle_evento);
	 txt_Hora = (TextView) findViewById(R.id.txtHora);
	 editTextDesc = (EditText) findViewById(R.id.txtDesc);
	 editTextLugar = (EditText) findViewById(R.id.txtLugar);
	 editTextAsunto = (EditText) findViewById(R.id.txtAsunto);
	 Bundle bundle=getIntent().getExtras();
	 idEvento=bundle.getLong("idevento");
	 
	 
	 DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
     db = helper.getWritableDatabase();
     daoMaster = new DaoMaster(db);
     daoSession = daoMaster.newSession();	     
//     elementoListaDao = daoSession.getElementoListaDao();
     eventoDao = daoSession.getEventoDao();
     //fin green day
     encontrarEvento();
     String strHoraIni="";
     String strHoraFi="";
     strHoraIni=remueveCerosDerecha(evento.getHoraInicio());
     strHoraFi=remueveCerosDerecha(evento.getHoraFin());
     txt_Hora.setText("De: "+strHoraIni+" a "+strHoraFi);
     editTextDesc.append(evento.getDescripcion());
     editTextLugar.append(evento.getLugar());
     editTextAsunto.append(evento.getAsunto());
//     Toast.makeText(this, "H: "+evento.getDescripcion(), Toast.LENGTH_LONG).show();
     
     
 	 }
	private String remueveCerosDerecha(String horaInicio) {
		// TODO Auto-generated method stub
		int tam=horaInicio.length();
		String resul="";
		resul=resul+horaInicio;
		resul=resul.substring(0, tam-3);	
		return resul;
	}
	private void encontrarEvento() {
		// TODO Auto-generated method stub
		String strIdEvento =String.valueOf(idEvento);
		List<Evento> eventos=eventoDao.queryBuilder()
	             .where(Properties.IdEvento.eq(strIdEvento))
	        	 .orderAsc(Properties.Id).list();
		if (eventos.size()>0)
			evento=eventos.get(0);
		else
			evento.setDescripcion("ERROR");
	}
	
	@Override
	protected void onDestroy() {
//		recuperarOriginal();		
		db.close();
	    super.onDestroy();
	}
}
