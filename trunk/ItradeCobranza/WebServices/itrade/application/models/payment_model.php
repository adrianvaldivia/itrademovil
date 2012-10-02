<?
class Payment_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_cliente = 'Cliente';
		$this->table_persona = 'Persona';
		$this->table_pedido = 'Pedido';
		$this->table_estado_pedido = 'EstadoPedido';
    }	
	
	public function pay_by_id($idpedido){
		//obtain pedido
		//si es que ya esta pagado, no se puede cambiar de estado y se devuelve un array vacio
		
		if ($this->pendiente($idpedido)){									
			$this->db->set('IdEstadoPedido', 1);
			$this->db->set('FechaCobranza', 'CURDATE()', FALSE);							
			$this->db->where('IdPedido', $idpedido);
			$this->db->update($this->table_pedido);
			return $this->get_by_id($idpedido);
		}
		return array();
		
	}
	
	public function get_by_id($idpedido){
		//IDPEDIDO, IDCLIENTE, NOMBRECLIENTE, MONTOTOTAL
		$this->db->select($this->table_pedido.".IdPedido, ".
							$this->table_pedido.".IdCliente, ".
							$this->table_persona.".Nombre, ".
							$this->table_persona.".ApePaterno, ".
							$this->table_persona.".ApeMaterno, ".
							$this->table_pedido.".FechaPedido, ".
							$this->table_pedido.".MontoTotal ");		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);			
		$query = $this->db->get();
		return $query->result();	
	}
	
	public function pendiente($idpedido){
		$this->db->select($this->table_pedido.".IdPedido");		
		$this->db->from($this->table_pedido);		
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);
		// 0 -> PENDIENTE, 1 -> PAGADO, 2 ->CANCELADO
		$this->db->where($this->table_pedido.".IdEstadoPedido", 0);// 0 indica que no esta pagado
		$query = $this->db->get();			
        if ($query->num_rows()>0){
			//QUIERE DECIR QUE se puede pagar
			return true;			
		}else{
			return false;
		}					
	}
}
?>
