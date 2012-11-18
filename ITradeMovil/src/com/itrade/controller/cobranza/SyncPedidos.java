package com.itrade.controller.cobranza;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.itrade.cobranzas.ClientesListTask;
import com.itrade.model.ClienteMapa;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.DaoSession;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.PedidoDao.Properties;

public class SyncPedidos {
	private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PedidoDao pedidoDao;
    private ClienteDao clienteDao;
    private List<Pedido> listaPedido;
    private List<ClienteMapa> listaCliente;
    private Context context;
    private Activity activity;
    private Syncronizar sync;
    private Gson gson;
	public SyncPedidos(Activity activ) {
		super();
		listaPedido=new ArrayList<Pedido>();
		listaCliente=new ArrayList<ClienteMapa>();
		activity=activ;
		this.context=activ;
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pedidoDao = daoSession.getPedidoDao();
        clienteDao = daoSession.getClienteDao();
        sync=new Syncronizar(activity);
        gson = new Gson();  
	}
	
	public void closeDB(){
		db.close();
	}
	public int cargarPedidos(String idusuario){
		//Consulta al Webservice		
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", idusuario));	
		sync.conexion(param,"/ws/pedido/get_pedidos_by_idvendedor/");
		try {
			sync.getHilo().join();
		} catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Pedido> pedList = new ArrayList<Pedido>();			 
		//Log.d("Pedido", sync.getResponse());
		pedList=gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());	  
		//bORRAR TODOS LOS PEDIDOS
		if (pedList.isEmpty()){
			Log.d("Pedido", "No hay datos de arriba");
			return 0;
		}
		//quitar el getPedidosHoy, buscar todos los pedidos, comparar el estado y modificar arriba,
		//si todo esta ok no hacer nada si esta pagado modificar arriba (esta en payment task) do it!
		//si esta elimnado... bueno ya esta esa parte...
		List<Pedido> pedList2= this.getPedidosHoyTotal();
		
