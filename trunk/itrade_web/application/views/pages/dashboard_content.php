<div class="accion"></div>

<div class="panel-wrapper"> <!--5-->
               		<div class="panel"> <!--6-->
               			<div class="title"><!--7--> <h4><? echo anchor('admin/usuario_controller/create_user', 'Nuevo Usuario', array('title' => 'Nuevo Usuario')); ?></h4> 
                        	<!--<div class="collapse">collapse</div>8--> 
                        </div><!--7--> 
                        <div class="content"> <!--9-->
                           	<table id="sample-table" class=""> 
                              	<thead> 
                              		<tr> 
                              			<th>Nombre</th>
                								<th>Perfil</th>
                								<th>Estado</th>
                								<th>Acciones</th>
                							</tr>
                						</thead> 
                              	<tbody> 
                              	<? foreach ($usuarios as $usuario) {
                							$idusuario = $usuario['IdUsuario'];
                							$idpersona = $usuario['IdPersona'];
                						?>
						               	<tr>
                    							<td class='nombre'>
                        						<div class="nombre"><? echo $usuario['Nombre']; ?></div>
                    							</td>
                    							<td class="perfil">
                        						<div class="perfil"><? echo $usuario['Perfil']; ?></div>
                    							</td >
                    							<td class='estado'>
                        						<div class="estado"><? echo $usuario['Activo']; ?></div>
                    							</td>
                    							<td class='accion'>
                        						<div class="accion">
                        						<a class="icon" href="<?php echo base_url() ?>admin/usuario_controller/modificar/<?php echo $idusuario.'/'.$idpersona; ?>"><img class="editar" alt="Editar Usuario"  title="Editar Usuario" src="<?php echo base_url() ?>/images/icon-edit.png"></a>
                        						<? if ($usuario['Perfil'] == 'VENDEDOR'): ?><a class="icon" href="<?php echo base_url() ?>admin/usuario_controller/metas_user/<?php echo $idusuario ?>"><img class="metas" alt="Metas" title="Metas" src="<?php echo base_url() ?>/images/icon-graph.png"></a>
                        						<? endif; ?>
                        						
                        						
														</div>
                    							</td>
                							</tr>
                							<? } ?> 
                						</tbody> 
			                    </table>  
                        </div> <!--9-->
                      </div><!--6-->  
                      <div class="shadow"></div> <!--10-->
</div><!--5-->
                
<?php $this->load->view("pages/tooltip"); ?> 				
     
