<?

class Usuario_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'usuario';
    }

    function get_all_users() {
        $query = $this->db->get($this->tablename);
        return $query->result();
    }

    function get($idUsuario) {
        $this->db->where('IdUsuario', $idUsuario);
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        return $rows[0];
		
    }
	
	function create($data)
	{
		$this->db->insert($this->tablename, $data);
		$query = $this->db->query('select last_insert_id()');
		$row = $query->row_array();
		return $row['last_insert_id()'];
	}

    function edit($idUsuario, $data) {
        $this->db->where('IdUsuario', $idUsuario);
        return $this->db->update($this->tablename, $data);
    }

    function get_by_username($username) {
        $this->db->where('Nombre', $username);
        $query = $this->db->get($this->tablename);
        return $query->row_array();
    }

    public function verify_user($username, $password) {
        $this->db->where('Nombre', $username);
        $this->db->where('Password', do_hash(xss_clean($password)));
        $query = $this->db->get($this->tablename);

        if ($query->num_rows != 1) {
            return FALSE;
        }
        return TRUE;
    }

}

?>
