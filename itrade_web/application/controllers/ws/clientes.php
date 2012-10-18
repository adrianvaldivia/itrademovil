<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Clientes extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Cliente_model');
    }	

	public function index()
	{					
		echo "Controlador Clientes";			
	}
	
	public function get_clientes_by_vendedor($idvendedor_w='')
	{
		$idvendedor=$this->input->post('idvendedor');				
		if (isset($idvendedor_w)&& $idvendedor_w!= "" ){			
			$result=$this->Cliente_model->get_clients_by_idvendedor($idvendedor_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));		
		}		
		if (isset($idvendedor)&& $idvendedor!=""  ){
			$result=$this->Cliente_model->get_clients_by_idvendedor($idvendedor);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}		
	}	
	
	public function get_pedidos_by_idvendedor($idvendedor_w='')
	{
		$idvendedor=$this->input->post('idvendedor');				
		if (isset($idvendedor_w)&& $idvendedor_w!= "" ){			
			$result=$this->Cliente_model->get_pedidos_by_idvendedor($idvendedor_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));		
		}		
		if (isset($idvendedor)&& $idvendedor!=""  ){
			$result=$this->Cliente_model->get_pedidos_by_idvendedor($idvendedor);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}		
	}
}

