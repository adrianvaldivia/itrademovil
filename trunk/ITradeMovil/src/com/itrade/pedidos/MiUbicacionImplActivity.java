package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

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

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.DaoMaster.DevOpenHelper;



public class MiUbicacionImplActivity extends Activity implements LocationListener {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final int MENU_ZOOMIN_ID = 10;
//    private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

    // ===========================================================
    // Fields
    // ===========================================================
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
  	PathOverlay myPath;
    private MapController mapController;
    private SimpleLocationOverlay posicionActualOverlay;
    final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    //private Timer myTimer;
    private Handler mHandler = new Handler();
    int idruta;
	int idunidad;
    //green dao
    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ClienteDao cliente2Dao;

    SimpleCursorAdapter adapter;
    // fin de green dao

    String GPSPROVIDER = LocationManager.GPS_PROVIDER;
    private  static final long MIN_GEOGRAPHIC_POOLING_TIME_PERIOD = 10000;
    private  static final float MIN_GEOGRAPHIC_POOLING_DISTANCE = (float)5.0;
    public LocationManager gpsLocationManager;
    static Context context = null;
    boolean primeraVez=false;


//    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 2; // in Meters
//    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 0; // in Milliseconds  
//    public LocationResult locationResult = new LocationResult(){
//        @Override
//        public void gotLocation(Location location){
//        	if (location!=null){
//        		latitudAux=location.getLatitude();
//        		longitudAux=location.getLongitude();
//        	}
//            //Got the location!
//        }
//    };
//  public MyLocation myLocation;// = new MyLocation(2000);

        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
			Bundle bundle = getIntent().getExtras();			
			
			idruta = bundle.getInt("idruta");
			idunidad =bundle.getInt("idunidad");
			setTitle("I Trade - Mi Ubicacion");
            //daoPara = new DAOParadero();
			//inicio de green Dao
	        //inicio green Dao
	        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	        db = helper.getWritableDatabase();
	        daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
	        cliente2Dao = daoSession.getClienteDao();
	        	        
	        //fin green dao
	        listaCliente=cliente2Dao.loadAll();
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
                                                                    MiUbicacionImplActivity.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    return true; // We 'handled' this event.
                                            }

                                            //long pressed
                                            public boolean onItemLongPress(final int index, final OverlayItem item) {
                                                    Toast.makeText(
                                                    		MiUbicacionImplActivity.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    //cambios chichan
                                                    Intent intent = new Intent(MiUbicacionImplActivity.this, BuscarProductos.class);
                                    				//intent.putExtra("idcliente", 3);//falta capturar el idcliente
                                    				startActivity(intent);
                                    				//fin cambios chichan
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
          			
//            GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp  
            GeoPoint gPt0 = new GeoPoint(49406100,8715140);//Alemania  
            mapController.setCenter(gPt0);
            mapController.setZoom(13);
            //////////////////////////////////////////////////////LAYER DE RUTA
            myPath = new PathOverlay(Color.RED, this);
            cargarGeoPoints();      
            if (this.listaGeoPoint!=null)
            	mapController.setCenter(gPt0);
            mOsmv.getOverlays().add(myPath);
          ////////////////////////////////////////////////////////LAYER DE POSICION ACTUAL
            this.posicionActualOverlay = new SimpleLocationOverlay(this);
            mOsmv.getOverlays().add(posicionActualOverlay);
//            if (this.listaGeoPoint!=null)
//            	posicionActualOverlay.setLocation(gPt0);
            ///////////////////////////////////////////////////////////////////////timer
            mHandler.removeCallbacks(Timer_Tick);
		    mHandler.postDelayed(Timer_Tick, 40000); //cada 30 segundos connsulta a la BD
    		//////////////////////////////////////////////////////////// fin timer
            
            //listeners para el GPS
//		    myLocation = new MyLocation(120000);//30 segundos



            

//            GeoPoint gPt1= new GeoPoint(49406100,8715140);//Ubico de la posicion actual
//            if (gPt1!=null){
//            	posicionActualOverlay.setLocation(gPt1);
//    			mapController.setCenter(gPt1);
//            }            
    }



	// ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass/Interfaces
    // ===========================================================

//    @Override
//    public boolean onCreateOptionsMenu(final Menu pMenu) {
//            pMenu.add(0, MENU_ZOOMIN_ID, Menu.NONE, "ZoomIn");
//            pMenu.add(0, MENU_ZOOMOUT_ID, Menu.NONE, "ZoomOut");
//
//            return true;
//    }

    @Override
    public boolean onMenuItemSelected(final int featureId, final MenuItem item) {
//            switch (item.getItemId()) {
//            case 0:
//                    this.mOsmv.getController().zoomIn();
//                    return true;
//
//            case 1:
//                    this.mOsmv.getController().zoomOut();
//                    return true;
//            }
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
				items.add(new OverlayItem(lis.get(i).getRazon_Social(), "SampleDescription1", lista.get(i)));
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
//		myLocation.getLocation(this, locationResult);//obtengo la ubicacion del listener
		
		boolean isavailable = gpsLocationManager.isProviderEnabled(GPSPROVIDER);

        if(isavailable) {
	        String mlocProvider;
	        Criteria hdCrit = new Criteria();

	        hdCrit.setAccuracy(Criteria.ACCURACY_COARSE);

	        mlocProvider = gpsLocationManager.getBestProvider(hdCrit, true);
//          Location loc = gpsLocationManager.getLastKnownLocation(mlocProvider);
//            Location loc = gpsLocationManager.getLastKnownLocation("gps");
            Location loc = gpsLocationManager.getLastKnownLocation(GPSPROVIDER);


            if(loc != null) {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                if (latitude!=0){
                	GeoPoint p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));
                	posicionActualOverlay.setLocation(p);
                	if(primeraVez){
                		mapController.setCenter(p);
                		primeraVez=false;
                	}        			
                    //Toast.makeText(MiUbicacionImplActivity.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
                }                
            }
            else
            	Toast.makeText(MiUbicacionImplActivity.this,"Error GPS", Toast.LENGTH_LONG).show();
        }				
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
		db.close();
		super.onDestroy();
		mHandler.removeCallbacks(Timer_Tick);
	}

	@Override
	public void onResume() {
	    super.onResume();
	    primeraVez=true;
	    if(displayGpsStatus()){
//	    	myLocation.getLocation(this, locationResult);	
	        context = this;
	       
//	        gpsLocationListener = new GpsLocationListener(this);

	        /*Start Location Service for GPS*/
	        gpsLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);	       

	        /*Register GPS listener with Location Manager*/
	        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 2, this);
//	        boolean isavailable = gpsLocationManager.isProviderEnabled(GPSPROVIDER);
//
//	        if(isavailable) {
//
////	            Location loc = gpsLocationManager.getLastKnownLocation("gps");
//	            Location loc = gpsLocationManager.getLastKnownLocation(GPSPROVIDER);
//
//	            if(loc != null) {
//	                double latitude = loc.getLatitude();
//	                double longitude = loc.getLongitude();
//	                GeoPoint p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));
//	            	posicionActualOverlay.setLocation(p);
//	    			mapController.setCenter(p);
//	                Toast.makeText(MiUbicacionImplActivity.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
//
//	            }
//	        }

	    }
	    else{
	    	Toast.makeText(MiUbicacionImplActivity.this, "No hay GPS", Toast.LENGTH_LONG).show();
	    }

	}
    // ===========================================================
	
	@Override
	  public void onPause() {
//	    myLocation.cancelTimer();
	    super.onPause();
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

