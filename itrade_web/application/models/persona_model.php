<?
class Persona_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'persona';	
    }
	
	function get_all_people(){		
		$query = $this->db->get('persona');		
        return $query->result();				
	}
}
?>
