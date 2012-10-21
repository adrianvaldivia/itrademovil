<div class="contenido_principal" style="width:70%; height:100%;  min-width:650px; max-width:650px;">

    <?
    //print_r($creditos);
    if (count($creditos) == 0) {
        ?>
        <? echo "Por el momento no hay solicitudes de credito dispionibles."; ?>

    <? } else {
        ?>
                        <!--    <div class="accion"><? // echo anchor('admin/credito_controller/create_user', 'Nuevo Usuario', array('title' => 'Nuevo Usuario'));        ?></div>-->

        <!--    <br/>-->

        <table>
            <thead>
                <tr>
                    <th>
                        Cliente (Razon Social)
                    </th>
                    <th>
                        Monto Solicitado
                    </th>
                    <th>
                        Monto Actual
                    </th>
                    <th>
                        Monto Aprobado
                    </th>
                    <th>
                        Estado
                    </th>
                    <th>
                        Acciones
                    </th>

                </tr>
            </thead>
            <tbody>

                <?
                foreach ($creditos as $credito) {
                    $idlinea = $credito['IdLinea'];
                    ?>
                    <tr>
                        <td>
                            <div class="nombre"><? echo $credito['Cliente']->Razon_Social; ?></div>
                        </td>
                        <td>
                            <div class="perfil"><? echo $credito['MontoSolicitado']; ?></div>
                        </td>
                        <td>
                            <div class="perfil"><? echo $credito['MontoActual']; ?></div>
                        </td>
                        <td>
                            <div class="perfil"><? echo $credito['MontoAprobado']; ?></div>
                        </td>
                        <td>
                            <div class="estado"><? if($credito['Activo'] == 1)echo 'Aprobado';
                                                if($credito['Activo'] == 0) echo 'Por Aprobar';
                                                if($credito['Activo'] == 2) echo 'Rechazado';?>
                            </div>
                        </td>
                        <td><? if ($credito['Activo'] == 0) { ?>
                                <div class="accion"><? echo anchor("admin/credito_controller/accept/" . $idlinea, 'Aceptar', array('title' => 'Aceptar')); ?></div>

                                <div class="accion"><? echo anchor("admin/credito_controller/reject/" . $idlinea, 'Rechazar', array('title' => 'Rechazar')); ?></div>
                            <? } else { ?>
                                <div class="accion"><? echo"-"; ?></div>
                            <? } ?>
                        </td>
                    </tr>
                <? } ?>
            </tbody>
        </table>
    <? }
    ?>
</div>
