<?
class Evento_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_evento = 'Evento';		
		$this->table_evento_persona = 'PersonaXEvento';	
    }		
	public function registrar_evento($idcreador,$descripcion,$fecha,$horaini,$horafin){
		$this->db->flush_cache();
		//$idpersona=$this->get_last_idPersona();
		$data=array("IdCreador"=>$idcreador,"Descripcion"=>$descripcion,"Fecha"=>$fecha,"HoraInicio"=>$horaini,"HoraFin"=>$horafin);
		$this->db->insert($this->table_evento, $data);	
		return $this->get_last_idEvento();
		//return $this->db->insert_id($this->table_persona, $data);
	}
	
	public function get_last_idEvento(){
		$this->db->flush_cache();
        $this->db->select_max('IdEvento');
        $query = $this->db->get($this->table_evento);
        return $query->row(0)->IdEvento;
    }	
	public function get_eventos_idusuario_month($idusuario,$month){
		
		$this->db->from($this->table_evento_persona);
		$this->db->join($this->table_evento,$this->table_evento.".IdPersona =".$this->table_cliente.".IdCliente");
		$str="month(".$this->table_evento.".FechaPedido) = ".$month;		
		$this->db->where($str);//UBIGEO			
		$query = $this->db->get($this->table_evento);
		return $query->result();
	}
}
?>
