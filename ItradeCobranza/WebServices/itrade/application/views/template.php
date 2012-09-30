<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title><?php echo $title;?></title>   
    <script type="text/javascript">
        //<![CDATA[
        base_url = '<?php echo base_url();?>';
        //]]>
    </script>

</head>
<body>
	
	<div id="header" style="width:100%; height:100px; background-color:green;">
	HEADER
	<?if ($this->session->userdata('logged_in')){?>
		<div class="header_left" style="height:100%;width:40%;float:left;" >
			<p>El usuario que se loggeo es: <? echo $username;?><br>Su nombre es: <? echo $name;?></p>		
		</div>
		<div class="header_rigth" style="height:100%;width:40%;float:left;" >
			<?echo anchor('home/logout', 'Logout', array('title' => 'title logout'));?>
		</div>
	<?}?>
	</div>
    <div id="contenido" style="width:100%;height:100%;min-height:450px;max-height:450px;">
		<?php $this->load->view($main);?>
	</div>
	<div id="footer" style="width:100%; height:50px; background-color:blue;">
	FOOTER
	</div>
</body>
</html>
