<div id="login_principal">
    <fieldset >
        <legend><b>INTRANET</b></legend>   
        <?php if ($this->session->flashdata('error')) { ?>
            <div class='message' style="text-align:center; margin-bottom:10px; color:red">
                <?php echo $this->session->flashdata('error') ?>
            </div>
        <?php } ?>		
        <?php echo form_open("validate/validate_admin", array('id' => 'loginForm', 'name' => 'loginForm')); ?>            
        <table >
            <tr>
                <td style="width: 45%;text-align:right;padding:5px;color:darkgray;"><label><b>Usuario: </b></label></td>
                <td style="width: 55%">
                    <?php $udata = array('name' => 'username', 'id' => 'username'); ?>
                    <?php echo form_input($udata) ?>						
                </td>
            </tr>
            <tr>
                <td style="text-align:right;padding:5px;color: darkgray"><label><b>Contrase&ntilde;a: </b></label></td>
                <td>
                    <?php $pdata = array('name' => 'password', 'id' => 'password'); ?>
                    <?php echo form_password($pdata) ?>
                </td>
            </tr>

            <tr>
                <td style="text-align:right;padding:5px;"> <!--<label>Combobox</label>--> </td>
                <td>
                    <?
                    $options = array(
                        'small' => 'Small Shirt',
                        'med' => 'Medium Shirt',
                        'large' => 'Large Shirt',
                        'xlarge' => 'Extra Large Shirt',
                    );
                    $shirts_on_sale = array('small', 'med');
                    /* echo form_dropdown('shirts', $options, ''); */ //Nombre, opciones del combo, seleccionado
                    ?>
                </td>

            </tr>

            <tr>
                <td style="text-align:right;padding:5px;"> <!--<label>CheckBOX: </label>--> </td>
                <td>
                    <?php /* echo form_checkbox('check', 'accept', FALSE); */ ?>											
                </td>
            </tr>	
            <tr>
                <td style="text-align:right;padding:5px;"> <!--<label>RADIOBUTTON: </label>--> </td>
                <td>
                    <?php /* echo form_radio('radio', 'MACHO', FALSE); */ ?>											
                    <?php /* echo form_radio('radio', 'HEMBRA', FALSE); */ ?>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td style="padding:5px;">
                    <?
                    $data = array(
                        'name' => 'button_submit',
                        'id' => 'button_submit',
                        'type' => 'submit',
                        'content' => 'Ingresar'
                    );
                    echo form_button($data);
                    ?>
                </td>
            </tr>
        </table>
        <input type="hidden" value="2" name="user_type">
        <?php echo form_close(); ?>
    </fieldset>

</div>