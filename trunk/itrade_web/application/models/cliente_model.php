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

	function get($idCliente) {
        $this->db->where('IdCliente', $idCliente);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
    }

    function edit($idCliente, $data) {
        $this->db->where('IdCliente', $idCliente);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_idPersona($idPersona) {
        $this->db->where('IdPersona', $idPersona);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }

	function get_by_razonSocial($razonSocial) {
        $this->db->where('RazonSocial', $razonSocial);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }

	function get_by_ruc($ruc) {
        $this->db->where('RUC', $ruc);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }
    
}
?>
