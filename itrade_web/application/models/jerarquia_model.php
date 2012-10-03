<?
class Jerarquia_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'jerarquia';	
    }
	
	function get_all_hierarchies(){		
		$query = $this->db->get('jerarquia');		
        return $query->result();				
	}
}

?>
