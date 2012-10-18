<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Login_model');		
    }	

	public function index()
	{					
		echo "Controlador LOGIN RENZO CARLOS";			
	}	
	/*WEBSERVICE UPDATE PASSWORD*/
	public function change_password($username_w="",$oldpass_w="",$newpass_w=""){
		$username=$this->input->post('username');
		$oldpassword=$this->input->post('oldpassword');
		$newpassword=$this->input->post('newpassword');	
		/*SOLO PARA WEB*/		
		if (isset($username_w)&& $username_w!= "" && isset($oldpass_w)&& $oldpass_w!="" && isset($newpass_w)&& $newpass_w!=""){				
			$result=$this->Login_model->change_password($username_w,$oldpass_w,$newpass_w);				
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}
		/*SOLO PARA ANDROID*/		
		if (isset($username)&& $username!= "" && isset($oldpassword)&& $oldpassword!="" && isset($newpassword)&& $newpassword!=""){						
			$result=$this->Login_model->change_password($username,$oldpassword,$newpassword);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));								
		}
	}
	
	public function change($user="",$user2=""){
		echo "carloss".$user."parametro2".$user2;
	}	
	
	public function get_user_by_username_password($username_w='', $password_w='')
	{
		$username=$this->input->post('username');
		$password=$this->input->post('password');		
		/*ESTO ES PARA VER EL RESULTADO EN LA WEB*/
		if (isset($username_w)&& $username_w!= "" && isset($password_w)&& $password_w!="" ){						
			$result=$this->Login_model->get_by_username($username_w,$password_w);							
			$this->output->set_content_type('application/json')->set_output(json_encode($result));			
		}		
		/*ESTO ES PARA EL RESULTADO EN ANDROID*/
		if (isset($username)&& $username!="" && isset($password)&& $password!="" ){
			$result=$this->Login_model->get_by_username($username,$password);
			$this->output->set_content_type('application/json')->set_output(json_encode($result));					
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
	public function wb5($cadena)
	{	
		echo "cadena//".$cadena."//encriptada//".do_hash($cadena)."//";
	}
}

