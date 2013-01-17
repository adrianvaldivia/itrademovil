package com.alumnado;

import com.alumnado.R;
import com.alumnado.model.Pregunta;
import com.alumnado.model.PreguntaDao;


import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.webkit.*;

public class VisualizarProblemas extends Activity
implements View.OnClickListener
{	
//	public String  formula="<p>The largest common divisor of <script type=\"math/asciimath\">27</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">9</script>, and the largest common divisor of <script type=\"math/asciimath\">40</script> and <script type=\"math/asciimath\">x</script> is <script type=\"math/asciimath\">10</script>.&nbsp; Which of these is <script type=\"math/asciimath\">(((x + y)^2)/(x^5 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>?</p>";
//	public String  formula1="<script type=\"math/asciimath\">(((x + y)^2)/(x^4 - 45 sqrt 5))-((4 * 5y)/(4/5))</script>";	
//	public String formula2="`(((x + y)^2)/(x^4 - 45 sqrt 5))-((4 * 5y)/(4/5))`";
//	public String formula3="`int_0^1 x^2 dx`";
//	public String formula4="`f(x)=sum_(n=0)^oo(f^((n))(a))/(n!)(x-a)^n`";
	private Bundle bundle;
	private long idPregunta;
	private PreguntaDao preguntaDao;
	private WebView webVie;
	private AsTaskRenderFormula taskRenderFormula;
				
//	private String doubleEscapeTeX(String s) {//necesario cuando se usa TeX
//		String t="";
//		for (int i=0; i < s.length(); i++) {
//			if (s.charAt(i) == '\'') t += '\\';
//			if (s.charAt(i) != '\n') t += s.charAt(i);
//			if (s.charAt(i) == '\\') t += "\\";
//		}
//		return t;
//	}
	

	public void onClick(View v) {
		if (v == findViewById(R.id.buttonatras)) {
			idPregunta--;
			if (idPregunta==0)
				idPregunta=preguntaDao.count();
			Pregunta pregTmp= preguntaDao.loadByRowId(idPregunta);
			verFormula(pregTmp.getFormula());		
			}
		else if (v == findViewById(R.id.buttonadelante)) {
			idPregunta++;
			if (idPregunta==preguntaDao.count()+1)
				idPregunta=1;
        	Intent i= new Intent(VisualizarProblemas.this, VisualizarProblemas.class);
        	i.putExtra("idPregunta", idPregunta);
        	VisualizarProblemas.this.startActivity(i);
        	VisualizarProblemas.this.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);			
			//verFormula();
		}
	}

    /** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.problemas);
		
	    bundle = getIntent().getExtras();	
		idPregunta = bundle.getLong("idPregunta");
		
        MyApplication mApplication = (MyApplication)getApplicationContext();       
        preguntaDao=mApplication.getPreguntaDao();
        Pregunta preguntaTemp=preguntaDao.loadByRowId(idPregunta);
        String formula=preguntaTemp.getFormula();
        
        webVie = (WebView) findViewById(R.id.webview);
        webVie.getSettings().setJavaScriptEnabled(true);
        webVie.getSettings().setBuiltInZoomControls(true);
        webVie.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
		                      +"MathJax.Hub.Config({ " 
							  	+"showMathMenu: false, "
							  	+"jax: ['input/AsciiMath','output/HTML-CSS'], "
							  	+"extensions: ['asciimath2jax.js'] " 
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script><span id='math'></span>","text/html","utf-8","");
		Button b = (Button) findViewById(R.id.buttonatras);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.buttonadelante);
		b.setOnClickListener(this);			
		
		verFormulaConAsTask(formula);
		
	}
    
	public void verFormulaConAsTask(String cadena) 	
	{        
		taskRenderFormula= new AsTaskRenderFormula(VisualizarProblemas.this,webVie,cadena);
		taskRenderFormula.execute();
	}
	public void verFormula(String cadena) 	//utilizado cuando hago click atras
	{        
		webVie.loadUrl("javascript:document.getElementById('math').innerHTML='"
				  +""
		          +cadena
				  +""
		          +"';");
		webVie.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}
	
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    VisualizarProblemas.this.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
	    super.onBackPressed();
	}
	
	
	
}

