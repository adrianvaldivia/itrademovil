<?
class Cliente_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_cliente = 'Cliente';
		$this->table_persona = 'Persona';
		$this->table_pedido = 'Pedido';
		$this->table_estado_pedido = 'EstadoPedido';
		$this->table_linea = 'Linea_Credito';
    }	
	
	public function get_clients_by_idvendedor($idvendedor){				
		//IDPEDIDO, IDCLIENTE, NOMBRECLIENTE, MONTOTOTAL				
		$this->db->select($this->table_persona.".IdPersona, ".
						$this->table_cliente.".IdCliente, ".
						$this->table_persona.".Nombre, ".
						$this->table_persona.".ApePaterno, ".
						$this->table_cliente.".Razon_Social, ".
						$this->table_cliente.".RUC, ".
						$this->table_cliente.".Latitud, ".
						$this->table_cliente.".Longitud, ".
						$this->table_cliente.".Direccion, ".
						$this->table_cliente.".IdCobrador, ".
						$this->table_persona.".ApeMaterno ");
						
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		//Restriccion del día de hoy				
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);		
		//Restriccion de un solo vendedor
		$this->db->where($this->table_cliente.".IdCobrador", $idvendedor);					
		//Restriccion de pedidos en estado pendiente
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);		
		$this->db->group_by($this->table_cliente.".IdCliente");	
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_clients_by_idvendedor_p($idvendedor){				
		//IDPEDIDO, IDCLIENTE, NOMBRECLIENTE, MONTOTOTAL				
		$this->db->select($this->table_persona.".IdPersona, ".
						$this->table_cliente.".IdCliente, ".
						$this->table_persona.".Nombre, ".
						$this->table_persona.".ApePaterno, ".
						$this->table_cliente.".Razon_Social, ".
						$this->table_cliente.".RUC, ".
						$this->table_cliente.".Latitud, ".
						$this->table_cliente.".Longitud, ".
						$this->table_cliente.".Direccion, ".
						$this->table_cliente.".IdCobrador, ".
						$this->table_persona.".ApeMaterno ");
						
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		//Restriccion del día de hoy				
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);		
		//Restriccion de un solo vendedor
		$this->db->where($this->table_cliente.".IdVendedor", $idvendedor);					
		//Restriccion de pedidos en estado pendiente
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);	
		$this->db->where($this->table_cliente.".IdEstado", 2);			
		$this->db->group_by($this->table_cliente.".IdCliente");	
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_pedidos_by_idvendedor($idvendedor){
		$this->db->select($this->table_pedido.".IdCliente, ".
							$this->table_pedido.".IdPedido, ".
							$this->table_pedido.".MontoTotal ");		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");									
		//Restriccion del día de hoy				
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);		
		//Restriccion de un solo vendedor
		$this->db->where($this->table_cliente.".IdVendedor", $idvendedor);					
		//Restriccion de pedidos en estado pendiente
		$this->db->where($this->table_pedido.".IdEstadoPedido", 0);		
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_cliente_by_id($idcliente){
		$this->db->select($this->table_persona.".IdPersona, ".
						$this->table_cliente.".IdCliente, ".
						$this->table_persona.".Nombre, ".
						$this->table_persona.".ApePaterno, ".
						$this->table_cliente.".Razon_Social, ".
						$this->table_cliente.".RUC, ".
						$this->table_cliente.".Latitud, ".
						$this->table_cliente.".Longitud, ".
						$this->table_cliente.".Direccion, ".
						$this->table_cliente.".IdCobrador, ".
						$this->table_persona.".ApeMaterno ");
						
		$this->db->from($this->table_cliente);				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		//Restriccion del día de hoy							
		//Restriccion de un solo vendedor
		$this->db->where($this->table_cliente.".IdCliente", $idcliente);	
		$this->db->where($this->table_persona.".Activo", 1);
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_prospecto_by_vendedor($idvendedor,$razon_social){				
		//IDPEDIDO, IDCLIENTE, NOMBRECLIENTE, MONTOTOTAL				
		$this->db->select($this->table_persona.".IdPersona, ".
						$this->table_cliente.".IdCliente, ".
						$this->table_persona.".Nombre, ".
						$this->table_persona.".ApePaterno, ".
						$this->table_cliente.".Razon_Social, ".
						$this->table_cliente.".RUC, ".
						$this->table_cliente.".Latitud, ".
						$this->table_cliente.".Longitud, ".
						$this->table_cliente.".Direccion, ".
						$this->table_cliente.".IdCobrador, ".
						$this->table_persona.".ApeMaterno ");
						
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		//Restriccion del día de hoy				
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);		
		//Restriccion de un solo vendedor
		$this->db->where($this->table_cliente.".IdVendedor", $idvendedor);	
		$this->db->where($this->table_cliente.".IdEstado", 1);	
		$this->db->like($this->table_cliente.".Razon_Social", $razon_social);				
		$this->db->group_by($this->table_cliente.".IdCliente");	
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function registrerProspecto($ruc,$razon_social,$direccion,$nombre,$apepaterno,$apematerno,$telefono,$fechanac,$dni,$montosolicitado,$idvendedor){		
		$this->db->flush_cache();
		$idpersona=0;
		$this->registrarPersona($nombre, $apepaterno, $apematerno,$telefono,$fechanac,$dni);
		//echo "persona".$idpersona;
		$idpersona=$this->get_last_idPersona();
		//print_r($idpersona);
		if ($idpersona!=0){
			$this->registrarProspecto($idpersona,$ruc,$razon_social,$direccion,$idvendedor);
			$idcliente=$this->get_last_idCliente();	
			if ($idcliente!=0){
				$this->registrarLineaCredito($idcliente,$montosolicitado);
				$idlinea=$this->get_last_idlinea();
				if ($idlinea!=0){
					return "true";
				}else{
					return "false";
				}
			}else{
				return "false";
			}
		}else{
			return "false";
		}
			
	}
	public function registrarLineaCredito($idcliente,$montosolicitado){
		$this->db->flush_cache();
		$idlinea=$this->get_last_idlinea();
		$data=array("IdLinea"=>$idlinea+1,"MontoSolicitado"=>$montosolicitado,"IdCliente"=>$idcliente,"Activo"=>1);	
		$this->db->insert($this->table_linea, $data);
		//return $this->db->insert_id($this->table_linea, $data);
	}
	
	public function registrarProspecto($idpersona,$ruc,$razon_social,$direccion,$idvendedor){
		$this->db->flush_cache();
		$idcliente=$this->get_last_idCliente();
		$data=array("IdCliente"=>$idcliente+1,"IdPersona"=>$idpersona,"Razon_Social"=>$razon_social,"RUC"=>$ruc,"Direccion"=>$direccion,"IdVendedor"=>$idvendedor,"IdEstado"=>1);	
		$this->db->insert($this->table_cliente, $data);
		//return $this->db->insert_id($this->table_cliente, $data);
	}
	
	
	public function registrarPersona($nombre, $apepaterno, $apematerno,$telefono,$fechanac,$dni){
		$this->db->flush_cache();
		$idpersona=$this->get_last_idPersona();
		$data=array("IdPersona"=>$idpersona+1,"Nombre"=>$nombre,"ApePaterno"=>$apepaterno,"ApeMaterno"=>$apematerno,"Telefono"=>$telefono,"FechNac"=>$fechanac,"DNI"=>$dni);
		$this->db->insert($this->table_persona, $data);		
		//return $this->db->insert_id($this->table_persona, $data);
	}
	
	public function get_last_idPersona(){
		$this->db->flush_cache();
        $this->db->select_max('IdPersona');
        $query = $this->db->get($this->table_persona);
        return $query->row(0)->IdPersona;
    }
	public function get_last_idCliente(){
		$this->db->flush_cache();
        $this->db->select_max('IdCliente');
        $query = $this->db->get($this->table_cliente);
        return $query->row(0)->IdCliente;
    }
	public function get_last_idlinea(){
		$this->db->flush_cache();
        $this->db->select_max('IdLinea');
        $query = $this->db->get($this->table_linea);
        return $query->row(0)->IdLinea;
    }
}
?>
