<?php
class Usuario_controller extends CI_Controller {

    function Usuario_controller(){
        parent::__construct();
        //Cargar los modelos para la base de datos
        $this->load->model('Usuario_model');       
		$this->load->model('Persona_model');     
		
		/*
		$logged_in = $this->session->userdata('logged_in');

	    if(!isset($logged_in) || $logged_in != TRUE){
		$this->session->set_flashdata('message','Su sesión ha terminado.');
		redirect('application/controllers/login','refresh');
	    }
	    if ($this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER') == false ) {
		$this->session->set_flashdata('warning','Usted no tiene acceso a este módulo.');
		redirect('admin/dashboard');	
	    }
		*/
    }
	
	public function index()
	{
        //cargar las noticias                
        $data['title']="Itrade Mantenimientos!!!";             
        $data['main']="login/login_box.php";//RUTA			           			
        $this->load->vars($data);
        $this->load->view('user_views/create_user_view');
        //echo "<script languaje='javascript'>alert('Index')</script>";
	}
	public function set_validation_rules($rules)
    {
        if ($rules==0) //Rules para create
        {
            
        }
        if ($rules==1) //Rules para edit
        {
            $this->form_validation->set_rules('perfil', 'Perfil', 'required');
            $this->form_validation->set_rules('activo', 'Activo', 'required');            
        }
        if ($rules==2 ) //Rules para info
        {
            
        }
        if ($rules==3) //Rules para Change
        {
            
        }
    }
	
	
	
	public function edit($idUsuario='')
	{
		//Parametro para setear reglas del edit
        $rules_edit=1;
        $this->set_validation_rules($rules_edit);

		if ($this->form_validation->run()){
			$data = array(
			   'IdPerfil' => xss_clean($this->input->post('perfil')),
			   'Activo' => xss_clean($this->input->post('activo')) 
			);

		    $this->usuario_model->edit($this->input->post('IdUsuario'), $data);
		    $this->session->set_flashdata('message','Los datos del usuarios han sido modificados correctamente.');
		    //redirect('admin/admin_seguridad/users','refresh'); FATLTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		}else{
    		    $data['error_message'] = validation_errors();
		}
		$data['title'] = "Editar Usuario";
		$data['user'] = $this->usuario_model->get($idUsuario);		
		//$data['main'] = 'admin/admin_seguridad/users/edit'; FALTAAAAAA
		$this->load->vars($data);
		$this->load->view('template');//LEILA REVISA ESTO
	}
	
	
	
	
}
?>