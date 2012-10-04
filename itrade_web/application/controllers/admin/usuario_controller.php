<?php

class Usuario_controller extends CI_Controller {

    function Usuario_controller() {
        parent::__construct();
        //Cargar los modelos para la base de datos
        $this->load->model('Usuario_model');
        $this->load->model('Persona_model');
        $this->load->model('Perfil_model');

        /*
          $logged_in = $this->session->userdata('logged_in');

          if(!isset($logged_in) || $logged_in != TRUE){
          $this->session->set_flashdata('message','Su sesi�n ha terminado.');
          redirect('application/controllers/login','refresh');
          }
          if ($this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER') == false ) {
          $this->session->set_flashdata('warning','Usted no tiene acceso a este m�dulo.');
          redirect('admin/dashboard');
          }
         */
    }

    public function index() {
        //cargar las noticias               
        $data['title'] = "Itrade Mantenimientos - Registrar";
        $data['main'] = "login/login_box.php"; //RUTA		
        //OBTENIENDO DATA PARA PERFILES
        $data['perfiles'] = $this->get_all_profile();
		//print_r($data);
        $this->load->vars($data);        
        $this->load->view('user_views/create_user_view');
        //echo "<script languaje='javascript'>alert('Index')</script>";
    }
	
	public function modificar() {
        //cargar las noticias               
        $data['title'] = "Itrade Mantenimientos - Modificar";
        $data['main'] = "login/login_box.php"; //RUTA		
        //OBTENIENDO DATA PARA PERFILES
        $data['perfiles'] = $this->get_all_profile();
		$data['usuario'] = $this->Usuario_model->get(1);
		$data['persona'] = $this->Persona_model->get(1);
		//print_r($data);
        $this->load->vars($data);        
        $this->load->view('user_views/edit_user_view');
        //echo "<script languaje='javascript'>alert('Index')</script>";
    }
    public function set_validation_rules($rules) {
        if ($rules == 0) { //Rules para create
			$this->form_validation->set_rules('username', 'Nombre de Usuario', 'required|callback__check_username');//**
            $this->form_validation->set_rules('firstname', 'Nombre', 'required');
            $this->form_validation->set_rules('lastname1', 'Apellido Paterno', 'required');
            $this->form_validation->set_rules('lastname2', 'Apellido Materno', 'required');
            $this->form_validation->set_rules('birthdate', 'Fecha de Nacimiento', 'required');
            $this->form_validation->set_rules('phone', 'Telefono', 'number');
            $this->form_validation->set_rules('password', 'Password', 'required|matches[passwordrepeat]|min_length[5]');
            $this->form_validation->set_rules('passwordrepeat', 'Repetir Password', 'required|min_length[5]');
            $this->form_validation->set_rules('email', 'Email', 'required|valid_email|callback__check_email');
        }
        if ($rules == 1) { //Rules para edit
            $this->form_validation->set_rules('perfil', 'Perfil', 'required');
            $this->form_validation->set_rules('activo', 'Activo', 'required');
        }
        if ($rules == 2) { //Rules para info
        }
        if ($rules == 3) { //Rules para Change
        }
    }
	
    function create()
	{
/*        $has_access = $this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER_NEW');
        if ($has_access == false ) {
			$this->session->set_flashdata('warning','Usted no tiene permisos para crear usuario');
			redirect('admin/admin_seguridad/users');	
		}
		*/
        //Parametro para setear reglas del create
        $rules_create=0;
        $this->set_validation_rules($rules_create);

		if (/*$this->form_validation->run()*/true){
			//TABLA PERSONA
			$data = array(
			   'Nombre' => xss_clean($this->input->post('firstname')),
			   'ApePaterno' => xss_clean($this->input->post('lastname1')),
			   'ApeMaterno' => xss_clean($this->input->post('lastname2')),
			   'DNI' => xss_clean($this->input->post('dni')),
			   'FechNac' => xss_clean($this->input->post('birthdate')),
			   'Telefono' => xss_clean($this->input->post('phone')),
			   'Email' => xss_clean($this->input->post('email')),   
			   'Activo' => 1      
			);
			//crea la persona
			$idPersona = $this->Persona_model->create($data);			
			print_r($idPersona);
			
			//TABLA USUARIO			
			$data2 = array(
				'Nombre' => xss_clean($this->input->post('username')),
			   'Password' => substr(do_hash(xss_clean($_POST['password'])),0,50),
			   'IdPerfil' => xss_clean($this->input->post('perfil_id')) ,  
			   'IdPersona' => $idPersona,
			   'Activo' => 1
			);

			//crea el usuario
			$this->Usuario_model->create($data2);
			
		    //mensaje de verificacion
		    $this->session->set_flashdata('message','El usuario ha sido creado correctamente.');
		    
		    //redirect('admin/admin_seguridad/users','refresh');
		}else{
		
		    $data['error_message'] = validation_errors();		    	
			$data2['error_message'] = validation_errors();
		}

		$data['title'] = "Nuevo Usuario";		
		$data2['title'] = "Nuevo Usuario";		
		//$data['main'] = 'admin/admin_seguridad/users/create';
		$this->load->vars($data);
		$this->load->vars($data2); //REVISAR CON LEILA
		//$this->load->view('template');
	}

    public function edit($idUsuario = '') {
        //Parametro para setear reglas del edit
        $rules_edit = 1;
        $this->set_validation_rules($rules_edit);
		
		
        if (/*$this->form_validation->run()*/true) {
            $data = array(
                'IdPerfil' => xss_clean($this->input->post('perfil')),
                'Activo' => xss_clean($this->input->post('activo'))
            );
	
            $this->usuario_model->edit($this->input->post('IdUsuario'), $data);
            $this->session->set_flashdata('message', 'Los datos del usuarios han sido modificados correctamente.');
            //redirect('admin/admin_seguridad/users','refresh'); FATLTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        } else {
            $data['error_message'] = validation_errors();
        }
        $data['title'] = "Editar Usuario";
		
        $data['user'] = $this->Usuario_model->get($idUsuario);
				// echo'<pre>';
print_r($data);
// echo'</pre>';
        //$data['main'] = 'admin/admin_seguridad/users/edit'; FALTAAAAAA
        // $this->load->vars($data);
        // $this->load->view('template'); //LEILA REVISA ESTO
    }

    function get_all_profile() {
        $perfiles = array();
        $profiles = $this->Perfil_model->get_all_profile();
       
        foreach ($profiles as $profile) {
            $perfiles[$profile['IdPerfil']]=$profile['Descripcion'];
        }
        return $perfiles;
    }

}

?>