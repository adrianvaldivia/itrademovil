

    <div class="barra_lateral" style="width:30%; height:100%; min-width:350px; max-width:350px; float:left;">
	 <? if($acceso==1) {
			echo anchor('admin/usuario_controller/', 'Administrar Usuarios', array('title' => 'Administrar Usuarios')); 
			echo "<br />";
			
			echo anchor('admin/credito_controller/', 'Administrar Credito', array('title' => 'Administrar Credito')); 
			echo "<br />";
			
         }
	?>
    </div>
