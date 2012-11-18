package com.itrade.cobranzas;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.SimpleLocationOverlay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Cliente;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class RutaCliente  extends Activity implements LocationListener {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final int MENU_ZOOMIN_ID = 10;
//    private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

    // ===========================================================
    // Fields
    // ===========================================================
	private PathOverlay myPath;
	public int j=0;
    private MapView mOsmv;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    List<GeoPoint> listaGeoPoint =null;//ruta
    List<Cliente> listaCliente =null;
    
    private ResourceProxy mResourceProxy;
    private final double factor=1000000;
  	public int posxint,posyint;
  	public double posxdouble,posydouble;
  	public double latitudAux=0;
  	public double longitudAux=0;
    private MapController mapController;
    private SimpleLocationOverlay posicionActualOverlay;
    final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    //private Timer myTimer;
    private Handler mHandler = new Handler();
    //green dao
    private SQLiteDatabase db;

    SimpleCursorAdapter adapter;
    // fin de green dao

    String GPSPROVIDER = LocationManager.GPS_PROVIDER;
    private  static final long MIN_GEOGRAPHIC_POOLING_TIME_PERIOD = 10000;
    private  static final float MIN_GEOGRAPHIC_POOLING_DISTANCE = (float)5.0;
    public LocationManager gpsLocationManager;
    static Context context = null;
    boolean primeraVez=false;
    boolean boolHayGPS=true;

        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 
    	setContentView(R.layout.c_ruta);        
    	Intent i = getIntent();  			
			
//			idruta = bundle.getInt("idruta");

			setTitle("I Trade - Mi Ubicacion");
            //daoPara = new DAOParadero();
			//inicio de green Dao
	        //inicio green Dao
			 
			String IdCliente=(String)i.getSerializableExtra("idcliente"); 
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
										
				    listaCliente	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());		
				
	        	        
	        //fin green dao
	       // listaCliente=
//            listaCliente= daoPara.getAllParaderos(idruta);//idRuta
            listaGeoPoint=this.Convierte(listaCliente);

            mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

            final RelativeLayout rl = new RelativeLayout(this);

            this.mOsmv = new MapView(this, 256);
            rl.addView(this.mOsmv, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                            LayoutParams.FILL_PARENT));

            /* Itemized Overlay */
            {
                    /* Create a static ItemizedOverlay showing a some Markers on some cities. */


                    /* OnTapListener for the Markers, shows a simple Toast. */
                    this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                              				//single tap
                                            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                                                    Toast.makeText(
                                                                    RutaCliente.this,
                                                                    item.mDescription+"-"+item.getTitle() , Toast.LENGTH_LONG).show();
                                                    return true; // We 'handled' this event.
                                            }

                                            //long pressed
                                            public boolean onItemLongPress(final int index, final OverlayItem item) {
                                                   
                                                    return false;
                                            }
                                    }, mResourceProxy);
                    this.mOsmv.getOverlays().add(this.mMyLocationOverlay);
            }

            this.setContentView(rl);
            mOsmv.setBuiltInZoomControls(true);
            mOsmv.setMultiTouchControls(true);
  
            mapController = mOsmv.getController();
            //mapController.setZoom(5);//-12.071208,-77.077569
          			
            GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp  
