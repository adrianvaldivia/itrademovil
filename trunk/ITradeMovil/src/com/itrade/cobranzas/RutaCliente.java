package com.itrade.cobranzas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView; 
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle; 
import android.util.Log;
import android.widget.Toast;


public class RutaCliente extends Activity {
	private MapView myOpenMapView;
	private  MapController myMapController;
	private  String IdCliente; 
	private Location loc;
	private Geocoder geoCoder;
	private LocationManager locationManager;
	private LocationListener myLocationListener;
	private ArrayList<OverlayItem> anotherOverlayItemArray;

	  
	  @Override    
public void onCreate(Bundle savedInstanceState) {        
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.c_ruta);        
	Intent i = getIntent();                
	
	   myLocationListener = new LocationListener(){
		   public void onLocationChanged(Location location) {
		    // TODO Auto-generated method stub
		    updateLoc(location);
		   }

		   public void onProviderDisabled(String provider) {
		    // TODO Auto-generated method stub
		    
		   }

		   
		   public void onProviderEnabled(String provider) {
		    // TODO Auto-generated method stub
		    
		   }
		   
		   public void onStatusChanged(String provider, int status, Bundle extras) {
		    // TODO Auto-generated method stub
		    
		   }
		      
	    };
	
	IdCliente="1";//(String)i.getSerializableExtra("idempleado");
	  Syncronizar sync = new Syncronizar(RutaCliente.this);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("idcliente", IdCliente));	
			//String route="dp2/itrade/ws/clientes/get_clientes_by_vendedor/";
			String route="ws/clientes/get_cliente_by_id";
		    sync.conexion(param,route);
		    try {
				sync.getHilo().join();			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	    
			Log.d("ClienteeeEder", "hhahahhahahhah");
		    Gson gson = new Gson();
			ArrayList<Cliente> cliList = new ArrayList<Cliente>();							
			cliList	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());		
			Log.d("ClienteeeEder", cliList+"jahhaha");
		    Double Longitud = cliList.get(0).getLongitud();
		    Double Latitud = cliList.get(0).getLatitud();
			setTitle("I Trade - Mi Ubicacion");
			Log.d("ClienteeeEder", "hhahahhahahhah");
			
   

	myOpenMapView = (MapView) findViewById(R.id.openmapview);       
	myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
	myOpenMapView.setBuiltInZoomControls(true);       
	myMapController = myOpenMapView.getController();        
	myMapController.setZoom(10);  

	anotherOverlayItemArray = new ArrayList<OverlayItem>();
	   
    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
    //for demo, getLastKnownLocation from GPS only, not from NETWORK
    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    Log.d("GPSSSSS", "N"+lastLocation);
    if(lastLocation != null){
    	Log.d("GPSSSSS", "ENtroGPSSSS");
     updateLoc(lastLocation);
    }


	
	
	 
//	anotherOverlayItemArray.add(new OverlayItem("0, 0", "0, 0", new GeoPoint(0, 0)));
anotherOverlayItemArray.add(new OverlayItem("Cliente", cliList.get(0).getNombre()+" "+cliList.get(0).getApeMaterno(), new GeoPoint(Latitud,Longitud)));  
	//anotherOverlayItemArray.add(new OverlayItem("Peru", "Lima", new GeoPoint(loc.getAltitude(),loc.getLongitude()))); 
//	Log.d("Latituddd", ""+loc.getLatitude());

/*	anotherOverlayItemArray.add(new OverlayItem("US", "US", new GeoPoint(38.883333, -77.016667)));
	anotherOverlayItemArray.add(new OverlayItem("China", "China", new GeoPoint(39.916667, 116.383333)));
	anotherOverlayItemArray.add(new OverlayItem("United Kingdom", "United Kingdom", new GeoPoint(51.5, -0.116667)));
	anotherOverlayItemArray.add(new OverlayItem("Germany", "Germany", new GeoPoint(52.516667, 13.383333)));
	anotherOverlayItemArray.add(new OverlayItem("Korea", "Korea", new GeoPoint(38.316667, 127.233333)));
	anotherOverlayItemArray.add(new OverlayItem("India", "India", new GeoPoint(28.613333, 77.208333)));
	anotherOverlayItemArray.add(new OverlayItem("Russia", "Russia", new GeoPoint(55.75, 37.616667)));
	anotherOverlayItemArray.add(new OverlayItem("France", "France", new GeoPoint(48.856667, 2.350833)));
	anotherOverlayItemArray.add(new OverlayItem("Canada", "Canada", new GeoPoint(45.4, -75.666667))); */
	
	OnItemGestureListener<OverlayItem> myOnItemGestureListener = new OnItemGestureListener<OverlayItem>() {    
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {       
			// TODO Auto-generated method stub        
			return false;    }    
		
			public boolean onItemSingleTapUp(int index, OverlayItem item) {  
				Toast.makeText( RutaCliente.this,            
						item.mDescription + "\n" + item.mTitle + "\n"      
				+ item.mGeoPoint.getLatitudeE6() + " : "                
								+ item.mGeoPoint.getLongitudeE6(),       
					Toast.LENGTH_LONG).show();                     return true;    
					} };
					ItemizedOverlayWithFocus<OverlayItem> anotherItemizedIconOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this, anotherOverlayItemArray, myOnItemGestureListener);
					myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
		}
	  
	  private void updateLoc(Location loc){
		     GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
		     anotherOverlayItemArray.add(new OverlayItem("GPS", "GPS", locGeoPoint));  
		     Log.d("GPSSSSS", "ENtroGPSSSS"+loc.getLatitude());
		     myMapController.setCenter(locGeoPoint);
		     myOpenMapView.invalidate();
	   }
	  
	   @Override	   
	   protected void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
	   }
	   
	   @Override
	   protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	    locationManager.removeUpdates(myLocationListener);
	   }
	   
	  
	
	    
	   
	    
	    
	    
	 
	    
}
		     
		     


	  

