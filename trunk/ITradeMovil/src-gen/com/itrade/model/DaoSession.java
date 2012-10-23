package com.itrade.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import com.itrade.model.Usuario;
import com.itrade.model.Cliente;
import com.itrade.model.Prospecto;
import com.itrade.model.Producto;
import com.itrade.model.Persona;
import com.itrade.model.Pedido;
import com.itrade.model.Categoria;
import com.itrade.model.PedidoLinea;
import com.itrade.model.ElementoLista;

import com.itrade.model.UsuarioDao;
import com.itrade.model.ClienteDao;
import com.itrade.model.ProspectoDao;
import com.itrade.model.ProductoDao;
import com.itrade.model.PersonaDao;
import com.itrade.model.PedidoDao;
import com.itrade.model.CategoriaDao;
import com.itrade.model.PedidoLineaDao;
import com.itrade.model.ElementoListaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig usuarioDaoConfig;
    private final DaoConfig clienteDaoConfig;
    private final DaoConfig prospectoDaoConfig;
    private final DaoConfig productoDaoConfig;
    private final DaoConfig personaDaoConfig;
    private final DaoConfig pedidoDaoConfig;
    private final DaoConfig categoriaDaoConfig;
    private final DaoConfig pedidoLineaDaoConfig;
    private final DaoConfig elementoListaDaoConfig;

    private final UsuarioDao usuarioDao;
    private final ClienteDao clienteDao;
    private final ProspectoDao prospectoDao;
    private final ProductoDao productoDao;
    private final PersonaDao personaDao;
    private final PedidoDao pedidoDao;
    private final CategoriaDao categoriaDao;
    private final PedidoLineaDao pedidoLineaDao;
    private final ElementoListaDao elementoListaDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        usuarioDaoConfig = daoConfigMap.get(UsuarioDao.class).clone();
        usuarioDaoConfig.initIdentityScope(type);

        clienteDaoConfig = daoConfigMap.get(ClienteDao.class).clone();
        clienteDaoConfig.initIdentityScope(type);

        prospectoDaoConfig = daoConfigMap.get(ProspectoDao.class).clone();
        prospectoDaoConfig.initIdentityScope(type);

        productoDaoConfig = daoConfigMap.get(ProductoDao.class).clone();
        productoDaoConfig.initIdentityScope(type);

        personaDaoConfig = daoConfigMap.get(PersonaDao.class).clone();
        personaDaoConfig.initIdentityScope(type);

        pedidoDaoConfig = daoConfigMap.get(PedidoDao.class).clone();
        pedidoDaoConfig.initIdentityScope(type);

        categoriaDaoConfig = daoConfigMap.get(CategoriaDao.class).clone();
        categoriaDaoConfig.initIdentityScope(type);

        pedidoLineaDaoConfig = daoConfigMap.get(PedidoLineaDao.class).clone();
        pedidoLineaDaoConfig.initIdentityScope(type);

        elementoListaDaoConfig = daoConfigMap.get(ElementoListaDao.class).clone();
        elementoListaDaoConfig.initIdentityScope(type);

        usuarioDao = new UsuarioDao(usuarioDaoConfig, this);
        clienteDao = new ClienteDao(clienteDaoConfig, this);
        prospectoDao = new ProspectoDao(prospectoDaoConfig, this);
        productoDao = new ProductoDao(productoDaoConfig, this);
        personaDao = new PersonaDao(personaDaoConfig, this);
        pedidoDao = new PedidoDao(pedidoDaoConfig, this);
        categoriaDao = new CategoriaDao(categoriaDaoConfig, this);
        pedidoLineaDao = new PedidoLineaDao(pedidoLineaDaoConfig, this);
        elementoListaDao = new ElementoListaDao(elementoListaDaoConfig, this);

        registerDao(Usuario.class, usuarioDao);
        registerDao(Cliente.class, clienteDao);
        registerDao(Prospecto.class, prospectoDao);
        registerDao(Producto.class, productoDao);
        registerDao(Persona.class, personaDao);
        registerDao(Pedido.class, pedidoDao);
        registerDao(Categoria.class, categoriaDao);
        registerDao(PedidoLinea.class, pedidoLineaDao);
        registerDao(ElementoLista.class, elementoListaDao);
    }
    
    public void clear() {
        usuarioDaoConfig.getIdentityScope().clear();
        clienteDaoConfig.getIdentityScope().clear();
        prospectoDaoConfig.getIdentityScope().clear();
        productoDaoConfig.getIdentityScope().clear();
        personaDaoConfig.getIdentityScope().clear();
        pedidoDaoConfig.getIdentityScope().clear();
        categoriaDaoConfig.getIdentityScope().clear();
        pedidoLineaDaoConfig.getIdentityScope().clear();
        elementoListaDaoConfig.getIdentityScope().clear();
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public ProspectoDao getProspectoDao() {
        return prospectoDao;
    }

    public ProductoDao getProductoDao() {
        return productoDao;
    }

    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public PedidoDao getPedidoDao() {
        return pedidoDao;
    }

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public PedidoLineaDao getPedidoLineaDao() {
        return pedidoLineaDao;
    }

    public ElementoListaDao getElementoListaDao() {
        return elementoListaDao;
    }

}
