package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.SimpleLocationOverlay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.PedidoLinea;
import com.itrade.model.ProspectoDao.Properties;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.Prospecto;
import com.itrade.model.ProspectoDao;



public class UbicacionProspectoActivity extends Activity implements LocationListener {

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
	private final int  MAXERRORES = 4;
	PopupWindow m_pw;
	public int j=0;
    private MapView mOsmv;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    List<GeoPoint> listaGeoPoint =null;// puntos para la ruta
    List<Prospecto> listaCliente =null;
    
    private ResourceProxy mResourceProxy;
    private final double factor=1000000;
  	public int posxint,posyint;
  	public double posxdouble,posydouble;
  	public double latitudAux=0;
  	public double longitudAux=0;
  	PathOverlay myPath;
    private MapController mapController;
    private SimpleLocationOverlay posicionActualOverlay;    
    final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();//puntos del mapa
    
    private ExtendedItemizedIconOverlay<OverlayItem> markerOverlay;//layer del punto elegido del mapa
    final List<OverlayItem> pList = new ArrayList<OverlayItem>();//punto elegido en el mapa
    OnItemGestureListener<OverlayItem> pOnItemGestureListener = new MyItemGestureListener<OverlayItem>();//listener del punto elegido
  
    //private Timer myTimer;
    private Handler mHandler = new Handler();
    //green dao
    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProspectoDao prospectoDao;

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
    Prospecto cliente= new Prospecto();
    private TextView txt_nombre;
    int numLayers;
    int contadorErrores=0;

        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
			Bundle bundle = getIntent().getExtras();
			idusuario = bundle.getLong("idusuario");
			
			setTitle("I Trade - Mi Ubicacion");
 
	        //inicio green Dao
	        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	        db = helper.getWritableDatabase();
	        daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
	        prospectoDao = daoSession.getProspectoDao();	        	        
	        //fin green dao
	        
            mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

            final RelativeLayout rl = new RelativeLayout(this);

            this.mOsmv = new MapView(this, 256);
            rl.addView(this.mOsmv, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                            LayoutParams.FILL_PARENT));
            this.setContentView(rl);
            mOsmv.setBuiltInZoomControls(true);
            mOsmv.setMultiTouchControls(true);
///////////////////////////////////
                        
	        listaCliente=prospectoDao.loadAll();
            if (listaCliente!=null){
            	listaGeoPoint=this.Convierte();
            }
            
                        
            mapController = mOsmv.getController();
            //mapController.setZoom(5);//-12.071208,-77.077569
          			
//            GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp  
////            GeoPoint gPt0 = new GeoPoint(49406100,8715140);//Alemania  
//            mapController.setCenter(gPt0);
            mapController.setZoom(13);
            //////////////////////////////////////////////////////LAYER DE RUTA
            myPath = new PathOverlay(Color.RED, this);
            cargarGeoPointsRuta();      
//            mOsmv.getOverlays().add(myPath);//layer de la ruta comentado
          ////////////////////////////////////////////////////////////LAYER DE POSICION ACTUAL
            this.posicionActualOverlay = new SimpleLocationOverlay(this);
            mOsmv.getOverlays().add(posicionActualOverlay);               
            ////////////////////////////////////////////////////////////LAYER DEL PUNTO ELEGIDO
            markerOverlay = new ExtendedItemizedIconOverlay<OverlayItem>(getApplicationContext(), pList, pOnItemGestureListener);
            this.mOsmv.getOverlayManager().add(markerOverlay);
            //////////////////////////////////////////////////////////FIN LAYER PUNTO ELEGIDO
            numLayers=this.mOsmv.getOverlays().size();
            numLayers++;// sumo uno por el layer de los WayPoints
            
            ///Punto de ubicacion inicial
            GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp  
