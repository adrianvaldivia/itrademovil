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
		$this->table_periodos = 'PeriodoMeta';
		$this->table_ubigeo = 'Ubigeo';
		$this->table_deposito = 'Deposito';
		
    }	
	
	public function pay_by_id($idpedido,$montocobrado,$numVoucher){
		//obtain pedido
		//si es que ya esta pagado, no se puede cambiar de estado y se devuelve un array vacio
		
		if ($this->pendiente($idpedido)){
			//echo "PENDIENTE";
			if ($this->entregado($idpedido)){
				//echo "ENTREGADO";
				$this->db->flush_cache();
				$this->db->set('IdEstadoPedido', 2);//Entregado
			}else{
				//echo "PAGADO";
				$this->db->flush_cache();
				$this->db->set('IdEstadoPedido', 4);//Cobrado
			}			
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
	public function entregado($idpedido){
		$this->db->flush_cache();
		$this->db->select($this->table_pedido.".IdPedido");		
		$this->db->from($this->table_pedido);		
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);// 1 indica que no esta pagado
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);
		$query = $this->db->get();			
        if ($query->num_rows()>0){
			//QUIERE DECIR QUE se puede entregar
			return true;			
		}else{
			return false;
		}	
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
		$this->db->flush_cache();
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
		$this->db->or_where($this->table_pedido.".IdEstadoPedido", 4);
//		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)<=7)";
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
			SELECT P.Descripcion, M.Monto, P.FechaIni ,P.FechaFin  
			FROM PeriodoMeta P, Meta M
			WHERE P.FechaFin >= NOW( ) 
			AND P.FechaIni <= NOW( ) 
			AND P.IdPeriodo = M.IdPeriodo
			AND M.IdUsuario =  '".$idvendedor."'
		");	
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_monto($idvendedor,$fechini,$fechfin){
		$query = $this->db->query("
			SELECT SUM( P.MontoTotalPedido ) as montototal 
			FROM Pedido P, Cliente C
			WHERE  NOW() >= P.FechaPedido
			AND  '".$fechini."' <= P.FechaPedido
			AND P.IdCliente = C.IdCliente
			AND C.IdVendedor ='".$idvendedor."' 
		");
		//echo $this->db->last_query();
		return $query->result();	
	}
	public function get_periodos(){
		$this->db->from($this->table_periodos);				
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();
	}
	public function get_periodo_jeraquia_ubigeo($idjerarquia,$idubigeo){
		$objUbigeo=$this->get_objetoubigeo($idubigeo);
		$this->db->flush_cache(); 
		if ($idjerarquia==1)
			$this->db->where($this->table_ubigeo.".Pais", $objUbigeo->Pais);				
		if ($idjerarquia==2)
			$this->db->where($this->table_ubigeo.".Departamento", $objUbigeo->Departamento);	
		if ($idjerarquia==3)
			$this->db->where($this->table_ubigeo.".Distrito", $objUbigeo->Distrito);	
		if ($idjerarquia==4)
			$this->db->where($this->table_ubigeo.".Zona", $objUbigeo->Zona);
		if ($idjerarquia>4)
			return array();
		$query = $this->db->get($this->table_ubigeo);
		return $query->result();
	}
	public function get_objetoubigeo($idubigeo){
		$this->db->flush_cache();        
		$this->db->where($this->table_ubigeo.".IdUbigeo", $idubigeo);	
        $query = $this->db->get($this->table_ubigeo);
        return $query->row(0);
    }
	public function get_monto_zona($idzona,$idperiodo){
		$objPeriodo=$this->get_periodo($idperiodo);
		$this->db->flush_cache();    
		$query = $this->db->query("					
			SELECT Usuario.Nombre, Pedido.MontoTotalPedido, Pedido.FechaCobranza
			FROM PeriodoMeta, Pedido, Cliente, Usuario
			WHERE Pedido.IdCliente = Cliente.IdCliente
			AND Cliente.IdVendedor = Usuario.IdUsuario
			AND Usuario.IdUbigeo ='".$idzona."' 
			AND Pedido.IdEstadoPedido = '2'
			AND PeriodoMeta.IdPeriodo ='".$idperiodo." '
			AND '".$objPeriodo->FechaIni."' <= Pedido.FechaCobranza
			AND '".$objPeriodo->FechaFin."' >= Pedido.FechaCobranza			
		");	
		return $query->result();
	}
	
	public function get_periodo($idperiodo){
		$this->db->flush_cache();        
		$this->db->where($this->table_periodos.".IdPeriodo", $idperiodo);	
        $query = $this->db->get($this->table_periodos);
        return $query->row(0);
    }
	public function get_pedidos_detail($ides){
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
		$this->db->where_in($this->table_pedido_linea.'.IdPedido', $ides);
		$query = $this->db->get();        
		return $query->result();
	}
	public function checkin($idcliente){
		$this->db->set('CheckIn', 1);	
		$this->db->where('IdCliente', $idcliente);
		$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";
		$this->db->where($dates);
		//$this->db->update($this->table_pedido);
		return $this->db->update($this->table_pedido);		
	}
	
	public function get_estadoPedido(){
		$this->db->from($this->table_estado_pedido);				
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();
	}
	
	public function get_proximos_pedidos ($idcliente){
		$this->db->from($this->table_pedido);
		$this->db->join($this->table_cliente,$this->table_pedido.".IdCliente =".$this->table_cliente.".IdCliente");											
		$this->db->where($this->table_cliente.".IdCliente", $idcliente);	
		$this->db->where($this->table_pedido.".IdEstadoPedido", 1);//Pendiente de pago
		//$dates="(DATEDIFF( CURDATE(), ".$this->table_pedido.".FechaPedido)=7)";		
		$dates="( ".$this->table_pedido.".FechaPedido BETWEEN CURDATE()-6 AND CURDATE() )";				 
		$this->db->where($dates);
		$this->db->order_by("FechaPedido", "ASC"); 
		$query = $this->db->get();
		//echo $this->db->last_query();		
		return $query->result();
	}
	
	public function get_objpedido_by_idpedido($idpedido){
		$this->db->flush_cache();			
		$this->db->where($this->table_pedido.".IdPedido", $idpedido);	
		$query = $this->db->get($this->table_pedido);
		//echo $this->db->last_query();
		return $query->row(0);
	}
	
	public function get_clientes_checkin($idcobrador) {	
		$query = $this->db->query("					
			SELECT C.IdCliente, Pe.Nombre, Pe.ApePaterno, Pe.ApeMaterno, C.Latitud, C.Longitud, P.CheckIn
			FROM Persona Pe, Pedido P, Cliente C
			WHERE C.IdCliente = P.IdCliente
			AND C.IdCobrador ='".$idcobrador."'
			AND Pe.IdPersona = C.IdPersona
			AND DATEDIFF( CURDATE( ) , P.FechaPedido ) =7
			GROUP BY 1 		
		");	
		return $query->result();		
	}
	public function get_contactos_by_user_id($idubigeo) {	
		$distrito_str=$this->get_distrito($idubigeo);
		
		$this->db->flush_cache();	
		$query = $this->db->query("					
			SELECT P.IdPersona, Usu.IdUsuario, P.Nombre, P.ApePaterno, P.ApeMaterno, P.Activo, P.Telefono, P.Email, Usu.IdJerarquia
			FROM Usuario Usu
			INNER JOIN Ubigeo U ON Usu.IdUbigeo = U.IdUbigeo
			INNER JOIN Persona P ON Usu.IdPersona = P.IdPersona
			WHERE U.Distrito =  '".$distrito_str."' AND Usu.Activo=1 AND P.Activo=1
			AND ( Usu.IdJerarquia =5 or Usu.IdJerarquia =3)
		");	
		/*
		SELECT P.IdPersona, Usu.IdUsuario, P.Nombre, P.ApePaterno, P.ApeMaterno, P.Activo, P.Telefono, P.Email 
			FROM Usuario Usu
			INNER JOIN Ubigeo U ON Usu.IdUbigeo = U.IdUbigeo
			INNER JOIN Persona P ON Usu.IdPersona = P.IdPersona
			WHERE U.Distrito =  'SanMiguel' AND Usu.Activo=1 AND P.Activo=1
			AND ( Usu.IdJerarquia =5 or Usu.IdJerarquia =3)
		*/
		return $query->result();		
	}
	public function get_distrito($idubigeo){
		$this->db->where($this->table_ubigeo.".IdUbigeo", $idubigeo);	
        $query = $this->db->get($this->table_ubigeo);
        return $query->row(0)->Distrito;		
	}	
	public function entregar_pedido($idpedido){
		$this->db->set('IdEstadoPedido', 2);//Entregado
		$this->db->where('IdPedido', $idpedido);
		$this->db->update($this->table_pedido);
		return $this->get_by_id($idpedido);
	}
	
	public function registro_deposito($idusuario,$monto, $fecha, $numvoucher){
		$data=array("IdUsuario"=>$idusuario,
				"Fecha"=>$fecha,
				"Monto"=>$monto,
				"NumVoucher"=>$numvoucher);
		$this->db->insert($this->table_deposito, $data);
		return $this->get_last_iddeposito();
	}
	
	public function get_last_iddeposito(){
		$this->db->flush_cache();
        $this->db->select_max('IdDeposito');
        $query = $this->db->get($this->table_deposito);
        return $query->row(0)->IdDeposito;
    }
	public function get_deposito_by_user($idusuario,$fecha){	
		$this->db->from($this->table_deposito);
		$this->db->where('IdUsuario', $idusuario);
		$this->db->where('Fecha', $fecha);		
		$query = $this->db->get();
		return $query->result();
	}
}
?>
