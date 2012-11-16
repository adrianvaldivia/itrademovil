<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Pedido extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Payment_model');
		$this->load->model('ws/Cliente_model');
    }	

	public function index()
	{					
		echo "Controlador Pedido";			
	}	
	public function pagar_pedido($idpedido_w='',$montocobrado_w='',$numVoucher_w='')
	{
		$idpedido=$this->input->post('idpedido');		
		$montocobrado=$this->input->post('montocobrado');
		$numVoucher=$this->input->post('numVoucher');
		if ($idpedido_w!='' && $montocobrado_w !='' && $numVoucher_w!=''){
			$idpedido=$idpedido_w;		
			$montocobrado=$montocobrado_w;
			$numVoucher=$numVoucher_w;
		}
		//Actualizar el pedido del cliente
		//Obtener el IdCliente
		$objpedido=$this->Payment_model->get_objpedido_by_idpedido($idpedido);
		//OBTENER LINEA CREDITO
		//ACTUALIZAR LINEA CREDITO
		$linea_credito=$this->Cliente_model->get_linea_credito($objpedido->IdCliente);					
		$montoActual=number_format(round($linea_credito->MontoActual+$montocobrado,2),2,'.','');		
		$this->Cliente_model->updateLineaPedidoCliente($objpedido->IdCliente,$montoActual);	
		$result=$this->Payment_model->pay_by_id($idpedido,$montocobrado,$numVoucher);	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));							
	}
	public function consultar_pedido($idpedido_w='')
	{
		$idpedido=$this->input->post('idpedido');				
		if (isset($idpedido_w)&& $idpedido_w!= "" ){			
			$result=$this->Payment_model->get_by_id($idpedido_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}
		
		if (isset($idpedido)&& $idpedido!=""  ){
			$result=$this->Payment_model->get_by_id($idpedido);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}		
	}
	
	public function get_pedidos_by_idvendedor($idcobrador_w=''){
		$idcobrador=$this->input->post('idvendedor');				
		if (isset($idcobrador)&& $idcobrador!= "" ){			
			$result=$this->Payment_model->get_pedidos_by_idvendedor($idcobrador);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}
		
		if (isset($idcobrador_w)&& $idcobrador_w!=""  ){
			$result=$this->Payment_model->get_pedidos_by_idvendedor($idcobrador_w);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}	
	}	
	public function get_detail_by_idpedido($idpedido_w=''){
		$idpedido=$this->input->post('idpedido');				
		if (isset($idpedido)&& $idpedido!= "" ){			
			$result=$this->Payment_model->get_detail_by_idpedido($idpedido);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}	
		if (isset($idpedido_w)&& $idpedido_w!=""  ){
			$result=$this->Payment_model->get_detail_by_idpedido($idpedido_w);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}
	}	
	public function registrar_pedido($idcliente_w='',$montototalpedidosinigv_w=''){
		$idcliente=$this->input->post('idcliente');
		$montototalpedidosinigv=$this->input->post('montototalpedidosinigv');
		if (isset($idcliente)&& $idcliente!= "" ){
			$linea_credito=$this->Cliente_model->get_linea_credito($idcliente);			
			$montoconigv=number_format(round($montototalpedidosinigv*1.18,2),2,'.','');
			$montoActual=number_format(round($linea_credito->MontoActual-$montoconigv,2),2,'.','');
			if($montoActual>=0){
				$this->Cliente_model->updateLineaPedidoCliente($idcliente,$montoActual);				
				$result=$this->Payment_model->registrar_pedido($idcliente,$montototalpedidosinigv);												
				$this->output->set_content_type('application/json')->set_output(json_encode($result));
			}else{
				$this->output->set_content_type('application/json')->set_output(json_encode(0));
			}	
		}
		if (isset($idcliente_w)&& $idcliente_w!=""  ){
			$linea_credito=$this->Cliente_model->get_linea_credito($idcliente_w);			
			$montoconigv=number_format(round($montototalpedidosinigv_w*1.18,2),2,'.','');
			$montoActual=number_format(round($linea_credito->MontoActual-$montoconigv,2),2,'.','');
			if($montoActual>=0){
				$this->Cliente_model->updateLineaPedidoCliente($idcliente_w,$montoActual);				
				$result=$this->Payment_model->registrar_pedido($idcliente_w,$montototalpedidosinigv_w);												
				$this->output->set_content_type('application/json')->set_output(json_encode($result));
			}else{
				$this->output->set_content_type('application/json')->set_output(json_encode(0));
			}			
		}
	}
	public function registrar_pedido_linea($idpedido_w='',$idproducto_w='',$montolinea_w='',$cantidad_w=''){		
		$idpedido=$this->input->post('idpedido');
		$idproducto=$this->input->post('idproducto');
		$montolinea=$this->input->post('montolinea');
		$cantidad=$this->input->post('cantidad');		
		if (isset($idpedido)&& $idpedido!= "" ){					
			$result=$this->Payment_model->registrar_pedido_linea($idpedido,$idproducto,$montolinea,$cantidad);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		if (isset($idpedido_w)&& $idpedido_w!=""  ){
			$result=$this->Payment_model->registrar_pedido_linea($idpedido_w,$idproducto_w,$montolinea_w,$cantidad_w);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}
	}
	public function cancelar_pedido($idpedido_w=''){
		$idpedido=$this->input->post('idpedido');	
		if ($idpedido_w!=''){
			$idpedido=$idpedido_w;
		}
		if (isset($idpedido)&& $idpedido!= "" ){					
			$objpedido=$this->Payment_model->get_objpedido_by_idpedido($idpedido);			
			$linea_credito=$this->Cliente_model->get_linea_credito($objpedido->IdCliente);					
			$montoActual=number_format(round($linea_credito->MontoActual+$objpedido->MontoTotalPedido,2),2,'.','');		
			$this->Cliente_model->updateLineaPedidoCliente($objpedido->IdCliente,$montoActual);
			$result=$this->Payment_model->cancelar_pedido($idpedido);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}			
	}
	public function ultimos_pedidos($idvendedor_w=''){
		$idvendedor=$this->input->post('idvendedor');	
		if (isset($idvendedor)&& $idvendedor!= "" ){			
			$result=$this->Payment_model->ultimos_pedidos($idvendedor);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		if (isset($idvendedor_w)&& $idvendedor_w!=""  ){
			$result=$this->Payment_model->ultimos_pedidos($idvendedor_w);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}	
	}
	
	//idvendedor me pasas
	//Busco la de meta del vendedor
	//Busco la meta actual dependiendo de la fecha
	// 
	public function meta_periodo($idvendedor_w=''){
		$idvendedor=$this->input->post('idvendedor');
		//$idvendedor=1;
		if ($idvendedor_w!=''){
			$idvendedor=$idvendedor_w;
		}
		$arr_meta=$this->Payment_model->get_meta($idvendedor);
		//$arr=array("periodo"=>$query->row(0)->Descripcion,"monto"=>$query->row(0)->Monto);
	//var_dump($arr_meta);		
		foreach($arr_meta as $ele){
			$fechaini=$ele->FechaIni;
			$fechafin=$ele->FechaFin;
			$nombre=$ele->Descripcion;
			$meta=$ele->Monto;
			$metaout="$meta";
			$suma=$this->Payment_model->get_monto($idvendedor,$ele->FechaIni,$ele->FechaFin);			
		}				
		//var_dump($suma);
		foreach($suma as $elem){
			
			$nume=($elem->montototal!=null)?$elem->montototal:0;
			
			$numeout="$nume";
			$this->output->set_content_type('application/json')->set_output(json_encode(array("suma"=>$numeout,"fechini"=>$fechaini,"fechafin"=>$fechafin,"meta"=>$metaout,"nombre"=>$nombre)));
		}
		//$this->output->set_content_type('application/json')->set_output(json_encode(array("nom"=>$suma, "arra"=>implode(",",$arr_meta))));
	}
	public function get_periodos(){
		$result=$this->Payment_model->get_periodos();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));	
	}
	public function get_periodo_jeraquia_ubigeo($idjerarquia_w='',$idubigeo_w=''){
		$idjerarquia=$this->input->post('idjerarquia');
		$idubigeo=$this->input->post('idubigeo');
		if ($idubigeo_w!='' && isset($idubigeo_w) && $idjerarquia_w!='' && $idjerarquia_w){
			$idjerarquia=$idjerarquia_w;
			$idubigeo=$idubigeo_w;
		}
		$result=$this->Payment_model->get_periodo_jeraquia_ubigeo($idjerarquia,$idubigeo);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}
	public function get_monto_zona($idzona_w='',$idperiodo_w=''){
		$idzona=$this->input->post('idzona');
		$idperiodo=$this->input->post('idperiodo');
		if ($idzona_w!='' && isset($idzona_w) && $idperiodo_w!='' && $idperiodo_w){
			$idzona=$idzona_w;
			$idperiodo=$idperiodo_w;
		}
		$result=$this->Payment_model->get_monto_zona($idzona,$idperiodo);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}	
	public function get_pedidos_detail($idspedidos_w){
		$idspedidos=$this->input->post('idspedidos');
		if ($idspedidos_w!='' && isset($idspedidos_w)){			
			$idspedidos=$idspedidos_w;
		}
		$ides=array();
		$ides=explode("-", $idspedidos);			
		$result=$this->Payment_model->get_pedidos_detail($ides);		
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}
	
	public function get_estadoPedido(){
		$result=$this->Payment_model->get_estadoPedido();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}	
	
	public function get_proximos_pedidos($idcliente_w=''){
		$idcliente=$this->input->post('idcliente');
		if ($idcliente_w!=''){
			$idcliente=$idcliente_w;
		}
		$result=$this->Payment_model->get_proximos_pedidos($idcliente);		
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}
	public function get_clientes_checkin($idcobrador_w=''){
		$idcobrador=$this->input->post('idcobrador');
		if ($idcobrador_w!=''){
			$idcobrador=$idcobrador_w;
		}
		$result=$this->Payment_model->get_clientes_checkin($idcobrador);		
		$this->output->set_content_type('application/json')->set_output(json_encode($result));
	}
}

