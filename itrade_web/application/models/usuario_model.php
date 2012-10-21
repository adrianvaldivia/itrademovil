<?

class Usuario_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'Usuario';
        $this->table_perfil = 'Perfil';
        $this->tablename2 = 'Persona';
    }

    function get_all_users() {
        $this->db->select($this->tablename . ".IdUsuario, $this->tablename .IdPerfil,$this->tablename .IdPersona, $this->tablename2 .ApePaterno, $this->tablename2 .ApeMaterno, $this->tablename2 .Nombre NombrePersona, $this->tablename .Activo, $this->tablename2 .Nombre NombreUsuario");
        $this->db->from($this->tablename);
        $this->db->join($this->tablename2, $this->tablename2 . '.IdPersona=' . $this->tablename . '.IdPersona');
        $query = $this->db->get();
        $this->db->close();
        return $query->result_array();
    }

    function get($idUsuario) {
        $this->db->where('IdUsuario', $idUsuario);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        $this->db->close();
        return $rows[0];
    }

    function get_last_idUsuario() {
        $this->db->select_max('IdUsuario');
        $query = $this->db->get($this->tablename);
        $this->db->close();
        return $query->row(0)->IdUsuario;
    }

    function create($data) {
        return $this->db->insert($this->tablename, $data);
    }

    function edit($idUsuario, $data) {
        $this->db->where('IdUsuario', $idUsuario);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_username($username) {
        $this->db->where('Nombre', $username);
        $query = $this->db->get($this->tablename);
        $this->db->close();
        return $query->row_array();
    }

    public function verify_user($username, $password) {

        $this->db->where('Nombre', $username);
        $this->db->where('Password', do_hash(xss_clean($password)));
        $query = $this->db->get($this->tablename);
        $this->db->close();
        if ($query->num_rows != 1) {
            return FALSE;
        }
        return TRUE;
    }

    function get_tipo_usuario($IdPerfil) {
        $this->db->select($this->table_perfil . ".Descripcion");
        $this->db->where($this->tablename . '.IdPerfil', $IdPerfil);
        $this->db->from($this->tablename);
        $this->db->join($this->table_perfil, $this->table_perfil . '.IdPerfil=' . $this->tablename . '.IdPerfil');
        $query = $this->db->get();
        $this->db->close();
        return $query->row(0)->Descripcion;
    }

}

?>
