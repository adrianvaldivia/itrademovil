<?
class Perfil_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'perfil';	
    }
	
	function get_all_profile(){		
		$query = $this->db->get('perfil');		
        return $query->result();				
	}
}
?>
