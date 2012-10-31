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
	
	public function pay_by_id($idpedido,$montocobrado,$numVoucher){
		//obtain pedido
		//si es que ya esta pagado, no se puede cambiar de estado y se devuelve un array vacio
		
		if ($this->pendiente($idpedido)){									
			$this->db->set('IdEstadoPedido', 2);//Pagado
			$this->db->set('MontoTotalCobrado', $montocobrado);
			$this->db->set('FechaCobranza', 'CURDATE()', FALSE);
			if (trim($numVoucher)!=""){
				$this->db->set('NumVoucher', $numVoucher);
			}
			$this->db->where('IdPedido', $idpedido);
			$this->db->update($this->table_pedido);
			return $this->get_by_id($idpedido);
		}
		return array();
		
	}
	
	public function get_by_id($idpedido){
		//IDPEDIDO, IDCLIENTE, NOMBRECLIENTE, MONTOTOTAL
		/*$this->db->select($this->table_pedido.".IdPedido, ".
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
		*/
		$this->db->from($this->table_pedido);		
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);			
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();	
	}
	
	public function pendiente($idpedido){
		$this->db->select($this->table_pedido.".IdPedido");		
		$this->db->from($this->table_pedido);		
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);// 1 indica que no esta pagado
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
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);//Pendiente de pago
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
							$this->table_producto.".Precio, ".
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
	
	public function registrar_pedido($idcliente,$montototalpedidosinigv){		
		//$idpedido=$this->get_last_idpedido();					
		$montototalpedidosinigv=number_format(round($montototalpedidosinigv,2),2,'.','');
		$montototalconigv=number_format(round($montototalpedidosinigv*1.18,2),2,'.','');
		$igv=number_format(round($montototalconigv-$montototalpedidosinigv,2),2,'.','');
		//$data=array("IdPedido"=>$idpedido+1,"IdCliente"=>$idcliente,"IdEstadoPedido"=>1,
		$data=array("IdCliente"=>$idcliente,"IdEstadoPedido"=>1,
			"FechaPedido"=>date('Y-m-d'),"MontoSinIGV"=>$montototalpedidosinigv,"IGV"=>$igv,
			"MontoTotalPedido"=>$montototalconigv,"MontoTotalCobrado"=>0);
		$this->db->insert($this->table_pedido, $data);
		return $this->get_last_idpedido();
	}	

	public function get_last_idpedido(){
		$this->db->flush_cache();
        $this->db->select_max('IdPedido');
        $query = $this->db->get($this->table_pedido);
        return $query->row(0)->IdPedido;
    }
	public function cancelar_pedido($idpedido){
		if ($this->pendiente($idpedido)){									
			$this->db->set('IdEstadoPedido', 3);//Pagado						
			$this->db->where('IdPedido', $idpedido);
			$this->db->update($this->table_pedido);
			return $this->get_by_id($idpedido);
		}
		return array();
	}
	public function get_last_idpedido_linea(){
		$this->db->flush_cache();
        $this->db->select_max('IdPedido');
        $query = $this->db->get($this->table_pedido_linea);
        return $query->row(0)->IdPedido;
    }
	public function registrar_pedido_linea($idpedido,$idproducto,$montolinea,$cantidad){		
		//$idpedido=$this->get_last_idpedido();					
		$montolinea=number_format(round($montolinea,2),2,'.','');
		
		$data=array("IdPedido"=>$idpedido,"IdProducto"=>$idproducto,
			"MontoLinea"=>$montolinea,"Cantidad"=>$cantidad);
		$this->db->insert($this->table_pedido_linea, $data);
		return $this->get_last_idpedido_linea();
	}	
	public function ultimos_pedidos($idvendedor){
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");											
		$this->db->where($this->table_cliente.".IdVendedor", $idvendedor);						
		$this->db->order_by($this->table_pedido.".FechaPedido", "desc");
		$this->db->limit(30);
		$query = $this->db->get();		
		//echo $this->db->last_query();		
		return $query->result();	
	}
	public function get_meta($idvendedor){
		$query = $this->db->query("
			SELECT P.Descripcion, M.Monto
			FROM PeriodoMeta P, Meta M
			WHERE P.FechaFin >= NOW( ) 
			AND P.FechaIni <= NOW( ) 
			AND P.IdPeriodo = M.IdPeriodo
			AND M.IdUsuario =  '".$idvendedor."'
		");
		$query=$this->db->get();
		//$query->result();
		$arr=array("periodo"=>$query->row(0)->Descripcion,"monto"=>$query->row(0)->Monto);
		return $arr;	
	}
	public function get_monto($idvendedor,$fechini,$fechfin){
		$query = $this->db->query("
			SELECT SUM( MontoTotalPedido ) as montototal 
			FROM Pedido P, Cliente C
			WHERE  NOW() >= P.FechaPedido
			AND  '".$fechini."' <= P.FechaPedido
			AND P.IdCliente = C.IdCliente
			AND C.IdCobrador ='".$idvendedor."' 
		");
		//$query->result();
		$query=$this->db->get();
		return $query->row(0)->montototal;	
	}
	
	/*
	SELECT * 
FROM PeriodoMeta P, Meta M
WHERE P.FechaFin >= NOW( ) 
AND P.FechaIni <= NOW( ) 
AND P.IdPeriodo=M.IdPeriodo
AND M.IdUsuario=

SELECT SUM(MontoTotalPedido)
FROM Pedido P
WHERE '2012-12-03' >= P.FechaPedido
AND '2012-03-03' <= P.FechaPedido



SELECT SUM( MontoTotalPedido ) as montototal 
FROM Pedido P, Cliente C
WHERE  '2012-12-03' >= P.FechaPedido
AND  '2012-03-03' <= P.FechaPedido
AND P.IdCliente = C.IdCliente
AND C.IdCobrador =6

	*/
}
?>
