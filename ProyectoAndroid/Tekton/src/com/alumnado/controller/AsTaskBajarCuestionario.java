package com.alumnado.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alumnado.MyApplication;
import com.alumnado.R;
import com.alumnado.VisualizarProblemas;
import com.alumnado.model.Pregunta;
import com.alumnado.model.PreguntaDao;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.util.Log;

public class AsTaskBajarCuestionario extends AsyncTask<String, Void, String>
{		

    //green Dao
    private PreguntaDao preguntaDao;
    //fin green dao
//    private Cursor cursorElementoLista;       
    
	private ProgressDialog dialog;
	private Activity activity;
	private String response;
	private long idAlumno;
	
	public AsTaskBajarCuestionario(Activity activ,long idAlum) {		 
	        this.activity = activ;
	        this.idAlumno = idAlum;
//	        this.cursorElementoLista=cursorElementoLis;
	        this.dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
	            this.dialog.setMessage("Conectando");
	            this.dialog.show();		    	
	}
	
	@Override
	protected String doInBackground(String... params) {
		String str="Exito";
		cargarBaseLocal();
	    return str;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
        MyApplication mApplication = (MyApplication)activity.getApplicationContext();
        preguntaDao=mApplication.getPreguntaDao();
		
		///////////////////////////////////bd preguntas
		preguntaDao.deleteAll();
//		String  test="<p>Pregunta Alumn B The largest common divisor of <script type=\"math/asciimath\">27</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">9</script>, and the largest common divisor of <script type=\"math/asciimath\">40</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">10</script>.&nbsp; Which of these is <script type=\"math/asciimath\">(((x + y)^2)/(x^5 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>?</p>";
//		String  test2="<p>Pregunt Alumno C The largest common divisor of <script type=\"math/asciimath\">27</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">9</script>, and the largest common divisor of <script type=\"math/asciimath\">40</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">10</script>.&nbsp; Which of these is <script type=\"math/asciimath\">(((x + y)^2)/(x^5 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>?</p>";
		String  formula1="<p>Pregunta Alumno A The largest common divisor of <script type=\"math/asciimath\">27</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">9</script>, and the largest common divisor of <script type=\"math/asciimath\">40</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">10</script>.&nbsp; Which of these is <script type=\"math/asciimath\">(((x + y)^2)/(x^5 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>?</p>";
		String  formula2="<script type=\"math/asciimath\">(((x + y)^2)/(x^4 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>";
		String  formula3="<script type=\"math/asciimath\">(((z + y)^2)/(x^5 - 45 sqrt 5))-((4 * 6y)/(4/5))</script>";
		String  formula4="<script type=\"math/asciimath\">(((x + z)^2)/(x^6 - 45 sqrt 3))*((4 * 5y)/(4/5))</script>";
		String  formula5="<script type=\"math/asciimath\">(((x + y)^3)/(x^2 - 45 sqrt 5))/((4 * 8y)/(4/5))</script>";
//		String  formula6="`f(x)=sum_(n=0)^oo(f^((n))(a))/(n!)(x-a)^n`";
//		String  formula7="`int_0^1 x^2 dx`";
		
		List<String> listaFormulas = new ArrayList<String>();
		listaFormulas.add(formula1);	listaFormulas.add(formula2);
		listaFormulas.add(formula3);	listaFormulas.add(formula4);
		listaFormulas.add(formula5);

		for(int i=0;i<listaFormulas.size();i++){
			long longTemp=0;
			longTemp=longTemp+i;
			Pregunta preguntaTemp = new Pregunta(null,longTemp,listaFormulas.get(i));
			preguntaDao.insert(preguntaTemp);
		}		
		
	     if (this.dialog.isShowing()) {
	    	    try {
	    	    	this.dialog.dismiss();
	    	        dialog = null;
	    	    } catch (Exception e) {
	    	        // nothing
	    	    }	    	 	           
	     }
	     if (getResponse().compareTo("error")!=0){
		     leerDatos();//hago el parseo cuando no hay error
	     }
	     
	     Intent i= new Intent(activity, VisualizarProblemas.class);
	     long longTemp=1;
	     i.putExtra("idPregunta",longTemp );
	     activity.startActivity(i);
	     activity.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);		
//	     db.close();
	}
	 
	private void cargarBaseLocal() {//metodo que corre en background		               				
			
			/*****ws****/
			List<NameValuePair> param = new ArrayList<NameValuePair>();	
			param.add(new BasicNameValuePair("tag", "getCuestionarioByIdAlumno"));
			param.add(new BasicNameValuePair("idalumno", ""+idAlumno));
			String route="/alumnos/";
			try{		       
				WBhelper helper = new WBhelper("http://10.0.2.2/webservicestekton/");
//				WBhelper helper = new WBhelper("http://192.168.0.2/webservicestekton/");
				
				String responseBody=helper.obtainResponse(route,param);			
				
				Log.e("log_tag", responseBody );
				if (responseBody!="error"){					
					setResponse(responseBody);
				}else{
					Log.e("log_tag", "Error in webservice");
					setResponse("error");
				}				
			}catch(Exception e){
			     Log.e("log_tag", "Error in http connection "+e.toString());
//			     pd.dismiss();
			     setResponse("error");
			}
					
			
		    Log.d("TAG","LLEGA SEGUNDO AKI");		    
	}
	private void leerDatos() {
		// TODO Auto-generated method stub
		List<Pregunta> listaPregunta = new ArrayList<Pregunta>();
		Gson gson = new Gson();
		
		Log.e("log_tag", "no se cayo5" );
		listaPregunta	=	gson.fromJson(getResponse(), new TypeToken<List<Pregunta>>(){}.getType());
        MyApplication mApplication = (MyApplication)activity.getApplicationContext();
        preguntaDao=mApplication.getPreguntaDao();
        ///////////////////////////////////bd alumnos
        preguntaDao.deleteAll();
		for(int i=0;i<listaPregunta.size();i++){
			preguntaDao.insert(listaPregunta.get(i));
		}
	}
	

	private String getResponse() {
		return response;
	}

	private void setResponse(String response) {
		this.response = response;
	}	 	
	}