package com.tekton;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.webkit.*;

public class VisualizarProblemas extends Activity
implements View.OnClickListener
{	
	public String formula="f(z_0)= \\frac1{2\\pi i}\\oint_\\gamma \\frac{f(z)}{z-z_0} dz";
	public String formula2="\\int_{-\\infty}^{\\infty} e^{-x^2}\\, dx = \\sqrt{\\pi}";
	
	private String doubleEscapeTeX(String s) {
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
			WebView w = (WebView) findViewById(R.id.webview);
			w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
			          +doubleEscapeTeX(formula)
					  +"\\\\]';");
			w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");		}
		else if (v == findViewById(R.id.buttonadelante)) {
			WebView w = (WebView) findViewById(R.id.webview);
			w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
			          +doubleEscapeTeX(formula2)
					  +"\\\\]';");
			w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
		}
	}

    /** Called when the activity is first created. */
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
							  	+"jax: ['input/TeX','output/HTML-CSS'], "
							  	+"extensions: ['tex2jax.js'], " 
							  	+"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
							  	  +"'noErrors.js','noUndefined.js'] } "
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script><span id='math'></span>","text/html","utf-8","");
		Button b = (Button) findViewById(R.id.buttonatras);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.buttonadelante);
		b.setOnClickListener(this);	
		
	}
}

