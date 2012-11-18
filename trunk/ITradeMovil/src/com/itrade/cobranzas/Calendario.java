package com.itrade.cobranzas;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.itrade.R;
import com.itrade.controller.cobranza.SyncEventos;
import com.itrade.db.DAOEvento;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.DaoMaster.DevOpenHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Calendario extends Activity implements OnClickListener
	{
		private static final String tag = "SimpleCalendarViewActivity";

		private ImageView calendarToJournalButton;
		private Button selectedDayMonthYearButton;
		private Button currentMonth;
		private ImageView prevMonth;
		private ImageView nextMonth;
		private GridView calendarView;
		private GridCellAdapter adapter;
		private Calendar _calendar;
		private int month, year;
		private final DateFormat dateFormatter = new DateFormat();
		private static final String dateTemplate = "MMMM yyyy";
		private String idUsuario;
		//green dao	    
	    private SyncEventos sincEventos;
	    ///fin green dao	    


		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.c_calendar);
				//inicio green DAO 
				/*
		        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
		        db = helper.getWritableDatabase();
		        daoMaster = new DaoMaster(db);
		        daoSession = daoMaster.newSession();
		        eventoDao = daoSession.getEventoDao();
		        */
		       
		        //Fin greenDAO	
		        Intent i = getIntent();                       
				idUsuario=(String)i.getSerializableExtra("idusuario");
				sincEventos= new SyncEventos(this);
			    sqlite();
				_calendar = Calendar.getInstance(Locale.getDefault());
				month = _calendar.get(Calendar.MONTH) + 1;
				year = _calendar.get(Calendar.YEAR);
				Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: " + year);

				selectedDayMonthYearButton = (Button) this.findViewById(R.id.c_selectedDayMonthYear);
				selectedDayMonthYearButton.setText("Seleccion: ");

				prevMonth = (ImageView) this.findViewById(R.id.c_prevMonth);
				prevMonth.setOnClickListener(this);

				currentMonth = (Button) this.findViewById(R.id.c_currentMonth);
				//Cambios chichan
