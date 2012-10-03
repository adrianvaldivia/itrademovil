<div id="contenido_principal" style="width:100%; height:100%;" >	
    <div class="barra_lateral" style="width:30%; height:100%; min-width:350px; max-width:350px; float:left;">
    </div>
    <div class="contenido_principal" style="width:70%; height:100%;  min-width:650px; max-width:650px;">
        <? if($acceso==1) 
			echo anchor('admin/usuario_controller', 'Nuevo Usuario', array('title' => 'Nuevo Usuario')); 
			echo "<br />";
			echo anchor('admin/usuario_controller/modificar', 'Modificar Usuario', array('title' => 'Modificar Usuario')); 
		?>
		
    </div>
</div>