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
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
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

//public class VerMapaActivity extends Activity {
//    /** Called when the activity is first created. */
//    private MapController mapController;
//    private MapView mapView;
//    private SimpleLocationOverlay posicionActualOverlay;
//    private final double factor=1000000;
//    public int posxint,posyint;
//    public double posxdouble,posydouble;
//    int mIncr = 10000;
//    List<Paradero> listaParadero =null;
//    List<GeoPoint> listaGeoPoint =null;//ruta
//    PathOverlay myPath;
//    
//    DAOParadero daoPara =null;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        daoPara = new DAOParadero();
//        listaParadero= daoPara.getAllParaderos("4");
//        listaGeoPoint=this.Convierte(listaParadero);
//        setContentView(R.layout.mapalayout);
//        mapView = (MapView) findViewById(R.id.mapview);
//        mapView.setTileSource(TileSourceFactory.MAPNIK);
//        mapView.setBuiltInZoomControls(true);
//        mapView.setMultiTouchControls(true);
//
//        mapController = mapView.getController();
//        mapController.setZoom(15);//-12.071208,-77.077569
//        GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp
//
//        mapController.setCenter(gPt0);
//        
//        myPath = new PathOverlay(Color.RED, this);
//        cargarGeoPoints();
//        if (this.listaGeoPoint!=null)
//        	mapController.setCenter(this.listaGeoPoint.get(0));
//        mapView.getOverlays().add(myPath);
//        
//        this.posicionActualOverlay = new SimpleLocationOverlay(this);
//    	mapView.getOverlays().add(posicionActualOverlay);
//    	if (this.listaGeoPoint!=null)
//    		posicionActualOverlay.setLocation(this.listaGeoPoint.get(0));
//    }
//    private void cargarGeoPoints() {
//		// TODO Auto-generated method stub
//    	for(int i=0;i<this.listaGeoPoint.size();i++){
//    		myPath.addPoint(this.listaGeoPoint.get(i));
//    	}
//		
//	}
//	private List<GeoPoint> Convierte(List<Paradero> lis) {
//		List<GeoPoint> lista=new ArrayList<GeoPoint>();;
//		
//		for(int i=0;i<lis.size();i++){
//			posxdouble=lis.get(i).getPosx()*factor;
//			posydouble=lis.get(i).getPosy()*factor;
//			posxint=(int)posxdouble;
//			posyint=(int)posydouble;
//			GeoPoint aux = new GeoPoint(posxint,posyint);
//			lista.add(aux);
//		}
//		return lista;
//	}
//	protected boolean isRouteDisplayed() {
//        // TODO Auto-generated method stub
//        return false;
//    }
//} 
//final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
//items.add(new OverlayItem("Hannover", "SampleDescription", new GeoPoint(52370816, 9735936)));
//items.add(new OverlayItem("Berlin", "SampleDescription", new GeoPoint(52518333, 13408333)));
//items.add(new OverlayItem("Washington", "SampleDescription", new GeoPoint(38895000, -77036667)));
//items.add(new OverlayItem("San Francisco", "SampleDescription", new GeoPoint(37779300, -122419200)));
//items.add(new OverlayItem("Tolaga Bay", "SampleDescription", new GeoPoint(-38371000, 178298000)));

public class VerMapaActivity extends Activity {

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
			setTitle("I Trade - Ver Mapa");
            //daoPara = new DAOParadero();
			//inicio de green Dao
	        //inicio green Dao
	        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
	        db = helper.getWritableDatabase();
	        daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
	        cliente2Dao = daoSession.getClienteDao();
	        
	        // Segunda parte
//	        String textColumn = Cliente2Dao.Properties.Nombre.columnName;
//	        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
//	        cursor = db.query(cliente2Dao.getTablename(), cliente2Dao.getAllColumns(), null, null, null, null, orderBy);
//	        String[] from = { textColumn, Cliente2Dao.Properties.Apellidos.columnName };
//	        int[] to = { android.R.id.text1, android.R.id.text2 };
//	        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
//	                to);
	        
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
                                                                    VerMapaActivity.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    return true; // We 'handled' this event.
                                            }

                                            //long pressed
                                            public boolean onItemLongPress(final int index, final OverlayItem item) {
                                                    Toast.makeText(
                                                    		VerMapaActivity.this,
                                                                    item.mTitle , Toast.LENGTH_LONG).show();
                                                    //cambios chichan
                                                    Intent intent = new Intent(VerMapaActivity.this, BuscarProductos.class);
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
//            GeoPoint gPt0= daoPara.getPosicionActual(idunidad);//idUnidad ubico la unidad
//            if (gPt0!=null){
//            	posicionActualOverlay.setLocation(gPt0);
//    			mapController.setCenter(gPt0);
//            }            			
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
//            mHandler.removeCallbacks(Timer_Tick);
//		    mHandler.postDelayed(Timer_Tick, 9000); //cada 30 segundos connsulta a la BD
    		////////////////////////////////////////////////////////////
    }



	// ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean onCreateOptionsMenu(final Menu pMenu) {
//            pMenu.add(0, MENU_ZOOMIN_ID, Menu.NONE, "ZoomIn");
//            pMenu.add(0, MENU_ZOOMOUT_ID, Menu.NONE, "ZoomOut");

            return true;
    }

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
//	private void TimerMethod()
//	{
//		//This method is called directly by the timer
//		//and runs in the same thread as the timer.
//
//		//We call the method that will work with the UI
//		//through the runOnUiThread method.
//		this.runOnUiThread(Timer_Tick);
//	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			GeoPoint gPt0 =null;//pucp
		//This method runs in the same thread as the UI.    	       

		//Do something to the UI thread here
//			  Toast.makeText(
//                      VerMapaActivity.this,
//                      "Saludo prueba" , Toast.LENGTH_LONG).show();
	          if (j==10){
	        	  j=0;//solo cuando j es cero se mueve hacia el centro
	          }
			  //gPt0= daoPara.getPosicionActual(idunidad);//idUnidad
			  if (gPt0==null){
				  j++;
			  }
				  
//			  else{
//				  posicionActualOverlay.setLocation(gPt0);
//				  if (j==0){
//					  mapController.setZoom(16);
//					  //mapController.setCenter(gPt0);//cambio el centro del mapa					  
//				  }				   
//			  }				  			  
	          mOsmv.invalidate();
	          j++;	        	  
	          mHandler.removeCallbacks(Timer_Tick);
			  mHandler.postDelayed(this, 4000);
		}
		
	};
	@Override
	protected void onDestroy() {
		//cursor.close();
		db.close();
		super.onDestroy();
		mHandler.removeCallbacks(Timer_Tick);
	}
    // ===========================================================
}
