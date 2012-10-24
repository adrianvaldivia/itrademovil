<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Cobranza extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Product_model');
    }	

	public function index()
	{					
		echo "Controlador Pedido";			
	}	
	public function get_all_products()
	{				
		$result=$this->Product_model->get_all_products();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));	
	}
	public function get_all_categorias()
	{				
		$result=$this->Product_model->get_all_categorias();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));	
	}
}

