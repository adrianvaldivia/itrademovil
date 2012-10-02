<?php


function get_last_insert_id($table_name) {
	$ci=& get_instance();
        $ci->load->database(); 
	$query = $this->db->query('select last_insert_id() from '.$table_name);
	$row = $query->row_array();
	return $row['last_insert_id()'];

}

?>
