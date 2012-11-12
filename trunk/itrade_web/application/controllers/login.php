<?php
class Login extends CI_Controller {

    function Login(){
        parent::__construct();
        //Cargar los modelos para la base de datos    
    }
	public function index()
	{
        //cargar las noticias                
        $data['title']="Itrade - Login";             
        $data['main']="login/login_box.php";//RUTA			           			
        
        $this->load->vars($data);
        $this->load->view('login/login');
//        $this->load->view('user_views/dashboard_user_view');
        //echo "<script languaje='javascript'>alert('Index')</script>";
	}
}
?>
