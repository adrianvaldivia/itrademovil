package com.itrade.pedidos;


import java.util.List;


import org.osmdroid.ResourceProxy;

import org.osmdroid.util.GeoPoint;

import org.osmdroid.views.MapView;

import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;

import com.itrade.R;



import android.content.Context;

import android.graphics.drawable.Drawable;

import android.view.MotionEvent;

 

public class ExtendedItemizedIconOverlay<Item extends OverlayItem> extends ItemizedIconOverlay<Item> {
	
	private Context mContext; 
	
	public ExtendedItemizedIconOverlay(

		Context pContext,

		List<Item> pList,

		org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> pOnItemGestureListener) {
		

		super(pContext, pList, pOnItemGestureListener);
		this.mContext=pContext;

// TODO Auto-generated constructor stub

	}

 

	public ExtendedItemizedIconOverlay(

		List<Item> pList,

		Drawable pDefaultMarker,

		org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> pOnItemGestureListener,

		ResourceProxy pResourceProxy) {

		super(pList, pDefaultMarker, pOnItemGestureListener, pResourceProxy);

// TODO Auto-generated constructor stub

	}

 

	public ExtendedItemizedIconOverlay(

		List<Item> pList,

		org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> pOnItemGestureListener,

		ResourceProxy pResourceProxy) {

		super(pList, pOnItemGestureListener, pResourceProxy);

		// TODO Auto-generated constructor stub

	}

 

	@Override

	public boolean onLongPress(MotionEvent event, MapView mapView) {

		if (!super.onLongPress(event, mapView)){

			/* So we did not get an icon */

			boolean success = true;

			final Projection pj = mapView.getProjection();

			final int eventX = (int) event.getX();

			final int eventY = (int) event.getY();

			GeoPoint mGeoPoint = (GeoPoint) pj.fromPixels(eventX, eventY);

			OverlayItem vItem = new OverlayItem("PROSPECTO","PROSPECTO", mGeoPoint);
			
			Drawable newMarker = mContext.getResources().getDrawable(R.drawable.marker);
			
			vItem.setMarker(newMarker);
						
			this.removeAllItems();//cambios chichan

			addItem((Item)vItem);

			mapView.invalidate();

			if (!success)

				return false;
		}

		return true;
	}

}

