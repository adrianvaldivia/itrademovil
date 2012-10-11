<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Reportes extends CI_Controller {
	function __construct()
    {        
        parent::__construct();
		$this->load->model('Reporte_model');		
    }	
	public function index()
	{					
		echo "Controlador REPORTES";			
	}	
	/*WEBSERVICE UPDATE PASSWORD*/
	public function zonas_resumido($month_w="",$idjerarquia_w="",$idubigeo_w=""){		
		//2012-05-10
		$month=$this->input->post('month');
		$idjerarquia=$this->input->post('idjerarquia');
		$idubigeo=$this->input->post('idubigeo');			
		/*SOLO PARA WEB*/		
		if (isset($month_w)&& $month_w!= "" && isset($idjerarquia_w)&& $idjerarquia_w!="" && isset($idubigeo_w)&& $idubigeo_w!=""){	
			$obj_id=$result=$this->Reporte_model->objetoUbigeo($idjerarquia_w,$idubigeo_w);			
			foreach($result as $res){
				$id=$res->id;
			}	
			$result=$this->Reporte_model->zonas_resumido($month_w,$idjerarquia_w,$idubigeo_w,$id);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}
		/*SOLO PARA ANDROID*/		
		if (isset($month)&& $month!= "" && isset($idjerarquia)&& $idjerarquia!="" && isset($idubigeo)&& $idubigeo!=""){	
			$obj_id=$result=$this->Reporte_model->objetoUbigeo($idjerarquia,$idubigeo);			
			foreach($result as $res){
				$id=$res->id;
			}	
			$result=$this->Reporte_model->zonas_resumido($month,$idjerarquia,$idubigeo,$id);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}			
	}
	public function objetoubigeo(){		
		//2012-05-10
		/*SOLO PARA WEB*/		
				
		$result=$this->Reporte_model->objetoUbigeo(1,1);			
		foreach($result as $res){
			echo $res->id;
		}			
				
	}
	
	//REPORTE ZONAS DETALLADO
	
	public function zonas_detallado($month_w="",$idjerarquia_w="",$idubigeo_w=""){		
		//2012-05-10
		$month=$this->input->post('month');
		$idjerarquia=$this->input->post('idjerarquia');
		$idubigeo=$this->input->post('idubigeo');			
		/*SOLO PARA WEB*/		
		if (isset($month_w)&& $month_w!= "" && isset($idjerarquia_w)&& $idjerarquia_w!="" && isset($idubigeo_w)&& $idubigeo_w!=""){	
			$obj_id=$result=$this->Reporte_model->objetoUbigeo($idjerarquia_w,$idubigeo_w);			
			foreach($result as $res){
				$id=$res->id;
			}	
			$result=$this->Reporte_model->zonas_detallado($month_w,$idjerarquia_w,$idubigeo_w,$id);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}
		/*SOLO PARA ANDROID*/		
		if (isset($month)&& $month!= "" && isset($idjerarquia)&& $idjerarquia!="" && isset($idubigeo)&& $idubigeo!=""){	
			$obj_id=$result=$this->Reporte_model->objetoUbigeo($idjerarquia,$idubigeo);			
			foreach($result as $res){
				$id=$res->id;
			}	
			$result=$this->Reporte_model->zonas_detallado($month,$idjerarquia,$idubigeo,$id);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}			
	}
	
	public function circulo_resumido($month_w="",$iddistrito_w="",$iddepartamento_w="",$idpais_w=""){		
		//2012-05-10
		$month=$this->input->post('month');
		$iddistrito=$this->input->post('iddistrito');
		$iddepartamento=$this->input->post('iddepartamento');	
		$idpais=$this->input->post('idpais');			
		/*SOLO PARA WEB*/		
		if (isset($month_w)&& $month_w!= "" && isset($iddistrito_w)&& $iddistrito_w!="" && isset($iddepartamento_w)&& $iddepartamento_w!="" && isset($idpais_w)&& $idpais_w!=""){	
			//$obj_id=$result=$this->Reporte_model->objetoUbigeo($idjerarquia_w,$idubigeo_w);			
			
			$result=$this->Reporte_model->circulo_resumido($month_w,$iddistrito_w,$iddepartamento_w,$idpais_w);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}
		/*SOLO PARA ANDROID*/		
		if (isset($month)&& $month!= "" && isset($iddistrito)&& $iddistrito!="" && isset($iddepartamento)&& $iddepartamento!="" && isset($idpais)&& $idpais!=""){	
			$result=$this->Reporte_model->circulo_resumido($month,$iddistrito,$iddepartamento,$idpais);	
			//$result=$this->Reporte_model->zonas_detallado($month,$idjerarquia,$idubigeo,$id);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}					
	}
	
}

