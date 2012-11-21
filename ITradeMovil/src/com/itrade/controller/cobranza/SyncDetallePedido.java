package com.itrade.controller.cobranza;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoDao.Properties;
import com.itrade.model.PedidoLinea;
import com.itrade.model.PedidoLineaDao;

public class SyncDetallePedido {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PedidoDao pedidoDao;
    private PedidoLineaDao pedidoLineaDao;
    private ClienteDao clienteDao;
    private List<Pedido> listaPedido;
    private List<ClienteMapa> listaCliente;
    private List<PedidoLinea> listaPedidoLinea;
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
	
    public SyncDetallePedido(Activity activ){
    	super();
		listaPedido=new ArrayList<Pedido>();
		listaCliente=new ArrayList<ClienteMapa>();
		listaPedidoLinea=new ArrayList<PedidoLinea>();
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pedidoDao = daoSession.getPedidoDao();
        clienteDao = daoSession.getClienteDao();
        pedidoLineaDao = daoSession.getPedidoLineaDao();
        sync=new Syncronizar(activity);
        gson = new Gson();  
    }
    
    public void closeDB(){
		db.close();
	}
    
    public boolean networkAvailable() {    	
    	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if (connectMgr != null) {
    		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
    		if (netInfo != null) {
    			for (NetworkInfo net : netInfo) {
    				if (net.getState() == NetworkInfo.State.CONNECTED) {
    					return true;
    				}
    			}
    		}
    	} 
    	else {
    		Log.d("NETWORK", "No network available");
    	}
    	return false;
    }

	public Integer syncBDToSqlite(String idusuario) {
		// TODO Auto-generated method stub
		Integer registros=0;
		
		if (networkAvailable()){
    		//Primero intentar sincronizar la BD SQLite antes de limpiar la BD
			//if (syncSqliteToBD()){
			try{
	    		//Si tengo conexion llamo al webservice				
	    		//Si obtuve todos los registros limpio la base de datos SQLite
				
				Log.d("SqlLite","Entro a cargar");
				//registros +=cargarPedido(idpedido);
				registros +=cargarDetallePedidoTotales(idusuario);
				//registros +=cargarDetallePedido(idpedido);
				//WEB SERVICE SAQUE TODO EL DETALLE X PEDIDO
			}catch(Exception e){
			//}else{
				Log.d("SqlLite","No tiene cobertura");
				Log.d("Error", "SE CAYO" + " "+e.getMessage());
    			//Enviar mensaje que primero debe obtener cobertura, de otra manera se perderan los pedidos registrados				
			//}    	
			}
		}
		else{
			Toast.makeText(context, "No Hay Conexion a Internet", Toast.LENGTH_LONG).show();
			List<PedidoLinea> aux=pedidoLineaDao.queryBuilder()
					.list();
			return aux.size();
			
		}
		
		return registros;
	}

	private Integer cargarDetallePedidoTotales(String idusuario) {
		// TODO Auto-generated method stub
		List<Pedido> pedidos=pedidoDao.queryBuilder()
				.where(Properties.IdEstadoPedido.eq("1"))
				.list();
		
    	String pedidosws = "";
    	for(Pedido pedido: pedidos){				
			//adapter.addItem(ped);
			//iNSERT EN LA bd
			pedidosws+=pedido.getIdPedido()+"-";
			//Log.d("fecha",""+pedido.getFechaPedido().toString());
		}
    	List<NameValuePair> parameters;
    	parameters= new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("idspedidos", pedidosws));
		String route2="/ws/pedido/get_pedidos_detail/";
		sync.conexion(parameters,route2);
		try {
			sync.getHilo().join();
		} catch (InterruptedException e) {
			  // TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<PedidoLinea> listDetallePedidos = new ArrayList<PedidoLinea>();
		listDetallePedidos	=	gson.fromJson(sync.getResponse(), new TypeToken<List<PedidoLinea>>(){}.getType());	 
		
		List<PedidoLinea> listaPedTemp=pedidoLineaDao.queryBuilder()
							.list();
				
		for(PedidoLinea linea: listDetallePedidos ){
			if (!listaPedTemp.contains(linea)){
				pedidoLineaDao.insert(linea);
			}
		}
		Log.d("Num detalle pedidos",""+listDetallePedidos.size());
		return listDetallePedidos.size();
	}

	private Integer cargarDetallePedido(String idpedido) {
		// TODO Auto-generated method stub
		Log.d("cargarDetallePedido", "LLEGO");
		Log.d("LOG", idpedido);
		
		List<PedidoLinea> listaPedTemp=pedidoLineaDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		
		if (listaPedTemp.isEmpty() || listaPedTemp.size()==0){
		
			List<NameValuePair> param = new ArrayList<NameValuePair>();								
			param.add(new BasicNameValuePair("idpedido", idpedido));				
			sync.conexion(param,"/ws/pedido/get_detail_by_idpedido/");
			try {
				sync.getHilo().join();			
			} catch (InterruptedException e) {
				  // TODO Auto-generated catch block
				e.printStackTrace();
			}	    	  		
			
			ArrayList<PedidoLinea> linPedList = new ArrayList<PedidoLinea>();			
			linPedList=gson.fromJson(sync.getResponse(), new TypeToken<List<PedidoLinea>>(){}.getType());		
			if (linPedList.size()>0){
				for(PedidoLinea linea: linPedList){				
					//adapter.addItem(ped);
					//iNSERT EN LA bd
					pedidoLineaDao.insert(linea);
				}
			}else{
				Log.d("cargarDetallePedido", "No hay datos");
			}
		
		}
		else{
			Log.d("cargarDetallePedido", "Ya se encuentra en la bd");
		}
		
		return listaPedTemp.size();
	}

	public List<PedidoLinea> buscarLineaPedidos(String idpedido) {
		// TODO Auto-generated method stub
		List<PedidoLinea> listaPedTemp=pedidoLineaDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		
		List<PedidoLinea> listatotal=pedidoLineaDao.queryBuilder()
								.list();
		int size=listatotal.size();
		Log.d("sizetotal",""+size);
		
		return listaPedTemp;
	}
    
    
    
}
