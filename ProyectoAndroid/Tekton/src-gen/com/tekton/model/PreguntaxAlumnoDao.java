package com.tekton.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.tekton.model.PreguntaxAlumno;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PREGUNTAX_ALUMNO.
*/
public class PreguntaxAlumnoDao extends AbstractDao<PreguntaxAlumno, Long> {

    public static final String TABLENAME = "PREGUNTAX_ALUMNO";

    /**
     * Properties of entity PreguntaxAlumno.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdAlumno = new Property(1, Long.class, "IdAlumno", false, "ID_ALUMNO");
        public final static Property IdPregunta = new Property(2, Long.class, "IdPregunta", false, "ID_PREGUNTA");
        public final static Property Activo = new Property(3, Integer.class, "Activo", false, "ACTIVO");
    };


    public PreguntaxAlumnoDao(DaoConfig config) {
        super(config);
    }
    
    public PreguntaxAlumnoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PREGUNTAX_ALUMNO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_ALUMNO' INTEGER," + // 1: IdAlumno
                "'ID_PREGUNTA' INTEGER," + // 2: IdPregunta
                "'ACTIVO' INTEGER);"); // 3: Activo
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PREGUNTAX_ALUMNO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PreguntaxAlumno entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long IdAlumno = entity.getIdAlumno();
        if (IdAlumno != null) {
            stmt.bindLong(2, IdAlumno);
        }
 
        Long IdPregunta = entity.getIdPregunta();
        if (IdPregunta != null) {
            stmt.bindLong(3, IdPregunta);
        }
 
        Integer Activo = entity.getActivo();
        if (Activo != null) {
            stmt.bindLong(4, Activo);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PreguntaxAlumno readEntity(Cursor cursor, int offset) {
        PreguntaxAlumno entity = new PreguntaxAlumno( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // IdAlumno
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // IdPregunta
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3) // Activo
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PreguntaxAlumno entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdAlumno(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setIdPregunta(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setActivo(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PreguntaxAlumno entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PreguntaxAlumno entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
