<?
class Reporte_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_pedido = 'Pedido';
		$this->table_cliente = 'Cliente';
		$this->table_usuario = 'Usuario';
		$this->table_ubigeo = 'Ubigeo';
		$this->table_jerarquia = 'Jerarquia';
		$this->table_meta = 'Meta';
		$this->table_marca = 'Marca';
		$this->table_categoria = 'Categoria';
		$this->table_linea_pedido = 'Linea_Pedido';
		$this->table_producto = 'Producto';
    }	
	
	
	public function zonas_resumido($month,$idjerarquia,$idubigeo,$id){				
		switch($idjerarquia){
			case 1://pais
			//select date_format(now(),'%M')as Monthname;
				//$this->db->select("date_format(now(),'%M') as Monthname");
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_ubigeo.".Departamento");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");
				break;
			case 2://departamento	
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_ubigeo.".Distrito");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");
				break;
			case 3://distrito
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_ubigeo.".Zona");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");		
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");				
				break;
		}		
			
		$this->db->from($this->table_pedido);						
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");				
											
		switch($idjerarquia){
			case 1:				
				$this->db->where($this->table_ubigeo.".Pais", $id);
				break;
			case 2:				
				$this->db->where($this->table_ubigeo.".Departamento", $id);
				break;
			case 3:				
				$this->db->where($this->table_ubigeo.".Distrito", $id);
				break;
		}			
		$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO			
		switch($idjerarquia){
			case 1://pais
				$this->db->group_by($this->table_ubigeo.".Departamento");				
				break;
			case 2://departamento
							
				$this->db->group_by($this->table_ubigeo.".Distrito");	
				break;
			case 3://distrito							
				$this->db->group_by($this->table_ubigeo.".Zona");	
				break;
		}				
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	function objetoUbigeo($idubigeo,$idjerarquia){
		switch($idjerarquia){
			case 1://pais
				$this->db->select($this->table_ubigeo.".Pais as nombre");								
				break;
			case 2://departamento							
				$this->db->select($this->table_ubigeo.".Deparmento as nombre");						
				break;
			case 3://distrito		
				$this->db->select($this->table_ubigeo.".Distrito as nombre");								
				break;
		}
		$this->db->where('idUbigeo', $idubigeo);
		$query = $this->db->get($this->table_ubigeo);
		//echo $this->db->last_query();		
        return $query->result();		
	}
	
	//FUNCION ZONAS DETALLADO
	
		public function zonas_detallado($month,$idjerarquia,$idubigeo,$id){				
		switch($idjerarquia){
			case 1://pais
				$this->db->select($this->table_ubigeo.".Departamento");					
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");
				break;
			case 2://departamento							
				$this->db->select($this->table_ubigeo.".Distrito");			
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");
				break;
			case 3://distrito		
				$this->db->select($this->table_ubigeo.".Zona");					
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");
				$this->db->select_sum($this->table_pedido.".MontoTotalCobrado");						
				break;
		}				
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");				
										
		switch($idjerarquia){
			case 1:				
				$this->db->where($this->table_ubigeo.".Pais", $id);
				break;
			case 2:				
				$this->db->where($this->table_ubigeo.".Departamento", $id);
				break;
			case 3:				
				$this->db->where($this->table_ubigeo.".Distrito", $id);
				break;
		}			
		//$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		//$this->db->where($str);
		
		//prueba
		$str="month(".$this->table_pedido.".FechaPedido) >= MONTH( DATE_SUB( CURDATE( ) , INTERVAL 7 MONTH ) )";		
		$this->db->where($str);
		
		//$str1="month(".$this->table_pedido.".FechaPedido) = ".$month-1;		
		//$this->db->or_where($str1);
		
		//UBIGEO
		//$dates="(DATEDIFF(".$this->table_atencion_omega.".fecAtencion,".$this->table_atencion.".fecha)=0)";
		//$this->db->where($dates);
		//$this->//FECHA			
		switch($idjerarquia){
			case 1://pais
				$this->db->group_by($this->table_ubigeo.".Departamento");				
				break;
			case 2://departamento
							
				$this->db->group_by($this->table_ubigeo.".Distrito");	
				break;
			case 3://distrito							
				$this->db->group_by($this->table_ubigeo.".Zona");	
				break;
		}			
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	//Reporte de Circulo de Excelencia
	
	public function circulo_resumido($month,$iddistrito,$iddepartamento,$idpais){				
		
		//SELECTS
		
		$this->db->select($this->table_ubigeo.".Zona");				
		$this->db->select_sum($this->table_pedido.".MontoTotalPedido");						
		$this->db->select_sum($this->table_meta.".Monto");
						
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");	
		/*$this->db->join($this->table_departamento,$this->table_ubigeo.".IdDepartamento =".$this->table_departamento.".IdDepartamento");
		$this->db->join($this->table_distrito,$this->table_ubigeo.".IdDistrito =".$this->table_distrito.".IdDistrito");
		$this->db->join($this->table_zona,$this->table_ubigeo.".IdZona =".$this->table_zona.".IdZona");*/
		$this->db->join($this->table_meta,$this->table_usuario.".IdUsuario =".$this->table_meta.".IdUsuario");
												
				
		//$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		//$this->db->where($str);
		
		//prueba
		$str="month(".$this->table_pedido.".FechaPedido) >= MONTH( DATE_SUB( CURDATE( ) , INTERVAL 7 MONTH ) )";
		//$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);
		
		
		//WHERE
		$this->db->where($this->table_ubigeo.".Pais", $idpais);
		$this->db->where($this->table_ubigeo.".Departamento", $iddepartamento);
		$this->db->where($this->table_ubigeo.".Distrito", $iddistrito);
		
		
		//GROUP BY
		$this->db->group_by($this->table_ubigeo.".Zona");				
		
						
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	
	public function marca_resumido($month,$idcategoria){				
		
				$this->db->select($this->table_marca.".Descripcion");				
				$this->db->select_sum($this->table_linea_pedido.".MontoLinea");
				$this->db->select_sum($this->table_linea_pedido.".Cantidad");
		
		//RELACIONES
		$this->db->from($this->table_linea_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_pedido,$this->table_linea_pedido.".IdPedido =".$this->table_pedido.".IdPedido");				
		$this->db->join($this->table_producto,$this->table_linea_pedido.".IdProducto =".$this->table_producto.".IdProducto");				
		$this->db->join($this->table_marca,$this->table_producto.".IdMarca =".$this->table_marca.".IdMarca");
		$this->db->join($this->table_categoria,$this->table_producto.".IdCategoria =".$this->table_categoria.".IdCategoria");			
											
				
		$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO
		
		$this->db->where($this->table_categoria.".IdCategoria", $idcategoria);
		
		$this->db->group_by($this->table_marca.".Descripcion");	

			
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	public function pedido_resumido_e1($month,$idcategoria){				
		
				$this->db->select($this->table_marca.".Descripcion");				
				$this->db->select_sum($this->table_linea_pedido.".MontoLinea");
				$this->db->select_sum($this->table_linea_pedido.".Cantidad");
		
		//RELACIONES
		$this->db->from($this->table_linea_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_pedido,$this->table_linea_pedido.".IdPedido =".$this->table_pedido.".IdPedido");				
		$this->db->join($this->table_producto,$this->table_linea_pedido.".IdProducto =".$this->table_producto.".IdProducto");				
		$this->db->join($this->table_marca,$this->table_producto.".IdMarca =".$this->table_marca.".IdMarca");
		$this->db->join($this->table_categoria,$this->table_producto.".IdCategoria =".$this->table_categoria.".IdCategoria");			
											
				
		$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO
		
		$this->db->where($this->table_categoria.".IdCategoria", $idcategoria);
		
		$this->db->group_by($this->table_marca.".Descripcion");	

			
		$query = $this->db->get();	
		echo $this->db->last_query();
		return $query->result();
		
	}
	
	
}
?>
