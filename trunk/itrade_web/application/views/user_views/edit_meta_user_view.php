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
                $( "#fechaini" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
                $( "#fechafin" ).datepicker({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
                
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
        <p><? //= $error_message                     ?></p>
    </div>
    </p>-->
            <?php
            // endif; 
//            print_r($meta);
            ?>

            <div class="contenido_principal" style="width:70%; height:100%;  min-width:650px; max-width:650px;">
                <?= form_open("admin/usuario_controller/edit_meta/".$meta->IdMeta, array('id' => 'userForm')); ?>

                <fieldset class="left">
                    <legend>Datos Generales</legend>
                    <p>
                        <label>Fecha Inicio: <span class="mandatory">(*)</span> </label>
                        <?php $fechaini = array('name' => 'fechaini', 'id' => 'fechaini', 'size' => 15, 'class' => 'mandatory dateISO', 'title' => 'Por favor ingrese una fecha', 'value' => $meta->FechaIni); ?>
                        <?= form_input($fechaini) ?>
                    </p>
                    <p>
                        <label>Fecha Fin: <span class="mandatory">(*)</span> </label>
                        <?php $fechafin = array('name' => 'fechafin', 'id' => 'fechafin', 'size' => 15, 'class' => 'mandatory dateISO', 'title' => 'Por favor ingrese una fecha', 'value' =>$meta->FechaFin); ?>
                        <?= form_input($fechafin) ?>
                    </p>
                    <p>
                        <label>Monto: <span class="mandatory">(*)</span></label>
                        <?php $monto = array('name' => 'monto', 'id' => 'monto', 'size' => 15, 'class' => 'mandatory', 'title' => 'Por favor ingrese el monto', 'value' => $meta->Monto); ?>
                        <?= form_input($monto) ?>
                    </p>


                </fieldset>

                <p class="clear">
                    <input type="submit" value="Aceptar" class="button"/>
                    <input type="button" value="Cancelar" class="button" onclick="window.location='<?= base_url() ?>admin/usuario_controller/metas_user/<? echo $meta->IdUsuario ?>'"/>
                </p>
                <?= form_close(); ?>

            </div>

        </div>

    </body>
</html>
