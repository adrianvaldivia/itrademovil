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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itrade.R;
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.PedidoLinea;
import com.itrade.model.ClienteDao.Properties;
import com.itrade.model.DaoMaster.DevOpenHelper;



public class UbicacionCheckInActivity extends Activity implements LocationListener {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final int MENU_ZOOMIN_ID = 10;
//    private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

    // ===========================================================
    // Fields
    // ===========================================================
//	private final Double  TOLERANCIA = 0.0004;
	private final Double  TOLERANCIA = 0.004;
	private final Double  FACTOR = 1000000.0;
	PopupWindow m_pw;
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
    //green dao
    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ClienteDao clienteDao;

    SimpleCursorAdapter adapter;
    // fin de green dao

    String GPSPROVIDER = LocationManager.GPS_PROVIDER;
    private  static final long MIN_GEOGRAPHIC_POOLING_TIME_PERIOD = 10000;
    private  static final float MIN_GEOGRAPHIC_POOLING_DISTANCE = (float)5.0;
    public LocationManager gpsLocationManager;
    static Context context = null;
    boolean primeraVez=false;
    boolean boolHayGPS=true;
    public long idusuario;
    Cliente cliente= new Cliente();
    private TextView txt_nombre;

        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
			Bundle bundle = getIntent().getExtras();
			idusuario = bundle.getLong("idusuario");
			
//			idruta = bundle.getInt("idruta");

			setTitle("I Trade - Mi Ubicacion");
            //daoPara = new DAOParadero();
			//inicio de green Dao
	        //inicio green Dao
	        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	        db = helper.getWritableDatabase();
	        daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
	        clienteDao = daoSession.getClienteDao();
	        	        
	        //fin green dao
	        listaCliente=clienteDao.loadAll();
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
                                                                    UbicacionCheckInActivity.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    encuentraCliente(item.mDescription);
                                                    return true; // We 'handled' this event.
                                            }


											//long pressed
                                            public boolean onItemLongPress(final int index, final OverlayItem item) {
                                            		
//                                                    Toast.makeText(
//                                                    		UbicacionCheckInActivity.this,
//                                                                    item.mTitle, Toast.LENGTH_LONG).show();
                                                    encuentraCliente(item.mDescription);//mDescription es IdCliente
                                                    HacerCheckIn();//error
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
//            ///////////////////////////////////////////////////////////////////////timer
//            mHandler.removeCallbacks(Timer_Tick);
//		    mHandler.postDelayed(Timer_Tick, 40000); //cada 30 segundos connsulta a la BD
//    		//////////////////////////////////////////////////////////// fin timer
                     
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
				items.add(new OverlayItem(lis.get(i).getRazon_Social(), ""+lis.get(i).getIdCliente(), lista.get(i)));
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
                	if(primeraVez){
                		mapController.setCenter(p);
                		primeraVez=false;
                	}        			
                    //Toast.makeText(MiUbicacionImplActivity.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
                }                
            }
            else
            	Toast.makeText(UbicacionCheckInActivity.this,"Error GPS", Toast.LENGTH_LONG).show();
        }
        else
        	Toast.makeText(UbicacionCheckInActivity.this,"Encienda el GPS, y salga fuera del edificio por favor.", Toast.LENGTH_LONG).show();
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
	    	Toast.makeText(UbicacionCheckInActivity.this, "GPS no encendido", Toast.LENGTH_LONG).show();
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

	
	public void HacerCheckIn() {		
		// TODO Auto-generated method stub
		
   	 // Inicio del popup
   	 LayoutInflater inflater = (LayoutInflater)
        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	 View layout = inflater.inflate(R.layout.mypopupcheckin,
   	 (ViewGroup) findViewById(R.id.MyLinearLayoutCheckIn));
   	 txt_nombre  = (TextView) layout.findViewById(R.id.txtcheckin);   	 
   	 m_pw = new PopupWindow( layout,  350,  250,  true);
   	 txt_nombre.setText("Check In con "+cliente.getRazon_Social()+"?");
   	 m_pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

		
	}
    private void encuentraCliente(String strIdCliente) {
		// TODO Auto-generated method stub    	
        List<Cliente> clientesAux = clienteDao.queryBuilder()
        		.where(Properties.IdCliente.eq(strIdCliente))
        		.orderAsc(Properties.Id).list();
        
        if (clientesAux!=null){
        	if(clientesAux.size()>0){
        		this.cliente=clientesAux.get(0);
        	}
        	else
            	cliente.setRazon_Social("Error");
        }
	}
    
	public void onButtonInPopup (View target) {
		if(primeraVez==false){
			if(estaCerca()){
				int temp=cliente.getIdCliente();
			     Intent intent = new Intent(UbicacionCheckInActivity.this, DetalleCliente.class);
			     intent.putExtra("idcliente", temp);
			     intent.putExtra("idusuario", idusuario);
			     startActivity(intent);
			}
			else{
				Toast.makeText(
			             UbicacionCheckInActivity.this,
			                    "Cliente muy lejos de la Ubicacion actual, elija otro por favor" , Toast.LENGTH_LONG).show();
			}
		     
			
		}
		else{
            Toast.makeText(
             UbicacionCheckInActivity.this,
                    "No se capturo la posicion, Intente nuevamente." , Toast.LENGTH_LONG).show();
		}
	    m_pw.dismiss();

	}

	public void onButtonCancelarPopup (View target) {
	    m_pw.dismiss();
	}
	
	private boolean estaCerca() {
		boolean resul=false;		
        GeoPoint punto = posicionActualOverlay.getMyLocation();
        
        double resta1=0;
        double resta2=0;
        resta1=punto.getLatitudeE6()/FACTOR;
        resta1=resta1-cliente.getLatitud();
        resta2=punto.getLongitudeE6()/FACTOR;
        resta2=resta2-cliente.getLongitud();
        resta1=Math.abs (resta1);
        resta2=Math.abs (resta2);
        if(resta1<=TOLERANCIA && resta2<=TOLERANCIA)
            resul=true;
        else
            resul=false;
        
		// TODO Auto-generated method stub
		return resul;
	}

}

