<?php

class Credito_controller extends CI_Controller {

    function Credito_controller() {
        parent::__construct();
        //Cargar los modelos para la base de datos
        $this->load->model('Cliente_model');
        $this->load->model('Persona_model');
        $this->load->model('Credito_model');

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
        $data['title'] = "Creditos";
        $data['main'] = "pages/dashboard_credit_content.php"; //RUTA	
        $data['creditos'] = $this->list_all_creditos();

        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
        $this->load->vars($data);
        $this->load->view('pages/dashboard_credit_view');
    }

    function accept($idcredito = '') {
        /*        $has_access = $this->Rolemodel->has_access($this->session->userdata('role_id'), 'USER_NEW');
          if ($has_access == false ) {
          $this->session->set_flashdata('warning','Usted no tiene permisos para crear usuario');
          redirect('admin/admin_seguridad/users');
          }
         */
        //Parametro para setear reglas del create
//        $rules_edit = 1;
//        $this->set_validation_rules($rules_edit);
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');
//        if ($this->form_validation->run()) {
        $credito = $this->Credito_model->get($idcredito);

        print_r($this->Credito_model->accept($idcredito,$credito));

        //mensaje de verificacion
        $this->session->set_flashdata('message', 'La Linea de Credito ha sido aprobada.');

        redirect('admin/credito_controller', 'refresh');
//        } else {
//            $data['Persona']['error_message'] = validation_errors();
//            $data['Usuario']['error_message'] = validation_errors();
//        }
//        $data['title'] = "Nuevo Usuario";
//        $data['main'] = 'user_views/create_user_views';
//        $this->load->vars($data);
        //$this->load->view('template');
    }

    public function reject($idcredito = '') {
        //Parametro para setear reglas del edit
//        $rules_edit = 1;
//        $this->set_validation_rules($rules_edit);
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['acceso'] = $this->session->userdata('acceso');

//        if (/* $this->form_validation->run() */true) {
        $this->Credito_model->reject($idcredito);

        $this->session->set_flashdata('message', 'La Linea de Credito ha sido rechazada.');
        redirect('admin/credito_controller', 'refresh');

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

        redirect('admin/Credito_controller/metas_user/' . $idusuario, 'refresh');
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
        redirect('admin/Credito_controller/metas_user/' . $idusuario, 'refresh');

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

    function list_all_creditos() {

        $creditos = array();
        $credits = $this->Credito_model->get_all_creditos();

        foreach ($credits as $credit) {
            $creditos[$credit['IdLinea']]['IdLinea'] = $credit['IdLinea'];
            $creditos[$credit['IdLinea']]['Cliente'] = $this->Credito_model->get_cliente_idCredito($credit['IdLinea']);
            $creditos[$credit['IdLinea']]['MontoSolicitado'] = $credit['MontoSolicitado'];
            $creditos[$credit['IdLinea']]['MontoActual'] = $credit['MontoActual'];
            $creditos[$credit['IdLinea']]['MontoAprobado'] = $credit['MontoAprobado'];
            $creditos[$credit['IdLinea']]['IdCliente'] = $credit['IdCliente'];
            $creditos[$credit['IdLinea']]['Activo'] = $credit['Activo'];
        }
        return $creditos;
    }

}

?>
