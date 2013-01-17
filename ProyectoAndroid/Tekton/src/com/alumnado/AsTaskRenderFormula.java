package com.alumnado;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class AsTaskRenderFormula extends AsyncTask<String, Void, String>
{		
           
	private ProgressDialog dialog;
	private Activity activity;
	private WebView webV;	
	private String  formula;	
	
	public AsTaskRenderFormula(Activity activ,WebView webVie,String formu) {		 
	        this.activity = activ;		
	        this.webV = webVie;		
	        this.formula = formu;		
	        this.dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
	    		Log.d("TAG","LLEGA TERCERO AKI");
	            this.dialog.setMessage("Espere por favor...");
	            this.dialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		String str="Exito";
//        this.webV.getSettings().setJavaScriptEnabled(true);
//        this.webV.getSettings().setBuiltInZoomControls(true);
//        this.webV.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
//                +"MathJax.Hub.Config({ " 
//				  	+"showMathMenu: false, "
//				  	+"jax: ['input/AsciiMath','output/HTML-CSS'], "
//				  	+"extensions: ['asciimath2jax.js'] " 
//				  +"});</script>"
//                +"<script type='text/javascript' "
//				  +"src='file:///android_asset/MathJax/MathJax.js'"
//				  +"></script><span id='math'></span>","text/html","utf-8","");
        try {
            Thread.sleep(1000);         
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
	    return str;
	}
	
	@Override
	protected void onPostExecute(String result) {	
		
	     if (this.dialog.isShowing()) {
	    	    try {
	    	    	this.dialog.dismiss();
	    	        dialog = null;
	    	    } catch (Exception e) {
	    	        // nothing
	    	    }	    	 	           
	     }	    
		 actualizarUI();
//	     db.close();
	}
	private void actualizarUI() {
//		 cursorElementoLista.requery();	
		this.webV.loadUrl("javascript:document.getElementById('math').innerHTML='"
				  +""
		          +formula
				  +""
		          +"';");
		this.webV.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}
	 
		 	
	}