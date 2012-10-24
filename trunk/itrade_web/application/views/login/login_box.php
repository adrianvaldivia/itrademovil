<!-- Content -->
	<div id="login" class="content">	
		<div class="roundedBorders login-box">
			<!-- Title -->
			<div id="title" class="b2">
				<h2>Log In</h2>
				<!-- TitleActions -->
				<div id="titleActions">
					<div class="actionBlock">
					<a href="#">Forgot your password ?</a>
					</div>
				</div>
				<!-- /TitleActions -->
			</div>
			<!-- Title -->
	
			<!-- Inner Content -->
			<div id="innerContent">
				<!-- <form action="titanium.php" method="post"> -->
				 <?php echo form_open("validate/validate_admin", array('id' => 'loginForm', 'name' => 'loginForm')); ?> 
					<div class="field">
						<label for="username">Usuario: </label>
						<?php $udata = array('name' => 'username', 'id' => 'username'); ?>
                    <?php echo form_input($udata) ?>
						<!-- <input type="text" class="text" id="username" name="username" value="just click login !"/> -->
					</div>
					<div class="field">
									
						<label for="password">Contraseña: </label>
						<!-- <input type="password" class="text" id="password" name="password" value="aaaaaa"/> -->
						<?php $pdata = array('name' => 'password', 'id' => 'password'); ?>
                    <?php echo form_password($pdata) ?>
					</div>
					<div class="clearfix login-submit">
						<span class="fleft">
							<input type="checkbox" name="remember-me" id="remember-me" />
							<label for="remember-me">Remember me</label>
						</span>
						<span class="fright">
						 <?
                    $data = array(
                        'name' => 'button_submit',
                        'id' => 'button_submit',
                        'type' => 'submit',
                        'content' => 'Ingresar'
                    );
                    echo form_button($data);
                    ?>
							<!-- <button class="button" type="submit"><strong>Log In</strong></button> -->
						</span>
					</div>
				<!-- </form> -->
				 <input type="hidden" value="2" name="user_type">
        <?php echo form_close(); ?>
			</div>
			<!-- /Inner Content -->
			<div class="bBottom"><div></div></div>
		</div>
	</div>
