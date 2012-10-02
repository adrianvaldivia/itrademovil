<?php

class Home extends CI_Controller {

    function Home() {
        parent::__construct();
        //Cargar los modelos para la base de datos
        //$this->load->model('Noticias_model');
        session_start();
        if (!$this->session->userdata('logged_in')) {
            $this->session->set_flashdata('error', "DEBE HABERSE LOGGEADO.");
            redirect('login', 'refresh');
        }
    }

    public function index() {
        //cargar las noticias              
        $data['title'] = "Itrade Mantenimientos!!!";
        $data['username'] = $this->session->userdata('username');
        $data['name'] = $this->session->userdata('name');
        $data['main'] = "pages/resultado.php"; //Ruta del contenido del home			           			
        $this->load->vars($data);
        $this->load->view('login/login');
        //echo "<script languaje='javascript'>alert('Index')</script>";
    }

    function logout() {
        $this->session->sess_destroy();
        $this->session->unset_userdata('logged_in');
        $this->session->set_flashdata('error', "Usted ha sido deslogueado!");
        redirect('login', 'refresh');
    }

}

?>