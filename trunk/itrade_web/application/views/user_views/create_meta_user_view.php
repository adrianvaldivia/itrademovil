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
		<script src="<?php echo base_url() ?>js/jquery.validate.js" type="text/javascript"></script>
		<!-- <script src="<?php echo base_url() ?>js/css3-mediaqueries.js" type="text/javascript"></script> -->
		<script src="<?php echo base_url() ?>js/custom.js" type="text/javascript"></script>
		<!-- <script src="<?php echo base_url() ?>js/nicEdit.js" type="text/javascript"></script> -->
		<script src="<?php echo base_url() ?>js/jquery.uniform.min.js" type="text/javascript"></script>
		
        <script type="text/javascript">
            //<![CDATA[
            base_url = '<?php echo base_url(); ?>';
            //]]>
        </script>
    </head>
    <body>
        <script type="text/javascript">
            $(function() {
                $( "#fechaini" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
                $( "#fechafin" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
                $("#userForm").validate();
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
               	 <h2><?= $title ?></h2>

               <?= form_open("admin/usuario_controller/new_meta/" . $idusuario, array('id' => 'userForm')); ?>
	               <fieldset class="left">
					
                    <p>
             
                    		<label>Periodo: <span class="required">(*)</span> </label>
                    		<?= form_dropdown('idperiodo', $periodos) ?>
                    </p>
                    <p>
                        <label>Monto: <span class="required">(*)</span></label>
                        <?php $monto = array('name' => 'monto', 'id' => 'monto', 'size' => 15, 'class' => 'required number', 'title' => 'Por favor ingrese el monto', 'value' =>  set_value('monto')); ?>
                        <?= form_input($monto) ?>
                    </p>


                </fieldset>

   
            <p class="clear">
                    <input type="submit" value="Aceptar" class="button-blue"/>
                    <input type="button" value="Cancelar" class="button-blue" onclick="window.location='<?= base_url() ?>admin/usuario_controller/metas_user/<?echo $idusuario?>'"/>
            </p>
            <?= form_close(); ?>
               </div><!--4-->
        		</div><!--2-->                                                      
		</div><!--1-->
		
    </body>
</html>
