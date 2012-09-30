<?
class Payment_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_pedido = 'Pedido';
		$this->table_estado_pedido = 'EstadoPedido';
    }	
	
	public function pay_by_id($idpedido){
		//obtain pedido
		//si es que ya esta pagado, no se puede cambiar de estado y se devuelve un array vacio
		if ($this->pendiente($idpedido)){			
			//Es necesario updatear el estado del pedido
			$data = array(
               'IdEstadoPedido' => 1
			   'FechaCobranza'=>'CURDATE()'
            );
			$this->db->where('IdPedido', $idpedido);
			$this->db->update($this->table_pedido, $data);
			return $this->get_by_id($idpedido);
		}
		return array();
	}
	
	public function get_by_id($idpedido){
		$this->db->select($this->table_pedido.".IdPedido, ".$this->table_pedido.".FechaPedido, ".$this->table_pedido."");		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_estado_pedido,$this->table_pedido.".IdEstadoPedido =".$this->table_estado_pedido.".IdEstadoPedido");				
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);
		// 0 -> PENDIENTE, 1 -> PAGADO, 2 ->CANCELADO
		$this->db->where($this->table_estado_pedido.".IdEstadoPedido", 0);// 0 indica que no esta pagado
		$query = $this->db->get();
		return $query->result();	
	}
	
	public function pendiente($idpedido){
		$this->db->select($this->table_pedido.".IdPedido");		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_estado_pedido,$this->table_pedido.".IdEstadoPedido =".$this->table_estado_pedido.".IdEstadoPedido");				
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);
		// 0 -> PENDIENTE, 1 -> PAGADO, 2 ->CANCELADO
		$this->db->where($this->table_estado_pedido.".IdEstadoPedido", 0);// 0 indica que no esta pagado
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
