<?
class Distrito_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'distrito';	
    }
	
	function get_all_districts(){		
        $query = $this->db->get($this->tablename);
        return $query->result();				
	}
}
?>
