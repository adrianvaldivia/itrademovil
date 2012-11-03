package com.itrade.pedidos;

import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;

import android.util.Log;

public class MyItemGestureListener<T extends OverlayItem> implements OnItemGestureListener<T> {
	
	public boolean onItemSingleTapUp(int index, T item) {
		// TODO Auto-generated method stub		
		return false;
	}
	
	public boolean onItemLongPress(int index, T item) {
		// TODO Auto-generated method stub
		return false;
	}



}