//            GeoPoint gPt0 = new GeoPoint(49406100,8715140);//Alemania  
         //   mapController.setCenter(gPt0);
            mapController.setZoom(13);
            //////////////////////////////////////////////////////LAYER DE RUTA
            myPath = new PathOverlay(Color.BLACK, this);
            cargarGeoPoints();      
            if (this.listaGeoPoint!=null)
            	mapController.setCenter(gPt0);
            mOsmv.getOverlays().add(myPath);
          ////////////////////////////////////////////////////////LAYER DE POSICION ACTUAL
            this.posicionActualOverlay = new SimpleLocationOverlay(this);
            
            mOsmv.getOverlays().add(posicionActualOverlay);

    }




    @Override
    public boolean onMenuItemSelected(final int featureId, final MenuItem item) {

            return false;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void cargarGeoPoints() {
		// TODO Auto-generated method stub
    	for(int i=0;i<this.listaGeoPoint.size();i++){
    		myPath.addPoint(this.listaGeoPoint.get(i));
    	}
    	if (listaGeoPoint.isEmpty()){
    		listaGeoPoint=null;
    	}
    		
		
	}
	private List<GeoPoint> Convierte(List<Cliente> lis) {
		List<GeoPoint> lista=new ArrayList<GeoPoint>();;
		int i;
		for(i=0;i<lis.size();i++){
			posxdouble=lis.get(i).getLatitud()*factor;
			posydouble=lis.get(i).getLongitud()*factor;
			posxint=(int)posxdouble;
			posyint=(int)posydouble;
			GeoPoint aux = new GeoPoint(posxint,posyint);
			lista.add(aux);
		}
		if (!lis.isEmpty()){
			//inicio cambios icono por defecto
//			items.add(new OverlayItem(lis.get(0).getNombre(), "SampleDescription1", lista.get(0)));
//	        //icono customizado        
//	        OverlayItem olItem = new OverlayItem(lis.get(i-1).getNombre(), "SampleDescription", lista.get(i-1));
//	        Drawable newMarker = this.getResources().getDrawable(R.drawable.marker);
//	        olItem.setMarker(newMarker);
//	        items.add(olItem);
	        //fin cambios
			for(i=0;i<lis.size();i++){
				items.add(new OverlayItem(lis.get(i).getNombre()+" "+lis.get(i).getApePaterno(), "Cliente", lista.get(i)));
			}  
		}


		return lista;
	}
    // ===========================================================
    // Inner and Anonymous Classes

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
		//Do something to the UI thread here
		obtenerUbicacion();
	    mOsmv.invalidate();       	  
	    mHandler.removeCallbacks(Timer_Tick);
	    mHandler.postDelayed(this, 4000);
		}
		
	};
	      
	private void obtenerUbicacion() {
		boolean isavailable;
		if (boolHayGPS){
			isavailable = gpsLocationManager.isProviderEnabled(GPSPROVIDER);//error	
		}
		else
			isavailable=false;

        if(isavailable) {

            Location loc = gpsLocationManager.getLastKnownLocation(GPSPROVIDER);

            if(loc != null) {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                if (latitude!=0){
                	GeoPoint p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));
                	
                	posicionActualOverlay.setLocation(p);
                	cargarGeoPoints();
                	myPath.addPoint(posicionActualOverlay.getMyLocation());
                	if(primeraVez){
                		mapController.setCenter(p);
                		primeraVez=false;
                	}        			
                    //Toast.makeText(MiUbicacionImplActivity.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
                }                
            }
            else
            	Toast.makeText(RutaCliente.this,"Error GPS", Toast.LENGTH_LONG).show();
        }
        else
        	Toast.makeText(RutaCliente.this,"Encienda el GPS, y salga fuera del edificio por favor.", Toast.LENGTH_LONG).show();
	}

	 private Boolean displayGpsStatus() {  
		  ContentResolver contentResolver = getBaseContext()  
		  .getContentResolver();  
		  boolean gpsStatus = Settings.Secure  
		  .isLocationProviderEnabled(contentResolver,   
		  LocationManager.GPS_PROVIDER);  
		  if (gpsStatus) {  
		   return true;  
		  
		  } else {  
		   return false;  
		  }  
	} 


	 

	 
	@Override
	protected void onDestroy() {
		//cursor.close();
		//db.close();
		super.onDestroy();
		mHandler.removeCallbacks(Timer_Tick);
	}

	@Override
	public void onResume() {
	    super.onResume();
        ///////////////////////////////////////////////////////////////////////timer
        mHandler.removeCallbacks(Timer_Tick);
	    mHandler.postDelayed(Timer_Tick, 30000); //cada 30 segundos connsulta a la BD
		//////////////////////////////////////////////////////////// fin timer
	    primeraVez=true;
	    if(displayGpsStatus()){
	        context = this;
	       
	        /*Start Location Service for GPS*/
	        gpsLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);	       

	        /*Register GPS listener with Location Manager*/
	        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 2, this);

	    }
	    else{
	    	Toast.makeText(RutaCliente.this, "GPS no encendido", Toast.LENGTH_LONG).show();
	    	boolHayGPS=false;
	    }

	}
    // ===========================================================
	
	@Override
	  public void onPause() {
//	    myLocation.cancelTimer();
	    super.onPause();
	    if (mHandler!=null)
	    	mHandler.removeCallbacks(Timer_Tick);
	    if (gpsLocationManager!=null)
	    	gpsLocationManager.removeUpdates(this);
	  }



	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		if (loc!=null){
			double lati = loc.getLatitude();
			double longi = loc.getLongitude();
	        GeoPoint p = new GeoPoint((int) (lati * 1000000), (int) (longi * 1000000));
	//       myPath.getPaint().
	        posicionActualOverlay.setLocation(p);
	      myPath.clearPath();
	      myPath.addPoint(posicionActualOverlay.getMyLocation());
	    //  myPath.addPoint()
	    	if (primeraVez){
	    		mapController.setCenter(p);
	    		primeraVez=false;
	    	}			
			Log.e("cambio pos","lat:" +lati+" "+ longi);
		}				
	}



	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
