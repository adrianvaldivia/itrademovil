<?
class Pais_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'pais';	
    }
	
	function get_all_countries(){		
		$query = $this->db->get('pais');		
        return $query->result();				
	}
}
?>
