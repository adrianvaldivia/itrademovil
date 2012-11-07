<?

class Credito_model extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->tablename = 'Linea_Credito';
        $this->tablename2 = 'Cliente';
        $this->tablename3 = 'Persona';
    }

    function get_all_creditos() {
		$this->db->flush_cache();
		$query="SELECT `Linea_Credito`.`IdLinea`, `Linea_Credito`.`MontoSolicitado`, `Linea_Credito`.`MontoActual`, `Linea_Credito`.`MontoAprobado`, `Linea_Credito`.`IdCliente`, C2.`IdEstado` FROM `Linea_Credito`,`Cliente` C2,`Persona` WHERE  `C2`.`IdCliente`=`Linea_Credito`.`IdCliente` AND `C2`.`IdPersona`=`Persona`.`IdPersona`";
        // $this->db->select($this->tablename . ".IdLinea, $this->tablename.MontoSolicitado,$this->tablename.MontoActual	, $this->tablename.MontoAprobado, $this->tablename.IdCliente, $this->tablename2.IdEstado");
        // $this->db->from($this->tablename);
        // $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente');
        // $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona');
        $result = $this->db->query($query);
        $this->db->close();
		// print_r($this->db->last_query());
		// exit;
        return $result->result_array();
    }

    function get($idcredito) {
			$query="SELECT `Linea_Credito`.`IdLinea`, `Linea_Credito`.`MontoSolicitado`, `Linea_Credito`.`MontoActual`, `Linea_Credito`.`MontoAprobado`, `Linea_Credito`.`IdCliente`, C2.`IdEstado` FROM `Linea_Credito`,`Cliente` C2,`Persona` WHERE  `C2`.`IdCliente`=`Linea_Credito`.`IdCliente` AND `C2`.`IdPersona`=`Persona`.`IdPersona` AND `Linea_Credito`.`IdLinea` = $idcredito";
        // $this->db->select($this->tablename . ".IdLinea, $this->tablename.MontoSolicitado,$this->tablename.MontoActual	, $this->tablename.MontoAprobado, $this->tablename.IdCliente, $this->tablename2.IdEstado");
        // $this->db->from($this->tablename);
        // $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente');
        // $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona');
        // $this->db->where($this->tablename . ".IdLinea", $idcredito);
         $result = $this->db->query($query);
		 	// print_r($this->db->last_query());
		// exit;
        $this->db->close();
        return $result->row(0);
    }

    function accept($idcredito, $credito) {
    $actual=($credito->MontoActual==null?0:$credito->MontoActual);
        $query = "UPDATE Linea_Credito SET MontoActual=($credito->MontoSolicitado+$actual),MontoAprobado=$credito->MontoSolicitado,Activo=1 WHERE IdLinea=$idcredito";
        $this->db->query($query);
    }

    function reject($idcredito, $credito) {
    $actual=($credito->MontoActual==null?0:$credito->MontoActual);
        $query = "UPDATE Linea_Credito SET Activo=0,MontoAprobado=0, MontoActual=$actual WHERE IdLinea=$idcredito";
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
        $this->db->join($this->tablename2 . " C2", " C2" . '.IdCliente=' . $this->tablename . '.IdCliente');
        $this->db->join($this->tablename3, " C2" . '.IdPersona=' . $this->tablename3 . '.IdPersona');
        $this->db->where($this->tablename . ".IdLinea", $idcredito);

        $query = $this->db->get();
        $this->db->close();
        return $query->row(0)->MontoSolicitado;
    }

    public function get_cliente_idCredito($idcredito) {
		$this->db->flush_cache();
        $this->db->select($this->tablename2 . ".IdCliente, $this->tablename2.IdPersona,$this->tablename2.Razon_Social, $this->tablename2.RUC, $this->tablename2.IdCliente, $this->tablename3.Activo");
        $this->db->from($this->tablename2);		
        $this->db->join($this->tablename, $this->tablename . '.IdCliente=' . $this->tablename2 . '.IdCliente');
        $this->db->join($this->tablename3, $this->tablename2 . '.IdPersona=' . $this->tablename3 . '.IdPersona');
		$this->db->where($this->tablename . ".IdLinea", $idcredito);
        

        $query = $this->db->get();
		// print_r($this->db->last_query());
// exit;
        $this->db->close();
		// print_r($query);
// exit;
		// if($query!=null){
        return $query->row(0);
		 // echo 'si';
		// exit ;
		// }else {
		// return false;
		 // echo 'no';
		// exit;
		// }
    }

}

?>