//          GeoPoint gPt0 = new GeoPoint(49406100,8715140);//Alemania
            if (this.listaGeoPoint!=null)
            	mapController.setCenter(listaGeoPoint.get(0));
            else                	
            	mapController.setCenter(gPt0);
    }



	// ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================
    private void cargarGeoPointsRuta() {//aca cargo los puntos de la ruta
		// TODO Auto-generated method stub
    	if (listaGeoPoint!=null){
    		for(int i=0;i<this.listaGeoPoint.size();i++){
        		myPath.addPoint(this.listaGeoPoint.get(i));
        	}
        	if (listaGeoPoint.isEmpty()){
        		listaGeoPoint=null;
        	}
    	}        		
		
	}
	private List<GeoPoint> Convierte() {
		
		List<GeoPoint> lista=new ArrayList<GeoPoint>();;
		int i;
		for(i=0;i<listaCliente.size();i++){
			posxdouble=listaCliente.get(i).getLatitud()*factor;
			posydouble=listaCliente.get(i).getLongitud()*factor;
			posxint=(int)posxdouble;
			posyint=(int)posydouble;
			GeoPoint aux = new GeoPoint(posxint,posyint);
			lista.add(aux);
		}
		if (!listaCliente.isEmpty()){
			items.clear();
			for(i=0;i<listaCliente.size();i++){
				if (listaCliente.get(i).getActivo().compareTo("A")==0){
//					items.add(new OverlayItem(listaCliente.get(i).getRazon_Social(), ""+listaCliente.get(i).getIdCliente(), lista.get(i)));
					OverlayItem olItem = new OverlayItem(listaCliente.get(i).getRazon_Social(), ""+listaCliente.get(i).getIdProspecto(), lista.get(i));
			        Drawable newMarker = this.getResources().getDrawable(R.drawable.greenmarker3);
			        olItem.setMarker(newMarker);
			        items.add(olItem);
				}
				if (listaCliente.get(i).getActivo().compareTo("N")==0){
			        OverlayItem olItem = new OverlayItem(listaCliente.get(i).getRazon_Social(), ""+listaCliente.get(i).getIdProspecto(), lista.get(i));
			        Drawable newMarker = this.getResources().getDrawable(R.drawable.pinkmarker2);
			        olItem.setMarker(newMarker);
			        items.add(olItem);
				}
				
			}  
		}
		else
			return null;
					
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
	    if (contadorErrores==MAXERRORES){
		    if (mHandler!=null)
		    	mHandler.removeCallbacks(Timer_Tick);  	
	    }
		}
		
	};
	      
	private void obtenerUbicacion() {//metodo que ejecuta el timer cada cierto tiempo
		
		boolean isavailable;
		if (boolHayGPS){
			isavailable = gpsLocationManager.isProviderEnabled(GPSPROVIDER);//error	
		}
		else
			isavailable=false;
      if(!isavailable) {
    	  if (contadorErrores==0||
      	  	  contadorErrores==1||
      	  	  contadorErrores==2)//con cero o un errores aumento el contador
      	  			this.contadorErrores++;            	
          if(contadorErrores==1||contadorErrores==2)
        	  Toast.makeText(UbicacionProspectoActivity.this,"Asegurese de tener el " +
        	  		"GPS Encendido", Toast.LENGTH_LONG).show();            	
      }
//        if(isavailable) {
        if(gpsLocationManager!=null) {	
            Location loc = gpsLocationManager.getLastKnownLocation(GPSPROVIDER);

            if(loc != null) {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                if (latitude!=0){
                	GeoPoint p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));
                	posicionActualOverlay.setLocation(p);
                	if(primeraVez){
                		mapController.setCenter(p);
                		mapController.setZoom(16);
                		primeraVez=false;
                	}        			
                    //Toast.makeText(MiUbicacionImplActivity.this,"Longitude is  "+longitude + "   Latitude is   "+latitude, Toast.LENGTH_LONG).show();
                }                
            }
            else{
            	this.contadorErrores++;
            	Toast.makeText(UbicacionProspectoActivity.this,"Encienda el GPS," +
        				" y salga al aire libre por favor.", Toast.LENGTH_LONG).show();
            }
            	
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
//	        listaCliente=clienteDao.loadAll();
        	if (listaCliente!=null){
        		listaGeoPoint=this.Convierte();
        	}	        
        
        
        ////////////////////////////////////
        /* Itemized Overlay */
        {
                /* Create a static ItemizedOverlay showing a some Markers on some cities. */


                /* OnTapListener for the Markers, shows a simple Toast. */
                this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                          				//single tap
                                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                                                Toast.makeText(
                                                                UbicacionProspectoActivity.this,
                                                                item.mTitle , Toast.LENGTH_SHORT).show();
//                                                encuentraCliente(item.mDescription);
                                                return true; // We 'handled' this event.
                                        }


										//long pressed
                                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                                        		
//                                                Toast.makeText(
//                                                		UbicacionCheckInActivity.this,
//                                                                item.mTitle, Toast.LENGTH_LONG).show();
//                                                encuentraCliente(item.mDescription);//mDescription es IdCliente
//                                                HacerCheckIn();//error
                                                return false;
                                        }
                                }, mResourceProxy);
                this.mOsmv.getOverlays().add(this.mMyLocationOverlay);