//				currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime()));
				String strMes =(String) dateFormatter.format(dateTemplate, _calendar.getTime());
				String resul= this.traduceMes(strMes);
				currentMonth.setText(resul);
				// fin cambios chichan
				nextMonth = (ImageView) this.findViewById(R.id.c_nextMonth);
				nextMonth.setOnClickListener(this);

				calendarView = (GridView) this.findViewById(R.id.c_calendar);

				// Initialised
				adapter = new GridCellAdapter(getApplicationContext(), R.id.c_calendar_day_gridcell, month, year);
				adapter.notifyDataSetChanged();
				calendarView.setAdapter(adapter);
				sincEventos.closeDB();				
			}

		/**
		 * 
		 * @param month
		 * @param year
		 */
		private void setGridCellAdapterToDate(int month, int year)
			{
				adapter = new GridCellAdapter(getApplicationContext(), R.id.c_calendar_day_gridcell, month, year);
				_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
				//cambios chichan
//				currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime()));	
				String strMes =(String) dateFormatter.format(dateTemplate, _calendar.getTime());
				String resul= this.traduceMes(strMes);
				currentMonth.setText(resul);
				//fin cambios chichan
				adapter.notifyDataSetChanged();
				calendarView.setAdapter(adapter);
			}

		public void onClick(View v)
			{
				if (v == prevMonth)
					{
						if (month <= 1)
							{
								month = 12;
								year--;
							}
						else
							{
								month--;
							}
						Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
						setGridCellAdapterToDate(month, year);
					}
				if (v == nextMonth)
					{
						if (month > 11)
							{
								month = 1;
								year++;
							}
						else
							{
								month++;
							}
						Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
						setGridCellAdapterToDate(month, year);
					}

			}

		@Override
		public void onDestroy()
			{
				Log.d(tag, "Destroying View ...");
				super.onDestroy();
			}

		// ///////////////////////////////////////////////////////////////////////////////////////
		// Inner Class
		public class GridCellAdapter extends BaseAdapter implements OnClickListener
			{
				private static final String tag = "GridCellAdapter";
				private final Context _context;

				private final List<String> list;
				private static final int DAY_OFFSET = 1;
				private final String[] weekdays = new String[]{"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
				private final String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};
				private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
				private final int month, year;
				private int daysInMonth, prevMonthDays;
				private int currentDayOfMonth;
				private int currentWeekDay;
				private Button gridcell;
				private TextView num_events_per_day;
				private final HashMap eventsPerMonthMap;
				private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
				private final SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyy-MM-dd");

				// Days in Current Month
				public GridCellAdapter(Context context, int textViewResourceId, int month, int year)
					{
						super();
						this._context = context;
						this.list = new ArrayList<String>();
						this.month = month;
						this.year = year;

						Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: " + year);
						Calendar calendar = Calendar.getInstance();
						setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
						setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
						Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
						Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
						Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

						// Print Month
						printMonth(month, year);

						// Find Number of Events
						eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
					}
				private String getMonthAsString(int i)
					{
						return months[i];
					}

				private String getWeekDayAsString(int i)
					{
						return weekdays[i];
					}

				private int getNumberOfDaysOfMonth(int i)
					{
						return daysOfMonth[i];
					}

				public String getItem(int position)
					{
						return list.get(position);
					}

				public int getCount()
					{
						return list.size();
					}

				/**
				 * Prints Month
				 * 
				 * @param mm
				 * @param yy
				 */
				private void printMonth(int mm, int yy)
					{
						Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
						// The number of days to leave blank at
						// the start of this month.
						int trailingSpaces = 0;
						int leadSpaces = 0;
						int daysInPrevMonth = 0;
						int prevMonth = 0;
						int prevYear = 0;
						int nextMonth = 0;
						int nextYear = 0;

						int currentMonth = mm - 1;
						String currentMonthName = getMonthAsString(currentMonth);
						daysInMonth = getNumberOfDaysOfMonth(currentMonth);

						Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

						// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
						GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
						Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

						if (currentMonth == 11)
							{
								prevMonth = currentMonth - 1;
								daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
								nextMonth = 0;
								prevYear = yy;
								nextYear = yy + 1;
								Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
							}
						else if (currentMonth == 0)
							{
								prevMonth = 11;
								prevYear = yy - 1;
								nextYear = yy;
								daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
								nextMonth = 1;
								Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
							}
						else
							{
								prevMonth = currentMonth - 1;
								nextMonth = currentMonth + 1;
								nextYear = yy;
								prevYear = yy;
								daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
								Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
							}

						// Compute how much to leave before before the first day of the
						// month.
						// getDay() returns 0 for Sunday.
						int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
						trailingSpaces = currentWeekDay;

						Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
						Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
						Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

						if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1)
							{
								++daysInMonth;
							}

						// Trailing Month days
						for (int i = 0; i < trailingSpaces; i++)
							{
								Log.d(tag, "PREV MONTH:= " + prevMonth + " => " + getMonthAsString(prevMonth) + " " + String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i));
								list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
							}

						// Current Month Days
						for (int i = 1; i <= daysInMonth; i++)
							{
								Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
								if (i == getCurrentDayOfMonth())
									{
										list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
									}
								else
									{
										list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
									}
							}

						// Leading Month days
						for (int i = 0; i < list.size() % 7; i++)
							{
								Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
								list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
							}
					}

				/**
				 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
				 * ALL entries from a SQLite database for that month. Iterate over the
				 * List of All entries, and get the dateCreated, which is converted into
				 * day.
				 * 
				 * @param year
				 * @param month
				 * @return
				 */
				private HashMap findNumberOfEventsPerMonth(int year, int month)
					{
						HashMap map = new HashMap<String, Integer>();
						// DateFormat dateFormatter2 = new DateFormat();
						//						
						// String day = dateFormatter2.format("dd", dateCreated).toString();
						//
						// if (map.containsKey(day))
						// {
						// Integer val = (Integer) map.get(day) + 1;
						// map.put(day, val);
						// }
						// else
						// {
						// map.put(day, 1);
						// }
						return map;
					}

				public long getItemId(int position)
					{
						return position;
					}

				public View getView(int position, View convertView, ViewGroup parent)
					{
						View row = convertView;
						if (row == null)
							{
								LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								row = inflater.inflate(R.layout.c_grid_cell, parent, false);
							}

						// Get a reference to the Day gridcell
						gridcell = (Button) row.findViewById(R.id.c_calendar_day_gridcell);
						gridcell.setOnClickListener(this);

						// ACCOUNT FOR SPACING

						Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
						String[] day_color = list.get(position).split("-");
						String theday = day_color[0];
						String themonth = day_color[2];
						String theyear = day_color[3];
						if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null))
							{
								if (eventsPerMonthMap.containsKey(theday))
									{
										num_events_per_day = (TextView) row.findViewById(R.id.c_calendar_day_gridcell);
										Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
										num_events_per_day.setText(numEvents.toString());
									}
							}

						// Set the Day GridCell
						gridcell.setText(theday);

						//inicio camnbios chichan
						String strMonth="";						
						strMonth=convierteMesaNumero(themonth);
						String strDay="";
						strDay=agregaCeroDia(theday);
						
						
						gridcell.setTag(theyear + "-" + strMonth + "-" + strDay);
						//fin cambios chichan
