package com.itrade.cobranzas;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.osmdroid.views.overlay.SimpleLocationOverlay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.R;
import com.itrade.controller.cobranza.ClienteMapa;
import com.itrade.controller.cobranza.Syncronizar;


import android.app.Activity;



import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;   
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MapaClientes  extends Activity implements LocationListener, OnClickListener, OnInitListener {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final int MENU_ZOOMIN_ID = 10;
//    private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

    // ===========================================================
    // Fields
    // ===========================================================
	public int j=0;
	 private TextView txt_nombre;
	 private PopupWindow m_pw;
	 private GeoPoint cli;
    private MapView mOsmv;
    private String idCliente;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    List<GeoPoint> listaGeoPoint =null;//ruta
    List<ClienteMapa> listaCliente =null;
    private final Double  TOLERANCIA = 0.0018;	
	private OverlayItem itemActual;
	private final int  MAXERRORES = 5;
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
    
    // fin de green dao

    String GPSPROVIDER = LocationManager.GPS_PROVIDER;
    private  static final long MIN_GEOGRAPHIC_POOLING_TIME_PERIOD = 10000;
    private  static final float MIN_GEOGRAPHIC_POOLING_DISTANCE = (float)5.0;
    public LocationManager gpsLocationManager;
    static Context context = null;
    boolean primeraVez=false;
    boolean boolHayGPS=true;
    private ClienteMapa client; 
    int contadorErrores=0;
    /*Inicio agregado*/
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
	/*Fin agregado*/
        
    // ===========================================================
    // Constructors
    // ===========================================================
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 
    	setContentView(R.layout.c_mapa);        
    	Intent i = getIntent();
    	/*Inicio agregado*/
    	Intent checkTTSIntent = new Intent();
    	checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	    startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
	    /*Fin agregado*/
			
//			idruta = bundle.getInt("idruta");

			setTitle("I Trade - Mi Ubicacion");
            //daoPara = new DAOParadero();
			//inicio de green Dao
	        //inicio green Dao 
			 
			String IdCobrador=(String)i.getSerializableExtra("idusuario") ; 
			  Syncronizar sync = new Syncronizar(MapaClientes.this);
				List<NameValuePair> param = new ArrayList<NameValuePair>();								
				param.add(new BasicNameValuePair("idcobrador", IdCobrador));	
					//String route="dp2/itrade/ws/clientes/get_clientes_by_vendedor/";
					String route="ws/pedido/get_clientes_checkin";
				    sync.conexion(param,route);
				    try {
						sync.getHilo().join();			
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	    	    
				    
					Log.d("ClienteeeEder", "hhahahhahahhah1");
				    Gson gson = new Gson();
										
				    listaCliente	=	gson.fromJson(sync.getResponse(), new TypeToken<List<ClienteMapa>>(){}.getType());		
				    
	        	        
	        //fin green dao
	       // listaCliente=
//            listaCliente= daoPara.getAllParaderos(idruta);//idRuta
            listaGeoPoint=this.Convierte();
            Log.d("ClienteeeEder", "hhahahhahahhah2");
            mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

            final RelativeLayout rl = new RelativeLayout(this);

            this.mOsmv = new MapView(this, 256);
            rl.addView(this.mOsmv, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                            LayoutParams.FILL_PARENT));

            /* Itemized Overlay */
            {


                   
                    this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                              			
                                            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                                                    Toast.makeText(
                                                                    MapaClientes.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    speakWords(item.mTitle);
                                                    return true; 
                                            }

                                           
                                            public boolean onItemLongPress(final int index, final OverlayItem item) {
                                            	cli= item.getPoint();
                                            	idCliente= item.mDescription;
                                               	ChekIN(item);
												return true;
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
    
            if (this.listaGeoPoint!=null)
            	mapController.setCenter(gPt0);
    
            this.posicionActualOverlay = new SimpleLocationOverlay(this);
            mOsmv.getOverlays().add(posicionActualOverlay);

    }



    @Override
    public boolean onMenuItemSelected(final int featureId, final MenuItem item) {

            return false;
    }

  
    		
	
	private List<GeoPoint> Convierte() {
		
		List<GeoPoint> lista=new ArrayList<GeoPoint>();;
		int i;
		
		Log.d("Saleee de COnvertir", "EntroConvertir");
		
		for(i=0;i<listaCliente.size();i++){
			posxdouble=listaCliente.get(i).getLatitud()*factor;
			posydouble=listaCliente.get(i).getLongitud()*factor;
			posxint=(int)posxdouble;
			posyint=(int)posydouble;
			GeoPoint aux = new GeoPoint(posxint,posyint);
			lista.add(aux);
		}
		Log.d("Saleee de COnvertir", "hehehheheheh");
		
		if (!listaCliente.isEmpty()){
			items.clear();
			for(i=0;i<listaCliente.size();i++){
				Log.d("EstadoPedido", "forrrrrrrWhileee");         
				  
		/*		OverlayItem olItem = new OverlayItem("CLIENTE: "+listaCliente.get(i).getNombre() +" "+listaCliente.get(i).getApeMaterno(), ""+listaCliente.get(i).getIdCliente(), lista.get(i));
		        Drawable newMarker = this.getResources().getDrawable(R.drawable.skis1);		        
		        olItem.setMarker(newMarker);		        
		        items.add(olItem);*/
				  Log.d("LogCHEIN", ""+listaCliente.get(i).getCheckIn()); 
				if ((listaCliente.get(i).getCheckIn()==1)){ // azul
					OverlayItem olItem = new OverlayItem("CLIENTE: "+listaCliente.get(i).getNombre() +" "+listaCliente.get(i).getApeMaterno()+" Direccion="+listaCliente.get(i).getDireccion() , ""+listaCliente.get(i).getIdCliente(), lista.get(i));
			        Drawable newMarker = this.getResources().getDrawable(R.drawable.greenmarker3);
			        olItem.setMarker(newMarker);
			        items.add(olItem);
			        Log.d("entra", "NULL 1 1");					
				}
				else {			//((listaCliente.get(i).getCheckIN()==null) || (listaCliente.get(i).getCheckIN()==0) )	
					OverlayItem olItem = new OverlayItem("CLIENTE: "+listaCliente.get(i).getNombre() +" "+listaCliente.get(i).getApeMaterno()+" Direccion="+listaCliente.get(i).getDireccion(), ""+listaCliente.get(i).getIdCliente(), lista.get(i));
			        Drawable newMarker = this.getResources().getDrawable(R.drawable.pinkmarker4); //rojo
			        olItem.setMarker(newMarker);
			        items.add(olItem);
			        Log.d("entra", "NULL O 0");
				}
						
				
				
			}  
		}
		else
			return null;
					
		return lista;
	}
				
				
			
	
  
	private Runnable Timer_Tick = new Runnable() {
		public void run() {	
		obtenerUbicacion();
	    mOsmv.invalidate();       	  
	    mHandler.removeCallbacks(Timer_Tick);
	    mHandler.postDelayed(this, 4000);
		}
		
	};
	      
	/*private void obtenerUbicacion() {
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
                  
                }                
            }
            else
            	Toast.makeText(MapaClientes.this,"Error GPS", Toast.LENGTH_LONG).show();
        }
        else
        	Toast.makeText(MapaClientes.this,"Encienda el GPS, y salga fuera del edificio por favor.", Toast.LENGTH_LONG).show();
	}
	*/
private void obtenerUbicacion() {
		
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
//          	if(markerOverlay.size()>0)
//          		Toast.makeText(UbicacionCheckInActivity.this,""+markerOverlay.getItem(0).getPoint().getLatitudeE6()+" "+markerOverlay.getItem(0).getPoint().getLongitudeE6(), Toast.LENGTH_LONG).show();
          	if(contadorErrores==1||contadorErrores==2)
          		Toast.makeText(MapaClientes.this,"No se detecta el GPS", Toast.LENGTH_LONG).show();
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
//                	Toast.makeText(UbicacionCheckInActivity.this,"capture la posicion", Toast.LENGTH_SHORT).show();
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
            	Toast.makeText(MapaClientes.this,"Encienda el GPS," +
          				" y salga al aire libre por favor.", Toast.LENGTH_LONG).show();            	
            }
            	
        }
        else{
        	this.contadorErrores++;
        	Toast.makeText(MapaClientes.this,"Error GPS", Toast.LENGTH_LONG).show();
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

		private void ChekIN(OverlayItem item) {
			 LayoutInflater inflater = (LayoutInflater)
				        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   	 View layout = inflater.inflate(R.layout.checkin, (ViewGroup) findViewById(R.id.txtcheckinCobranza));
				   	 txt_nombre  = (TextView) layout.findViewById(R.id.txtcheckinCobranza);   	 
				   	 m_pw = new PopupWindow( layout,  350,  250,  true);
				   	 txt_nombre.setText("Check In con "+item.getTitle()+"?");
				   	 m_pw.showAtLocation(layout, Gravity.CENTER, 0, 0);			
				   	itemActual=item;
		}
	 

	 
	@Override
	protected void onDestroy() {
	
	
		super.onDestroy();
		mHandler.removeCallbacks(Timer_Tick);
	}
	
	private boolean hayUbicacion() {
		
		GeoPoint punto = posicionActualOverlay.getMyLocation();
		if (punto!=null){
			if (punto.getLatitudeE6()!=0){
				return true;				
			}
		}                    
		
		return false;
	}
	
	private boolean estaCerca() {
		boolean resul=false;		
        GeoPoint punto = posicionActualOverlay.getMyLocation();
        
    	Log.d("BotonsOKkkk", "CheckINPunto");
        double resta1=0;
        double resta2=0;
        Log.d("BotonsLAtitud", ""+punto.getLatitudeE6());
        resta1=punto.getLatitudeE6();
        resta1=resta1-cli.getLatitudeE6();
        resta2=punto.getLongitudeE6();
        resta2=resta2-cli.getLongitudeE6();
        

        resta1=Math.abs (resta1)/factor;
        resta2=Math.abs (resta2)/factor;
    
 
        
        Log.d("BotonsRestaaa", resta1 +"-"+resta2+"-Tolerancia:"+ TOLERANCIA);
        Log.d("BotonsOKkkk", "TRUE");
        if((resta1<TOLERANCIA) && (resta2 < TOLERANCIA))
            return true;
    	
        else           
        	Log.d("BotonsOKkkk", "False");
		return resul;
        
	}
	public void onButtonCancelarPopupCobranza(View target) {
		m_pw.dismiss();
	}
	
	public void onButtonInPopupCobranza (View target) {
		m_pw.dismiss();
		if(hayUbicacion()){
			
			if(estaCerca()){				
		actualizaIconoCliente(idCliente);
			 
			}
			else{
				Toast.makeText(
			             MapaClientes.this,
			                    "Debe acercarse más a la posición del cliente!" , Toast.LENGTH_LONG).show();
			}
		     
			 
		}  
		else{
            Toast.makeText(
            		MapaClientes.this,
                    "Encienda GPS!" , Toast.LENGTH_LONG).show();
		}
	      
		Log.d("BotonsOKkkk", "CheckIN");  
	}
	private void actualizaIconoCliente(String idCliente2) {
	//	http://200.16.7.111/dp2/itrade/ws/cobranza/checkin/
		//aqui actualizasa con el ws  
		String IdCliente=idCliente2;//(String)i.getSerializableExtra("idusuario"); 
		  Syncronizar sync = new Syncronizar(MapaClientes.this);
			List<NameValuePair> param = new ArrayList<NameValuePair>();								
			param.add(new BasicNameValuePair("idcliente", IdCliente));	
				//String route="dp2/itrade/ws/clientes/get_clientes_by_vendedor/";
				String route="ws/cobranza/checkin";
			    sync.conexion(param,route);  
			    try {  
					sync.getHilo().join();			
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			    Log.d("Responsee", sync.getResponse());
			    
			    			    Log.d("TAGgggCHEIN", "Cambia Icono");			   			 
		        Drawable newMarker = this.getResources().getDrawable(R.drawable.greenmarker3);		        
		        itemActual.setMarker(newMarker);
		        		       
	}



	@Override
	public void onResume() {
	    super.onResume();
     
        mHandler.removeCallbacks(Timer_Tick);
	    mHandler.postDelayed(Timer_Tick, 30000); //cada 30 segundos connsulta a la BD

	    primeraVez=true;
	    if(displayGpsStatus()){
	        context = this;
	       
	        /*Start Location Service for GPS*/
	        gpsLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);	       

	        /*Register GPS listener with Location Manager*/
	        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 2, this);

	    }
	    else{
	    	Toast.makeText(MapaClientes.this, "GPS no encendido", Toast.LENGTH_LONG).show();
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



	public void onInit(int initStatus) {
		// TODO Auto-generated method stub
		if (initStatus == TextToSpeech.SUCCESS) {
			if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
				myTTS.setLanguage(Locale.US);
		}
		else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				//the user has the necessary data - create the TTS
			myTTS = new TextToSpeech(this, this);
			}
			else {
					//no data - install it now
				Intent installTTSIntent = new Intent();
				installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}
/*
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		EditText enteredText = (EditText)findViewById(R.id.enter2);
    	String words = enteredText.getText().toString();	    		    
    	speakWords(words);
		
	}
	*/
	private void speakWords(String speech) {

		//speak straight away
    	myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
}



	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}