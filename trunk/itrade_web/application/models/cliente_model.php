<?
class Cliente_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'cliente';	
    }
	
	function get_all_clients(){		
		$query = $this->db->get('cliente');		
        return $query->result();				
	}
}
?>
