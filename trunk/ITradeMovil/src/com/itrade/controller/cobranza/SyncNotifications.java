package com.itrade.controller.cobranza;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;
import com.itrade.model.Notificacion;
import com.itrade.model.NotificacionDao;
import com.itrade.model.NotificacionDao.Properties;

public class SyncNotifications {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NotificacionDao notificacionDao;       
    private Context context;
    private Activity activity;    
    private Gson gson;
	
    public SyncNotifications(Activity activ){
    	super();    	
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        notificacionDao = daoSession.getNotificacionDao();        
        gson = new Gson();  
    }
    
    public void closeDB(){
		db.close();
	}
    
    public void saveNotification(String idusuario){
    	String fecha=getFechaActual();
    	Notificacion notification = new Notificacion(null, null, Integer.parseInt(idusuario), fecha);
    	notificacionDao.insert(notification);    	    	
    }
    public boolean sendNotification(String idusuario){
    	String fechaEvento=getFechaActual();
    	List<Notificacion> listNotif=notificacionDao.queryBuilder()
				.where(Properties.IdUsuario.eq(idusuario))
				.where(Properties.Fecha.eq(fechaEvento))
				.list();
    	if (listNotif.size()>0){
    		return true;
    	}
    	return false;
    }
    private String agregaCeroMes(String themonth) {
		String resul="";
		if (themonth.length()==1)
			resul="0"+themonth;
		else
			resul=""+themonth;
		return resul;	
	}
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
}
