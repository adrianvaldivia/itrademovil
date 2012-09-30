<?php
class Principal extends CI_Controller {

    function Principal(){
        parent::__construct();
        //Cargar los modelos para la base de datos
        //$this->load->model('Noticias_model');       
    }
	public function index()
	{
        //cargar las noticias                
        $data['title']="Itrade Mantenimientos!!!";             
        $data['main']="pages/index.php";//RUTA			           			
        $this->load->vars($data);
        $this->load->view('template');
        //echo "<script languaje='javascript'>alert('Index')</script>";
	}
}
?>