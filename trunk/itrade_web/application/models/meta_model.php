<?

class Meta_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'Meta';
        $this->tablename2 = 'Usuario';
		 $this->tablename3 = 'PeriodoMeta';
    }

    function get_all_metas() {
        $this->db->select($this->tablename . ".IdUsuario, $this->tablename .IdPerfil,$this->tablename .IdPersona, $this->tablename2 .ApePaterno, $this->tablename2 .ApeMaterno, $this->tablename2 .Nombre NombrePersona, $this->tablename .Activo, $this->tablename2 .Nombre NombreUsuario");
        $this->db->from($this->tablename);
        $this->db->join($this->tablename2, $this->tablename2 . '.IdPersona=' . $this->tablename . '.IdPersona');
        $query = $this->db->get();
        $this->db->close();
        return $query->result_array();
    }
    
    function get_all_periodos() {
        $this->db->select('Descripcion');
        $this->db->from($this->tablename3);
        $query = $this->db->get();
        $this->db->close();
        return $query->result_array();
    }

    function get($idusuario,$idperiodo) {
    		
        $this->db->where(array('IdPeriodo'=>$idperiodo,'IdUsuario'=> $idusuario));
        $query = $this->db->get($this->tablename);
        $rows = $query->result();
        $this->db->close();
        return $rows[0];
    }

    function create($data) {
        return $this->db->insert($this->tablename, $data);
    }

    function edit($idusuario,$idperiodo,$data) {
      $this->db->where(array('IdPeriodo'=>$idperiodo,'IdUsuario'=> $idusuario));
    	return $this->db->update($this->tablename, $data);

    }

    function get_last_idMeta() {
        $this->db->select_max('IdMeta');
        $query = $this->db->get($this->tablename);
        $this->db->close();
        return $query->row(0)->IdMeta;
    }

    function get_meta_idUsuario($idmeta) {
        $this->db->select($this->tablename . ".IdUsuario");
        $this->db->where('IdMeta', $idmeta);
        $query = $this->db->get($this->tablename);
        $this->db->close();
        return $query->row(0)->IdUsuario;
    }

    public function get_metas_usuario($idusuario) {
        $this->db->select($this->tablename3 . ".Descripcion Periodo, $this->tablename3.FechaIni FechaIni, $this->tablename3.IdPeriodo IdPeriodo, $this->tablename3.FechaFin FechaFin, $this->tablename .Monto Monto,$this->tablename .IdUsuario IdUsuario");
        $this->db->from($this->tablename3);
        $this->db->where($this->tablename . ".IdUsuario",$idusuario);
        $this->db->join($this->tablename, $this->tablename . '.IdPeriodo=' . $this->tablename3 . '.IdPeriodo');
        $this->db->join($this->tablename2, $this->tablename2 . '.IdUsuario=' . $this->tablename . '.IdUsuario');
        $query = $this->db->get();
        $this->db->close();
        return $query->result_array();
    }
    

	function get_periodo_by_user($idusuario){
	 $this->db->select($this->tablename3 . ".IdPeriodo IdPeriodo");
        $this->db->from($this->tablename3);
        $this->db->where($this->tablename . ".IdUsuario",$idusuario);
        $this->db->join($this->tablename, $this->tablename . '.IdPeriodo=' . $this->tablename3 . '.IdPeriodo');
        $this->db->join($this->tablename2, $this->tablename2 . '.IdUsuario=' . $this->tablename . '.IdUsuario');
        $query = $this->db->get();
        //print_r($this->db->last_query());
       // exit;
        $this->db->close();
        return $query->result_array();
	}
}

?>
