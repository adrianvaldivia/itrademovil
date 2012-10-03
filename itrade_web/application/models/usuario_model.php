<?
class Usuario_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'usuario';
    }
	
	function get_all_users(){		
		$query = $this->db->get('usuario');		
        return $query->result();				
	}
	
	function get($idUsuario) {
		$this->db->select();
		$this->db->where('IdUsuario', $idUsuario);		
		$query = $this->db->get($this->tablename);		
		$rows = $query->result();
		return $rows[0];		
	}
	
	function edit($idUsuario, $data)
	{		
		$this->db->where('IdUsuario', $idUsuario);	
		return $this->db->update($this->tablename, $data);
	}
}
?>
