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
import com.itrade.db.DAOContacto;
import com.itrade.db.DAOEvento;
import com.itrade.db.DAOMeta;
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


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsTaskCargarDatos extends AsyncTask<String, Void, String>
{		
	DAOCliente daoCliente =null;
	DAOPedido daoPedido =null;
	DAOEvento daoEvento =null;
	DAOContacto daoContacto =null;
	DAOProducto daoProducto =null;
	DAOProspecto daoProspecto =null;
	//modif ela
	DAOMeta daoMeta = null;
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
    //fin green dao
    
    
    
    
	 private ProgressDialog dialog;
	 private Activity activity;		 
	 String nusuario="";
	 String pass="";
	 Usuario usuario;
	 Calendar _calendar;

	 public AsTaskCargarDatos(Activity activ,Usuario usu) {		 
	        this.activity = activ;		
	        this.usuario=usu;
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
		        
//		        pedidoLineaDao.deleteAll();
		        //fin green dao
	        }



	@Override
	protected String doInBackground(String... params) {
		String str="Hola";
		////////////////////////////////////
		// TODO Auto-generated method stub
					long idUsuario = usuario.getIdUsuario();
					long idUbigeo  = usuario.getIdUbigeo();
					usuario.setNombreReal("BDLOCAL");
					////////////////////////////////////////////////////////////Sincronizacion de Usuarios
					usuarioDao.deleteAll();
					usuarioDao.insert(usuario);
					if (usuario.getIdPerfil()==2){
						////////////////////////////////////////////////////////Sincronizacion de Productos
						daoProducto = new DAOProducto(activity);
						List<Producto> listaProducto = daoProducto.getAllProductos(); //obtiene los productos
						List<Categoria> listaCategoria=daoProducto.getAllCategorias();
				       
						productoDao.deleteAll();
						categoriaDao.deleteAll();
						
				        
						for(int i=0;i<listaProducto.size();i++){
							Producto productoAux = new Producto(null,listaProducto.get(i).getIdProducto(),listaProducto.get(i).getDescripcion(),listaProducto.get(i).getPrecio(),listaProducto.get(i).getStock(),listaProducto.get(i).getActivo(),listaProducto.get(i).getIdCategoria(),listaProducto.get(i).getIdMarca());		
							productoDao.insert(productoAux);				    
						}
						for(int i=0;i<listaCategoria.size();i++){
							Categoria categoria = new Categoria(null,listaCategoria.get(i).getIdCategoria(),listaCategoria.get(i).getDescripcion());
							categoriaDao.insert(categoria);
						}
	
						
						///////////////////////////////////////////////////Sincronizacion de Clientes		
				        daoCliente = new DAOCliente(activity);  
				        List<Cliente> listaCliente = daoCliente.getAllClientes(idUsuario); //obtiene los clientes
				        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
				        Double x;
						Double y;
						clienteDao.deleteAll();
						for(int i=0;i<listaCliente.size();i++){
							x=listaCliente.get(i).getLatitud();
							y=listaCliente.get(i).getLongitud();
							Cliente cliente2 = new Cliente(null,listaCliente.get(i).getIdPersona(),listaCliente.get(i).getIdCliente(),
									listaCliente.get(i).getNombre(),listaCliente.get(i).getApePaterno(),listaCliente.get(i).getTelefono(),
									listaCliente.get(i).getRazon_Social(),listaCliente.get(i).getRazon_Social(),
									listaCliente.get(i).getRUC(),x,y,listaCliente.get(i).getDireccion(),
									listaCliente.get(i).getIdCobrador(),listaCliente.get(i).getIdUsuario(),
									listaCliente.get(i).getActivo(),listaCliente.get(i).getMontoActual());
							cliente2.setActivo("A");//util para el checkin del mapa
					        clienteDao.insert(cliente2);
						}
						//////////////////////////////////////////////sincronizacion de pedidos
						daoPedido = new DAOPedido(activity);
						List<Pedido> listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos
						pedidoDao.deleteAll();        
						for(int i=0;i<listaPedido.size();i++){
							//numvoucher = A de antiguo
							Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),"A",listaPedido.get(i).getMontoTotal());
							pedidoDao.insert(pedido);
					        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
						}
						////////////////////////////////////////////////////////Sincronizacion de pedido Linea				
						int tam=0;
						String strIdPedidos="";
						tam=listaPedido.size();
						for(int i=0;i<tam;i++){
							Pedido pedAux= listaPedido.get(i);
							strIdPedidos=strIdPedidos+pedAux.getIdPedido()+"-";
				        }
						List<PedidoLinea> listaPedidoLinea = daoPedido.getAllPedidoLineas(strIdPedidos);
						
						Pedido pedidoAux3;
						pedidoLineaDao.deleteAll();
						long idPed=0;
						long idPedLi=0;
						for(int j=0;j<listaPedido.size();j++){
							long idLoc=1;
							idLoc=idLoc+j;
							pedidoAux3=listaPedido.get(j);
							idPed=pedidoAux3.getIdPedido();
							for(int i=0;i<listaPedidoLinea.size();i++){
								PedidoLinea pedLinTemp= listaPedidoLinea.get(i);
								idPedLi=pedLinTemp.getIdPedido();
								if (idPed==idPedLi){
									pedLinTemp.setIdPedido(idLoc);
									pedLinTemp.setMarca("A");//A de antiguo porque ya esta registrado
									pedidoLineaDao.insert(pedLinTemp);
								}							
							}
						}
	
						/////////////////////////////////////////////////////////////////////////fin  de Pedido Linea
						/////////////////////////////////////////////Sincronizacion eventos
						//if (usuario.getIdPerfil()==2){
							eventoDao.deleteAll();
							daoEvento = new DAOEvento(activity);
		//					String fechaEvento="2012-10-12";
							String fechaEvento=getFechaActual();
							List<Evento> listaEvento = daoEvento.getAllEventos(idUsuario,fechaEvento); //obtiene los eventos        
							for(int i=0;i<listaEvento.size();i++){
								//numvoucher = A de antiguo
								Evento evento = new Evento(null, listaEvento.get(i).getIdEvento(),listaEvento.get(i).getIdPersona(),listaEvento.get(i).getCreador(),listaEvento.get(i).getAsunto(),listaEvento.get(i).getLugar(),listaEvento.get(i).getDescripcion(),listaEvento.get(i).getFecha(),listaEvento.get(i).getHoraInicio(),listaEvento.get(i).getHoraFin());
								eventoDao.insert(evento);
						        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
							}		
							//sincronizacion de metas									
							metaDao.deleteAll();
							daoMeta = new DAOMeta(activity);
							//Meta meta= new Meta(null,null,100.0,"2012-11-10","2012-12-10",200.0,"Octubre 2012");
							Meta meta = daoMeta.buscarMetaxVendedor(""+idUsuario);
							//Log.d("meta de mela", meta.getNombre());
							metaDao.insert(meta);
						
						///////////////////////////////////////////////////sincronizacion de prospectos
						prospectoDao.deleteAll();   	
						daoProspecto = new DAOProspecto(activity);
						List<Prospecto> listaProspecto = daoProspecto.buscarProspectosxVendedor(""+idUsuario,"");
				        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
				        Double xx;
						Double yy;
	
				        
						for(int i=0;i<listaProspecto.size();i++){
							xx=listaProspecto.get(i).getLatitud();
							yy=listaProspecto.get(i).getLongitud();
							Prospecto prospecto2 = new Prospecto(null,listaProspecto.get(i).getIdPersona(),listaProspecto.get(i).getIdProspecto(),
									listaProspecto.get(i).getNombre(),listaProspecto.get(i).getApePaterno(),
									listaProspecto.get(i).getRazon_Social(),listaProspecto.get(i).getRazon_Social(),
									listaProspecto.get(i).getRUC(),xx,yy,listaProspecto.get(i).getDireccion(),
									listaProspecto.get(i).getIdCobrador(),listaProspecto.get(i).getIdUsuario(),
									listaProspecto.get(i).getActivo(),listaProspecto.get(i).getMontoActual(),
									listaProspecto.get(i).getDNI(),listaProspecto.get(i).getFechNac(),
									listaProspecto.get(i).getTelefono(),listaProspecto.get(i).getEmail());
							prospecto2.setActivo("A");//util para la sincronizacion de prospectos
					        prospectoDao.insert(prospecto2);	        	        
						}
						
						
						//sincronizacion de contactos
						
						contactoDao.deleteAll();
						daoContacto = new DAOContacto(activity);
	
						List<Contacto> listaContacto = daoContacto.getAllContacto(idUbigeo);        
						for(int i=0;i<listaContacto.size();i++){						
							Contacto contacto = new Contacto(null, listaContacto.get(i).getIdPersona() , listaContacto.get(i).getIdUsuario() , listaContacto.get(i).getNombre(), listaContacto.get(i).getApePaterno(), listaContacto.get(i).getApeMaterno(), listaContacto.get(i).getActivo(), listaContacto.get(i).getTelefono(),listaContacto.get(i).getEmail(), listaContacto.get(i).getIdJerarquia());
							contactoDao.insert(contacto);				        
						}	
					}
					//contactoDao.deleteAll();
					/*
					long temp=0;
					temp++;
					Contacto contacto= new Contacto(null,temp,null,"Benito","Leon","Cordova","A","951711196","benito@corp.com");
					contactoDao.insert(contacto);
					temp++;
					Contacto contacto2= new Contacto(null,temp,null,"Ushpa","Leon","Co","A","016392394","ushpa@corp.com");
					contactoDao.insert(contacto2);
					temp++;
					Contacto contacto3= new Contacto(null,temp,null,"Andres","Godinez","Co","A","971199644","anna@corp.com");
					contactoDao.insert(contacto3);*/

	    return str;
	}



	 @Override
	protected void onPostExecute(String result) {
	     if (this.dialog.isShowing()) {
	           this.dialog.dismiss();
	     }	     
	     lanzarActivitys(usuario);
	     db.close();
	}
	 private void lanzarActivitys(Usuario usuario) {
	    	//Preguntar si quiere borrar los datos del usuario logeado anteriormente	   
	    		if (usuario.getIdPerfil()==2){//PEDIDOS
	    			lanzarPedidos(usuario);
	    		}
	    		if (usuario.getIdPerfil()==3){//Cobranza
	    			Intent intent = new Intent(activity, ClientesListTask.class);					
	    		    String nombre= usuario.getNombre();
	    			String apellidos=usuario.getApePaterno()+" "+usuario.getApeMaterno();							
	    			intent.putExtra("idusuario", usuario.getIdUsuario().toString());
	    			intent.putExtra("nombre", nombre);
	    			intent.putExtra("apellidos", apellidos);
	    			//intent.putExtra("usuario", usuario);					
	    			activity.startActivity(intent);			
//	    			textView_Password.setText("");
	    		}    	    	
	    }
	 private void lanzarPedidos(Usuario usuario) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(activity, BuscarClientesGreenDao.class);					
		    String nombre= usuario.getNombre();
			String apellidos=usuario.getApePaterno()+" "+usuario.getApeMaterno();				
			intent.putExtra("idusuario", usuario.getIdUsuario());
			intent.putExtra("nombre", nombre);
			intent.putExtra("apellidos", apellidos);
			//intent.putExtra("usuario", usuario);					
			activity.startActivity(intent);					
