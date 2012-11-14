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
		$this->table_estado_pedido = 'estadopedido';
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
											
		/*switch($idjerarquia){
			case 1:				
				$this->db->where($this->table_ubigeo.".Pais", $id);
				break;
			case 2:				
				$this->db->where($this->table_ubigeo.".Departamento", $id);
				break;
			case 3:				
				$this->db->where($this->table_ubigeo.".Distrito", $id);
				break;
		}		*/	
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
				$this->db->select($this->table_ubigeo.".Departamento as nombre");						
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
		echo $this->db->last_query();
		return $query->result();
		
	}

	public function circulo_resumido2($month,$idubigeo){				
		
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
		/*$this->db->where($this->table_ubigeo.".Pais", $idpais);
		$this->db->where($this->table_ubigeo.".Departamento", $iddepartamento);
		$this->db->where($this->table_ubigeo.".Distrito", $iddistrito);*/
		$this->db->where($this->table_ubigeo.".IdUbigeo", $idubigeo);
		
		
		//GROUP BY
		$this->db->group_by($this->table_ubigeo.".Zona");				
		
						
		$query = $this->db->get();	
		echo $this->db->last_query();
		return $query->result();
		
	}	
	
	public function pedido_resumido_e1($month){				
		
				$this->db->select($this->table_ubigeo.".Zona");		
				$this->db->select($this->table_estado_pedido.".Descripcion");					
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");	
	
		
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");			
		$this->db->join($this->table_estado_pedido,$this->table_pedido.".IdEstadoPedido =".$this->table_estado_pedido.".IdEstadoPedido");									
		//WHERES
		
		$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO
		
		$str="(".$this->table_estado_pedido.".IdEstadoPedido) = 1";		
		$this->db->where($str);
		//ESTADO PEDIDO
		
		
		//GROUP BY
		
		$this->db->group_by($this->table_ubigeo.".Zona");
		$this->db->group_by($this->table_estado_pedido.".Descripcion");
		
		
		//$this->db->where($this->table_categoria.".IdCategoria", $idcategoria);
		
		//$this->db->group_by($this->table_marca.".Descripcion");	

			
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}
	
	
	
	public function zonas_resumido_sinc2($anho,$idjerarquia,$idubigeo,$id){				
		
		//trabajo del arrego $anho
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		
		switch($idjerarquia){
		case 1:
		
		
		//DATE_FORMAT( Pedido.FechaPedido,  '%M' ) AS Monthname, 
		$query = $this->db->query("
		
		
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year, 
		MONTH( Pedido.FechaPedido) AS Month, 
			`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`, 
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoPedido , SUM(`Pedido`.`MontoTotalCobrado`) AS MontoCobrado
		FROM (
		`Pedido`
		)
		JOIN  `Cliente` ON  `Pedido`.`IdCliente` =  `Cliente`.`IdCliente` 
		JOIN  `Usuario` ON  `Cliente`.`IdVendedor` =  `Usuario`.`IdUsuario` 
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Pais`= '".$id."'  
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;
		
		case 2:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year, 
		MONTH( Pedido.FechaPedido) AS Month, 
			`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`, 
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoPedido , SUM(`Pedido`.`MontoTotalCobrado`) AS MontoCobrado 
		FROM (
		`Pedido`
		)
		JOIN  `Cliente` ON  `Pedido`.`IdCliente` =  `Cliente`.`IdCliente` 
		JOIN  `Usuario` ON  `Cliente`.`IdVendedor` =  `Usuario`.`IdUsuario` 
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Departamento`= '".$id."'  
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;
		
		
		case 3:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year, 
		MONTH( Pedido.FechaPedido) AS Month, 
			`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`, 
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoPedido, SUM(`Pedido`.`MontoTotalCobrado`)  AS MontoCobrado
		FROM (
		`Pedido`
		)
		JOIN  `Cliente` ON  `Pedido`.`IdCliente` =  `Cliente`.`IdCliente` 
		JOIN  `Usuario` ON  `Cliente`.`IdVendedor` =  `Usuario`.`IdUsuario` 
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Distrito`= '".$id."'    
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;	
		}		
		//echo $this->db->last_query();
		return $query->result();
		
	}
	
	public function circulo_resumido_sinc($anho,$idjerarquia,$idubigeo,$id){				
		
		//SELECTS
		switch($idjerarquia){
		
		//YA NO SE DEVOLVERAN SUMAS ACUMULADAS SINO CADA UNO DE LOS PEDIDOS POR SEPARADO
			case 1://pais
			//select date_format(now(),'%M')as Monthname;
				//$this->db->select("date_format(now(),'%M') as Monthname");
				//$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_pedido.".FechaPedido");				
				$this->db->select($this->table_ubigeo.".Departamento");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");						
				$this->db->select_sum($this->table_meta.".Monto");
				break;
			case 2://departamento	
				//$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_ubigeo.".Distrito");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");						
				$this->db->select_sum($this->table_meta.".Monto");
				break;
			case 3://distrito
				//$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_pedido.".FechaPedido");				
				$this->db->select($this->table_ubigeo.".Zona");				
				$this->db->select_sum($this->table_pedido.".MontoTotalPedido");						
				$this->db->select_sum($this->table_meta.".Monto");				
				break;
		}		
		
						
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");	
	
		$this->db->join($this->table_meta,$this->table_usuario.".IdUsuario =".$this->table_meta.".IdUsuario");
												
		//WHERES
		
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

			
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
			
		$this->db->where($str);//UBIGEO
		
		
		
					
		
						
		$query = $this->db->get();	
		echo $this->db->last_query();
		return $query->result();
		
	}
	
	public function circulo_resumido_sinc2($anho,$idjerarquia,$idubigeo,$id){				
		
	
		//trabajo del arrego $anho
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		switch($idjerarquia){
		Case 1:
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month, 
		`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`,
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoTotalPedido, SUM(`Meta`.`Monto`) AS MontoMeta 
		FROM (`Pedido`)
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente`
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo`
		JOIN `Meta` ON `Usuario`.`IdUsuario` =`Meta`.`IdUsuario` 
		WHERE   ".$str." and `Ubigeo`.`Pais`= '".$id."' 
		GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;
		
		case 2:
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month, 
		`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`,
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoTotalPedido, SUM(`Meta`.`Monto`) AS MontoMeta 
		FROM (`Pedido`)
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente`
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo`
		JOIN `Meta` ON `Usuario`.`IdUsuario` =`Meta`.`IdUsuario` 
		WHERE   ".$str."  and `Ubigeo`.`Departamento`= '".$id."' 
		GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;
		
		
		case 3:
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month,  
		`Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`, `Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito` , `Ubigeo`.`Zona`,
		SUM(`Pedido`.`MontoTotalPedido`) AS MontoTotalPedido, SUM(`Meta`.`Monto`) AS MontoMeta 
		FROM (`Pedido`)
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente`
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo`
		JOIN `Meta` ON `Usuario`.`IdUsuario` =`Meta`.`IdUsuario` 
		WHERE   ".$str."  and `Ubigeo`.`Distrito`= '".$id."' 
		GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona 
		");
		break;
		
		}
		
		//echo $this->db->last_query();
		return $query->result();
		
	}
	
	public function marcas_resumido_sinc($anho,$idjerarquia,$idubigeo,$id){				
		
		//trabajo del arrego $anho
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		
		switch($idjerarquia){
		case 1:
		
		 
		$query = $this->db->query("		
		
		SELECT 	
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month,  
		`Usuario`.`Nombre`, 
		`Ubigeo`.`IdUbigeo`, 
		`Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, 
		`Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , 
		`Ubigeo`.`Zona`,		
		`Marca`.`IdMarca`,
		`Marca`.`Descripcion` AS MarcaDescripcion, 
		`Categoria`.`IdCategoria` ,
		`Categoria`.`Descripcion` AS CategoriaDescripcion, 
		SUM(`Linea_Pedido`.`MontoLinea`) AS MontoLinea, 
		SUM(`Linea_Pedido`.`Cantidad`) AS Cantidad
		FROM (`Linea_Pedido`)
		JOIN `Pedido` ON `Linea_Pedido`.`IdPedido` =`Pedido`.`IdPedido`
		JOIN `Producto` ON `Linea_Pedido`.`IdProducto` =`Producto`.`IdProducto`
		JOIN `Marca` ON `Producto`.`IdMarca` =`Marca`.`IdMarca`
		JOIN `Categoria` ON `Producto`.`IdCategoria` =`Categoria`.`IdCategoria`
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente` 
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Pais`= '".$id."'  
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona` , 
		`Marca`.`IdMarca`,
		MarcaDescripcion,
		`Categoria`.`IdCategoria`, 
		CategoriaDescripcion
		");
		break;
		
		case 2:
		
		$query = $this->db->query("		
		
		SELECT 	
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month,  
		`Usuario`.`Nombre`, 
		`Ubigeo`.`IdUbigeo`, 
		`Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, 
		`Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , 
		`Ubigeo`.`Zona`,		
		`Marca`.`IdMarca`,
		`Marca`.`Descripcion` AS MarcaDescripcion, 
		`Categoria`.`IdCategoria` ,
		`Categoria`.`Descripcion` AS CategoriaDescripcion, 
		SUM(`Linea_Pedido`.`MontoLinea`) AS MontoLinea, 
		SUM(`Linea_Pedido`.`Cantidad`) AS Cantidad
		FROM (`Linea_Pedido`)
		JOIN `Pedido` ON `Linea_Pedido`.`IdPedido` =`Pedido`.`IdPedido`
		JOIN `Producto` ON `Linea_Pedido`.`IdProducto` =`Producto`.`IdProducto`
		JOIN `Marca` ON `Producto`.`IdMarca` =`Marca`.`IdMarca`
		JOIN `Categoria` ON `Producto`.`IdCategoria` =`Categoria`.`IdCategoria`
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente` 
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Departamento`= '".$id."'  
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona` , 
		`Marca`.`IdMarca`,
		MarcaDescripcion,
		`Categoria`.`IdCategoria`, 
		CategoriaDescripcion
		");
		break;
		
		
		case 3:
		
				$query = $this->db->query("		
		
		SELECT 	
		DATE_FORMAT( Pedido.FechaPedido,  '%Y' ) AS Year,  
		MONTH( Pedido.FechaPedido) AS Month,  
		`Usuario`.`Nombre`, 
		`Ubigeo`.`IdUbigeo`, 
		`Usuario`.`IdJerarquia`, 
		`Ubigeo`.`Pais`, 
		`Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , 
		`Ubigeo`.`Zona`,		
		`Marca`.`IdMarca`,
		`Marca`.`Descripcion` AS MarcaDescripcion, 
		`Categoria`.`IdCategoria` ,
		`Categoria`.`Descripcion` AS CategoriaDescripcion, 
		SUM(`Linea_Pedido`.`MontoLinea`) AS MontoLinea, 
		SUM(`Linea_Pedido`.`Cantidad`) AS Cantidad
		FROM (`Linea_Pedido`)
		JOIN `Pedido` ON `Linea_Pedido`.`IdPedido` =`Pedido`.`IdPedido`
		JOIN `Producto` ON `Linea_Pedido`.`IdProducto` =`Producto`.`IdProducto`
		JOIN `Marca` ON `Producto`.`IdMarca` =`Marca`.`IdMarca`
		JOIN `Categoria` ON `Producto`.`IdCategoria` =`Categoria`.`IdCategoria`
		JOIN `Cliente` ON `Pedido`.`IdCliente` =`Cliente`.`IdCliente` 
		JOIN `Usuario` ON `Cliente`.`IdVendedor` =`Usuario`.`IdUsuario`
		JOIN `Ubigeo` ON `Usuario`.`IdUbigeo` =`Ubigeo`.`IdUbigeo` 
		WHERE   ".$str." and `Ubigeo`.`Distrito`= '".$id."'  
		 GROUP BY 
		Year, Month, `Usuario`.`Nombre`, `Ubigeo`.`IdUbigeo`,`Usuario`.`IdJerarquia`,
		`Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , `Ubigeo`.`Distrito`, `Ubigeo`.`Zona` , 
		`Marca`.`IdMarca`,
		MarcaDescripcion,
		`Categoria`.`IdCategoria`, 
		CategoriaDescripcion
		");
		break;	
		}		
		//echo $this->db->last_query();
		return $query->result();
		
	}
	
	
	
	
	
	public function reporte_pedido_sinc($anho,$idjerarquia,$idubigeo,$id){
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		switch($idjerarquia){
		case 1:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month,
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		
		SUM(CASE WHEN IdEstadoPedido=1 THEN 1 ELSE 0 END) AS CANTPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=1 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=2 THEN 1 ELSE 0 END) AS CANTPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=2 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Pais`= '".$id."' 
		GROUP BY 
		Year, 
		Month,
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		case 2:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month, 
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		SUM(CASE WHEN IdEstadoPedido=1 THEN 1 ELSE 0 END) AS CANTPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=1 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=2 THEN 1 ELSE 0 END) AS CANTPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=2 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Departamento`= '".$id."' 
		GROUP BY 
		Year, 
		Month, 
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,
		`D`.`Pais`, 
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		case 3:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month, 
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		SUM(CASE WHEN IdEstadoPedido=1 THEN 1 ELSE 0 END) AS CANTPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=1 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE1,
		SUM(CASE WHEN IdEstadoPedido=2 THEN 1 ELSE 0 END) AS CANTPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=2 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE2,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Distrito`= '".$id."' 
		GROUP BY 
		Year, 
		Month, 
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		
		}
		//echo $this->db->last_query();
		return $query->result();
	}
	
	
	public function reporte_rechazo_sinc($anho,$idjerarquia,$idubigeo,$id){
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		switch($idjerarquia){
		case 1:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month,
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		
		COUNT(`Pedido`.`IdPedido`) AS CANTPEDIDO,
		SUM(`Pedido`.`MontoTotalPedido`) AS VENTAPEDIDO,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Pais`= '".$id."' 
		GROUP BY 
		Year, 
		Month,
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		case 2:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month, 
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		
		COUNT(`Pedido`.`IdPedido`) AS CANTPEDIDO,
		SUM(`Pedido`.`MontoTotalPedido`) AS VENTAPEDIDO,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Departamento`= '".$id."' 
		GROUP BY 
		Year, 
		Month, 
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,
		`D`.`Pais`, 
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		case 3:
		
		$query = $this->db->query("
		SELECT 
		DATE_FORMAT( `Pedido`.`FechaPedido`,  '%Y' ) AS Year,
		MONTH(`Pedido`.`FechaPedido`) AS Month, 
		`C`.`Nombre`,
		`C`.`IdUbigeo`,
		`C`.`IdJerarquia`,
		
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`,
		
		COUNT(`Pedido`.`IdPedido`) AS CANTPEDIDO,
		SUM(`Pedido`.`MontoTotalPedido`) AS VENTAPEDIDO,
		SUM(CASE WHEN IdEstadoPedido=3 THEN 1 ELSE 0 END) AS CANTPEDIDOE3,
		SUM(CASE WHEN IdEstadoPedido=3 THEN MontoTotalPedido ELSE 0 END) AS VENTAPEDIDOE3
		
		FROM Pedido, Cliente B, Usuario C, Ubigeo D 
		WHERE `Pedido`.`IdCliente`=`B`.`IdCliente` AND `B`.`IdVendedor`=`C`.`IdUsuario` AND `C`.`IdUbigeo`=`D`.`IdUbigeo`
		AND  ".$str." and `D`.`Distrito`= '".$id."' 
		GROUP BY 
		Year, 
		Month, 
		`C`.`Nombre`,
		`C`.`IdJerarquia`,
		`C`.`IdUbigeo`,
		`D`.`Pais`,
		`D`.`Departamento`,
		`D`.`Distrito`,
		`D`.`Zona`
		");
		break;
		
		
		}
		//echo $this->db->last_query();
		return $query->result();
	}
	
	public function usuarios_resumido_sinc($anho,$idjerarquia,$idubigeo,$id){				
		
		//trabajo del arrego $anho
		
		$myarr = explode("-",$anho);
		
		$str="(";
		for($i = 0 ; $i< count($myarr); $i++)
		
		{ if ((count($myarr)-$i)==1)
		{
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];
			$str.=")";
		}
		else
		{ 
			$str.=" ( year(".$this->table_pedido.".FechaPedido) = ".$myarr[$i];	
			$str.=") or ";
			
		}
		}
		$str.=")";
		
		
		switch($idjerarquia){
		case 1:
		
		
		//DATE_FORMAT( Pedido.FechaPedido,  '%M' ) AS Monthname, 
		$query = $this->db->query("
		
		
		SELECT `Usuario`.`Nombre`, `Persona`.`Nombre`, `Persona`.`ApePaterno`, `Persona`.`ApeMaterno`, `Persona`.`DNI` 
		, `Usuario`.`IdJerarquia` , `Usuario`.`IdUbigeo`, `Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , `Ubigeo`.`Zona`   
		FROM ( 
		`Usuario`
		)
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		JOIN  `Persona` ON  `Persona`.`IdPersona` =  `Usuario`.`IdPersona` 
		WHERE  `Ubigeo`.`Pais`= '".$id."' and `Usuario`.`IdJerarquia`= 5 and `Usuario`.`Activo`= 1  and `Usuario`.`IdPerfil`= 2
		
		");
		break;
		
		case 2:
		
		$query = $this->db->query("
		
		
		SELECT `Usuario`.`Nombre`, `Persona`.`Nombre`, `Persona`.`ApePaterno`, `Persona`.`ApeMaterno`, `Persona`.`DNI` 
		, `Usuario`.`IdJerarquia` , `Usuario`.`IdUbigeo`,  `Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , `Ubigeo`.`Zona`   
		FROM ( 
		`Usuario`
		)
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		JOIN  `Persona` ON  `Persona`.`IdPersona` =  `Usuario`.`IdPersona` 
		WHERE  `Ubigeo`.`Departamento`= '".$id."' and `Usuario`.`IdJerarquia`= 5 and `Usuario`.`Activo`= 1 and `Usuario`.`IdPerfil`= 2
		
		");
		break;
		
		
		case 3:
		
		$query = $this->db->query("
		
		
		SELECT `Usuario`.`Nombre`, `Persona`.`Nombre`, `Persona`.`ApePaterno`, `Persona`.`ApeMaterno`, `Persona`.`DNI` 
		, `Usuario`.`IdJerarquia` , `Usuario`.`IdUbigeo`,  `Ubigeo`.`Pais`, `Ubigeo`.`Departamento` , 
		`Ubigeo`.`Distrito` , `Ubigeo`.`Zona`   
		FROM ( 
		`Usuario`
		)
		JOIN  `Ubigeo` ON  `Usuario`.`IdUbigeo` =  `Ubigeo`.`IdUbigeo` 
		JOIN  `Persona` ON  `Persona`.`IdPersona` =  `Usuario`.`IdPersona` 
		WHERE   ".$str." and `Ubigeo`.`Distrito`= '".$id."' and `Usuario`.`IdJerarquia`= 5 and `Usuario`.`Activo`= 1 and `Usuario`.`IdPerfil`= 2 
		
		");
		break;	
		}		
		//echo $this->db->last_query();
		return $query->result();
		
	}
	
	
}
?>
