package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
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
	Calendar _calendar;
	
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ElementoListaDao elementoListaDao;
    private EventoDao eventoDao;

//    private Cursor cursor;
    private Cursor cursorElementoLista;
    SimpleCursorAdapter adapterElementoLista;
	
    
	private ImageView prevDate;
	private ImageView nextDate;
	private TextView txtFecha;
	
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.detalle_dia);
		 prevDate = (ImageView) this.findViewById(R.id.prevDate);
		 nextDate = (ImageView) this.findViewById(R.id.nextDate);
		 txtFecha = (TextView) this.findViewById(R.id.txtfecha);
		 _calendar = Calendar.getInstance(Locale.getDefault());
		 
		 Bundle bundle=getIntent().getExtras();
		 strFecha=bundle.getString("fecha");
		 asignarFechaSeleccionada();
//		    Toast.makeText(this, "Fecha: "+strFecha, Toast.LENGTH_LONG).show();
		    
		 
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
	     mostrarLista();
	     txtFecha.setText(strFecha);
	     prevDate.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					strFecha=getFechaElegida("A");
					txtFecha.setText(strFecha);
					filtraLista();
					mostrarLista();
	
				}
		 });
	     nextDate.setOnClickListener(new android.view.View.OnClickListener() {
					public void onClick(View v) {
						strFecha=getFechaElegida("S");
						txtFecha.setText(strFecha);
						filtraLista();
						mostrarLista();
				    }
		 });
	         		
	 }
	 

	private void asignarFechaSeleccionada() {
		// TODO Auto-generated method stub
		int diames=0;
		int mes=0;
		int anhio=0;
		diames=obtenerDia();
		mes=obtenerMes();
		anhio=obtenerAnhio();
		
		_calendar.set(Calendar.DAY_OF_MONTH,diames);
		_calendar.set(Calendar.MONTH,mes-1);
		_calendar.set(Calendar.YEAR,anhio);
	}


	private int obtenerDia() {
		int di=1;
		// TODO Auto-generated method stub
		String strDia=strFecha;
		int tam=strFecha.length();
		strDia=""+strDia.substring(tam-2, tam);	
		try {
			di=Integer.parseInt(strDia);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return di;
	}
	
	private int obtenerMes() {
		int di=1;
		// TODO Auto-generated method stub
		String strDia=strFecha;
		int tam=strFecha.length();
		strDia=""+strDia.substring(tam-5, tam-3);	
		try {
			di=Integer.parseInt(strDia);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return di;
	}
	
	private int obtenerAnhio() {
		int di=1;
		// TODO Auto-generated method stub
		String strDia=strFecha;
		int tam=strFecha.length();
		strDia=""+strDia.substring(0, 4);	
		try {
			di=Integer.parseInt(strDia);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return di;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	     // TODO Auto-generated method stub
	     //super.onListItemClick(l, v, position, id);
//		 String selection="";
//	     selection = l.getItemAtPosition(position).toString();
	     
	    //Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show();
		 this.encuentraEvento(id);//encuentra evento
	     Intent intent = new Intent(VerDia.this, verEvento.class); 
	     	     	    
	     //intent.putExtra("IdEvento", evento.getIdEvento());
	     intent.putExtra("idevento",evento.getIdEvento()); //hardcode
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
//			Toast.makeText(this, "filtrado", Toast.LENGTH_LONG).show();
			}
	private void mostrarLista() {
			elementoListaDao.deleteAll();	        
			for(int i=0;i<eventos.size();i++){
		        long temp=0;
		        temp=temp+eventos.get(i).getId();
				ElementoLista elemento = new ElementoLista(null,eventos.get(i).getAsunto(),"Inicio: "
						+remueveCerosDerecha(eventos.get(i).getHoraInicio()),"Fin: "+remueveCerosDerecha(eventos.get(i).getHoraFin()),temp);
				elementoListaDao.insert(elemento);
		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
			}
	        cursorElementoLista.requery();	
			
		}
	private String remueveCerosDerecha(String horaInicio) {
			// TODO Auto-generated method stub
			int tam=horaInicio.length();
			String resul="";
			resul=resul+horaInicio;
			resul=resul.substring(0, tam-3);	
			return resul;
		}
	private String getFechaElegida(String anteriorSiguiente) {
		    	String resul;			
				int month=1;
				int year=1;
				int day=1;
				
				Calendar prevDay = (Calendar) _calendar.clone();
				prevDay.add (Calendar.DAY_OF_YEAR, -1);
				System.out.println ("Previous Day: " + prevDay.getTime());

				Calendar nextDay = (Calendar) _calendar.clone();
				nextDay.add (Calendar.DAY_OF_YEAR, 1);
				System.out.println ("Next Day: " + nextDay.getTime());
				
				if(anteriorSiguiente.compareToIgnoreCase("A")==0){					
			    	year = prevDay.get(Calendar.YEAR);
					month = prevDay.get(Calendar.MONTH) + 1;
					day= prevDay.get(Calendar.DAY_OF_MONTH);
					_calendar=(Calendar) prevDay.clone();
				}
				if (anteriorSiguiente.compareToIgnoreCase("S")==0){					
			    	year = nextDay.get(Calendar.YEAR);
					month = nextDay.get(Calendar.MONTH) + 1;
					day= nextDay.get(Calendar.DAY_OF_MONTH);		
					_calendar=(Calendar) nextDay.clone();
				}


				
				String strMonth=""+month;
				String strDay=""+day;
				strMonth=agregaCeroMes(strMonth);
				strDay=agregaCeroDia(strDay);
				resul=""+year+"-"+strMonth+"-"+strDay;		
				return resul;
			}
	private String agregaCeroMes(String themonth) {
				String resul="";
				if (themonth.length()==1)
					resul="0"+themonth;
				else
					resul=""+themonth;
					
				return resul;
				
			}
	private String agregaCeroDia(String theday) {
				String resul="";
				if (theday.length()==1)
					resul="0"+theday;
				else
					resul=""+theday;
					
				return resul;
				
			}
	@Override
	protected void onDestroy() {
//			recuperarOriginal();		
			db.close();
			cursorElementoLista.close();
		    super.onDestroy();
	}
	
}