//						gridcell.setTag(theday + "-" + themonth + "-" + theyear);
																	
						Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-" + theyear);

						if (day_color[1].equals("GREY"))
							{
								gridcell.setTextColor(Color.LTGRAY);
							}
						if (day_color[1].equals("WHITE"))
							{
								gridcell.setTextColor(Color.WHITE);
							}
						if (day_color[1].equals("BLUE"))
							{
								gridcell.setTextColor(getResources().getColor(R.color.static_text_color));
							}
						return row;
					}

				public void onClick(View view)
					{
						String date_month_year = (String) view.getTag();
						selectedDayMonthYearButton.setText(""+date_month_year);
						//
						Intent intent = new Intent(Calendario.this, DetalleDia.class); 
						intent.putExtra("fecha", date_month_year);
						intent.putExtra("usuario", idUsuario );
				    	startActivity(intent);	
						//
						try
							{
								Date parsedDate = dateFormatter2.parse(date_month_year);
								Log.d(tag, "Parsed Date: " + parsedDate.toString());

							}
						catch (ParseException e)
							{
								e.printStackTrace();
							}
					}

				public int getCurrentDayOfMonth()
					{
						return currentDayOfMonth;
					}

				private void setCurrentDayOfMonth(int currentDayOfMonth)
					{
						this.currentDayOfMonth = currentDayOfMonth;
					}
				public void setCurrentWeekDay(int currentWeekDay)
					{
						this.currentWeekDay = currentWeekDay;
					}
				public int getCurrentWeekDay()
					{
						return currentWeekDay;
					}
			}//fin del inner class
		private String agregaCeroDia(String theday) {
			String resul="";
			if (theday.length()==1)
				resul="0"+theday;
			else
				resul=""+theday;
				
			return resul;
			
		}
		private String convierteMesaNumero(String themonth) {
			// TODO Auto-generated method stub
			String resul="";
			if (themonth.compareTo("Enero")==0)
				resul="01";
			if (themonth.compareTo("Febrero")==0)
				resul="02";
			if (themonth.compareTo("Marzo")==0)
				resul="03";
			if (themonth.compareTo("Abril")==0)
				resul="04";
			if (themonth.compareTo("Mayo")==0)
				resul="05";
			if (themonth.compareTo("Junio")==0)
				resul="06";
			if (themonth.compareTo("Julio")==0)
				resul="07";
			if (themonth.compareTo("Agosto")==0)
				resul="08";
			if (themonth.compareTo("Setiembre")==0)
				resul="09";
			if (themonth.compareTo("Octubre")==0)
				resul="10";
			if (themonth.compareTo("Noviembre")==0)
				resul="11";
			if (themonth.compareTo("Diciembre")==0)
				resul="12";
			return resul;
		}
		private String traduceMes(String fecha) {
			int tam=fecha.length();
			String anhio=""+fecha;
			anhio=" "+anhio.substring(tam-4, tam);			
			String themonth=""+fecha;
			themonth=themonth.substring(0, tam-5);//5 por el espacio en blanco entre mes y anhio

			
			
			
			// TODO Auto-generated method stub
			String resul="";
			if (themonth.compareTo("January")==0)
				resul="Enero"+anhio;
			if (themonth.compareTo("February")==0)
				resul="Febrero"+anhio;
			if (themonth.compareTo("March")==0)
				resul="Marzo"+anhio;
			if (themonth.compareTo("April")==0)
				resul="Abril"+anhio;
			if (themonth.compareTo("May")==0)
				resul="Mayo"+anhio;
			if (themonth.compareTo("June")==0)
				resul="Junio"+anhio;
			if (themonth.compareTo("July")==0)
				resul="Julio"+anhio;
			if (themonth.compareTo("August")==0)
				resul="Agosto"+anhio;
			if (themonth.compareTo("September")==0)
				resul="Setiembre"+anhio;
			if (themonth.compareTo("October")==0)
				resul="Octubre"+anhio;
			if (themonth.compareTo("November")==0)
				resul="Noviembre"+anhio;
			if (themonth.compareTo("December")==0)
				resul="Diciembre"+anhio;
			return resul;
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.menuagenda, menu);
		    return true;
		}
		public void sqlite(){
			String fechaEvento=getFechaActual();		        		
    		sincEventos.syncBDToSqlite(idUsuario,fechaEvento);			
		}
/*
		private void cargarBaseLocal() {

			eventoDao.deleteAll();
	
			String fechaEvento=getFechaActual();
			
			
			
			List<Evento> listaEvento = daoEvento.getAllEventos(Integer.parseInt(idUsuario),fechaEvento); //obtiene los eventos        
			
			for(int i=0;i<listaEvento.size();i++){
			
				Evento evento = new Evento(null, listaEvento.get(i).getIdEvento(),listaEvento.get(i).getCreador(),listaEvento.get(i).getAsunto(),listaEvento.get(i).getLugar(),listaEvento.get(i).getDescripcion(),listaEvento.get(i).getFecha(),listaEvento.get(i).getHoraInicio(),listaEvento.get(i).getHoraFin());
				eventoDao.insert(evento);
		    
			}
			
		}
		*/
	    private String getFechaActual() {
	    	String resul;
			Calendar _calendar;
			int month, year;
	    	_calendar = Calendar.getInstance(Locale.getDefault());
	    	year = _calendar.get(Calendar.YEAR);
			month = _calendar.get(Calendar.MONTH) + 1;
			String strMonth=""+month;
			strMonth=agregaCeroMes(strMonth);		
			resul=""+year+"-"+strMonth+"-01";		
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
		/*private boolean haveNetworkConnection() {
		    boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
		}*/
	}

