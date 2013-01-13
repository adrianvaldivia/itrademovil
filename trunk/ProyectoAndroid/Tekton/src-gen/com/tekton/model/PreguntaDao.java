package com.tekton.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.tekton.model.Pregunta;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PREGUNTA.
*/
public class PreguntaDao extends AbstractDao<Pregunta, Long> {

    public static final String TABLENAME = "PREGUNTA";

    /**
     * Properties of entity Pregunta.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdPregunta = new Property(1, Long.class, "IdPregunta", false, "ID_PREGUNTA");
        public final static Property Formula = new Property(2, String.class, "Formula", false, "FORMULA");
    };


    public PreguntaDao(DaoConfig config) {
        super(config);
    }
    
    public PreguntaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PREGUNTA' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_PREGUNTA' INTEGER," + // 1: IdPregunta
                "'FORMULA' TEXT);"); // 2: Formula
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PREGUNTA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Pregunta entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long IdPregunta = entity.getIdPregunta();
        if (IdPregunta != null) {
            stmt.bindLong(2, IdPregunta);
        }
 
        String Formula = entity.getFormula();
        if (Formula != null) {
            stmt.bindString(3, Formula);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Pregunta readEntity(Cursor cursor, int offset) {
        Pregunta entity = new Pregunta( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // IdPregunta
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // Formula
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Pregunta entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdPregunta(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setFormula(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Pregunta entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Pregunta entity) {
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
