<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <!-- Meta -->
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"> 
            <!-- End of Meta -->
				<link rel="icon" type="image/ico" href="<?php echo base_url() ?>images/logicon.png"/>
            <title><?php echo $title; ?></title>   

            <!--styleshets CSS-->
          	<link href="<?php echo base_url() ?>css/styles.css" rel="stylesheet" />	
            <!--<link href="<?php echo base_url() ?>css/login_box.css" rel="stylesheet" />	 -->
             <link href="<?php echo base_url() ?>css/login.css" rel="stylesheet" />	 
             	  

            <!--scripts js-->
            <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.2.3.pack.js"></script>
            <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.js"></script>
            <script type="text/javascript" src="<?php echo base_url() ?>js/jquery-1.7.2.min.js"></script>

            <script type="text/javascript">
                //<![CDATA[
                base_url = '<?php echo base_url(); ?>';
                //]]>
            </script>
  				
            <!--[if IE]>
            <link rel="stylesheet" type="text/css" href="<?php echo base_url() ?>css/ie.css" media="screen, projection, print" />
    <![endif]-->
            <!--[if lt IE 7]>
           <script src="js/DD_belatedPNG_0.0.7a-min.js" type="text/javascript"></script>
           <script>
   
                   DD_belatedPNG.fix(' #header, h1, h1 a, .close, .field,.paginate .current, .icon, .required-icon');
   
           </script>
             /* <link rel="stylesheet" href="<?php echo base_url() ?>css/ie6.css" type="text/css" media="screen, projection"/> */
           <![endif]-->

    </head>
    <body>
       <div id="header">
			<div id="logo">
				<a href="<? echo base_url() ?>home"> <img src="<?php echo base_url() ?>images/logo.png"/></a>
         </div>
         
        <? if ($this->session->userdata('logged_in') && (isset($username) && isset($name))) { ?>
         <div class="header_left"  >
	          <span class="bienvenido">Bienvenido <span class="user"><? echo trim($username); ?></span></span>
             <span class="nombre"><? echo trim($name); ?></span>
         </div>
         <div class="header_right" >
             <a href="<? echo base_url() ?>home/logout"><img src="<? echo base_url() ?>images/logout.png" /></a>
            <? echo anchor(base_url() . 'home/logout', 'Logout', array('title' => 'Salir de la sesion')); ?>
         </div>
         <script type="text/javascript">
             $('#header .header_right').click(function (){
                 $(this).children('a').each(function(){
                 $(this).click();
               });
	           });
   	   </script> 
        <? } ?>
      </div> 
        
        <?php
        $this->load->view($main);
        ?>

   </body>
</html>
