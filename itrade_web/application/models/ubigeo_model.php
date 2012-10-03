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
	
    function get($idUbigeo) {
        $this->db->where('IdUbigeo', $idUbigeo);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
    }

    function edit($idUbigeo, $data) {
        $this->db->where('IdUbigeo', $idUbigeo);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_zone($zone) {
		$this->db->distinct();
        $this->db->where('Zona', $zone);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
	function get_by_district($district) {
		$this->db->distinct();
        $this->db->where('Distrito', $district);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
	function get_by_department($department) {
		$this->db->distinct();
        $this->db->where('Departamento', $department);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
	function get_by_country($country) {
		$this->db->distinct();
        $this->db->where('Pais', $country);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
}
?>
