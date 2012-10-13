<?php

class Validate extends CI_Controller {

    function Validate() {
        parent::__construct();
        //Cargar los modelos para la base de datos
        $this->load->model('Usuario_model');
    }

    public function index() {
        //cargar las noticias                
        $data['title'] = "Itrade Mantenimientos!!!";
        $data['main'] = "login/login_box.php"; //RUTA			           			
        $this->load->vars($data);
        $this->load->view('login/login');
        //echo "<script languaje='javascript'>alert('Index')</script>";
    }

    public function validate_admin() {
        //validacion de los campos
        $this->form_validation->set_rules('username', 'Username', 'required');
        $this->form_validation->set_rules('password', 'Password', 'required');

        if ($this->form_validation->run()) {
            $u = $this->input->post('username');
            $pw = $this->input->post('password');
            //Verificacion de un unico usuario
            if (!$this->Usuario_model->verify_user($u, $pw)) {
                $this->session->set_flashdata('error', "Por favor verifique sus datos");
                redirect('login', 'refresh');
            }
            //
            $user = $this->Usuario_model->get_by_username($u);
            /* En caso se devuelva un objecto en $query->result();
              foreach($user as $u){
              echo "campo".$u->user;
              }
             */
            $data = array(
                'username' => $user['Nombre'],
                'id_contact' => $user['IdUsuario'],
                'password' => $user['Password'],
                'logged_in' => TRUE,
                'acceso' => $user['IdPerfil'],
                'number' => $user['IdPersona'],
                
            );
            //Encapsula variables de una sesion
            $this->session->set_userdata($data);
            //Redirect RUTA DEL CONTROLADOR  REFRESH
            redirect('home', 'refresh');
        } else {
            $this->session->set_flashdata('error', "Debe ingresar su nombre y su contrase\361a");
            redirect('login', 'refresh');
        }
    }

}

?>
