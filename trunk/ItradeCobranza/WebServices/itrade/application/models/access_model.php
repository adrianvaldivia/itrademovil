<?
class Access_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_contact = 'contacts';
		$this->table_access = 'accesos';
		$this->table_accesos_contacts = 'accessos_contactos';
    }	
	
	function get_access_by_id($iduser){
		/*
		SELECT accesos.descripcion, contacts.id_contact
		FROM accesos 
		LEFT JOIN (accessos_contactos, contacts)
		ON (accesos.id_access=accessos_contactos.id_access AND accessos_contactos.id_contact=contacts.id_contact)
		*/
		$this->db->select($this->table_access.".descripcion, ".$this->table_contact.".id_contact");
		$this->db->from($this->table_accesos_contacts);
		$this->db->join($this->table_access,$this->table_accesos_contacts.".id_access =".$this->table_access.".id_access");
		$this->db->join($this->table_contact,$this->table_accesos_contacts.".id_contact =".$this->table_contact.".id_contact");		
		$this->db->where($this->table_contact.".id_contact", $iduser);		
		$query = $this->db->get();			
        return $query->result();
	}
	function get_by_username_wb($username){		
		$this->db->where('user', $username);
		$query = $this->db->get($this->tablename);			
        return $query->result();				
	}	
	public function verify_user($username, $password)
	{
   		$this->db->where('user', $username);
		$this->db->where('password', do_hash(xss_clean($password)));		
		$query = $this->db->get($this->tablename);
		//echo $db1->last_query();
		if($query->num_rows != 1)
		{
			return FALSE;
		}
		return TRUE;
	}
	
	
    /*
    function get_last_ten_entries()
    {
        $query = $this->db->get('entries', 10);
        return $query->result();
    }

    function insert_entry()
    {
        $this->title   = $_POST['title']; // please read the below note
        $this->content = $_POST['content'];
        $this->date    = time();

        $this->db->insert('entries', $this);
    }

    function update_entry()
    {
        $this->title   = $_POST['title'];
        $this->content = $_POST['content'];
        $this->date    = time();

        $this->db->update('entries', $this, array('id' => $_POST['id']));
    }
	*/
}
?>
