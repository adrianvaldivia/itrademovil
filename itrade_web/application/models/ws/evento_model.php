<?
class Evento_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_evento = 'Evento';		
		$this->table_evento_persona = 'PersonaXEvento';	
		$this->table_usuario='Usuario';
		$this->table_persona='Persona';
    }		
	public function registrar_evento($idcreador,$descripcion,$fecha,$horaini,$horafin,$lugar,$asunto){
		$this->db->flush_cache();
		//$idpersona=$this->get_last_idPersona();
		$data=array("IdCreador"=>$idcreador,"Descripcion"=>$descripcion,"Fecha"=>$fecha,"HoraInicio"=>$horaini,"HoraFin"=>$horafin,"Lugar"=>$lugar,"Asunto"=>$asunto);
		$this->db->insert($this->table_evento, $data);	
		$id=$this->get_last_idEvento();
		if ($id!=0){			
			return $id;
		}else{
			return  0;
		}	
	}
	
	public function registrar_invitados($id,$invitados){
		$this->db->flush_cache();
		if ($invitados!=0){
			//echo "INTIVADOS=".$invitados."<br>";
			$idsusuarios=explode("-", $invitados);
			//var_dump($idsusuarios);
			$count=0;
			for($i=0;$i<count($idsusuarios);$i++){
				$this->db->flush_cache();
				$data=array("IdEvento"=>$id,"IdPersona"=>$idsusuarios[$i],"Asistir"=>1);
				$this->db->insert($this->table_evento_persona, $data);
				$count++;
			}
			return $count;
		}
		return 0;				
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
	public function get_eventos_idcreador($idusuario){
		$this->db->from($this->table_evento);		
		//$str="( month(".$this->table_evento.".Fecha) = '11' )" ;		
		$this->db->where($this->table_evento.".IdCreador", $idusuario);			
		$query = $this->db->get();		
		return $query->result();
	}
	public function get_eventos_por_dia($idusuario,$fecha){		
		$this->db->from($this->table_evento);
		$this->db->where($this->table_evento.".IdCreador", $idusuario);	
		$this->db->where($this->table_evento.".Fecha", $fecha);
		$query = $this->db->get();
		//echo $this->db->last_query();
		return $query->result();
	}
	
	public function contactos_por_evento($ideventos){
		$eventos=explode("-", $ideventos);		
		//Datos		
		$this->db->select($this->table_evento_persona.".IdEvento");
		$this->db->select($this->table_persona.".IdPersona");
		$this->db->select($this->table_usuario.".IdUsuario");
		$this->db->select($this->table_persona.".Nombre");
		$this->db->select($this->table_persona.".ApePaterno");
		$this->db->select($this->table_persona.".ApeMaterno");
		$this->db->select($this->table_persona.".Activo");
		$this->db->select($this->table_persona.".Telefono");
		$this->db->select($this->table_persona.".Email");
		$this->db->select($this->table_usuario.".IdJerarquia");		
		//Joins
		$this->db->from($this->table_persona);								
		$this->db->join($this->table_usuario,$this->table_persona.".IdPersona =".$this->table_usuario.".IdPersona");
		$this->db->join($this->table_evento_persona,$this->table_evento_persona.".IdPersona =".$this->table_usuario.".IdUsuario");							
		$this->db->where_in($this->table_evento_persona.'.IdEvento', $eventos);		
		//$this->db->group_by($this->table_persona.".IdPersona");				
		$query = $this->db->get();   
		//echo $this->db->last_query();		
		return $query->result();		
	}
}
?>
