<?
class Departamento_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'departamento';	
    }
	
	function get_all_departments(){		
		$query = $this->db->get($this->tablename);
        return $query->result();				
	}
}
?>
