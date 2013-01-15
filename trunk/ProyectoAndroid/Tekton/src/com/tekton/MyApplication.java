package com.tekton;

import com.tekton.model.AlumnoDao;
import com.tekton.model.DaoMaster.DevOpenHelper;
import com.tekton.model.DaoMaster;
import com.tekton.model.DaoSession;
import com.tekton.model.ElementoListaDao;
import com.tekton.model.PreguntaDao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class MyApplication extends Application {

  //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AlumnoDao alumnoDao;
    private PreguntaDao preguntaDao;
    private ElementoListaDao elementoListaDao;
    //fin green dao
    
	@Override
	public void onCreate() {
		super.onCreate();
		inicializarBdLocal();
	}
	
	public DaoSession getDaoSession() {
		return daoSession;
	}

	public void setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
	}

	public AlumnoDao getAlumnoDao() {
		return alumnoDao;
	}

	public void setAlumnoDao(AlumnoDao alumnoDao) {
		this.alumnoDao = alumnoDao;
	}

	public PreguntaDao getPreguntaDao() {
		return preguntaDao;
	}

	public void setPreguntaDao(PreguntaDao preguntaDao) {
		this.preguntaDao = preguntaDao;
	}
	
	public ElementoListaDao getElementoListaDao() {
		return elementoListaDao;
	}

	public void setElementoListaDao(ElementoListaDao elementoListaDao) {
		this.elementoListaDao = elementoListaDao;
	}
	
	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void inicializarBdLocal() {
        if (db!=null)
    		db.close();
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "matematica-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        alumnoDao= daoSession.getAlumnoDao();
        preguntaDao= daoSession.getPreguntaDao();
        setElementoListaDao(daoSession.getElementoListaDao());
	}
}
