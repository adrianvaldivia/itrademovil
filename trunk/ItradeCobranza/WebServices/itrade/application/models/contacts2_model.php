<?
class Contacts2_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();
		$this->tablename = 'contacts';	
    }
	
	function get_all_contacts(){		
		$query = $this->db->get('contacts');		
        return $query->result();				
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
