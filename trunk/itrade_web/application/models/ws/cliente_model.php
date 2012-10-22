<?
class Cliente_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_cliente = 'Cliente';
		$this->table_persona = 'Persona';
		$this->table_pedido = 'Pedido';
		$this->table_estado_pedido = 'EstadoPedido';
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
}
?>
