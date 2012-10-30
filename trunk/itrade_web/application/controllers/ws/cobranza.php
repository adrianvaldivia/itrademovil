<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Cobranza extends CI_Controller {
	
	function __construct()
    {        
        parent::__construct();
		$this->load->model('ws/Product_model');
		$this->load->model('ws/Cliente_model');
		$this->load->library('email');
    }	

	public function index()
	{					
		echo "Controlador Pedido";			
	}	
	public function get_all_products()
	{				
		$result=$this->Product_model->get_all_products();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));	
	}
	public function get_all_categorias()
	{				
		$result=$this->Product_model->get_all_categorias();	
		$this->output->set_content_type('application/json')->set_output(json_encode($result));	
	}
	public function send_notifications($idcobrador){
		$clientes=$this->Cliente_model->get_clients_by_idvendedor($idcobrador);
		$cantidadClientes=count($clientes);
		if (count($clientes)>0){
			$count=0;
			foreach($clientes as $cliente){
				if ($this->sendmail($cliente)=="OK"){
					$count++;
				}
			}
			if ($count==$cantidadClientes){
				$this->output->set_content_type('application/json')->set_output(json_encode(1));			
			}else{
				$this->output->set_content_type('application/json')->set_output(json_encode(0));			
			}										
		}else{
			$this->output->set_content_type('application/json')->set_output(json_encode(0));	
		}
			
	}
	public function sendmail($cliente){		   
        
		$config['protocol']='smtp';        
        $config['smtp_host']='ssl://smtp.googlemail.com';
        $config['smtp_port']='465';
        $config['smtp_timeout']='60';
        $config['smtp_user']='itradedp2@gmail.com';
        $config['smtp_pass']='depresion2';
        $config['charset']='utf-8';
        $config['newline']="\r\n";
        $config['mailtype'] = "html";
		
		/*
		$config['protocol']='smtp';
        $config['mailpath'] = '/usr/sbin/sendmail';
        $config['smtp_host']='mail.aniak.org';
        $config['smtp_port']='587';
        $config['smtp_timeout']='60';
        $config['smtp_user']='pacifico@aniak.org';
        $config['smtp_pass']='k?xbHJbH';
        $config['charset']='utf-8';
        $config['newline']="\r\n";
        $config['mailtype'] = "html";
		*/
        $this->email->initialize($config);
        $this->email->from('itradedp2@gmail.com', 'Itrade');
		echo "CLIENTE MAIL: ".$cliente->Email;
        $this->email->to($cliente->Email);
        $this->email->subject('Itrade Notificacion');
/*        $this->email->message("Estimado(a) ".$data['apepat']." ".$data['apemat'].",  ".$data['nombre']."
                                <br>
                                <br> Su contrase&ntilde; ha sido restaurado, puede ingresar desde nuestra página con los siguientes datos:
                                <br>Usuario: ".$data['usuarioWeb']."
                                <br>Contrase&ntilde;: ".$data['Pwd']."
                                <br>
                                <br><a href='".base_url()."' target='_blank' >www.precisa.com</a>
                                ");
*/
		$this->email->message("Mi prueba");
								
        if ($this->email->send()){
			echo $this->email->print_debugger();
            return "OK";
        }else{
			echo $this->email->print_debugger();
            return "error";
        }
	}
}

