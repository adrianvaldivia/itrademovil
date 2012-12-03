package com.itrade.pedidos;

import android.app.Application;

public class MyApplication extends Application {

    private boolean boolSePuede;

	public boolean isBoolSePuede() {
		return boolSePuede;
	}

	public void setBoolSePuede(boolean boolSePuede) {
		this.boolSePuede = boolSePuede;
	}

}
