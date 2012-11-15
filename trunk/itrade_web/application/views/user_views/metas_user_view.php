<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <!-- Meta -->
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"> 
        <!-- End of Meta -->

        <title><?php echo $title; ?></title>   
<link rel="icon" type="image/ico" href="<?php echo base_url() ?>images/logicon.png"/>
            <!--styleshets CSS
            <link href="<?php echo base_url() ?>css/login.css" rel="stylesheet" />	
            <link href="<?php echo base_url() ?>css/login_box.css" rel="stylesheet" />-->
			<link href="<?php echo base_url() ?>css/style.css" rel="stylesheet" type="text/css" media="all" />
            <link href="<?php echo base_url() ?>css/styles.css" rel="stylesheet" />
            <link href="<?php echo base_url() ?>css/1140.css" rel="stylesheet" />	
			<link href="<?php echo base_url() ?>css/user_create.css" rel="stylesheet" />	
			<link href="<?php echo base_url() ?>css/uniform.default.css" rel="stylesheet" />	
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />

        <!--scripts js-->
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.2.3.pack.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.min.js"></script>
        <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>

        <script type="text/javascript">
            //<![CDATA[
            base_url = '<?php echo base_url(); ?>';
            //]]>
        </script>
    </head>
    <body>
        <script type="text/javascript">
            $(function() {
                $( "#birthdate" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
            });
        </script>
        <div id="header-wrapper" class="container"> 
            <div id="user-options" class="row"> 
            	<div class="threecol">
            		<a href="<? echo base_url() ?>home">
            			<img src="<?php echo base_url() ?>images/logo.png"/>
            		</a>
            </div> 
            	
                                                        
            <div id="user-account" class="row"> 
						<div class="ninecol last"> 
							<a href="<? echo base_url() ?>home/logout">Logout</a> 
							<?
               if ($this->session->userdata('logged_in')) {
						if ((isset($username) && isset($name))) {
					?><span>|</span> 
							<span>Bienvenido, <strong><? echo trim($username); ?></strong></span> 
							<? } ?> 
   	         <? } ?> 
						</div> 
   	       </div>
             <div class="ninecol last fixed"></div> 
          </div>
        </div>  
		
		<div class="container"> <!--1-->
         	<div class="row">  <!--2-->
				<div id="content" class="ninecol last">   <!--4-->
               	  <div class="accion"></div>
				<h2><?= $title ?></h2>
				<div class="panel-wrapper"> <!--5-->
               		<div class="panel"> <!--6-->

						  
				  
                <? if (count($metas) == 0) { ?>
                   
               			<div class="title"><!--7--> <h4><span>El usuario seleccionado no tiene metas.   </span><? echo anchor('admin/usuario_controller/create_meta/' . $idusuario, 'Nueva Meta', array('title' => 'Nueva Meta')); ?></h4> 
                        	<!--<div class="collapse">collapse</div>8--> 
                        </div><!--7--> 
                <? } else {
                    ?>

                			<div class="title"><!--7--> <h4><? echo anchor('admin/usuario_controller/create_meta/' . $idusuario, 'Nueva Meta', array('title' => 'Nueva Meta')); ?></h4> 
                        	<!--<div class="collapse">collapse</div>8--> 
                        </div><!--7--> 

				  
                        <div class="content"> <!--9-->
                           	<table id="sample-table" class=""> 
                              	<thead>
								<tr>
									<th>Periodo</th>
									<th>Fecha Inicio</th>
									<th>Fecha Fin</th>
									<th>Monto</th>
									<th>Acciones</th>
								</tr>
								</thead>
                              	<tbody> 
								
								 <?
                            
                            foreach ($metas as $meta) {
                                ?>
                                <tr>
                                    <td>
                                        <div class="nombre"><? echo $meta['Periodo']; ?></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><? echo $meta['FechaIni']; ?></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><? echo $meta['FechaFin']; ?></div>
                                    </td>
                                    <td>
                                        <div class="estado"><? echo $meta['Monto']; ?></div>
                                    </td>
                                    <td><a class="icon" href="<?php echo base_url() ?>admin/usuario_controller/modificar_meta/<?php echo $idusuario.'/'.$meta['IdPeriodo']; ?>"><img class="editar" alt="Editar Meta"  title="Editar Meta" src="<?php echo base_url() ?>/images/icon-edit.png"></a>

                                    </td>
                                </tr>
                                <?
                            }
                            ?>
							
				
                						</tbody> 
			                    </table>  
                        </div> <!--9-->
						
                <? }
                ?>

                      </div><!--6-->  
                      <div class="shadow"></div> <!--10-->
					  
					</div><!--5-->
                				  <input type="button" value="Regresar" class="button-blue" onclick="window.location='<?= base_url() ?>admin/usuario_controller/'"/>

               </div><!--4-->
        	</div><!--2-->                                                      
		</div><!--1-->
			
			
       

    </body>
</html>
