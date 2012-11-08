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
                $("#birthdate" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
				$("#userForm").validate();
			});
        </script>
        <div id="header">
            <!--            <div id="title">iTRADE</div>-->
            <div id="logo">
                <a href="<? echo base_url() ?>home"> <img src="<?php echo base_url() ?>images/logo.png"/></a>
            </div>
            <? if ($this->session->userdata('logged_in') && (isset($username) && isset($name))) { ?>
                <div class="header_left"  >
                    <span class="bienvenido">Bienvenido <span class="user"><? echo trim($username); ?></span></span>
                    <span class="nombre"><? echo trim($name); ?>
                    </span>
                </div>
                <div class="header_right" >
                    <a href="<? echo base_url() ?>home/logout"><img src="<? echo base_url() ?>images/logout.png" /></a>
                    <? echo anchor(base_url() . 'home/logout', 'Logout', array('title' => 'Salir de la sesion')); ?>
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
        <p><? //= $error_message           ?></p>
    </div>
    </p>-->
            <?php
            // endif; 
            // print_r($persona);
            ?>

            <?= form_open("admin/usuario_controller/edit/" . $usuario->IdUsuario, array('id' => 'userForm')); ?>

            <fieldset class="left">
                <legend>Datos Generales</legend>


                <p>
                    <label>Nombres: <span class="required">(*)</span> </label>
                    <?php $firstname = array('name' => 'firstname', 'id' => 'firstname', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el nombre', 'value' => $persona->Nombre); ?>
                    <?= form_input($firstname) ?>
                </p>
                <p>
                    <label>Apellido Paterno: <span class="required">(*)</span></label>
                    <?php $lastname1 = array('name' => 'lastname1', 'id' => 'lastname', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el primer apellido', 'value' => $persona->ApePaterno); ?>
                    <?= form_input($lastname1) ?>
                </p>
                <p>
                    <label>Apellido Materno: <span class="required">(*)</span></label>
                    <?php $lastname2 = array('name' => 'lastname2', 'id' => 'lastname2', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el segundo apellido', 'value' => $persona->ApeMaterno); ?>
                    <?= form_input($lastname2) ?>
                </p>
                <p>
                    <label>Teléfono: </label>
                    <?php $phone = array('name' => 'phone', 'id' => 'phone', 'size' => 15, 'class' => 'number', 'title' => 'Por favor ingrese un telefono válido', 'maxlength' => '10', 'value' => $persona->Telefono); ?>
                    <?= form_input($phone) ?>
                </p>
                <p>
                    <label>Email: <span class="required">(*)</span></label>
                    <?php $email = array('name' => 'email', 'id' => 'm', 'size' => 15, 'class' => 'required email', 'title' => 'Por favor ingrese un email valido.', 'value' => $persona->Email); ?>
                    <?= form_input($email) ?>
                </p
                <p>
                    <label>DNI: <span class="required">(*)</span></label>
                    <?php $dni = array('name' => 'dni', 'id' => 'dni', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese su DNI', 'maxlength' => '8', 'value' => $persona->DNI); ?>
                    <?= form_input($dni) ?>
                </p>
                <p>
                    <label>Fecha de Nacimiento:</label>
                    <?php $birthdate = array('name' => 'birthdate', 'id' => 'birthdate', 'size' => 15, 'class' => 'required dateISO', 'title' => 'Por favor ingrese una fecha', 'value' => date($persona->FechNac)); ?>
                    <?= form_input($birthdate) ?>
                </p>

                <?php $idPersona = array('IdPersona' => $persona->IdPersona); ?>
                <?= form_hidden($idPersona); ?>

            </fieldset>
            <fieldset class="left">
                <legend>Cuenta de Usuario</legend>
                <?php $idUsuario = array('IdUsuario' => $usuario->IdUsuario); ?>
                <?= form_hidden($idUsuario); ?>

                <p>
                    <label>Usuario: <span class="required">(*)</span></label>
                    <?php $username = array('name' => 'username', 'id' => 'u', 'size' => 15, 'class' => 'required', 'title' => 'Por favor ingrese el nombre de usuario', 'value' => $usuario->Nombre); ?>
                    <?= form_input($username) ?>
                </p>
                <p>
                    <label>Password: <span class="required">(*)</span></label>
                    <?php $password = array('name' => 'password', 'id' => 'password', 'size' => 15, 'class' => 'required', 'disabled' => 'disabled', 'minlength' => '5', 'title' => 'Por favor ingrese un password (al menos 5 letras) ', 'value' => 'xxxxxxxx'); ?>
                    <?= form_password($password) ?>
                </p>
                <p>
                    <label>Repetir Password: <span class="required">(*)</span></label>
                    <?php $passwordrepeat = array('name' => 'passwordrepeat', 'id' => 'pr', 'size' => 15, 'equalTo' => '#password', 'disabled' => 'disabled', 'title' => 'Por favor ingrese el mismo valor que el campo password', 'value' => 'xxxxxxxx'); ?>
                    <?= form_password($passwordrepeat) ?>
                </p>
                <p>
                    <label>Perfil <span class="required">(*)</span></label>
                    <?= form_dropdown('perfil_id', $perfiles, $usuario->IdPerfil) ?>				
                </p>
                <p>  
                    <label>Activo: </label>
                    <?php
                    echo form_checkbox('activo', $persona->Activo, $usuario->Activo);
                    ?>
                </p>
                                <p>
                    <label>Jerarquia <span class="required">(*)</span></label>
                    <?= form_dropdown('jerarquia_id', $jerarquias,$usuario->IdJerarquia-1) ?>
                </p>	
                <p>
                    <label>Ubigeo <span class="required">(*)</span></label>
                    <?= form_dropdown('ubigeo_id', $ubigeo, $usuario->IdUbigeo) ?>
                </p>	
            </fieldset>

            <p class="clear">
                <input type="submit" value="Aceptar" class="button"/>
                <input type="button" value="Cancelar" class="button" onclick="window.location='<?= base_url() ?>admin/usuario_controller/'"/>
            </p>
            <?= form_close(); ?>


        </div>
<!--        <div id="footer">
            <div>2012 - i-Trade</div>
        </div>-->
    </body>
</html>
