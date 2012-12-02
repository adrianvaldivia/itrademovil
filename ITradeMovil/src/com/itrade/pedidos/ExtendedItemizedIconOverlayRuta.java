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
import com.itrade.jsonParser.AsTaskCargarRuta;



import android.app.Activity;
import android.content.Context;

import android.graphics.drawable.Drawable;

import android.view.MotionEvent;

 

public class ExtendedItemizedIconOverlayRuta<Item extends OverlayItem> extends ItemizedIconOverlay<Item> {
	
	private Context mContext; 
	
	int colorRuta=0;
	
	int numLayersBase=0;
	
	GeoPoint srcGeoPoint;
	
	private Activity activity;
	
	public ExtendedItemizedIconOverlayRuta(

		Context pContext,

		List<Item> pList,

		org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> pOnItemGestureListener,
		
		Activity activ,int colorRut,GeoPoint orig,int numLayersBas) {
		

		super(pContext, pList, pOnItemGestureListener);
		this.mContext=pContext;
		
        this.activity = activ;		
        this.colorRuta=colorRut;
        this.srcGeoPoint=orig;
        this.numLayersBase=numLayersBas;

// TODO Auto-generated constructor stub

	}

 

	public ExtendedItemizedIconOverlayRuta(

		List<Item> pList,

		Drawable pDefaultMarker,

		org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> pOnItemGestureListener,

		ResourceProxy pResourceProxy) {

		super(pList, pDefaultMarker, pOnItemGestureListener, pResourceProxy);

// TODO Auto-generated constructor stub

	}

 

	public ExtendedItemizedIconOverlayRuta(

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
			
			Drawable newMarker = mContext.getResources().getDrawable(R.drawable.bluemarker3);
			
			vItem.setMarker(newMarker);
						
			this.removeAllItems();//cambios chichan

			addItem((Item)vItem);
			
			mapView.invalidate();
			
			mapView.getOverlays().remove(numLayersBase);
						
	        AsTaskCargarRuta _connectAsyncTask = new AsTaskCargarRuta(activity,
	        		mapView,colorRuta,
	        		srcGeoPoint, mGeoPoint,numLayersBase);
	        _connectAsyncTask.execute();

			if (!success)

				return false;
		}

		return true;
	}

}

