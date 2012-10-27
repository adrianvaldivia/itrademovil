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

        <!--scripts js-->
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.2.3.pack.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.min.js"></script>

        <script type="text/javascript">
            //<![CDATA[
            base_url = '<?php echo base_url(); ?>';
            //]]>
        </script>
    </head>
    <body class="user">

        <div id="header">
            <!--            <div id="title">iTRADE</div>-->
            <div id="logo">
               <a href="<?echo base_url()?>home"> <img src="<?php echo base_url() ?>images/logo.png"/></a>
            </div>
            <?
            if ($this->session->userdata('logged_in')) {

                if ((isset($username) && isset($name))) {
                    ?>

                    <div class="header_left"  >
                        <span class="bienvenido">Bienvenido <span class="user"><? echo trim($username); ?></span></span>
                        <span class="nombre"><? echo trim($name); ?>
                        </span>
                    </div>
                <? } ?>
                <div class="header_right" >
                    <a href="<?echo base_url()?>home/logout"><img src="<? echo base_url() ?>images/logout.png" /></a>
                    <? echo anchor(base_url().'home/logout', 'Logout', array('title' => 'Salir de la sesion')); ?>
                </div>

            <? } else { ?>
                <? //si entra un usuario logueado al home ?>
            <? } ?>
        </div>
        <div id="content">
            <div id="contenido_principal" style="width:100%; height:100%;" >
                <?php 
				// echo $main;
				$this->load->view($main); ?>
            </div>
        </div>
<!--        <div id="footer">
            <div>2012 - i-Trade</div>
        </div>-->
    </body>
</html>
