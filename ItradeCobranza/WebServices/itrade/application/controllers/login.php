<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('Login_model');
    }	

	public function index()
	{					
		echo "Controlador LOGIN";			
	}	
	public function get_user_by_username_password($username_w='', $password_w='')
	{
		$username=$this->input->post('username');
		$password=$this->input->post('password');		
		if (isset($username_w)&& $username_w!= "" && isset($password_w)&& $password_w!="" ){			
			$result=$this->Login_model->get_by_username($username_w,$password_w);	
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		if (isset($username)&& $username!="" && isset($password)&& $password!="" ){
			$result=$this->Login_model->get_by_username($username,$password);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
		}
		else{
			$this->output->set_content_type('application/json')->set_output(json_encode(array()));					
		}
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

