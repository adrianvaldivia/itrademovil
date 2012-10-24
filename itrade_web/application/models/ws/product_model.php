<?
class Product_model extends CI_Model {

    function __construct()
    {        
        parent::__construct();		
		$this->table_producto = 'Producto';		
		$this->table_categoria = 'Categoria';	
    }	
	
	public function get_all_products(){		
		$this->db->from($this->table_producto);					
		$this->db->where($this->table_producto.".Activo", 1);			
		$query = $this->db->get();
		return $query->result();			
	}		
	public function get_all_categorias(){		
		$this->db->from($this->table_categoria);
		$query = $this->db->get();
		return $query->result();			
	}
}
?>
