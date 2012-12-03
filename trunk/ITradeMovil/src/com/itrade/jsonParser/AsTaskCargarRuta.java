package com.itrade.jsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itrade.R;
import com.itrade.pedidos.UbicacionCheckInActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;


public class AsTaskCargarRuta extends AsyncTask<String, Void, String>
{
    Document doc;
    GeoPoint srcGeoPoint,destGeoPoint;
    List<GeoPoint> _geopoints=null;
    PathOverlay myPath;
    private Activity activity;
    int colorRuta=0;
    int numAlpha=0;
    private MapView mOsmv;
    int nuevoTamanio;
    int numLayersActuales;
    int numLayersBase;
    
	 public AsTaskCargarRuta(Activity activ,MapView mOsmvv,int colorRut,
			 				GeoPoint orig, GeoPoint destino,int numLayersBas) {		 
	        this.activity = activ;		
	        this.mOsmv=mOsmvv;
	        this.colorRuta=colorRut;
	        this.srcGeoPoint=orig;
	        this.destGeoPoint=destino;
	        this.numLayersActuales=this.mOsmv.getOverlays().size();//
	        this.numLayersBase=numLayersBas;
	        this.numAlpha=170;
	    }
	 private ProgressDialog progressDialog;
     @Override
     protected void onPreExecute() {
         // TODO Auto-generated method stub
         super.onPreExecute();
         
         myPath = new PathOverlay(colorRuta, activity);
         Paint paintTemp=myPath.getPaint();
         paintTemp.setStrokeWidth(5.0f);
         paintTemp.setAlpha(numAlpha);
         myPath.setPaint(paintTemp);
         doc = null;
         
         
         progressDialog = new ProgressDialog(activity);
         progressDialog.setMessage("Obteniendo la Ruta, Espere por favor...");
         progressDialog.setIndeterminate(true);
         progressDialog.show();
         
         
     }
	@Override
	protected String doInBackground(String... arg0) {
	    // TODO Auto-generated method stub
        fetchData();
        return null;
	}
	 @Override
	protected void onPostExecute(String result) {
		 super.onPostExecute(result); 
         if(doc!=null){
//           Overlay ol = new MyOverlay(_activity,srcGeoPoint,srcGeoPoint,1);
//           mOverlays.add(ol);
           NodeList _nodelist = doc.getElementsByTagName("status");
           Node node1 = _nodelist.item(0);
           String _status1  = node1.getChildNodes().item(0).getNodeValue();
           if(_status1.equalsIgnoreCase("OK")){
               NodeList _nodelist_path = doc.getElementsByTagName("overview_polyline");
               Node node_path = _nodelist_path.item(0);
               Element _status_path = (Element)node_path;
               NodeList _nodelist_destination_path = _status_path.getElementsByTagName("points");
               Node _nodelist_dest = _nodelist_destination_path.item(0);
               String _path  = _nodelist_dest.getChildNodes().item(0).getNodeValue();
               _geopoints = decodePoly(_path);
               GeoPoint gp1;
               GeoPoint gp2;
               gp2 = _geopoints.get(0);
               Log.d("_geopoints","::"+_geopoints.size());
               for(int i=1;i<_geopoints.size();i++) // the last one would be crash
               {

                   gp1 = gp2;
                   gp2 = _geopoints.get(i);
//                   Overlay ol1 = new MyOverlay(gp1,gp2,2,Color.BLUE);
//                   mOverlays.add(ol1);
               }
//               Overlay ol2 = new MyOverlay(_activity,destGeoPoint,destGeoPoint,3);
//               mOverlays.add(ol2);
               myPath.clearPath();
               cargarGeoPointsRuta();
//               nuevoTamanio=this.mOsmv.getOverlays().size();
               if(numLayersActuales>numLayersBase){
                  	this.mOsmv.getOverlays().remove(numLayersActuales-1);
                  }
               mOsmv.getOverlays().add(myPath);//layer de la ruta comentado               
               mOsmv.invalidate();
               progressDialog.dismiss();
           }else{
               showAlert("No se pudo encontrar la ruta");
               progressDialog.dismiss();
           }

//           Overlay ol2 = new MyOverlay(_activity,destGeoPoint,destGeoPoint,3);
//           mOverlays.add(ol2);
           progressDialog.dismiss();
//           mapView.scrollBy(-1,-1);
//           mapView.scrollBy(1,1);
       }else{
           showAlert("No se pudo encontrar la ruta");
           progressDialog.dismiss();
       }

	}
    private void fetchData()
    {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.google.com/maps/api/directions/xml?origin=");
        urlString.append( Double.toString((double)srcGeoPoint.getLatitudeE6()/1.0E6 ));
        urlString.append(",");
        urlString.append( Double.toString((double)srcGeoPoint.getLongitudeE6()/1.0E6 ));
        urlString.append("&destination=");//to
        urlString.append( Double.toString((double)destGeoPoint.getLatitudeE6()/1.0E6 ));
        urlString.append(",");
        urlString.append( Double.toString((double)destGeoPoint.getLongitudeE6()/1.0E6 ));
        urlString.append("&sensor=true&mode=driving");   
        Log.d("url","::"+urlString.toString());
        HttpURLConnection urlConnection= null;
        URL url = null;
        try
        {
            url = new URL(urlString.toString());
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = (Document) db.parse(urlConnection.getInputStream());//Util.XMLfromString(response);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private List<GeoPoint> decodePoly(String encoded) {

        List<GeoPoint> poly = new ArrayList<GeoPoint>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
                    (int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }

        return poly;
    }
    private void showAlert(String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Error");
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alert.show();
    }
    private void cargarGeoPointsRuta() {//aca cargo los puntos de la ruta
		// TODO Auto-generated method stub
    	if (_geopoints!=null){
    		for(int i=0;i<this._geopoints.size();i++){
        		myPath.addPoint(this._geopoints.get(i));
        	}
        	if (_geopoints.isEmpty()){
        		_geopoints=null;
        	}
    	}        		
		
	}

}