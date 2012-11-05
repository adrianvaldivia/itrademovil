package com.itrade.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.itrade.model.Evento;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EVENTO.
*/
public class EventoDao extends AbstractDao<Evento, Long> {

    public static final String TABLENAME = "EVENTO";

    /**
     * Properties of entity Evento.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdEvento = new Property(1, Integer.class, "IdEvento", false, "ID_EVENTO");
        public final static Property Creador = new Property(2, String.class, "Creador", false, "CREADOR");
        public final static Property Asunto = new Property(3, String.class, "Asunto", false, "ASUNTO");
        public final static Property Lugar = new Property(4, String.class, "Lugar", false, "LUGAR");
        public final static Property Descripcion = new Property(5, String.class, "Descripcion", false, "DESCRIPCION");
        public final static Property Fecha = new Property(6, String.class, "Fecha", false, "FECHA");
        public final static Property HoraInicio = new Property(7, String.class, "HoraInicio", false, "HORA_INICIO");
        public final static Property HoraFin = new Property(8, String.class, "HoraFin", false, "HORA_FIN");
    };


    public EventoDao(DaoConfig config) {
        super(config);
    }
    
    public EventoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENTO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_EVENTO' INTEGER," + // 1: IdEvento
                "'CREADOR' TEXT," + // 2: Creador
                "'ASUNTO' TEXT," + // 3: Asunto
                "'LUGAR' TEXT," + // 4: Lugar
                "'DESCRIPCION' TEXT," + // 5: Descripcion
                "'FECHA' TEXT," + // 6: Fecha
                "'HORA_INICIO' TEXT," + // 7: HoraInicio
                "'HORA_FIN' TEXT);"); // 8: HoraFin
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Evento entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer IdEvento = entity.getIdEvento();
        if (IdEvento != null) {
            stmt.bindLong(2, IdEvento);
        }
 
        String Creador = entity.getCreador();
        if (Creador != null) {
            stmt.bindString(3, Creador);
        }
 
        String Asunto = entity.getAsunto();
        if (Asunto != null) {
            stmt.bindString(4, Asunto);
        }
 
        String Lugar = entity.getLugar();
        if (Lugar != null) {
            stmt.bindString(5, Lugar);
        }
 
        String Descripcion = entity.getDescripcion();
        if (Descripcion != null) {
            stmt.bindString(6, Descripcion);
        }
 
        String Fecha = entity.getFecha();
        if (Fecha != null) {
            stmt.bindString(7, Fecha);
        }
 
        String HoraInicio = entity.getHoraInicio();
        if (HoraInicio != null) {
            stmt.bindString(8, HoraInicio);
        }
 
        String HoraFin = entity.getHoraFin();
        if (HoraFin != null) {
            stmt.bindString(9, HoraFin);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Evento readEntity(Cursor cursor, int offset) {
        Evento entity = new Evento( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // IdEvento
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Creador
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Asunto
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Lugar
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Descripcion
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Fecha
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // HoraInicio
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // HoraFin
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Evento entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdEvento(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setCreador(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAsunto(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLugar(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDescripcion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFecha(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setHoraInicio(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHoraFin(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Evento entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Evento entity) {
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