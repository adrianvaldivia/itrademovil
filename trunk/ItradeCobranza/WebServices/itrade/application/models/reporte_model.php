<?
class Reporte_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_pedido = 'Pedido';
		$this->table_cliente = 'Cliente';
		$this->table_usuario = 'Usuario';
		$this->table_ubigeo = 'Ubigeo';
		$this->table_zona = 'Zona';
		$this->table_distrito = 'Distrito';
		$this->table_departamento = 'Departamento';
		$this->table_pais = 'Pais';
		$this->table_jerarquia = 'Jerarquia';
		$this->table_meta = 'Meta';
		
    }	

	public function zonas_resumido($month,$idjerarquia,$idubigeo,$id){				
		switch($idjerarquia){
			case 1://pais
			//select date_format(now(),'%M')as Monthname;
				//$this->db->select("date_format(now(),'%M') as Monthname");
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_departamento.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");
				break;
			case 2://departamento	
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_distrito.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");
				break;
			case 3://distrito
				$this->db->select($this->table_pedido.".FechaPedido");
				$this->db->select($this->table_zona.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");						
				break;
		}		
			//$str="month(".$this->table_pedido.".FechaPedido) >= MONTH( DATE_SUB( CURDATE( ) , INTERVAL 7 MONTH ) )";
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");				
		switch($idjerarquia){			
			case 1://pais
				$this->db->join($this->table_departamento,$this->table_ubigeo.".IdDepartamento =".$this->table_departamento.".IdDepartamento");
				break;
			case 2://pais
				$this->db->join($this->table_distrito,$this->table_ubigeo.".IdDistrito =".$this->table_distrito.".IdDistrito");
				break;
			case 3://pais
				$this->db->join($this->table_zona,$this->table_ubigeo.".IdZona =".$this->table_zona.".IdZona");
				break;
		}												
		switch($idjerarquia){
			case 1:				
				$this->db->where($this->table_ubigeo.".IdPais", $id);
				break;
			case 2:				
				$this->db->where($this->table_ubigeo.".IdDepartamento", $id);
				break;
			case 3:				
				$this->db->where($this->table_ubigeo.".IdDistrito", $id);
				break;
		}			
		$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO
		//$dates="(DATEDIFF(".$this->table_atencion_omega.".fecAtencion,".$this->table_atencion.".fecha)=0)";
		//$this->db->where($dates);
		//$this->//FECHA			
		switch($idjerarquia){
			case 1://pais
				$this->db->group_by($this->table_departamento.".Descripcion");				
				break;
			case 2://departamento
							
				$this->db->group_by($this->table_distrito.".Descripcion");	
				break;
			case 3://distrito							
				$this->db->group_by($this->table_zona.".Descripcion");	
				break;
		}				
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	//FUNCION OBJETO UBIGEO
	function objetoUbigeo($idubigeo,$idjerarquia){
		switch($idjerarquia){
			case 1://pais
				$this->db->select($this->table_ubigeo.".idPais as id");								
				break;
			case 2://departamento							
				$this->db->select($this->table_ubigeo.".idDeparmento as id");						
				break;
			case 3://distrito		
				$this->db->select($this->table_ubigeo.".idDistrito as id");								
				break;
		}
		$this->db->where('idUbigeo', $idubigeo);
		$query = $this->db->get($this->table_ubigeo);			
        return $query->result();		
	}
	
	//FUNCION ZONAS DETALLADO
	
		public function zonas_detallado($month,$idjerarquia,$idubigeo,$id){				
		switch($idjerarquia){
			case 1://pais
				$this->db->select($this->table_departamento.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");
				break;
			case 2://departamento							
				$this->db->select($this->table_distrito.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");
				break;
			case 3://distrito		
				$this->db->select($this->table_zona.".Descripcion");				
				$this->db->select_sum($this->table_pedido.".MontoTotal");						
				break;
		}				
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");				
		switch($idjerarquia){			
			case 1://pais
				$this->db->join($this->table_departamento,$this->table_ubigeo.".IdDepartamento =".$this->table_departamento.".IdDepartamento");
				break;
			case 2://pais
				$this->db->join($this->table_distrito,$this->table_ubigeo.".IdDistrito =".$this->table_distrito.".IdDistrito");
				break;
			case 3://pais
				$this->db->join($this->table_zona,$this->table_ubigeo.".IdZona =".$this->table_zona.".IdZona");
				break;
		}												
		switch($idjerarquia){
			case 1:				
				$this->db->where($this->table_ubigeo.".IdPais", $id);
				break;
			case 2:				
				$this->db->where($this->table_ubigeo.".IdDepartamento", $id);
				break;
			case 3:				
				$this->db->where($this->table_ubigeo.".IdDistrito", $id);
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
				$this->db->group_by($this->table_departamento.".Descripcion");				
				break;
			case 2://departamento
							
				$this->db->group_by($this->table_distrito.".Descripcion");	
				break;
			case 3://distrito							
				$this->db->group_by($this->table_zona.".Descripcion");	
				break;
		}				
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	//Reporte de Circulo de Excelencia
	
	public function circulo_resumido($month,$iddistrito,$iddepartamento,$idpais){				
		
		//SELECTS
		
		$this->db->select($this->table_zona.".Descripcion");				
		$this->db->select_sum($this->table_pedido.".MontoTotal");						
		$this->db->select_sum($this->table_meta.".Monto");
						
		//RELACIONES
		$this->db->from($this->table_pedido);
						//TABLA JOIN, RELACION//
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_usuario,$this->table_cliente.".IdVendedor =".$this->table_usuario.".IdUsuario");				
		$this->db->join($this->table_ubigeo,$this->table_usuario.".IdUbigeo =".$this->table_ubigeo.".IdUbigeo");	
		$this->db->join($this->table_departamento,$this->table_ubigeo.".IdDepartamento =".$this->table_departamento.".IdDepartamento");
		$this->db->join($this->table_distrito,$this->table_ubigeo.".IdDistrito =".$this->table_distrito.".IdDistrito");
		$this->db->join($this->table_zona,$this->table_ubigeo.".IdZona =".$this->table_zona.".IdZona");
		$this->db->join($this->table_meta,$this->table_usuario.".IdUsuario =".$this->table_meta.".IdUsuario");
												
				
		//$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		//$this->db->where($str);
		
		//prueba
		$str="month(".$this->table_pedido.".FechaPedido) >= MONTH( DATE_SUB( CURDATE( ) , INTERVAL 7 MONTH ) )";
		//$str="month(".$this->table_pedido.".FechaPedido) = ".$month;		
		$this->db->where($str);
		
		
		
		//GROUP BY
		$this->db->group_by($this->table_zona.".Descripcion");				
		$this->db->where($this->table_ubigeo.".IdPais", $idpais);
		$this->db->where($this->table_ubigeo.".IdDepartamento", $iddepartamento);
		$this->db->where($this->table_ubigeo.".IdDistrito", $iddistrito);
						
		$query = $this->db->get();	
		//echo $this->db->last_query();
		return $query->result();
		
	}	
	
	
}
?>
