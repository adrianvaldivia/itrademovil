<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <!-- Meta -->
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"> 
        <!-- End of Meta -->

        <title><?php echo $title; ?></title>   

        <!--styleshets CSS-->
        <link href="<?php echo base_url() ?>css/login.css" rel="stylesheet" />	
        <link href="<?php echo base_url() ?>css/login_box.css" rel="stylesheet" />	
        <link href="<?php echo base_url() ?>css/user_create.css" rel="stylesheet" />	
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />

        <!--scripts js-->
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.2.3.pack.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.min.js"></script>
        <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
		<script src="<?php echo base_url() ?>js/jquery.validate.js" type="text/javascript"></script>


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
        <div id="header">
            <!--            <div id="title">iTRADE</div>-->
            <div id="logo">
                <a href="<?echo base_url()?>home"> <img src="<?php echo base_url() ?>images/logo.png"/></a>
            </div>
            <? if ($this->session->userdata('logged_in') && (isset($username) && isset($name))) { ?>
                <div class="header_left"  >
                    <span class="bienvenido">Bienvenido <span class="user"><? echo trim($username); ?></span></span>
                    <span class="nombre"><? echo trim($name); ?>
                    </span>
                </div>
                <div class="header_right" >
                    <a href="<?echo base_url()?>home/logout"><img src="<? echo base_url() ?>images/logout.png" /></a>
                    <? echo anchor(base_url().'home/logout', 'Logout', array('title' => 'Salir de la sesion')); ?>
                </div>
            <? } else { ?>
                <? //si entra un usuario logueado al home ?>
            <? } ?>
        </div>
        <div id="content">


            <h2><?= $title ?></h2>

            <?php // if ($error_message): ?>
<!--    <p class="space">
    <div class="message error close">		
        <h2>Error!</h2>		
        <p><? //= $error_message         ?></p>
    </div>
    </p>-->
            <?php // endif; ?>

            <?= form_open("admin/usuario_controller/create", array('id' => 'userForm')); ?>
            <fieldset class="left">
                <legend>Datos Generales</legend>
				         <table class="usuario">
                        <tbody>
                                <tr>
                                    <td>
                                        <div class="nombre"><label>Nombres: <span class="required">(*)</span> </label></div>
                                    </td>
                                    <td>
                                        <div class="perfil">
										<?php $firstname = array('name' => 'firstname', 'id' => 'firstname', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el nombre', 'value' => set_value('firstname')); ?>
										<?= form_input($firstname) ?>
										</div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Apellido Paterno: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $lastname1 = array('name' => 'lastname1', 'id' => 'lastname', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el primer apellido', 'value' => set_value('lastname1')); ?>
                    <?= form_input($lastname1) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Apellido Materno: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $lastname2 = array('name' => 'lastname2', 'id' => 'lastname2', 'size' => 30, 'class' => 'required', 'title' => 'Por favor ingrese el segundo apellido', 'value' => set_value('lastname2')); ?>
                    <?= form_input($lastname2) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Teléfono: </label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $phone = array('name' => 'phone', 'id' => 'phone', 'size' => 15, 'class' => 'number', 'title' => 'Por favor ingrese un telefono válido', 'maxlength' => '10', 'value' => set_value('phone')); ?>
                    <?= form_input($phone) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Email: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $email = array('name' => 'email', 'id' => 'm', 'size' => 30, 'class' => 'required email', 'title' => 'Por favor ingrese un email valido.', 'value' => set_value('email')); ?>
                    <?= form_input($email) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>DNI: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $dni = array('name' => 'dni', 'id' => 'dni', 'size' => 15, 'class' => 'required number', 'title' => 'Por favor ingrese su DNI', 'maxlength' => '8', 'value' => set_value('dni')); ?>
                    <?= form_input($dni) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Fecha de Nacimiento:</label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $birthdate = array('name' => 'birthdate', 'id' => 'birthdate', 'size' => 15, 'class' => 'required dateISO', 'title' => 'Por favor ingrese una fecha', 'value' => set_value('birthdate')); ?>
                    <?= form_input($birthdate) ?></div>
                                    </td>
                                </tr>
                         </tbody>
                    </table>
                

            </fieldset>
            <fieldset class="left">
                <legend>Cuenta de Usuario</legend>
				         <table  class="usuario">
                        <tbody>

                                <tr>
                                    <td>
                                        <div><label>Usuario: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil">
										<?php $username = array('name' => 'username', 'id' => 'u', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el nombre de usuario', 'value' => set_value('username')); ?>
                    <?= form_input($username) ?>
										</div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Password: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $password = array('name' => 'password', 'id' => 'password', 'size' => 15, 'class' => 'required', 'minlength' => '5', 'title' => 'Por favor ingrese un password (al menos 5 letras) '); ?>
                    <?= form_password($password) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Repetir Password: <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?php $passwordrepeat = array('name' => 'passwordrepeat', 'id' => 'pr', 'size' => 15, 'equalTo' => '#password', 'title' => 'Por favor ingrese el mismo valor que el campo password'); ?>
                    <?= form_password($passwordrepeat) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Perfil <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?= form_dropdown('perfil_id', $perfiles) ?></div>
                                    </td>
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Jerarquia <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?= form_dropdown('jerarquia_id', $jerarquias) ?></div>
                                    </td>
                               
                                </tr>
								<tr>
                                    <td>
                                        <div class="nombre"><label>Ubigeo <span class="required">(*)</span></label></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><?= form_dropdown('ubigeo_id', $ubigeo) ?></div>
                                    </td>
                               
                                </tr>
								
                         </tbody>
                    </table>
				

            </fieldset>

            <p class="clear">
                <input type="submit" value="Aceptar" class="button"/>
                <input type="button" value="Cancelar" class="button" onclick="window.location='<?= base_url()?>admin/usuario_controller/'"/>
            </p>
            <?= form_close(); ?>


        </div>
<!--        <div id="footer">
            <div>2012 - i-Trade</div>
        </div>-->
    </body>
</html>
