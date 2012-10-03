<?
class Ubigeo_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'ubigeo';	
    }
	
	function get_all_ubigeos(){		
		$query = $this->db->get('ubigeo');		
        return $query->result();				
	}
}
?>
