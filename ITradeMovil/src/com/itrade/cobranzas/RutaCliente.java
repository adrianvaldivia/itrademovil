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
import com.itrade.jsonParser.AsTaskCargarRuta;
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
    boolean boolSeDibujo=false;

    private GeoPoint ubikCliente;
        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 
    	setContentView(R.layout.c_ruta);        
    	Intent i = getIntent();  						
    	setTitle("I Trade - Mi Ubicacion");

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
    		//TODO Auto-generated catch block
    		e.printStackTrace();
    	}	    	    			
    	Gson gson = new Gson();									
    	listaCliente = gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());		
    	listaGeoPoint=this.Convierte(listaCliente);
    	mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
    	final RelativeLayout rl = new RelativeLayout(this);
    	this.mOsmv = new MapView(this, 256);
        rl.addView(this.mOsmv, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));           
        {
        	this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                              				//single tap
	        	public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
	        		Toast.makeText(RutaCliente.this,item.mDescription+"-"+item.getTitle() , Toast.LENGTH_LONG).show();
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
          			
        GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp  
        mapController.setZoom(13);
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
	private List<GeoPoint> Convierte(List<Cliente> lis) {
		List<GeoPoint> lista=new ArrayList<GeoPoint>();;
		int i;
		for(i=0;i<lis.size();i++){
			posxdouble=lis.get(i).getLatitud()*factor;
			posydouble=lis.get(i).getLongitud()*factor;
			posxint=(int)posxdouble;
			posyint=(int)posydouble;
			GeoPoint aux = new GeoPoint(posxint,posyint);
			ubikCliente= aux;
			lista.add(aux);
		}
		if (!lis.isEmpty()){
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
                if (latitude!=0 && !boolSeDibujo){
                	GeoPoint p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));                	
                	posicionActualOverlay.setLocation(p);                	                                	               
                	if(primeraVez){
                		mapController.setCenter(p);
                		primeraVez=false;
                	}
                	//Pintar ruta
                	dibujaRuta(p,ubikCliente);
                    //Toast.makeText(RutaCliente.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
                }                
            }
            else
            	Toast.makeText(RutaCliente.this,"Error GPS", Toast.LENGTH_LONG).show();
        }
        else
        	Toast.makeText(RutaCliente.this,"Encienda el GPS, y salga fuera del edificio por favor.", Toast.LENGTH_LONG).show();
	}
	
	private void dibujaRuta(GeoPoint geoPointOrigen,GeoPoint geoPointDestino) {
		AsTaskCargarRuta _connectAsyncTask = new AsTaskCargarRuta(RutaCliente.this,mOsmv,
        		Color.parseColor(getResources().getString(R.color.Aqua)),
        		geoPointOrigen, geoPointDestino,this.mOsmv.getOverlays().size());        
        _connectAsyncTask.execute();
        boolSeDibujo=true;            
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
	        posicionActualOverlay.setLocation(p);
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
