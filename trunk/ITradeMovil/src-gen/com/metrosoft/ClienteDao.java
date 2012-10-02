package com.metrosoft;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.metrosoft.Cliente;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CLIENTE.
*/
public class ClienteDao extends AbstractDao<Cliente, Long> {

    public static final String TABLENAME = "CLIENTE";

    /**
     * Properties of entity Cliente.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nombre = new Property(1, String.class, "Nombre", false, "NOMBRE");
        public final static Property Apellidos = new Property(2, String.class, "Apellidos", false, "APELLIDOS");
        public final static Property Ruc = new Property(3, String.class, "Ruc", false, "RUC");
        public final static Property Latitude = new Property(4, Float.class, "Latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(5, Float.class, "Longitude", false, "LONGITUDE");
    };


    public ClienteDao(DaoConfig config) {
        super(config);
    }
    
    public ClienteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CLIENTE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NOMBRE' TEXT NOT NULL ," + // 1: Nombre
                "'APELLIDOS' TEXT," + // 2: Apellidos
                "'RUC' TEXT," + // 3: Ruc
                "'LATITUDE' REAL," + // 4: Latitude
                "'LONGITUDE' REAL);"); // 5: Longitude
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CLIENTE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Cliente entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getNombre());
 
        String Apellidos = entity.getApellidos();
        if (Apellidos != null) {
            stmt.bindString(3, Apellidos);
        }
 
        String Ruc = entity.getRuc();
        if (Ruc != null) {
            stmt.bindString(4, Ruc);
        }
 
        Float Latitude = entity.getLatitude();
        if (Latitude != null) {
            stmt.bindDouble(5, Latitude);
        }
 
        Float Longitude = entity.getLongitude();
        if (Longitude != null) {
            stmt.bindDouble(6, Longitude);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Cliente readEntity(Cursor cursor, int offset) {
        Cliente entity = new Cliente( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // Nombre
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Apellidos
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Ruc
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // Latitude
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5) // Longitude
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Cliente entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNombre(cursor.getString(offset + 1));
        entity.setApellidos(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRuc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLatitude(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setLongitude(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Cliente entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Cliente entity) {
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