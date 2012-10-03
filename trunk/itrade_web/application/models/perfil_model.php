<?

class Perfil_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'perfil';
    }

    function get_all_profile() {
        $this->db->where('Activo', 1);
        $query = $this->db->get($this->tablename);
        return $query->result_array();
    }

    function get($idPerfil) {
        $this->db->where('IdPerfil', $idPerfil);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
    }

    function edit($idPerfil, $data) {
        $this->db->where('IdPerfil', $idPerfil);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_description($description) {
        $this->db->where('Descripcion', $description);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }

}

?>
