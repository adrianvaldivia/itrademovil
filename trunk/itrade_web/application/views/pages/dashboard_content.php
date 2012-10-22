<div class="contenido_principal" style="width:70%; height:100%;  min-width:650px; max-width:650px;">


    <div class="accion"><? echo anchor('admin/usuario_controller/create_user', 'Nuevo Usuario', array('title' => 'Nuevo Usuario')); ?></div>

    <br/>

    <table>
        <thead>
            <tr>
                <th>
                    Nombre
                </th>
                <th>
                    Perfil
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
            foreach ($usuarios as $usuario) {
                $idusuario = $usuario['IdUsuario'];
                $idpersona = $usuario['IdPersona'];
                ?>
                <tr>
                    <td>
                        <div class="nombre"><? echo $usuario['Nombre']; ?></div>
                    </td>
                    <td>
                        <div class="perfil"><? echo $usuario['Perfil']; ?></div>
                    </td>
                    <td>
                        <div class="estado"><? echo $usuario['Activo']; ?></div>
                    </td>
                    <td>
                        <div class="accion"><? echo anchor("admin/usuario_controller/modificar/$idusuario/$idpersona", 'Editar', array('title' => 'Editar')); ?></div>
                        <? if ($usuario['Perfil'] == 'Vendedor'): ?><div class="accion"><? echo anchor("admin/usuario_controller/metas_user/$idusuario", 'Metas', array('title' => 'Metas')); ?></div>
                        <? endif; ?>
                    </td>
                </tr>
                <?
            }
            ?>
        </tbody>
    </table>
</div>