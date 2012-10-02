<?
class Usuario_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'usuario';	
    }
	
	function get_all_users(){		
		$query = $this->db->get('usuario');		
        return $query->result();				
	}
}
?>
