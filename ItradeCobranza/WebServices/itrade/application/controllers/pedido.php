<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Pedido extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('Payment_model');
    }	

	public function index()
	{					
		echo "Controlador Pedido";			
	}	
	public function pagar_pedido($idpedido_w='')
	{
		$idpedido=$this->input->post('idpedido');		
		
		if (isset($idpedido_w)&& $idpedido_w!= "" ){			
			$result=$this->Payment_model->pay_by_id($idpedido_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}
		
		if (isset($idpedido)&& $idpedido!=""  ){
			$result=$this->Payment_model->pay_by_id($idpedido);
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
}

