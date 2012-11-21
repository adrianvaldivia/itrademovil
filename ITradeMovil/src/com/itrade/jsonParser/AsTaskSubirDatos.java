package com.itrade.jsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.cobranzas.ClientesListTask;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.db.DAOCliente;
import com.itrade.db.DAOEvento;
import com.itrade.db.DAOPedido;
import com.itrade.db.DAOProducto;
import com.itrade.db.DAOProspecto;
import com.itrade.db.DAOUsuario;
import com.itrade.model.Categoria;
import com.itrade.model.CategoriaDao;
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.Contacto;
import com.itrade.model.ContactoDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.ElementoLista;
import com.itrade.model.ElementoListaDao;
import com.itrade.model.Evento;
import com.itrade.model.EventoDao;
import com.itrade.model.Meta;
import com.itrade.model.MetaDao;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoLinea;
import com.itrade.model.PedidoLineaDao;
import com.itrade.model.Producto;
import com.itrade.model.ProductoDao;
import com.itrade.model.Prospecto;
import com.itrade.model.ProspectoDao;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.pedidos.BuscarClientesGreenDao;
import com.itrade.pedidos.Login;
import com.itrade.pedidos.RegistrarProspecto;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class AsTaskSubirDatos extends AsyncTask<String, Void, String>
{		
	DAOCliente daoCliente =null;
	DAOPedido daoPedido =null;
	DAOEvento daoEvento =null;
	DAOProducto daoProducto =null;
	DAOProspecto daoProspecto =null;
    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private PedidoDao pedidoDao;
    private PedidoLineaDao pedidoLineaDao;
    private ProductoDao productoDao;
    private CategoriaDao categoriaDao;
    private EventoDao eventoDao;
    private MetaDao metaDao;
    private ProspectoDao prospectoDao;
    private ContactoDao contactoDao;
    private ElementoListaDao elementoListaDao;
    //fin green dao
    private Cursor cursorElementoLista;
    
    
    List<Cliente> listaCliente;
    
	 private ProgressDialog dialog;
	 private Activity activity;		 
	 String nusuario="";
	 String pass="";
	 long idusuario;
	 Calendar _calendar;

	 public AsTaskSubirDatos(Activity activ,long idusu,Cursor cursorElementoLis) {		 
	        this.activity = activ;		
	        this.idusuario=idusu;
	        this.cursorElementoLista=cursorElementoLis;
	        this.dialog = new ProgressDialog(activity);
	    }

	    @Override
	     protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
	            this.dialog.setMessage("Conectando");
	            this.dialog.show();
		    	_calendar = Calendar.getInstance(Locale.getDefault());
		    	
			    //inicio green DAO 
		        DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "itrade-db", null);
		        db = helper.getWritableDatabase();
		        daoMaster = new DaoMaster(db);
		        daoSession = daoMaster.newSession();
		        usuarioDao = daoSession.getUsuarioDao();
		        clienteDao = daoSession.getClienteDao();
		        pedidoDao = daoSession.getPedidoDao();
		        pedidoLineaDao = daoSession.getPedidoLineaDao();
		        productoDao = daoSession.getProductoDao();
		        categoriaDao = daoSession.getCategoriaDao();
		        eventoDao = daoSession.getEventoDao();
		        metaDao = daoSession.getMetaDao();
		        prospectoDao = daoSession.getProspectoDao();
		        contactoDao = daoSession.getContactoDao();
		        elementoListaDao = daoSession.getElementoListaDao();
		        
		        //fin green dao
	        }



	@Override
	protected String doInBackground(String... params) {
		String str="Hola";
		cargarBaseLocal();
	    return str;
	}



	 @Override
	protected void onPostExecute(String result) {
	     if (this.dialog.isShowing()) {
	    	    try {
	    	    	this.dialog.dismiss();
	    	        dialog = null;
	    	    } catch (Exception e) {
	    	        // nothing
	    	    }	    	 	           
	     }	     
	     actualizarUI();
	     db.close();
	}
	 private void actualizarUI() {
		 cursorElementoLista.requery();		
	}
	 
	 private void cargarBaseLocal() {
			sincronizarBaseSubida();
	        daoCliente = new DAOCliente(activity);  
	        listaCliente = daoCliente.getAllClientes(this.idusuario); //obtiene los clientes
	        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
	        Double x;
			Double y;
			clienteDao.deleteAll();
			elementoListaDao.deleteAll();
	        
			for(int i=0;i<listaCliente.size();i++){
				x=listaCliente.get(i).getLatitud();
				y=listaCliente.get(i).getLongitud();
				Cliente cliente2 = new Cliente(null,listaCliente.get(i).getIdPersona(),listaCliente.get(i).getIdCliente(),
						listaCliente.get(i).getNombre(),listaCliente.get(i).getApePaterno(),
						listaCliente.get(i).getRazon_Social(),listaCliente.get(i).getRazon_Social(),
						listaCliente.get(i).getRUC(),x,y,listaCliente.get(i).getDireccion(),
						listaCliente.get(i).getIdCobrador(),listaCliente.get(i).getIdUsuario(),
						listaCliente.get(i).getActivo(),listaCliente.get(i).getMontoActual());
				cliente2.setActivo("A");//util para el checkin del mapa
		        clienteDao.insert(cliente2);
		        long temp=0;
//		        temp=temp+listaCliente.get(i).getIdCliente();//aqui estaba el error
		        temp=temp+i+1;//aca tambien habia error
				ElementoLista elemento = new ElementoLista(null,listaCliente.get(i).getRazon_Social(),"RUC: "+listaCliente.get(i).getRUC(),null,temp);
				elementoListaDao.insert(elemento);
		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
			}
//	        cursorElementoLista.requery();		
//	        guardaListaOriginal();        
		}
	 
	 private void sincronizarBaseSubida() {
		 //sincronizacion de Prospectos
		 int tam2=0;
			DAOProspecto daoprospect = new DAOProspecto(activity);
		    List<Prospecto> prospectosAux = prospectoDao.queryBuilder()
		    		.where(com.itrade.model.ProspectoDao.Properties.Activo.eq("N"))
		    		.orderAsc(com.itrade.model.ProspectoDao.Properties.Id).list();
		    tam2=prospectosAux.size();
		    for(int i=0;i<tam2;i++){
		    	Prospecto prospectoTemp=prospectosAux.get(i);
		    	prospectoTemp.setActivo("A");;
		    	prospectoDao.deleteByKey(prospectoTemp.getId());
		    	prospectoDao.insert(prospectoTemp);
//				long idu3;
//				idu3 = Long.parseLong(idu);
				daoprospect.registrarProspecto(prospectoTemp, idusuario);
		    }
		 // fin de sincronizacion de prospectos
		 
		 	///sincronizacion de Pedidos
			long idPedido=0;
			long idPedidoLocal;
			int tam=0;
	        daoPedido = new DAOPedido(activity);
	        List<Pedido> pedidosAux = pedidoDao.queryBuilder()
	        		.where(com.itrade.model.PedidoDao.Properties.NumVoucher.eq("N"))
	        		.orderAsc(com.itrade.model.PedidoDao.Properties.Id).list();
	        tam=pedidosAux.size();
	        if (tam>0){
	                for(int i=0;i<tam;i++){
	                	Pedido pedidoTemp=pedidosAux.get(i);
	                	pedidoTemp.setNumVoucher("A");;
	                	pedidoDao.deleteByKey(pedidoTemp.getId());
	                	pedidoDao.insert(pedidoTemp);
	                	idPedidoLocal=pedidoTemp.getId();
	                	idPedido=daoPedido.registrarPedido(pedidoTemp);//id de bd externa
	                    List<PedidoLinea> pedidosLineaAux = pedidoLineaDao.queryBuilder()
	                    		.where(com.itrade.model.PedidoLineaDao.Properties.IdPedido.eq(idPedidoLocal))
	                    		.orderAsc(com.itrade.model.PedidoLineaDao.Properties.Id).list();
	                    for(int j=0;j<pedidosLineaAux.size();j++){
	                    	PedidoLinea pedidoLineaTemp=pedidosLineaAux.get(j);
	                    	pedidoLineaTemp.setIdPedido(idPedido);//lo setteo con el Id de BD externa
	                    	daoPedido.registrarPedidoLinea(pedidoLineaTemp);
	                    }
	                }        			        	   
	        }
	        //fin de sincronizacion de pedidos
	        ////Inicio sincronizacion de ....
	        
		}

	 
	 
//	    private String getFechaActual() {
//	    	String resul;			
//			int month, year;
////	    	_calendar = Calendar.getInstance(Locale.getDefault());
//	    	year = _calendar.get(Calendar.YEAR);
//			month = _calendar.get(Calendar.MONTH) + 1;
//			String strMonth=""+month;
//			strMonth=agregaCeroMes(strMonth);		
//			resul=""+year+"-"+strMonth+"-01";		
//			return resul;
//		}
//		private String agregaCeroMes(String themonth) {
//			String resul="";
//			if (themonth.length()==1)
//				resul="0"+themonth;
//			else
//				resul=""+themonth;
//				
//			return resul;
//			
//		}
	}