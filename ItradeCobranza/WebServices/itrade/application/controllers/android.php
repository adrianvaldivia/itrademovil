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
			$conta=$this->Contacts_model->get_by_username_wb($username);	
			$this->output->set_content_type('application/json')->set_output(json_encode($conta));			
		}		
	}
	public function wb5()
	{	
		echo "adminpass//".do_hash("adminpass")."//";echo "admindr//".do_hash("admindr")."//";echo "admincsb//".do_hash("admincsb")."//";echo "admingal//".do_hash("admingal")."//";
	}
	public function wb6()
	{
		$micadena="JORGE";
		echo "//".$micadena."//";
		$cadena_encriptada = $this->encrypt($micadena ,654123789);
		echo "//".$cadena_encriptada."//";		
		$cadena_desencriptada = $this->decrypt($cadena_encriptada,654123789);
		echo "//".$cadena_desencriptada."//";
	}
	public	function encrypt($string, $key) {
	   $result = '';
	   for($i=0; $i<strlen($string); $i++) {
		  $char = substr($string, $i, 1);
		  $keychar = substr($key, ($i % strlen($key))-1, 1);
		  $char = chr(ord($char)+ord($keychar));
		  $result.=$char;
		}
		return base64_encode($result);
    }
	public function decrypt($string, $key) {
	   $result = '';
	   $string = base64_decode($string);
	   for($i=0; $i<strlen($string); $i++) {
		  $char = substr($string, $i, 1);
		  $keychar = substr($key, ($i % strlen($key))-1, 1);
		  $char = chr(ord($char)-ord($keychar));
		  $result.=$char;
	   }
	   return $result;
	}
}

