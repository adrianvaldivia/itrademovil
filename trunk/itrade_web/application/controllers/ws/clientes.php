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
	//public function registrar_prospecto($ruc,$razon_social,$direccion,$nombre,$apepaterno,$apematerno,$telefono,$fechanac,$dni,$montosolicitado,$idvendedor)
	public function registrar_prospecto()
	{
		
		$ruc=$this->input->post('ruc');				
		$razon_social=$this->input->post('razon_social');				
		$direccion=$this->input->post('direccion');				
		$nombre=$this->input->post('nombre');				
		$apepaterno=$this->input->post('apepaterno');				
		$apematerno=$this->input->post('apematerno');
		$telefono=$this->input->post('telefono');
		$fechanac=$this->input->post('fechanac');
		$dni=$this->input->post('dni');
		$montosolicitado=$this->input->post('montosolicitado');
		$idvendedor=$this->input->post('idvendedor');			
		
		if (isset($idvendedor)&& $idvendedor!=""  ){
			$result=$this->Cliente_model->registrerProspecto($ruc,$razon_social,$direccion,$nombre,$apepaterno,$apematerno,$telefono,$fechanac,$dni,$montosolicitado,$idvendedor);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}		
	}
	public function get_prospecto_by_vendedor($idvendedor_w='',$razon_social_w='')
	{
		$idvendedor=$this->input->post('idvendedor');				
		$razon_social=$this->input->post('razon_social');
		if (isset($idvendedor_w)&& $idvendedor_w!= "" ){			
			$result=$this->Cliente_model->get_prospecto_by_vendedor($idvendedor_w,$razon_social_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));		
		}		
		if (isset($idvendedor)&& $idvendedor!=""  ){
			$result=$this->Cliente_model->get_prospecto_by_vendedor($idvendedor,$razon_social);
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
	
	public function get_cliente_by_id($idcliente_w=''){
		$idcliente=$this->input->post('idcliente');				
		if (isset($idcliente_w)&& $idcliente_w!= "" ){			
			$result=$this->Cliente_model->get_cliente_by_id($idcliente_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));		
		}		
		if (isset($idcliente)&& $idcliente!=""  ){
			$result=$this->Cliente_model->get_cliente_by_id($idcliente);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}	
	}
}
