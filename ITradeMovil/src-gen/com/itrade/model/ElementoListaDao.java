package com.itrade.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.itrade.model.ElementoLista;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ELEMENTO_LISTA.
*/
public class ElementoListaDao extends AbstractDao<ElementoLista, Long> {

    public static final String TABLENAME = "ELEMENTO_LISTA";

    /**
     * Properties of entity ElementoLista.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Principal = new Property(1, String.class, "Principal", false, "PRINCIPAL");
        public final static Property Secundario = new Property(2, String.class, "Secundario", false, "SECUNDARIO");
        public final static Property IdElemento = new Property(3, Long.class, "IdElemento", false, "ID_ELEMENTO");
    };


    public ElementoListaDao(DaoConfig config) {
        super(config);
    }
    
    public ElementoListaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ELEMENTO_LISTA' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'PRINCIPAL' TEXT NOT NULL ," + // 1: Principal
                "'SECUNDARIO' TEXT," + // 2: Secundario
                "'ID_ELEMENTO' INTEGER);"); // 3: IdElemento
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ELEMENTO_LISTA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ElementoLista entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPrincipal());
 
        String Secundario = entity.getSecundario();
        if (Secundario != null) {
            stmt.bindString(3, Secundario);
        }
 
        Long IdElemento = entity.getIdElemento();
        if (IdElemento != null) {
            stmt.bindLong(4, IdElemento);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ElementoLista readEntity(Cursor cursor, int offset) {
        ElementoLista entity = new ElementoLista( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // Principal
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Secundario
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // IdElemento
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ElementoLista entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPrincipal(cursor.getString(offset + 1));
        entity.setSecundario(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIdElemento(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ElementoLista entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ElementoLista entity) {
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
