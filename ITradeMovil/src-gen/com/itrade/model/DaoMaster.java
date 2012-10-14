package com.itrade.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.IdentityScopeType;

import com.itrade.model.ClienteDao;
import com.itrade.model.ProspectoDao;
import com.itrade.model.UsuarioDao;
import com.itrade.model.ProductoDao;
import com.itrade.model.PersonaDao;
import com.itrade.model.PedidoDao;
import com.itrade.model.CategoriaDao;
import com.itrade.model.PedidoLineaDao;
import com.itrade.model.ElementoListaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        ClienteDao.createTable(db, ifNotExists);
        ProspectoDao.createTable(db, ifNotExists);
        UsuarioDao.createTable(db, ifNotExists);
        ProductoDao.createTable(db, ifNotExists);
        PersonaDao.createTable(db, ifNotExists);
        PedidoDao.createTable(db, ifNotExists);
        CategoriaDao.createTable(db, ifNotExists);
        PedidoLineaDao.createTable(db, ifNotExists);
        ElementoListaDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        ClienteDao.dropTable(db, ifExists);
        ProspectoDao.dropTable(db, ifExists);
        UsuarioDao.dropTable(db, ifExists);
        ProductoDao.dropTable(db, ifExists);
        PersonaDao.dropTable(db, ifExists);
        PedidoDao.dropTable(db, ifExists);
        CategoriaDao.dropTable(db, ifExists);
        PedidoLineaDao.dropTable(db, ifExists);
        ElementoListaDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(ClienteDao.class);
        registerDaoClass(ProspectoDao.class);
        registerDaoClass(UsuarioDao.class);
        registerDaoClass(ProductoDao.class);
        registerDaoClass(PersonaDao.class);
        registerDaoClass(PedidoDao.class);
        registerDaoClass(CategoriaDao.class);
        registerDaoClass(PedidoLineaDao.class);
        registerDaoClass(ElementoListaDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
