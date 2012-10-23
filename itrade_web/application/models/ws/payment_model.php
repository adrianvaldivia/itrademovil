<?
class Payment_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_cliente = 'Cliente';
		$this->table_persona = 'Persona';
		$this->table_pedido = 'Pedido';		
		$this->table_estado_pedido = 'EstadoPedido';
		$this->table_pedido_linea = 'Linea_Pedido';
		$this->table_producto = 'Producto';
		$this->table_marca = 'Marca';
		$this->table_categoria = 'Categoria';
		
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
							$this->table_pedido.".IdEstadoPedido, ".							
							$this->table_pedido.".FechaCobranza, ".
							$this->table_pedido.".MontoTotalPedido ");		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");				
		$this->db->join($this->table_persona,$this->table_persona.".IdPersona =".$this->table_cliente.".IdPersona");				
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);			
		$query = $this->db->get();
		//echo $this->db->last_query();
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
	
	public function get_pedidos_by_idvendedor($idcobrador){		
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");											
		$this->db->where($this->table_cliente.".IdCobrador", $idcobrador);	
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_detail_by_idpedido($idpedido){
		$this->db->select($this->table_pedido_linea.".IdPedido, ".
							$this->table_pedido_linea.".IdProducto, ".
							$this->table_pedido_linea.".MontoLinea, ".
							$this->table_pedido_linea.".Cantidad, ".
							$this->table_producto.".Descripcion as NombreProducto , ".
							$this->table_marca.".Descripcion as Marca , ".
							$this->table_categoria.".Descripcion as Categoria  "							
							);
		$this->db->from($this->table_pedido_linea);
		$this->db->join($this->table_producto,$this->table_pedido_linea.".IdProducto =".$this->table_producto.".IdProducto");
		$this->db->join($this->table_marca,$this->table_producto.".IdMarca =".$this->table_marca.".IdMarca");
		$this->db->join($this->table_categoria,$this->table_producto.".IdCategoria =".$this->table_categoria.".IdCategoria");
		$this->db->where($this->table_pedido_linea.".IdPedido", $idpedido);	
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();
	}	
}
?>
