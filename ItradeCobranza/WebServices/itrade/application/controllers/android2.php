<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Android2 extends CI_Controller { //Nombre de la clase debe comenzar con mayuscula
	
	function __construct()
    {        
        
		parent::__construct();
		$this->load->model('Contacts2_model');//Nombre de la clase debe comenzar con mayuscula
		
    }	
	public function index()
	{			
		//$conta=$this->Contacts_model->get_all_contacts();				
		//$this->output->set_content_type('application/json')->set_output(json_encode($conta));		
		echo "Ochi, titi, kal!!";
	}
	public function chunchin($param='',$param2='')
	{			
		echo $param2." Y ".$param;
		echo "JORGE YAOMING!!! EDER";		
	}
	public function chunchin2()
	{			
		$conta=$this->Contacts2_model->get_all_contacts();	
		$this->output->set_content_type('application/json')->set_output(json_encode($conta));				
	}	
	public function wb1()
	{	
		$id=$this->input->post('id');
		$this->output->set_content_type('application/json')->set_output(json_encode(array('foo' => 'bar2')));		
	}
	//172.17.9.249
}