//                int nuevoTamanio=this.mOsmv.getOverlays().size();
//                if(nuevoTamanio>numLayers){
//                	this.mOsmv.getOverlays().remove(numLayers-1);
//                }
                              
                mOsmv.invalidate();
        }

               
        /////////////////////////////////////
                                        
        
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
	    	Toast.makeText(UbicacionProspectoActivity.this, "GPS no encendido", Toast.LENGTH_LONG).show();
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

	
//	public void HacerCheckIn() {		
//		// TODO Auto-generated method stub
//		
//   	 // Inicio del popup
//   	 LayoutInflater inflater = (LayoutInflater)
//        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//   	 View layout = inflater.inflate(R.layout.mypopupcheckin,
//   	 (ViewGroup) findViewById(R.id.MyLinearLayoutCheckIn));
//   	 txt_nombre  = (TextView) layout.findViewById(R.id.txtcheckin);   	 
//   	 m_pw = new PopupWindow( layout,  350,  250,  true);
//   	 txt_nombre.setText("Check In con "+cliente.getRazon_Social()+"?");
//   	 m_pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
//
//		
//	}
//    private void encuentraCliente(String strIdCliente) {
//		// TODO Auto-generated method stub    	
//        List<Prospecto> clientesAux = prospectoDao.queryBuilder()
//        		.where(Properties.IdProspecto.eq(strIdCliente))
//        		.orderAsc(Properties.Id).list();
//        
//        if (clientesAux!=null){
//        	if(clientesAux.size()>0){
//        		this.cliente=clientesAux.get(0);
//        	}
//        	else
//            	cliente.setRazon_Social("Error");
//        }
//	}
    
//	public void onButtonInPopup (View target) {
//		m_pw.dismiss();
//		if(hayUbicacion()){
//			if(estaCerca()){				
//				actualizaIconoCliente();
//				int temp=cliente.getIdCliente();				
//			    Intent intent = new Intent(UbicacionProspectoActivity.this, DetalleCliente.class);
//			    intent.putExtra("idcliente", temp);
//			    intent.putExtra("idusuario", idusuario);
//			    startActivity(intent);
//			}
//			else{
//				Toast.makeText(
//			             UbicacionProspectoActivity.this,
//			                    "Cliente muy lejos de la Ubicacion actual, elija otro por favor" , Toast.LENGTH_LONG).show();
//			}
//		     
//			
//		}
//		else{
//            Toast.makeText(
//             UbicacionProspectoActivity.this,
//                    "No se capturo la posicion, Intente nuevamente." , Toast.LENGTH_LONG).show();
//		}
//	    
//
//	}

	private boolean hayUbicacion() {
		boolean resul=false;
		GeoPoint punto = posicionActualOverlay.getMyLocation();
		if (punto!=null){
			if (punto.getLatitudeE6()!=0){
				resul=true;				
			}
		}                    
		// TODO Auto-generated method stub
		return resul;
	}



//	private void actualizaIconoCliente() {
//		long longTemp=cliente.getId();
//		Cliente clienteTemp=clienteDao.loadByRowId(longTemp);
//		clienteTemp.setActivo("C");
//		prospectoDao.deleteByKey(longTemp);
//		prospectoDao.insert(clienteTemp);
//	}



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
	
	
	
	
	@Override
	public void onBackPressed() {
		
	    new AlertDialog.Builder(this)
        .setTitle("Confirmar Ubicacion")
        .setMessage("Desea confirmar la Ubicacion?")
        .setNegativeButton("No", null)
        .setPositiveButton("Si", new OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            	if (markerOverlay.size()>0){
            		//Toast.makeText(UbicacionProspectoActivity.this,""+markerOverlay.getItem(0).getPoint().getLatitudeE6()+" "+markerOverlay.getItem(0).getPoint().getLongitudeE6(), Toast.LENGTH_LONG).show();
            		UbicacionProspectoActivity.super.onBackPressed();
            	}
            	else
            		Toast.makeText(UbicacionProspectoActivity.this,"Primero Debe seleccionar una Ubicacion", Toast.LENGTH_LONG).show();
            }
        }).create().show();	
	}
	@Override
	public void finish(){
		double la=0;
		double lo=0;
		if (markerOverlay.size()>0){
			la=markerOverlay.getItem(0).getPoint().getLatitudeE6()/FACTOR;
		    lo=markerOverlay.getItem(0).getPoint().getLongitudeE6()/FACTOR;
		}
		Bundle extras = new Bundle();
		extras.putDouble("latitud",la);
		extras.putDouble("longitud", lo);
		Intent intent = new Intent();
		intent.putExtras(extras);
		setResult(RESULT_OK,intent);
		super.finish();			
	}
	
}

