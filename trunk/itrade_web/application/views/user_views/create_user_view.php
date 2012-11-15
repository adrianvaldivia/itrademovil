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
            $(document).ready(function() {
                $( "#birthdate" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
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

            <?= form_open("admin/usuario_controller/create", array('id' => 'userForm')); ?>
			 <fieldset class="left">
                <legend>Datos Generales</legend>


                <p>
                    <label>Nombres: <span class="required">(*)</span> </label>
                    <?php $firstname = array('name' => 'firstname', 'id' => 'firstname', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el nombre', 'value' => set_value('firstname')); ?>
										<?= form_input($firstname) ?>
                </p>
                <p>
                    <label>Apellido Paterno: <span class="required">(*)</span></label>
                    <?php $lastname1 = array('name' => 'lastname1', 'id' => 'lastname', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el primer apellido', 'value' => set_value('lastname1')); ?>
                    <?= form_input($lastname1) ?>
                </p>
                <p>
                    <label>Apellido Materno: <span class="required">(*)</span></label>
                    <?php $lastname2 = array('name' => 'lastname2', 'id' => 'lastname2', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el segundo apellido', 'value' => set_value('lastname2')); ?>
                    <?= form_input($lastname2) ?>
                </p>
                <p>
                    <label>Teléfono: </label>
                   <?php $phone = array('name' => 'phone', 'id' => 'phone', 'size' => 15, 'class' => 'number', 'title' => 'Por favor ingrese un telefono válido', 'maxlength' => '10', 'value' => set_value('phone')); ?>
                    <?= form_input($phone) ?>
                </p>
                <p>
                    <label>Email: <span class="required">(*)</span></label>
                    <?php $email = array('name' => 'email', 'id' => 'm', 'size' => 30, 'class' => 'required email', 'title' => 'Por favor ingrese un email valido.', 'value' => set_value('email')); ?>
                    <?= form_input($email) ?>
                </p
                <p>
                    <label>DNI: <span class="required">(*)</span></label>
                    <?php $dni = array('name' => 'dni', 'id' => 'dni', 'size' => 15, 'class' => 'required number', 'title' => 'Por favor ingrese su DNI', 'maxlength' => '8', 'value' => set_value('dni')); ?>
                    <?= form_input($dni) ?>
                </p>
                <p>
                    <label>Fecha de Nacimiento:</label>
                    <?php $birthdate = array('name' => 'birthdate', 'id' => 'birthdate', 'size' => 15, 'class' => 'required dateISO', 'title' => 'Por favor ingrese una fecha', 'value' => set_value('birthdate')); ?>
                    <?= form_input($birthdate) ?>
                </p>


            </fieldset>
            <fieldset class="left">
                <legend>Cuenta de Usuario</legend>

                <p>
                    <label>Usuario: <span class="required">(*)</span></label>
                    <?php $username = array('name' => 'username', 'id' => 'u', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el nombre de usuario', 'value' => set_value('username')); ?>
                    <?= form_input($username) ?>
                </p>
                <p>
                    <label>Password: <span class="required">(*)</span></label>
                    <?php $password = array('name' => 'password', 'id' => 'password', 'size' => 15, 'class' => 'required', 'minlength' => '5', 'title' => 'Por favor ingrese un password (al menos 5 letras) '); ?>
                    <?= form_password($password) ?>
                </p>
                <p>
                    <label>Repetir Password: <span class="required">(*)</span></label>
                   <?php $passwordrepeat = array('name' => 'passwordrepeat', 'id' => 'pr', 'size' => 15, 'equalTo' => '#password', 'title' => 'Por favor ingrese el mismo valor que el campo password'); ?>
                    <?= form_password($passwordrepeat) ?>
                </p>
                <p>
                    <label>Perfil <span class="required">(*)</span></label>
                    <?= form_dropdown('perfil_id', $perfiles) ?>			
                </p>

                                <p>
                    <label>Jerarquia <span class="required">(*)</span></label>
                    <?= form_dropdown('jerarquia_id', $jerarquias) ?>
                </p>	
                <p>
                    <label>Ubigeo <span class="required">(*)</span></label>
                    <?= form_dropdown('ubigeo_id', $ubigeo) ?>
                </p>	
            </fieldset>

            <p class="clear">
                <input type="submit" value="Aceptar" class="button-blue"/>
                <input type="button" value="Cancelar" class="button-blue" onclick="window.location='<?= base_url() ?>admin/usuario_controller/'"/>
            </p>
			
           
           
               </div><!--4-->
        		</div><!--2-->                                                      
    	</div><!--1-->
			
		
       
    </body>
</html>
