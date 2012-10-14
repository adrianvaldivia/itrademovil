package com.itrade.db;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itrade.model.Categoria;
import com.itrade.model.Producto;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOProducto {

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_IDPRODUCTO = "IdProducto";
	private static String KEY_DESCRIPCION = "Descripcion";
	private static String KEY_PRECIO = "Precio";
	private static String KEY_STOCK = "Stock";
	private static String KEY_IDCATEGORIA = "IdCategoria";
	private static String KEY_IDMARCA = "IdMarca";
	private static String KEY_ACTIVO = "Activo";
	private static String KEY_PRODUCTOS = "productos";
	private static String KEY_CATEGORIAS = "categorias";
	
    JSONArray productos = null;
    JSONArray categorias = null;
	
	public DAOProducto(){
		super();
	}

	public List<Producto> getAllProductos() {
		List<Producto> listaproducto = new ArrayList<Producto>();
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json = userFunction.getAllProductos();
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				
				if(Integer.parseInt(res) == 1){					
					productos = json.getJSONArray(KEY_PRODUCTOS);						
//					JSONObject json_user = json.getJSONObject("user");
                    for (int i = 0; i < productos.length(); i++) {
                        JSONObject json_producto = productos.getJSONObject(i);
                        
                        
                      Producto producto = new Producto();
                      // Storing each json item in variable 
                      String strIdproducto=json_producto.getString(KEY_IDPRODUCTO);
                      String strDescripcion=json_producto.getString(KEY_DESCRIPCION);
                      String strPrecio=json_producto.getString(KEY_PRECIO);
                      String strStock=json_producto.getString(KEY_STOCK);
                      String strIdcategoria=json_producto.getString(KEY_IDCATEGORIA);
                      String strIdmarca=json_producto.getString(KEY_IDMARCA);
                      String strActivo=json_producto.getString(KEY_ACTIVO);
                      
                      producto.setId(new Long(strIdproducto));
                  
                      producto.setDescripcion(strDescripcion);
                      producto.setPrecio(Double.parseDouble((strPrecio)));
                      producto.setStock(new Integer(strStock));
                      producto.setIdCategoria(new Integer(strIdcategoria));
                      producto.setIdMarca(new Integer(strIdmarca));
                      producto.setActivo(strActivo);
              
                      // adding object to ArrayList
                      listaproducto.add(producto);
                    }
					
				}else{
					// Error when trying to getAllproductos
//					resul=-1;
//					loginErrorMsg.setText("No hay productos");
				}
			}
			else{
//				resul=-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();			
		}		
		return listaproducto;
	}	

	
	public List<Categoria> getAllCategorias() {
		List<Categoria> listacategoria = new ArrayList<Categoria>();
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json = userFunction.getAllCategorias();
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				
				if(Integer.parseInt(res) == 1){					
					categorias = json.getJSONArray(KEY_CATEGORIAS);						
//					JSONObject json_user = json.getJSONObject("user");
                    for (int i = 0; i < categorias.length(); i++) {
                        JSONObject json_categoria = categorias.getJSONObject(i);
                        
                        
                      Categoria categoria = new Categoria();
                      // Storing each json item in variable
                      String strIdcategoria=json_categoria.getString(KEY_IDCATEGORIA);
                      String strDescripcion=json_categoria.getString(KEY_DESCRIPCION);
                                            
                      categoria.setId(new Long(strIdcategoria));
                      categoria.setDescripcion(strDescripcion);
              
                      // adding object to ArrayList
                      listacategoria.add(categoria);
                    }
					
				}else{
					// Error when trying to getAllproductos
//					resul=-1;
//					loginErrorMsg.setText("No hay productos");
				}
			}
			else{
//				resul=-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();			
		}		
		return listacategoria;
	}
	
}
