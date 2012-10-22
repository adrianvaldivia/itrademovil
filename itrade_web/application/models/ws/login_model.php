<?
class Login_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_usuario = 'Usuario';
		$this->table_persona = 'Persona';
    }	
	function get_all_contacts(){		
		$query = $this->db->get($this->tablename);		
        return $query->result();				
	}
	function get_by_id($id){		
		$this->db->where('id_contact', $id);		
		$query = $this->db->get($this->tablename);			
        return $query->result();				
	}	
	function get_by_username($username,$password){			
		/*Relacion entre las tablas*/		
		$this->db->select($this->table_usuario.".Nombre as Username, ".$this->table_persona.".Nombre , ".$this->table_persona.".ApePaterno, ".$this->table_persona.".ApeMaterno, ".$this->table_usuario.".IdPerfil");
		$this->db->from($this->table_persona);
		$this->db->join($this->table_usuario,$this->table_usuario.".IdPersona =".$this->table_persona.".IdPersona");
		/*hace el match con los parametros*/
		$this->db->where($this->table_usuario.".Nombre", $username);
		$this->db->where($this->table_usuario.".Password", do_hash(xss_clean($password)));		
		$query = $this->db->get();
		//echo $this->db->last_query();        
		
		return $query->result();			
	}
	function get_by_username_wb($username){		
		$this->db->where('user', $username);
		$query = $this->db->get($this->tablename);			
        return $query->result();				
	}	
	public function verify_user($username, $password)
	{
   		$this->db->where('user', $username);
		$this->db->where('password', do_hash(xss_clean($password)));		
		$query = $this->db->get($this->tablename);
		//echo $db1->last_query();
		if($query->num_rows != 1)
		{
			return FALSE;
		}
		return TRUE;
	}
	
	function change_password($username,$oldpass,$newpass){						
		//verificar el usuario
		if ($this->verificaUsuario($username,$oldpass)){
			//cambiar password			
			$this->db->set('Password', $newpass);							
			$this->db->where('Nombre', $username);
			$this->db->update($this->table_usuario);					
			//retornar usuario
			return $this->get_by_username($username,$newpass);
		}else{
			return array();
		}							
	}
	
	function verificaUsuario($username,$oldpass){
		$usuario=$this->get_by_username($username,$oldpass);
		if (count($usuario)>0){
			return true;
		}
		return false;		
	}	

    /*
    function get_last_ten_entries()
    {
        $query = $this->db->get('entries', 10);
        return $query->result();
    }

    function insert_entry()
    {
        $this->title   = $_POST['title']; // please read the below note
        $this->content = $_POST['content'];
        $this->date    = time();

        $this->db->insert('entries', $this);
    }

    function update_entry()
    {
        $this->title   = $_POST['title'];
        $this->content = $_POST['content'];
        $this->date    = time();

        $this->db->update('entries', $this, array('id' => $_POST['id']));
    }
	*/
}
?>
