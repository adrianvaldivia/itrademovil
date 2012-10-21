<?php

class Usuario_controller extends CI_Controller {

    function Usuario_controller() {
        parent::__construct();
        //Cargar los modelos para la base de datos
        $this->load->model('Usuario_model');
        $this->load->model('Persona_model');
        $this->load->model('Perfil_model');
        $this->load->model('Ubigeo_model');
        $this->load->model('Meta_model');
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
        $data['title'] = "Itrade Mantenimientos";
        $data['main'] = "pages/dashboard_content.php"; //RUTA		
        $data['usuarios'] = $this->list_all_users();
//        print_r($data['usuarios']);
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $this->load->vars($data);
        $this->load->view('user_views/dashboard_user_view');
    }

    public function modificar($idcontact, $number) {
        //cargar las noticias               
        $data['title'] = "Modificar Usuario";
        $data['main'] = "login/login_box.php"; //RUTA		
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        //OBTENIENDO DATA PARA PERFILES
        $data['perfiles'] = $this->get_all_profile();
        $data['ubigeo'] = $this->get_all_ubigeos();
        $data['usuario'] = $this->Usuario_model->get($idcontact);
        $data['persona'] = $this->Persona_model->get($number);
//print_r($data['persona']);
        $this->load->vars($data);
        $this->load->view('user_views/edit_user_view');
        //echo "<script languaje='javascript'>alert('Index')</script>";
    }

