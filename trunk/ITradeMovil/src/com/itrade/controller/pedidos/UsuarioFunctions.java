package com.itrade.controller.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.itrade.jsonParser.JSONParser;


public class UsuarioFunctions {
	
	private JSONParser jsonParser;
	
	//private static String loginURL = "http://10.0.2.2/webservices/Login/";
	private static String URL = "http://190.222.214.208/webservices/Login/";
	private static String URL_producto = "http://190.222.214.208/webservices/Login/index_producto.php";
	private static String URL_pedidoJorge = "http://190.222.214.208/webservices/Login/index_pedido.php";
	private static String URL_pedidoDaniela = "http://190.222.214.208/webservices/Login/indexConPEDIDOyLineaPedido.php";
	
	
	private static String login_tag = "login";
	private static String cliente_tag = "cliente";
	private static String datosempleado_tag = "datosempleado";
	
	private static String getAllUsuarios_tag = "getAllUsuarios";
	private static String getAllPersonas_tag = "getAllPersonas";
	private static String getAllProductos_tag = "getAllProductos";
	private static String getAllCategorias_tag = "getAllCategorias";
	private static String getAllPedidos_tag = "getAllPedidos";
	
	private static String registrarPedido_tag = "registrarpedido";
	private static String registrarLineaPedido_tag = "registrarlineapedido";
	
	// constructor
	public UsuarioFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param nombre
	 * @param password
	 * */
	public JSONObject loginUser(String nombre, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("nombre", nombre));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject obtenerDatosEmpleado(int idusuario){
		// Building Parameters
		String strIdUsuario=String.valueOf(idusuario);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", datosempleado_tag));
		params.add(new BasicNameValuePair("idusuario", strIdUsuario));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getAllClientes(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", cliente_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getAllUsuarios(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllUsuarios_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	public JSONObject getAllProductos(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllProductos_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL_producto, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	public JSONObject getAllCategorias(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllCategorias_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL_producto, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	public JSONObject getAllPersonas(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllPersonas_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}
	
	public JSONObject registrarPedido(int idCliente, double montoSinIGV, double igv, double montoTotal){
		
		// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("idcliente", String.valueOf(idCliente)));
				params.add(new BasicNameValuePair("montosinigv", String.valueOf(montoSinIGV)));
				params.add(new BasicNameValuePair("igv", String.valueOf(igv)));
				params.add(new BasicNameValuePair("montototal", String.valueOf(montoTotal)));
				params.add(new BasicNameValuePair("tag", registrarPedido_tag));
		
		JSONObject json = jsonParser.getJSONFromUrl(URL_pedidoDaniela, params);
		
		return json;
	}
	
	public JSONObject registrarLineaPedido(int idPedido, int idProducto, double montoLinea, int cantidad){
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("IdPedido", String.valueOf(idPedido)));
		params.add(new BasicNameValuePair("IdProducto", String.valueOf(idProducto)));
		params.add(new BasicNameValuePair("MontoLinea", String.valueOf(montoLinea)));
		params.add(new BasicNameValuePair("Cantidad", String.valueOf(cantidad)));
		params.add(new BasicNameValuePair("tag", registrarLineaPedido_tag));
		
		JSONObject json = jsonParser.getJSONFromUrl(URL_pedidoDaniela, params);
		
		return json;
	}
	
	public JSONObject getAllPedidos(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getAllPedidos_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL_pedidoJorge, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
}