//			textView_Password.setText("");
		}
//	 private void sincronizarBase(Usuario usuario) {
//			// TODO Auto-generated method stub
//			long idUsuario=usuario.getIdUsuario();
//			usuario.setNombreReal("BDLOCAL");
//			////////////////////////////////////////////////////////////Sincronizacion de Usuarios
//			usuarioDao.deleteAll();
//			usuarioDao.insert(usuario);
//			////////////////////////////////////////////////////////Sincronizacion de Productos
//			daoProducto = new DAOProducto(activity);
//			List<Producto> listaProducto = daoProducto.getAllProductos(); //obtiene los productos
//			List<Categoria> listaCategoria=daoProducto.getAllCategorias();
//	       
//			productoDao.deleteAll();
//			categoriaDao.deleteAll();
//			
//	        
//			for(int i=0;i<listaProducto.size();i++){
//				Producto productoAux = new Producto(null,listaProducto.get(i).getIdProducto(),listaProducto.get(i).getDescripcion(),listaProducto.get(i).getPrecio(),listaProducto.get(i).getStock(),listaProducto.get(i).getActivo(),listaProducto.get(i).getIdCategoria(),listaProducto.get(i).getIdMarca());		
//				productoDao.insert(productoAux);				    
//			}
//			for(int i=0;i<listaCategoria.size();i++){
//				Categoria categoria = new Categoria(null,listaCategoria.get(i).getIdCategoria(),listaCategoria.get(i).getDescripcion());
//				categoriaDao.insert(categoria);
//			}
//
//			
//			///////////////////////////////////////////////////Sincronizacion de Clientes		
//	        daoCliente = new DAOCliente(activity);  
//	        List<Cliente> listaCliente = daoCliente.getAllClientes(idUsuario); //obtiene los clientes
//	        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
//	        Double x;
//			Double y;
//			clienteDao.deleteAll();
//			for(int i=0;i<listaCliente.size();i++){
//				x=listaCliente.get(i).getLatitud();
//				y=listaCliente.get(i).getLongitud();
//				Cliente cliente2 = new Cliente(null,listaCliente.get(i).getIdPersona(),listaCliente.get(i).getIdCliente(),
//						listaCliente.get(i).getNombre(),listaCliente.get(i).getApePaterno(),
//						listaCliente.get(i).getRazon_Social(),listaCliente.get(i).getRazon_Social(),
//						listaCliente.get(i).getRUC(),x,y,listaCliente.get(i).getDireccion(),
//						listaCliente.get(i).getIdCobrador(),listaCliente.get(i).getIdUsuario(),
//						listaCliente.get(i).getActivo(),listaCliente.get(i).getMontoActual());
//				cliente2.setActivo("A");//util para el checkin del mapa
//		        clienteDao.insert(cliente2);
//			}
//			//////////////////////////////////////////////sincronizacion de pedidos
//			daoPedido = new DAOPedido(activity);
//			List<Pedido> listaPedido = daoPedido.getAllPedidos(idUsuario); //obtiene los pedidos
//			pedidoDao.deleteAll();        
//			for(int i=0;i<listaPedido.size();i++){
//				//numvoucher = A de antiguo
//				Pedido pedido = new Pedido(null, listaPedido.get(i).getIdPedido(),listaPedido.get(i).getIdCliente(),listaPedido.get(i).getIdEstadoPedido(),listaPedido.get(i).getCheckIn(),listaPedido.get(i).getFechaPedido(),listaPedido.get(i).getFechaCobranza(),listaPedido.get(i).getMontoSinIGV(),listaPedido.get(i).getIGV(),listaPedido.get(i).getMontoTotalPedido(),listaPedido.get(i).getMontoTotalCobrado(),"A",listaPedido.get(i).getMontoTotal());
//				pedidoDao.insert(pedido);
//		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//			}
//			////////////////////////////////////////////////////////Sincronizacion de pedido Linea
////			pedidoLineaDao.deleteAll();
//			/////////////////////////////////////////////Sincronizacion eventos
//			eventoDao.deleteAll();
//			daoEvento = new DAOEvento(activity);
////			String fechaEvento="2012-10-12";
//			String fechaEvento=getFechaActual();
//			List<Evento> listaEvento = daoEvento.getAllEventos(idUsuario,fechaEvento); //obtiene los eventos        
//			for(int i=0;i<listaEvento.size();i++){
//				//numvoucher = A de antiguo
//				Evento evento = new Evento(null, listaEvento.get(i).getIdEvento(),listaEvento.get(i).getCreador(),listaEvento.get(i).getAsunto(),listaEvento.get(i).getLugar(),listaEvento.get(i).getDescripcion(),listaEvento.get(i).getFecha(),listaEvento.get(i).getHoraInicio(),listaEvento.get(i).getHoraFin());
//				eventoDao.insert(evento);
//		        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//			}		
//			//sincronizacion de metas
//			metaDao.deleteAll();
//			Meta meta= new Meta(null,null,100.0,"2012-11-10","2012-12-10",200.0,"Octubre 2012");
//			metaDao.insert(meta);
//			///////////////////////////////////////////////////sincronizacion de prospectos
//			prospectoDao.deleteAll();   	
//			daoProspecto = new DAOProspecto(activity);
//			List<Prospecto> listaProspecto = daoProspecto.buscarProspectosxVendedor(""+idUsuario,"");
//	        //listaClienteOriginal = daoCliente.getAllClientes(this.idUsuario); //obtiene los clientes
//	        Double xx;
//			Double yy;
//
//	        
//			for(int i=0;i<listaProspecto.size();i++){
//				xx=listaProspecto.get(i).getLatitud();
//				yy=listaProspecto.get(i).getLongitud();
//				Prospecto prospecto2 = new Prospecto(null,listaProspecto.get(i).getIdPersona(),listaProspecto.get(i).getIdProspecto(),
//						listaProspecto.get(i).getNombre(),listaProspecto.get(i).getApePaterno(),
//						listaProspecto.get(i).getRazon_Social(),listaProspecto.get(i).getRazon_Social(),
//						listaProspecto.get(i).getRUC(),xx,yy,listaProspecto.get(i).getDireccion(),
//						listaProspecto.get(i).getIdCobrador(),listaProspecto.get(i).getIdUsuario(),
//						listaProspecto.get(i).getActivo(),listaProspecto.get(i).getMontoActual(),
//						listaProspecto.get(i).getDNI(),listaProspecto.get(i).getFechNac(),
//						listaProspecto.get(i).getTelefono(),listaProspecto.get(i).getEmail());
//				prospecto2.setActivo("A");//util para la sincronizacion de prospectos
//		        prospectoDao.insert(prospecto2);	        	        
//			}
//			//sincronizacion de contactos
//			contactoDao.deleteAll();
//			long temp=0;
//			temp++;
//			Contacto contacto= new Contacto(null,temp,null,"Benito","Leon","Cordova","A","997565670","benito@corp.com");
//			contactoDao.insert(contacto);
//			temp++;
//			Contacto contacto2= new Contacto(null,temp,null,"Ushpa","Leon","Co","A","976755699","ushpa@corp.com");
//			contactoDao.insert(contacto2);
//			temp++;
//			Contacto contacto3= new Contacto(null,temp,null,"Andres","Godinez","Co","A","971199644","anna@corp.com");
//			contactoDao.insert(contacto3);

			
//		}
	    private String getFechaActual() {
	    	String resul;			
			int month, year;
//	    	_calendar = Calendar.getInstance(Locale.getDefault());
	    	year = _calendar.get(Calendar.YEAR);
			month = _calendar.get(Calendar.MONTH) + 1;
			String strMonth=""+month;
			strMonth=agregaCeroMes(strMonth);		
			resul=""+year+"-"+strMonth+"-01";		
			return resul;
		}
		private String agregaCeroMes(String themonth) {
			String resul="";
			if (themonth.length()==1)
				resul="0"+themonth;
			else
				resul=""+themonth;
				
			return resul;
			
		}
	}