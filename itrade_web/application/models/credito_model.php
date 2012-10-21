<?

class Credito_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'Linea_Credito';
        $this->tablename2 = 'Cliente';
        $this->tablename3 = 'Persona';
    }

    function get_all_creditos() {
        $this->db->select($this->tablename . ".IdLinea, $this->tablename.MontoSolicitado,$this->tablename.MontoActual	, $this->tablename.MontoAprobado, $this->tablename.IdCliente, $this->tablename.Activo");
        $this->db->from($this->tablename);
        $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente', 'right');
        $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona', 'left');
        $query = $this->db->get();
        $this->db->close();
        return $query->result_array();
    }

    function get($idcredito) {
        $this->db->select($this->tablename . ".IdLinea, $this->tablename.MontoSolicitado,$this->tablename.MontoActual	, $this->tablename.MontoAprobado, $this->tablename.IdCliente, $this->tablename.Activo");
        $this->db->from($this->tablename);
        $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente', 'right');
        $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona', 'left');
        $this->db->where($this->tablename . ".IdLinea", $idcredito);
        $query = $this->db->get();
        $this->db->close();
        return $query->row(0);
    }

    function accept($idcredito, $credito) {
        $query = "UPDATE Linea_Credito SET MontoAprobado=$credito->MontoSolicitado,Activo=1 WHERE IdLinea=$idcredito";
        $this->db->query($query);
    }

    function reject($idcredito) {
        $query = "UPDATE Linea_Credito SET Activo=2 WHERE IdLinea=$idcredito";
        $this->db->query($query);
    }

    function get_last_IdCredito() {
        $this->db->select_max('IdCredito');
        $query = $this->db->get($this->tablename);
        $this->db->close();
        return $query->row(0)->IdCredito;
    }

    function get_montosolicitado_cliente($idcredito) {
        $this->db->select($this->tablename . ".MontoSolicitado");
        $this->db->from($this->tablename);
        $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente', 'right');
        $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona', 'left');
        $this->db->where($this->tablename . ".IdLinea", $idcredito);

        $query = $this->db->get();
        $this->db->close();
        return $query->row(0)->MontoSolicitado;
    }

    public function get_cliente_idCredito($idcredito) {
        $this->db->select($this->tablename2 . ".IdCliente, $this->tablename2.IdPersona,$this->tablename2.Razon_Social, $this->tablename2.RUC, $this->tablename2.IdCliente, $this->tablename2.Activo");
        $this->db->from($this->tablename2);
        $this->db->join($this->tablename, $this->tablename . '.IdCliente=' . $this->tablename2 . '.IdCliente', 'right');
        $this->db->join($this->tablename3, $this->tablename2 . '.IdPersona=' . $this->tablename3 . '.IdPersona', 'left');
        $this->db->where($this->tablename . ".IdLinea", $idcredito);

        $query = $this->db->get();
        $this->db->close();
        return $query->row(0);
    }

}

?>
