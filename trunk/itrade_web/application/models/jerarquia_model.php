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

    function get($idJerarquia) {
        $this->db->where('IdJerarquia', $idJerarquia);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
    }

    function edit($idJerarquia, $data) {
        $this->db->where('IdJerarquia', $idJerarquia);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_description($description) {
        $this->db->where('Descripcion', $description);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
}

?>
