package com.itrade.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.itrade.model.Producto;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRODUCTO.
*/
public class ProductoDao extends AbstractDao<Producto, Long> {

    public static final String TABLENAME = "PRODUCTO";

    /**
     * Properties of entity Producto.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Descripcion = new Property(1, String.class, "Descripcion", false, "DESCRIPCION");
        public final static Property Precio = new Property(2, Double.class, "Precio", false, "PRECIO");
        public final static Property Stock = new Property(3, Integer.class, "Stock", false, "STOCK");
        public final static Property Activo = new Property(4, String.class, "Activo", false, "ACTIVO");
        public final static Property IdCategoria = new Property(5, Integer.class, "IdCategoria", false, "ID_CATEGORIA");
        public final static Property IdMarca = new Property(6, Integer.class, "IdMarca", false, "ID_MARCA");
    };


    public ProductoDao(DaoConfig config) {
        super(config);
    }
    
    public ProductoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRODUCTO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DESCRIPCION' TEXT NOT NULL ," + // 1: Descripcion
                "'PRECIO' REAL," + // 2: Precio
                "'STOCK' INTEGER," + // 3: Stock
                "'ACTIVO' TEXT," + // 4: Activo
                "'ID_CATEGORIA' INTEGER," + // 5: IdCategoria
                "'ID_MARCA' INTEGER);"); // 6: IdMarca
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRODUCTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Producto entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDescripcion());
 
        Double Precio = entity.getPrecio();
        if (Precio != null) {
            stmt.bindDouble(3, Precio);
        }
 
        Integer Stock = entity.getStock();
        if (Stock != null) {
            stmt.bindLong(4, Stock);
        }
 
        String Activo = entity.getActivo();
        if (Activo != null) {
            stmt.bindString(5, Activo);
        }
 
        Integer IdCategoria = entity.getIdCategoria();
        if (IdCategoria != null) {
            stmt.bindLong(6, IdCategoria);
        }
 
        Integer IdMarca = entity.getIdMarca();
        if (IdMarca != null) {
            stmt.bindLong(7, IdMarca);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Producto readEntity(Cursor cursor, int offset) {
        Producto entity = new Producto( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // Descripcion
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // Precio
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // Stock
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Activo
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // IdCategoria
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // IdMarca
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Producto entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDescripcion(cursor.getString(offset + 1));
        entity.setPrecio(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setStock(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setActivo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIdCategoria(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setIdMarca(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Producto entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Producto entity) {
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