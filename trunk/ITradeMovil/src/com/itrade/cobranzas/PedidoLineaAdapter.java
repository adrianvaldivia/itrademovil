package com.itrade.cobranzas;

import java.util.ArrayList;

import com.itrade.R;
import com.itrade.model.PedidoLinea;

import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

class PedidoLineaAdapter extends BaseAdapter {
	private ArrayList<com.itrade.cobranzas.PedidoLinea> items;
	private Context context;
	private LayoutInflater inflater;
	private int resource;
	
    public PedidoLineaAdapter(Context context, int textViewResourceId, ArrayList<com.itrade.cobranzas.PedidoLinea> items) {
		super();
		// TODO Auto-generated constructor stub
		this.context=context;		
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = textViewResourceId;
        this.items = items;
	}        
    public View getView(int position, View convertView, ViewGroup parent) {
        // reuse a given view, or inflate a new one from the xml
        View view;
         
        if (convertView == null) {
                view = this.inflater.inflate(resource, parent, false);
        } else {
                view = convertView;
        }
        
        // bind the data to the view object
        return this.bindData(view, position);
    }
    public View bindData(View view, int position) {
        // make sure it's worth drawing the view
        if (this.items.get(position) == null) {
                return view;
        }
        
        // pull out the object
        com.itrade.cobranzas.PedidoLinea item = this.items.get(position);
        
        TextView tprodid = (TextView) view.findViewById(R.id.tvProdId);
        TextView tprodcant = (TextView) view.findViewById(R.id.tvProdCant);
        TextView txtTitle = (TextView) view.findViewById(R.id.tvProdMonto);
        TextView tprodmont = (TextView) view.findViewById(R.id.tvProdSubt);    
        tprodid.setText(item.getNombreProducto()+" "+item.getIdProducto().toString());
        tprodcant.setText("Cantidad "+item.getCantidad().toString());
        txtTitle.setText("Precio: "+item.getPrecio().toString());
        tprodmont.setText("Subtotal: "+item.getMontoLinea().toString());
                             
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