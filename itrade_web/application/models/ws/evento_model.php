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
	// or month(".$this->table_evento.".Fecha) = '". $month+1 ."' )";		
	public function get_eventos_idusuario_month($idusuario,$year,$month){			
		$this->db->from($this->table_evento_persona);
		$this->db->join($this->table_evento,$this->table_evento.".IdEvento =".$this->table_evento_persona.".IdEvento");
		//$str="( month(".$this->table_evento.".Fecha) = '11' )" ;
		$month2=$month+1;		
		$year2=$year;
		if($month==12){
			$month2=1;
			$year2=$year+1;
		}
		$str="( ( ( month(".$this->table_evento.".Fecha) = '".$month."' ) and ( year(".$this->table_evento.".Fecha) = '".$year."'  ) )
		or ( ( month(".$this->table_evento.".Fecha) = '".$month2."' ) and ( year(".$this->table_evento.".Fecha) = '".$year2."'  ) ) )" ;				 
		$this->db->where($str);
		$this->db->where($this->table_evento_persona.".IdPersona", $idusuario);	
		$this->db->where($this->table_evento_persona.".Asistir", '1');	
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();
	}
}
?>
