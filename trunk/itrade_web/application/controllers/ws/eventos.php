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
	
	public function registrar_evento($idcreador_w='',$descripcion_w='',$fecha_w='',$horaini_w='',$horafin_w='',$lugar_w=''){	
		$idcreador=$this->input->post('idcreador');
		$descripcion=$this->input->post('descripcion');
		$fecha=$this->input->post('fecha');
		$horaini=$this->input->post('horaini');
		$horafin=$this->input->post('horafin');				
		$lugar=$this->input->post('lugar');		
		if (isset($idcreador_w)&& $idcreador_w!=""  ){
			$idcreador=$idcreador_w;
			$descripcion=$descripcion_w;
			$fecha=$fecha_w;
			$horaini=$horaini_w;
			$horafin=$horafin_w;
			$lugar=$lugar_w;			
		}
		$result=$this->Evento_model->registrar_evento($idcreador,$descripcion,$fecha,$horaini,$horafin,$lugar);
		$this->output->set_content_type('application/json')->set_output($result);					
	}
	
	public function registrar_invitados($idevento_w='',$invitados_w=''){
		$invitados=$this->input->post('invitados');
		$idevento=$this->input->post('idevento');
		if (isset($idevento_w)&& $idevento_w!=""  ){			
			$idevento=$idevento_w;
			$invitados=$invitados_w;
		}
		$result=$this->Evento_model->registrar_invitados($idevento,$invitados);
		$this->output->set_content_type('application/json')->set_output($result);
	}
	public function contactos_por_evento($ideventos_w=''){
		$ideventos=$this->input->post('ideventos');
		if (isset($ideventos_w)&& $ideventos_w!="" ){
			$ideventos=$ideventos_w;
		}
		$result=$this->Evento_model->contactos_por_evento($ideventos);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));							
	}
	
	public function get_eventos_idusuario_month($idusuario_w='',$fecha_w=''){
		$idusuario=$this->input->post('idusuario');
		$fecha=$this->input->post('fecha');	
		if (isset($idusuario_w)&& $idusuario_w!="" && isset($fecha_w)&& $fecha_w!=""  ){
			$idusuario=$idusuario_w;
			$fecha=$fecha_w;			
		}
		list($year, $month, $day) = explode("-", $fecha);
		$result=$this->Evento_model->get_eventos_idusuario_month($idusuario,$year,$month);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));					
	}
	public function get_eventos_por_dia($idusuario_w='',$fecha_w=''){
		$idusuario=$this->input->post('idusuario');
		$fecha=$this->input->post('fecha');	
		if (isset($idusuario_w)&& $idusuario_w!="" && isset($fecha_w)&& $fecha_w!=""  ){
			$idusuario=$idusuario_w;
			$fecha=$fecha_w;			
		}
		//list($year, $month, $day) = explode("-", $fecha);
		$result=$this->Evento_model->get_eventos_por_dia($idusuario,$fecha);
		$this->output->set_content_type('application/json')->set_output(json_encode($result));					
	}	
}

