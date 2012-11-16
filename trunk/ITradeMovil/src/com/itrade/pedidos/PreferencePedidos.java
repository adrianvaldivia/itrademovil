package com.itrade.pedidos;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.itrade.R;

public class PreferencePedidos extends PreferenceActivity {
	public final static String KEY_PREF_SYNC_CONN="pref_key_auto_delete";
	public final static String KEY_PREF_MONEDA="listPref";
	public final static String KEY_PREF_USUARIO="pref_dialog";
	public final static String KEY_PREF_CHECK_USUARIO="pref_key_recordar_usuario";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencepedidos);
    }
}