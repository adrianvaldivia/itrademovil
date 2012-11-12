<div class="container"><!--1--> 
   		<div class="row">  <!--2--> 
   			<div class="panel-wrapper panel-login"> <!--3--> 
   				<div class="panel"> <!--4--> 
   					<div class="title"> <h4>INTRANET</h4></div>  <!--5--> 
   				   <div class="content"> <!--6-->  
   				   <?php if ($this->session->flashdata('error')) { ?>
         		   <div class='message' style="text-align:center; margin-top:10px; color:red">
         	       <?php echo $this->session->flashdata('error') ?>
        			   </div>
			        <?php } ?>
	   				<?php echo form_open("validate/validate_admin", array('id' => 'loginForm', 'name' => 'loginForm')); ?>         
					   		<div>Usuario
					   		<?php $udata = array('name' => 'username', 'id' => 'username'); ?>
                    		<?php echo form_input($udata) ?>
                     	</div>  
	   						<div>Password  
	   						<?php $pdata = array('name' => 'password', 'id' => 'password'); ?>
                    		<?php echo form_password($pdata) ?>
                    		</div>  
	   						<div>
	   						<?
                    			$data = array(	'name' => 'button_submit',
						                        'id' => 'button_submit',
                  						      'type' => 'submit',
                  						      'content' => 'Ingresar',
                  						      'class'=>'button-blue submit');
		                    echo form_button($data);
      	               ?>
                    		</div> 
		   	      <?php echo form_close(); ?>
	   				</div><!--6--> 
 
	   			</div><!--4-->   
	   		
	   			<div class="shadow"></div> <!--7--> 
	   		</div><!--3-->   
	   	</div>  <!--2--> 
	   </div> <!--1--> 
   


