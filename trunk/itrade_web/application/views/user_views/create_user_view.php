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
        <div id="header">
            <!--            <div id="title">iTRADE</div>-->
            <div id="logo">
                <a href="<?echo base_url()?>home"> <img src="<?php echo base_url() ?>images/loguito.png"/></a>
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
                <p>
                    <label>Nombres: <span class="mandatory">(*)</span> </label>
                    <?php $firstname = array('name' => 'firstname', 'id' => 'firstname', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese el nombre', 'value' => set_value('firstname')); ?>
                    <?= form_input($firstname) ?>
                </p>
                <p>
                    <label>Apellido Paterno: <span class="mandatory">(*)</span></label>
                    <?php $lastname1 = array('name' => 'lastname1', 'id' => 'lastname', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese el primer apellido', 'value' => set_value('lastname1')); ?>
                    <?= form_input($lastname1) ?>
                </p>
                <p>
                    <label>Apellido Materno: <span class="mandatory">(*)</span></label>
                    <?php $lastname2 = array('name' => 'lastname2', 'id' => 'lastname2', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese el segundo apellido', 'value' => set_value('lastname2')); ?>
                    <?= form_input($lastname2) ?>
                </p>
                <p>
                    <label>Teléfono: </label>
                    <?php $phone = array('name' => 'phone', 'id' => 'phone', 'size' => 15, 'class' => 'number', 'title' => 'Por favor ingrese un telefono válido', 'maxlength' => '10', 'value' => set_value('phone')); ?>
                    <?= form_input($phone) ?>
                </p>
                <p>
                    <label>Email: <span class="mandatory">(*)</span></label>
                    <?php $email = array('name' => 'email', 'id' => 'm', 'size' => 15, 'class' => 'mandatory email', 'title' => 'Por favor ingrese un email valido.', 'value' => set_value('email')); ?>
                    <?= form_input($email) ?>
                </p
                <p>
                    <label>DNI: <span class="mandatory">(*)</span></label>
                    <?php $dni = array('name' => 'dni', 'id' => 'dni', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese su DNI', 'maxlength' => '8', 'value' => set_value('dni')); ?>
                    <?= form_input($dni) ?>
                </p>
                <p>
                    <label>Fecha de Nacimiento:</label>
                    <?php $birthdate = array('name' => 'birthdate', 'id' => 'birthdate', 'size' => 15, 'class' => 'mandatory dateISO', 'title' => 'Por favor ingrese una fecha', 'value' => set_value('birthdate')); ?>
                    <?= form_input($birthdate) ?>
                </p>


            </fieldset>
            <fieldset class="left">
                <legend>Cuenta de Usuario</legend>
                <p>
                    <label>Usuario: <span class="mandatory">(*)</span></label>
                    <?php $username = array('name' => 'username', 'id' => 'u', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese el nombre de usuario', 'value' => set_value('username')); ?>
                    <?= form_input($username) ?>
                </p>
                <p>
                    <label>Password: <span class="mandatory">(*)</span></label>
                    <?php $password = array('name' => 'password', 'id' => 'password', 'size' => 15, 'class' => 'mandatory', 'minlength' => '5', 'title' => 'Por favor ingrese un password (al menos 5 letras) '); ?>
                    <?= form_password($password) ?>
                </p>
                <p>
                    <label>Repetir Password: <span class="mandatory">(*)</span></label>
                    <?php $passwordrepeat = array('name' => 'passwordrepeat', 'id' => 'pr', 'size' => 15, 'equalTo' => '#password', 'title' => 'Por favor ingrese el mismo valor que el campo password'); ?>
                    <?= form_password($passwordrepeat) ?>
                </p>
                <p>
                    <label>Perfil <span class="mandatory">(*)</span></label>
                    <?= form_dropdown('perfil_id', $perfiles) ?>
                </p>	
                <p>
                    <label>Ubigeo <span class="mandatory">(*)</span></label>
                    <?= form_dropdown('ubigeo_id', $ubigeo) ?>
                </p>	
            </fieldset>

            <p class="clear">
                <input type="submit" value="Aceptar" class="button"/>
                <input type="button" value="Cancelar" class="button" onclick="window.location='<?= base_url()?>home/'"/>
            </p>
            <?= form_close(); ?>


        </div>
<!--        <div id="footer">
            <div>2012 - i-Trade</div>
        </div>-->
    </body>
</html>