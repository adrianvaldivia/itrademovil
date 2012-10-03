<?
class Zona_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'zona';	
    }
	
	function get_all_zones(){		
        $query = $this->db->get($this->tablename);
        return $query->result();				
	}
}
?>
