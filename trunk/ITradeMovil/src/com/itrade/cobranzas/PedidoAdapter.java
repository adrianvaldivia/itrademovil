package com.itrade.cobranzas;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PedidoAdapter extends BaseAdapter {

	private List<com.itrade.model.Pedido> items;
	private Context context;
	private LayoutInflater inflater;
	private int resource;
	
	public PedidoAdapter(Context context, int textViewResourceId, List<com.itrade.model.Pedido> items) {
		super();
		// TODO Auto-generated constructor stub
		this.context=context;		
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = textViewResourceId;
        this.items = items;
        
        System.out.println("PedidoAdapter: Constructor");
        
	} 
	
	

	public View getView(int position, View convertView, ViewGroup parent) {
		 System.out.println("PedidoAdapter: getview");
		View view;
        
        if (convertView == null) {
                view = this.inflater.inflate(resource, parent, false);
        } else {
                view = convertView;
        }
        
        // bind the data to the view object
        
        // make sure it's worth drawing the view
        return this.bindData(view, position);
	}
        
	  public View bindData(View view, int position) {
	        // make sure it's worth drawing the view
	        if (this.items.get(position) == null) {
	                return view;
	        }
	        
	        
        // pull out the object
        com.itrade.model.Pedido item = this.items.get(position);
        
        TextView numPed = (TextView) view.findViewById(R.id.idPedido);
        TextView fechPed = (TextView) view.findViewById(R.id.cant);
        
        numPed.setText("Pedido No. " + Long.toString(item.getIdPedido()));
        fechPed.setText(item.getFechaPedido());
        
                                     
        // return the final view object
        return view;
		
		
	}

	
	public int getCount() {
		// TODO Auto-generated method stub
		return this.items.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.items.get(arg0);
		}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
}
