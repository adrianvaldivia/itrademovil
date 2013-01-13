package com.tekton.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.tekton.model.Alumno;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ALUMNO.
*/
public class AlumnoDao extends AbstractDao<Alumno, Long> {

    public static final String TABLENAME = "ALUMNO";

    /**
     * Properties of entity Alumno.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdAlumno = new Property(1, Long.class, "IdAlumno", false, "ID_ALUMNO");
        public final static Property Nombres = new Property(2, String.class, "Nombres", false, "NOMBRES");
        public final static Property ApePaterno = new Property(3, String.class, "ApePaterno", false, "APE_PATERNO");
        public final static Property ApeMaterno = new Property(4, String.class, "ApeMaterno", false, "APE_MATERNO");
    };


    public AlumnoDao(DaoConfig config) {
        super(config);
    }
    
    public AlumnoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ALUMNO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_ALUMNO' INTEGER," + // 1: IdAlumno
                "'NOMBRES' TEXT," + // 2: Nombres
                "'APE_PATERNO' TEXT," + // 3: ApePaterno
                "'APE_MATERNO' TEXT);"); // 4: ApeMaterno
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ALUMNO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Alumno entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long IdAlumno = entity.getIdAlumno();
        if (IdAlumno != null) {
            stmt.bindLong(2, IdAlumno);
        }
 
        String Nombres = entity.getNombres();
        if (Nombres != null) {
            stmt.bindString(3, Nombres);
        }
 
        String ApePaterno = entity.getApePaterno();
        if (ApePaterno != null) {
            stmt.bindString(4, ApePaterno);
        }
 
        String ApeMaterno = entity.getApeMaterno();
        if (ApeMaterno != null) {
            stmt.bindString(5, ApeMaterno);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Alumno readEntity(Cursor cursor, int offset) {
        Alumno entity = new Alumno( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // IdAlumno
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Nombres
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ApePaterno
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // ApeMaterno
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Alumno entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdAlumno(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNombres(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setApePaterno(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setApeMaterno(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Alumno entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Alumno entity) {
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
