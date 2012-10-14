package com.itrade.pedidos;

import com.itrade.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewFlipper;

public class RegistrarProspecto extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	ViewFlipper vf;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainprospecto);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper);

//        Button bt1 = (Button) findViewById(R.id.buttonUno);
//        bt1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showNext();
//			}
//		});
//
//        Button bt2 = (Button) findViewById(R.id.buttondos);
//        bt2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showPrevious();
//			}
//		});

        vf.setOnTouchListener(new ListenerTouchViewFlipper());

    }


	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


public class ListenerTouchViewFlipper extends Activity implements View.OnTouchListener{

//	MainActivity mac = new MainActivity();
	

	public boolean onTouch(View v, MotionEvent event) {

		float init_x=0;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
			init_x=event.getX();
			return true;
		case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
			float distance =init_x-event.getX();

			if(distance>0)
    		{
                 vf.showPrevious();
    		}

    		if(distance<0)
    		{
    		     vf.showNext();
    		}

		default:
			break;
		}
		return false;
	}
}

public class ViewflipperBlogActivity extends Activity implements OnClickListener{
    public float init_x;
	private ViewFlipper vf;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper);

//        Button bt1 = (Button) findViewById(R.id.buttonUno);
//        bt1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showNext();
//			}
//		});
//
//        Button bt2 = (Button) findViewById(R.id.buttondos);
//        bt2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				vf.showPrevious();
//			}
//		});

        vf.setOnTouchListener(new ListenerTouchViewFlipper());

    }

    private class ListenerTouchViewFlipper implements View.OnTouchListener{


		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
				init_x=event.getX();
				return true;
			case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
				float distance =init_x-event.getX();

				if(distance>0)
	    		{
	    			 vf.setInAnimation(inFromRightAnimation());
	                 vf.setOutAnimation(outToLeftAnimation());
	                 vf.showPrevious();
	    		}

	    		if(distance<0)
	    		{
	    		     vf.setInAnimation(inFromLeftAnimation());
	    		     vf.setOutAnimation(outToRightAnimation());
	    		     vf.showNext();
	    		}

			default:
				break;
			}

			return false;
		}

    }

    private Animation inFromRightAnimation() {

    	Animation inFromRight = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f );

    	inFromRight.setDuration(500);
    	inFromRight.setInterpolator(new AccelerateInterpolator());

    	return inFromRight;

    }

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

}