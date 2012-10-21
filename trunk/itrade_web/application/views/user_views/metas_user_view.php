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
                <a href="<? echo base_url() ?>home"> <img src="<?php echo base_url() ?>images/loguito.png"/></a>
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
        <p><? //= $error_message                    ?></p>
    </div>
    </p>-->
            <?php
            // endif; 
//            print_r($metas);
            ?>

            <div class="contenido_principal" style="width:70%; height:100%;  min-width:650px; max-width:650px;">

                <? if (count($metas) == 0) { ?>
                    <? echo "El usuario seleccionado no tiene metas."; ?>
                    <div class="accion"><? echo anchor('admin/usuario_controller/create_meta/' . $idusuario, 'Nueva Meta', array('title' => 'Nueva Meta')); ?></div>
                <? } else {
                    ?>

                    <div class="accion"><? echo anchor('admin/usuario_controller/create_meta/' . $idusuario, 'Nueva Meta', array('title' => 'Nueva Meta')); ?></div>
                    <br/>

                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Meta
                                </th>
                                <th>
                                    Fecha Inicio
                                </th>
                                <th>
                                    Fecha Fin
                                </th>
                                <th>
                                    Acciones
                                </th>

                            </tr>
                        </thead>
                        <tbody>

                            <?
                            //print_r($metas);
                            foreach ($metas as $meta) {
                                ?>
                                <tr>
                                    <td>
                                        <div class="nombre"><? echo $meta['Monto']; ?></div>
                                    </td>
                                    <td>
                                        <div class="perfil"><? echo $meta['FechaIni']; ?></div>
                                    </td>
                                    <td>
                                        <div class="estado"><? echo $meta['FechaFin']; ?></div>
                                    </td>
                                    <td>
                                        <div class="accion"><? echo anchor("admin/usuario_controller/modificar_meta/".$meta['IdMeta'], 'Editar', array('title' => 'Editar')); ?></div>
        <!--                                    <? // if ($metas['Perfil'] == 'Vendedor'):         ?><div class="accion"><? // echo anchor("admin/usuario_controller/metas_user/$idusuario", 'Metas', array('title' => 'Metas'));        ?></div>-->

                                    </td>
                                </tr>
                                <?
                            }
                            ?>
                        </tbody>
                    </table>
                <? }
                ?>


            </div>


        </div>

    </body>
</html>
