package com.itrade.pedidos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Usuario;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;

public class PreferencePedidos extends PreferenceActivity {
	public final static String KEY_PREF_SYNC_CONN="pref_key_auto_delete";
	public final static String KEY_PREF_MONEDA="listPref";
	public final static String KEY_PREF_USUARIO="pref_dialog";
	public final static String KEY_PREF_CHECK_USUARIO="pref_key_recordar_usuario";
	
	
	//green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UsuarioDao usuarioDao;
    Usuario ultimoUsuario = new  Usuario();
    EditTextPreference txtNombreUsuario;
    CheckBoxPreference chkRecordarNombre;
    boolean boolRecordarNombre=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencepedidos);
	    //inicio green DAO 
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        usuarioDao = daoSession.getUsuarioDao();
        ultimoUsuario=usuarioDao.loadByRowId(1);//Obtengo el ultimo Usuario
        Preference pref_chk_recordar= findPreference("pref_key_recordar_usuario");
        if (pref_chk_recordar!=null)
        	chkRecordarNombre= (CheckBoxPreference) pref_chk_recordar;
        if(chkRecordarNombre!=null){
        	boolRecordarNombre=chkRecordarNombre.isChecked();
        }
        
        
        
        Preference pref_dialog= findPreference("pref_dialog");
        if (pref_dialog!=null)
        	txtNombreUsuario= (EditTextPreference) pref_dialog;
        if (txtNombreUsuario!=null&&ultimoUsuario!=null){
        		if (!boolRecordarNombre){
        			txtNombreUsuario.setText("");
        		}
            	if(txtNombreUsuario.getText().length()==0){
            		txtNombreUsuario.setText(ultimoUsuario.getUsername());
//            		Toast.makeText(getBaseContext(), "U:"+ultimoUsuario.getUsername(), Toast.LENGTH_SHORT).show();
            	}            	
            	        	            
        }
        
        

    }
	@Override
	protected void onDestroy() { 
		db.close();
	    super.onDestroy();
	}
}