		if (!pedList2.isEmpty()){
//			Pedido ped=pedList2.get(0);
//			if ((pedList.size()==pedList2.size())&&(pedList.get(0).getIdPedido().equals(ped.getIdPedido()))){
//				Log.d("SqlLite","Hey they are equals");
//			}
//			else{
//				List<Pedido> pedList3= this.getPedidosEliminados();
//				//Actualizar arriba
//				List<NameValuePair> parameters;
//				for (Pedido pedidin:pedList3){
//					parameters= new ArrayList<NameValuePair>();
//					parameters.add(new BasicNameValuePair("idpedido", pedidin.getIdPedido().toString()));
//					String route2="/ws/pedido/cancelar_pedido/";
//					sync.conexion(parameters,route2);
//					try {
//						sync.getHilo().join();
//					} catch (InterruptedException e) {
//						  // TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				List<NameValuePair> parameters;
				for (Pedido pedidin:pedList2){
					if (pedidin.getIdEstadoPedido()==3){
						parameters= new ArrayList<NameValuePair>();
						parameters.add(new BasicNameValuePair("idpedido", pedidin.getIdPedido().toString()));
						String route2="/ws/pedido/cancelar_pedido/";
						sync.conexion(parameters,route2);
						try {
							sync.getHilo().join();
						} catch (InterruptedException e) {
							  // TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (pedidin.getIdEstadoPedido()==2){
						parameters = new ArrayList<NameValuePair>();	
						//String numVoucher= editNumVoucher.getText().toString();
						param.add(new BasicNameValuePair("idpedido", pedidin.getIdPedido().toString()));
						param.add(new BasicNameValuePair("montocobrado", pedidin.getMontoTotalPedido().toString()));			
						if (pedidin.getNumVoucher()!=null){
							param.add(new BasicNameValuePair("numVoucher", pedidin.getNumVoucher()));
						}else{
							param.add(new BasicNameValuePair("numVoucher", ""));
						}
						String route2="/ws/pedido/pagar_pedido/";
						sync.conexion(param,route2);
						try {
							sync.getHilo().join();			
						} catch (InterruptedException e) {
							  // TODO Auto-generated catch block
							e.printStackTrace();
						}	    	  
					}
				}
				//SI sale lo de arriba esto seria x las weee
//				pedidoDao.deleteAll();
//				for(Pedido pedido: pedList){				
//					//adapter.addItem(ped);
//					//iNSERT EN LA bd
//					pedidoDao.insert(pedido);
//				}
//			}
		}else{
			pedidoDao.deleteAll();
			for(Pedido pedido: pedList){				
				//adapter.addItem(ped);
				//iNSERT EN LA bd
				pedidoDao.insert(pedido);
				//Log.d("fecha",""+pedido.getFechaPedido().toString());
			}
		}
		Toast.makeText(context, "Syncronizado correctamente", Toast.LENGTH_LONG).show();
		return pedList.size();
	}
	public int cargarClientes(String idusuario){		
		//Limpio la BD
		clienteDao.deleteAll();
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idvendedor", idusuario));	
		sync.conexion(param,"/ws/clientes/get_clientes_by_vendedor/");
		try {
			sync.getHilo().join();			
		} catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<ClienteMapa> cliList = new ArrayList<ClienteMapa>();
		cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<ClienteMapa>>(){}.getType());	  		
		for(ClienteMapa cliente: cliList ){
			clienteDao.insert(cliente);
		}
		return cliList.size();
	}
	
	public boolean syncSqliteToBD(){
		
		return true;
	}
	public Integer syncBDToSqlite(String idusuario){    	
		Integer registros=0;
		//Compruebo si tengo conexión a internet;    			
		if (networkAvailable()){
    		//Primero intentar sincronizar la BD SQLite antes de limpiar la BD
			//if (syncSqliteToBD()){
			try{
	    		//Si tengo conexion llamo al webservice				
	    		//Si obtuve todos los registros limpio la base de datos SQLite
				
				Log.d("SqlLite","Entro a cargar");
				registros +=cargarClientes(idusuario);
				registros +=cargarPedidos(idusuario);
			}catch(Exception e){	
			//}else{
				Log.d("SqlLite","No tiene cobertura");
				Log.d("Error", "SE CAYO");
    			//Enviar mensaje que primero debe obtener cobertura, de otra manera se perderan los pedidos registrados				
			//}    	
			}
		}else{
    		//Obtener los clientes del vendedor 
			Log.d("SqlLite","No hay internet");
			Toast.makeText(context, "No Hay Conexion a Internet", Toast.LENGTH_LONG).show();
//			List<Cliente> listTemp;
//			listTemp = clienteDao.queryBuilder()
//    				.where(com.itrade.model.ClienteDao.Properties.IdCobrador.eq (idusuario))
//    				.list();    		
//    		//Pedido mipedido2 = new Pedido(null, null, 1, 1, 1, "2012-10-25", "2012-10-31", 123.12, 12.23, 135.12, 0.2, "Q", 0.5);
//    		//pedidoDao.insert(mipedido2);
//			//Obtener la fecha de hoy
//			// ESTE CODIGO X LAS WE
//			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");		
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(new Date());
//			calendar.add(Calendar.DAY_OF_MONTH,-7);
//			String previousDate = form.format(calendar.getTime());			
//			for (Cliente cliente : listTemp)  {
//				List<Pedido> pedTemp=pedidoDao.queryBuilder()
//						.where(Properties.FechaPedido.eq(previousDate))
//						.where(Properties.IdCliente.eq(cliente.getIdCliente()))
//						.where(Properties.CheckIn.eq(1))					
//						.list();
//				listaPedido.addAll(pedTemp);
//				if (pedTemp.size()>0){
//					listaCliente.add(cliente);					
//				}											
//			}			
		}      	    	    
    	return registros;
    }
	
	
    public List<Pedido> getListaPedido() {    	
    	return pedidoDao.loadAll();
	}
    
    public List<Pedido> getPedidosHoy(){
    	List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.IdEstadoPedido.eq("1"))
				.list();
		return pedTemp;    	
    }
    
    public List<Pedido> getPedidosHoyTotal(){
    	SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH,-7);
		String previousDate = form.format(calendar.getTime());
    	List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.FechaPedido.eq(previousDate))
				.list();
		return pedTemp;    	
    }
    
    public List<Pedido> getPedidosEliminados(){
    	List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.IdEstadoPedido.eq("3"))
				.list();
		return pedTemp;    	
    }
    
    public List<ClienteMapa> getListaCliente() {		
		return clienteDao.loadAll();
	}
    
	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}
	
	public void setListaCliente(List<ClienteMapa> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public int syncPedidos(){
    	//Obtener data de pedidos actual de SQLite
    	listaPedido = pedidoDao.loadAll();  
    	//Si tengo conexion a internet
    	if (networkAvailable()){
    		//Si hay llamar al webservice actualizador por cada registro que tenga en la bdSQLITE
    		//Toast.makeText( context,"Hay internet",Toast.LENGTH_SHORT).show();    		 
    		Log.d("INTERNET", "hay internet!!!!!!! cantidad="+listaPedido.size());
    		return 1;
    	}else{
    		//Si no tengo enviar mensaje respectivo
    		//Toast.makeText( context,"NOOOOO Hay internet",Toast.LENGTH_SHORT).show();
    		Log.d("INTERNET", "no hay iternet :( cantidad="+listaPedido.size());
    		return 0;
    	}    	    	    		      
    }
    
   
    public int syncClientes(){    	
    	listaCliente = clienteDao.loadAll();  
    	if (networkAvailable()){  		
    		Log.d("INTERNET", "hay internet!!!!!!! cantidad="+listaCliente.size());
    		return 1;
    	}else{
    		Log.d("INTERNET", "no hay iternet :( cantidad="+listaCliente.size());
    		return 0;
    	}  
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

	public Integer eliminarPedido(String idpedido) {
		// TODO Auto-generated method stub
		List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		Pedido ped=pedTemp.get(0);
		ped.setIdEstadoPedido(3);
		pedidoDao.update(ped);		
		return 1;
	}

	public Integer pagarPedido(String idpedido,String numVoucher) {
		// TODO Auto-generated method stub
		List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		Pedido ped=pedTemp.get(0);
		ped.setIdEstadoPedido(2);
		ped.setNumVoucher(numVoucher);
		Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");        
		ped.setFechaCobranza(formateador.format(ahora).toString());
		pedidoDao.update(ped);	
		Log.d("fechacob",ped.getFechaCobranza().toString());
		return pedTemp.size();
	}

	public ClienteMapa buscarCliente(String idcliente) {
		// TODO Auto-generated method stub
		
		List<ClienteMapa> clie=clienteDao.queryBuilder()
				.where(Properties.IdCliente.eq(idcliente))
				.list();
		ClienteMapa cliente=clie.get(0);
		return cliente;
	}

	public Pedido buscarPedido(String idpedido) {
		// TODO Auto-generated method stub
		List<Pedido> pedTemp=pedidoDao.queryBuilder()
				.where(Properties.IdPedido.eq(idpedido))
				.list();
		Pedido ped=pedTemp.get(0);
		return ped;
	}
    
}
