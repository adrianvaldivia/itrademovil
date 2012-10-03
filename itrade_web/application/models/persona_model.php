<?
class Persona_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'persona';	
    }
	
	function get_all_people(){		
        $query = $this->db->get($this->tablename);
        return $query->result();				
	}
	
    function get($idPersona) {
        $this->db->where('IdPersona', $idPersona);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
    }

    function edit($idPersona, $data) {
        $this->db->where('IdPersona', $idPersona);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_name($name) {
        $this->db->where('Nombre', $name);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
	function get_by_firstLastName($firstLastName) {
        $this->db->where('ApePaterno', $firstLastName);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
	
	function get_by_secondLastName($secondLastName) {
        $this->db->where('ApeMaterno', $secondLastName);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }    

	function get_by_dni($dni) {
        $this->db->where('DNI', $dni);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }    
	
}
?>