    public function set_validation_rules($rules) {
        if ($rules == 0) { //Rules para create
            $this->form_validation->set_rules('username', 'Nombre de Usuario', 'required|callback__check_username'); //**
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

    function create_user() {
        //OBTENIENDO DATA PARA PERFILES
        $data['title'] = "Nuevo Usuario";
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $data['perfiles'] = $this->get_all_profile();
        $data['ubigeo'] = $this->get_all_ubigeos();
//        print_r($data['ubigeo']);
        $this->load->vars($data);
        $this->load->view('user_views/create_user_view');
    }

    function create_meta($idusuario) {
        //OBTENIENDO DATA PARA PERFILES
        $data['title'] = "Nueva Meta";
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $data['idusuario'] = $idusuario;
//        print_r($data['ubigeo']);
        $this->load->vars($data);
        $this->load->view('user_views/create_meta_user_view');
    }

    function metas_user($idusuario = "") {
        //OBTENIENDO DATA PARA PERFILES
        $data['title'] = "Metas";
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $data['metas'] = $this->get_metas_usuario($idusuario);
        $data['idusuario'] = $idusuario;
//        print_r($data['metas']);

        $this->load->vars($data);
        $this->load->view('user_views/metas_user_view');
    }

    public function modificar_meta($idmeta) {
        //cargar las noticias               
        $data['title'] = "Modificar Meta";
        $data['main'] = "login/login_box.php"; //RUTA		
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        //OBTENIENDO DATA PARA PERFILES
        $data['meta'] = $this->Meta_model->get($idmeta);

        $this->load->vars($data);
        $this->load->view('user_views/edit_meta_user_view');
        
    }

    function create() {
        /*        $has_access = $this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER_NEW');
          if ($has_access == false ) {
          $this->session->set_flashdata('warning','Usted no tiene permisos para crear usuario');
          redirect('admin/admin_seguridad/users');
          }
         */
        //Parametro para setear reglas del create
        $rules_create = 0;
        $this->set_validation_rules($rules_create);

//        if ($this->form_validation->run()) {
        //TABLA PERSONA
        $idPersona = $this->Persona_model->get_last_idPersona() + 1;
        $data['Persona'] = array(
            'IdPersona' => $idPersona,
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

        $this->Persona_model->create($data['Persona']);
        // print_r($idPersona);
        //TABLA USUARIO
        $data['Usuario'] = array(
            'IdUsuario' => $this->Usuario_model->get_last_idUsuario() + 1,
            'Nombre' => xss_clean($this->input->post('username')),
            'Password' => substr(do_hash(xss_clean($_POST['password'])), 0, 50),
            'IdPerfil' => xss_clean($this->input->post('perfil_id')),
            'IdPersona' => $idPersona,
            'Activo' => 1,
            'IdJerarquia' => 1,
            'IdUbigeo' => $this->input->post('ubigeo_id')
        );

        //crea el usuario
        $this->Usuario_model->create($data['Usuario']);

        //mensaje de verificacion
        $this->session->set_flashdata('message', 'El usuario ha sido creado correctamente.');

        redirect('admin/usuario_controller', 'refresh');
//        } else {
//            $data['Persona']['error_message'] = validation_errors();
//            $data['Usuario']['error_message'] = validation_errors();
//        }
//        $data['title'] = "Nuevo Usuario";
//        $data['main'] = 'user_views/create_user_views';
//        $this->load->vars($data);
        //$this->load->view('template');
    }

    public function edit($idUsuario = '') {
        //Parametro para setear reglas del edit
        $rules_edit = 1;
        $this->set_validation_rules($rules_edit);
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');

//        if (/* $this->form_validation->run() */true) {

        $data['Persona'] = array(
            'Nombre' => xss_clean($this->input->post('firstname')),
            'ApePaterno' => xss_clean($this->input->post('lastname1')),
            'ApeMaterno' => xss_clean($this->input->post('lastname2')),
            'DNI' => xss_clean($this->input->post('dni')),
            'FechNac' => xss_clean($this->input->post('birthdate')),
            'Telefono' => xss_clean($this->input->post('phone')),
            'Email' => xss_clean($this->input->post('email')),
            'Activo' => 1
        );

        //TABLA USUARIO
        $data['Usuario'] = array(
            'Nombre' => xss_clean($this->input->post('username')),
            'IdPerfil' => xss_clean($this->input->post('perfil_id')),
            'Activo' => $this->input->post('activo'),
            'IdJerarquia' => 1,
            'IdUbigeo' => $this->input->post('ubigeo_id')
        );
        echo '<pre>';
        // print_r ($this->input->post());

        $this->Persona_model->edit($this->input->post('IdPersona'), $data['Persona']);
        $this->Usuario_model->edit($this->input->post('IdUsuario'), $data['Usuario']);

        $this->session->set_flashdata('message', 'Los datos del usuarios han sido modificados correctamente.');
        redirect('admin/usuario_controller', 'refresh');

//        } else {
//            $data['error_message'] = validation_errors();
//        }
//        $data['title'] = "Editar Usuario";
//
//        $data['user'] = $this->Usuario_model->get($idUsuario);
        // echo'<pre>';
//        print_r($data);
// echo'</pre>';
        //$data['main'] = 'admin/admin_seguridad/users/edit'; FALTAAAAAA
        // $this->load->vars($data);
        // $this->load->view('template'); //LEILA REVISA ESTO
    }

    function new_meta($idusuario) {
        /*        $has_access = $this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER_NEW');
          if ($has_access == false ) {
          $this->session->set_flashdata('warning','Usted no tiene permisos para crear usuario');
          redirect('admin/admin_seguridad/users');
          }
         */
        //Parametro para setear reglas del create
        $rules_create = 0;
        $this->set_validation_rules($rules_create);

//        if ($this->form_validation->run()) {
        // print_r($idPersona);
        //TABLA USUARIO
        $data['Meta'] = array(
            'IdMeta' => $this->Meta_model->get_last_idMeta() + 1,
            'IdUsuario' => $idusuario,
            'Monto' => xss_clean($this->input->post('monto')),
            'FechaIni' => xss_clean($this->input->post('fechaini')),
            'FechaFin' => xss_clean($this->input->post('fechafin')),
        );

        //crea el usuario
        $this->Meta_model->create($data['Meta']);

        //mensaje de verificacion
        $this->session->set_flashdata('message', 'La meta ha sido creada correctamente.');

        redirect('admin/usuario_controller/metas_user/' . $idusuario, 'refresh');
//        } else {
//            $data['Persona']['error_message'] = validation_errors();
//            $data['Usuario']['error_message'] = validation_errors();
//        }
//        $data['title'] = "Nuevo Usuario";
//        $data['main'] = 'user_views/create_user_views';
//        $this->load->vars($data);
        //$this->load->view('template');
    }

    public function edit_meta($idmeta = '') {
        //Parametro para setear reglas del edit
        $rules_edit = 1;
        $this->set_validation_rules($rules_edit);
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $idusuario = $this->Meta_model->get_meta_idUsuario($idmeta);
//        if (/* $this->form_validation->run() */true) {
        //TABLA USUARIO
        $data['Meta'] = array(
            'IdMeta' => $idmeta,
            'IdUsuario' => $idusuario,
            'Monto' => xss_clean($this->input->post('monto')),
            'FechaIni' => xss_clean($this->input->post('fechaini')),
            'FechaFin' => xss_clean($this->input->post('fechafin')),
        );

        echo '<pre>';
//        print_r($this->input->post());


        $this->Meta_model->edit($idmeta, $data['Meta']);

        $this->session->set_flashdata('message', 'Los datos del usuarios han sido modificados correctamente.');
        redirect('admin/usuario_controller/metas_user/' . $idusuario, 'refresh');

//        } else {
//            $data['error_message'] = validation_errors();
//        }
//        $data['title'] = "Editar Usuario";
//
//        $data['user'] = $this->Usuario_model->get($idUsuario);
        // echo'<pre>';
//        print_r($data);
// echo'</pre>';
        //$data['main'] = 'admin/admin_seguridad/users/edit'; FALTAAAAAA
        // $this->load->vars($data);
        // $this->load->view('template'); //LEILA REVISA ESTO
    }

    function get_all_profile() {
        $perfiles = array();
        $profiles = $this->Perfil_model->get_all_profile();

        foreach ($profiles as $profile) {
            $perfiles[$profile['IdPerfil']] = $profile['Descripcion'];
        }
        return $perfiles;
    }

    function get_metas_usuario($idusuario) {
        $metas = array();
        $goals = $this->Meta_model->get_metas_usuario($idusuario);

        foreach ($goals as $goal) {
            $metas[$goal['IdMeta']]['IdMeta'] = $goal['IdMeta'];
            $metas[$goal['IdMeta']]['IdUsuario'] = $goal['IdUsuario'];
            $metas[$goal['IdMeta']]['FechaIni'] = $goal['FechaIni'];
            $metas[$goal['IdMeta']]['FechaFin'] = $goal['FechaFin'];
            $metas[$goal['IdMeta']]['Monto'] = $goal['Monto'];
        }
        return $metas;
    }

    function list_all_users() {
        $usuarios = array();
        $users = $this->Usuario_model->get_all_users();

        foreach ($users as $user) {
            $usuarios[$user['IdUsuario']]['IdUsuario'] = $user['IdUsuario'];
            $usuarios[$user['IdUsuario']]['IdPersona'] = $user['IdPersona'];
            $usuarios[$user['IdUsuario']]['Perfil'] = $this->Usuario_model->get_tipo_usuario($user['IdPerfil']);
            $usuarios[$user['IdUsuario']]['Nombre'] = $user['NombrePersona'] . " " . $user['ApePaterno'] . " " . $user['ApeMaterno'];
            $usuarios[$user['IdUsuario']]['Activo'] = ($user['Activo'] == 1) ? 'Activo' : 'Inactivo';
        }
        return $usuarios;
    }

    function get_all_ubigeos() {
        $ubigeos = array();
        $allubigeos = $this->Ubigeo_model->get_all_ubigeos();
        foreach ($allubigeos as $ubigeo) {
            $ubigeos[$ubigeo['IdUbigeo']] = $ubigeo['Pais'] . "-" . $ubigeo['Departamento'] . "-" . $ubigeo['Distrito'];
        }
        return $ubigeos;
    }

}

?>
