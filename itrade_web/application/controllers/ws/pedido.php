<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Pedido extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Payment_model');
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
		
		if (isset($idpedido_w)&& $idpedido_w!= "" ){			
			$result=$this->Payment_model->pay_by_id($idpedido_w,$montocobrado_w,$numVoucher_W);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));		
		}		
		if (isset($idpedido)&& $idpedido!=""  ){
			$result=$this->Payment_model->pay_by_id($idpedido,$montocobrado,$numVoucher);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}		
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
			$result=$this->Payment_model->registrar_pedido($idcliente,$montototalpedidosinigv);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		if (isset($idcliente_w)&& $idcliente_w!=""  ){
			$result=$this->Payment_model->registrar_pedido($idcliente_w,$montototalpedidosinigv_w);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
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
		if (isset($idpedido)&& $idpedido!= "" ){					
			$result=$this->Payment_model->cancelar_pedido($idpedido);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		if (isset($idpedido_w)&& $idpedido_w!=""  ){
			$result=$this->Payment_model->cancelar_pedido($idpedido_w);
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
	public function meta_periodo($idvendedor=''){
		//$idvendedor=$this->input->post('idvendedor');
		//$idvendedor=1;
		$arr_meta=$this->Payment_model->get_meta($idvendedor);
		//$arr=array("periodo"=>$query->row(0)->Descripcion,"monto"=>$query->row(0)->Monto);
		var_dump($arr_meta);
		echo implode(",",$arr_meta)."<BR>";
		$suma=$this->Payment_model->get_monto($idvendedor,$arr_meta['fechaini'],$arr_meta['fechafin']);		
		var_dump($suma);
		$this->output->set_content_type('application/json')->set_output(json_encode(array("nom"=>$suma, "arra"=>implode(",",$arr_meta))));
	}
	
}

