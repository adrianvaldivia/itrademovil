package com.itrade.pedidos;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.itrade.R;

public class PreferencePedidos extends PreferenceActivity {
	public final static String KEY_PREF_SYNC_CONN="pref_key_auto_delete";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencepedidos);
    }
}