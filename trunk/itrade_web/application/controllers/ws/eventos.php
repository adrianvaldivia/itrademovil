<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Eventos extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();		
		$this->load->model('ws/Evento_model');

    }	

	public function index()
	{					
		echo "Controlador Pedido";			
	}	
	
	public function registrar_evento($idcreador_w='',$descripcion_w='',$fecha_w='',$horaini_w='',$horafin_w=''){	
		$idcreador=$this->input->post('idcreador');
		$descripcion=$this->input->post('descripcion');
		$fecha=$this->input->post('fecha');
		$horaini=$this->input->post('horaini');
		$horafin=$this->input->post('horafin');				
		if (isset($idcreador_w)&& $idcreador_w!=""  ){
			$idcreador=$idcreador_w;
			$descripcion=$descripcion_w;
			$fecha=$fecha_w;
			$horaini=$horaini_w;
			$horafin=$horafin_w;
		}
		$result=$this->Evento_model->registrar_evento($idcreador,$descripcion,$fecha,$horaini,$horafin);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));					
	}
	public function get_eventos_idusuario_month($idusuario_w='',$month_w=''){
		$idusuario=$this->input->post('idusuario');
		$month=$this->input->post('month');	
		if (isset($idusuario_w)&& $idusuario_w!="" && isset($month_w)&& $month_w!=""  ){
			$idusuario=$idusuario_w;
			$month=$month_w;
		}
		$result=$this->Evento_model->get_eventos_idusuario_month($idusuario,$month);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));					
	}
}

