<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Android extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('Contacts_model');
    }	
	public function index()
	{					
		$id=$this->input->post('id');
		//$id=7;
		if (isset($id)&& $id!=""){
			$conta=$this->Contacts_model->get_by_id($id);	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));		
		}else{
			$conta=$this->Contacts_model->get_all_contacts();	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));		
		}
		
	}	
	public function wb1($id='')
	{		
		$id_con=$this->input->post('id_contact');
		if (isset($id_con)&& $id_con!=""){
			$conta=$this->Contacts_model->get_by_id($id_con);	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));			
		}
		if (isset($id)&& $id!=""){
			$conta=$this->Contacts_model->get_by_id($id);	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));			
		}	
		$username=$this->input->post('username');
		if (isset($username)&& $username!=""){
			$conta=$this->Contacts_model->get_by_username($username);	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));			
		}		
	}
	public function wb2()
	{	
		echo "adminpass//".do_hash("adminpass")."//";
	}
}

