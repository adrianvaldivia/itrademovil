<div class="accion"></div>

<div class="panel-wrapper"> <!--5-->
               		<div class="panel"> <!--6-->
               			<!--<div class="title"><!--7--> <!--<h4><? echo anchor('admin/usuario_controller/create_user', 'Nuevo Usuario', array('title' => 'Nuevo Usuario')); ?></h4> 
                        	<!--<div class="collapse">collapse</div>8--> 
                        <!--</div><!--7--> 
                            <?
    if (count($creditos) == 0) {
        ?>
        <? echo "Por el momento no hay solicitudes de credito disponibles."; ?>

    <? } else {
        ?>
                        <div class="content"> <!--9-->
                           	<table id="sample-table" class=""> 
                              	<thead> 
                              		<tr> 
                              			<th>
                        Razon Social
                    </th>
                    <th>
                        Monto Solicitado
                    </th>
                    
                    <th>
                        Monto Aprobado
                    </th>
                    <th>
                        Monto Actual
                    </th>
                    <th>
                        Estado
                    </th>
                    <th>
                        Acciones
                    </th>
                							</tr>
                						</thead> 
                              	<tbody> 
                             <?
                foreach ($creditos as $credito) {
                    $idlinea = $credito['IdLinea'];
                    ?>
                    <tr>
                        <td class="razon">
                            <div class="nombre"><? echo $credito['Cliente']->Razon_Social; ?></div>
                        </td>
                        <td>
                            <div class="monto"><? echo $credito['MontoSolicitado']; ?></div>
                        </td>
                         <td>
                            <div class="monto"><? echo $credito['MontoAprobado']; ?></div>
                        </td>
                        <td>
                            <div class="monto"><? echo $credito['MontoActual']; ?></div>
                        </td>
                       
                        <td>
                            <div class="estado"><? if($credito['Activo'] == 2)echo 'Aprobado';
                                                if($credito['Activo'] == 1) echo 'Por Aprobar';
                                                if($credito['Activo'] == 3) echo 'Rechazado';?>
                            </div>
                        </td>
                        <td class="accion"><? if ($credito['Activo'] == 1) { ?>
                                <div class="accion">
                                <a class="icon" href="<?php echo base_url() ?>admin/credito_controller/accept/<?php echo $idlinea ?>"><img alt="Aprobar" title="Aprobar" src="<?php echo base_url() ?>/images/icon-archive.png"></a>
                               
                                <a class="icon" href="<?php echo base_url() ?>admin/credito_controller/reject/<?php echo  $idlinea ?>"><img alt="Rechazar" title="Rechazar" src="<?php echo base_url() ?>/images/icon-cancel.png"></a></div>
                            <? } else { ?>
                                <div class="accion"><? echo"-"; ?></div>
                            <? } ?>
                        </td>
                    </tr>
                <? } ?>
                						</tbody> 
			                    </table> 
			                      <? }
    ?> 
                        </div> <!--9-->
                      </div><!--6-->  
                      <div class="shadow"></div> <!--10-->
</div><!--5-->
                   
   
