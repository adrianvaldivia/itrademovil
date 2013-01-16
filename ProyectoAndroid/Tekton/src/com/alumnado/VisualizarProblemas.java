package com.alumnado;

import java.util.ArrayList;
import java.util.List;

import com.alumnado.R;


import android.annotation.SuppressLint;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.webkit.*;

public class VisualizarProblemas extends Activity
implements View.OnClickListener
{	
//	public String formula="f(z_0)= \\frac1{2\\pi i}\\oint_\\gamma \\frac{f(z)}{z-z_0} dz";
//	public String formula3="\\int_{-\\infty}^{\\infty} e^{-x^2}\\, dx = \\sqrt{\\pi}";
	public String  formula1="`(((x + y)^3)/(x^2 - 45 sqrt 5))/((4 * 8y)/(4/5))`";	
	public String formula2="`(((x + y)^2)/(x^4 - 45 sqrt 5))-((4 * 5y)/(4/5))`";
	public String formula3="`int_0^1 x^2 dx`";
	public String formula4="`f(x)=sum_(n=0)^oo(f^((n))(a))/(n!)(x-a)^n`";
	public String formula5="`[[a,b],[c,d]]((n),(k))`";
	public List<String> listaFormulas = new ArrayList<String>();	
	public int indiceFormula=0;	
	
	
	
	
	private String doubleEscapeTeX(String s) {//necesario cuando se usa TeX
		String t="";
		for (int i=0; i < s.length(); i++) {
			if (s.charAt(i) == '\'') t += '\\';
			if (s.charAt(i) != '\n') t += s.charAt(i);
			if (s.charAt(i) == '\\') t += "\\";
		}
		return t;
	}
	

	public void onClick(View v) {
		if (v == findViewById(R.id.buttonatras)) {
			indiceFormula--;
			if (indiceFormula==-1)
				indiceFormula=listaFormulas.size()-1;
			verFormula();		
			}
		else if (v == findViewById(R.id.buttonadelante)) {
			indiceFormula++;
			if (indiceFormula==listaFormulas.size())
				indiceFormula=0;
			verFormula();
		}
	}

    /** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.problemas);
		WebView w = (WebView) findViewById(R.id.webview);
		w.getSettings().setJavaScriptEnabled(true);
		w.getSettings().setBuiltInZoomControls(true);
		w.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
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
		
		listaFormulas.add(formula1);
		listaFormulas.add(formula2);
		listaFormulas.add(formula3);
		listaFormulas.add(formula4);
		listaFormulas.add(formula5);
		
		verFormula();
		
	}
    
	public void verFormula() 
	{
		WebView wbb = (WebView) findViewById(R.id.webview);
		wbb.loadUrl("javascript:document.getElementById('math').innerHTML='"
				  +"\\\\"
		          +doubleEscapeTeX(listaFormulas.get(indiceFormula))
				  +"\\\\"
		          +"';");
		wbb.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}
	
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    VisualizarProblemas.this.overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
	    super.onBackPressed();
	}
	
	
	
}

