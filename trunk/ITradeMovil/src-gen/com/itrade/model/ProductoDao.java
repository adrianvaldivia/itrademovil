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
        public final static Property IdProducto = new Property(1, Long.class, "IdProducto", false, "ID_PRODUCTO");
        public final static Property Descripcion = new Property(2, String.class, "Descripcion", false, "DESCRIPCION");
        public final static Property Precio = new Property(3, Double.class, "Precio", false, "PRECIO");
        public final static Property Stock = new Property(4, Integer.class, "Stock", false, "STOCK");
        public final static Property Activo = new Property(5, String.class, "Activo", false, "ACTIVO");
        public final static Property IdCategoria = new Property(6, Integer.class, "IdCategoria", false, "ID_CATEGORIA");
        public final static Property IdMarca = new Property(7, Integer.class, "IdMarca", false, "ID_MARCA");
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
                "'ID_PRODUCTO' INTEGER," + // 1: IdProducto
                "'DESCRIPCION' TEXT NOT NULL ," + // 2: Descripcion
                "'PRECIO' REAL," + // 3: Precio
                "'STOCK' INTEGER," + // 4: Stock
                "'ACTIVO' TEXT," + // 5: Activo
                "'ID_CATEGORIA' INTEGER," + // 6: IdCategoria
                "'ID_MARCA' INTEGER);"); // 7: IdMarca
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
 
        Long IdProducto = entity.getIdProducto();
        if (IdProducto != null) {
            stmt.bindLong(2, IdProducto);
        }
        stmt.bindString(3, entity.getDescripcion());
 
        Double Precio = entity.getPrecio();
        if (Precio != null) {
            stmt.bindDouble(4, Precio);
        }
 
        Integer Stock = entity.getStock();
        if (Stock != null) {
            stmt.bindLong(5, Stock);
        }
 
        String Activo = entity.getActivo();
        if (Activo != null) {
            stmt.bindString(6, Activo);
        }
 
        Integer IdCategoria = entity.getIdCategoria();
        if (IdCategoria != null) {
            stmt.bindLong(7, IdCategoria);
        }
 
        Integer IdMarca = entity.getIdMarca();
        if (IdMarca != null) {
            stmt.bindLong(8, IdMarca);
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
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // IdProducto
            cursor.getString(offset + 2), // Descripcion
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // Precio
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // Stock
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Activo
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // IdCategoria
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // IdMarca
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Producto entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdProducto(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setDescripcion(cursor.getString(offset + 2));
        entity.setPrecio(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setStock(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setActivo(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIdCategoria(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setIdMarca(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
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
