package com.itrade.cobranzas;

import java.util.ArrayList;

import com.itrade.R;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class ExpandableListAdapter extends BaseExpandableListAdapter{	 
	    public boolean areAllItemsEnabled()
	    {
	        return true;
	    }
	    
	    private Context context;
	    private ArrayList<String> nomclientes;
	    private ArrayList<String> clientes;
	    private ArrayList<ArrayList<Pedido>> pedidos;
	    public ExpandableListAdapter(Context context, ArrayList<String> parclientes, ArrayList<String> nomclientes,ArrayList<ArrayList<Pedido>> pedidos) {
	        this.context = context;
	        this.clientes = new ArrayList<String>();	        
	        this.pedidos= new ArrayList<ArrayList<Pedido>>();
	        this.nomclientes= new ArrayList<String>();
	        for(int i=0;i<parclientes.size();i++){
	        	this.nomclientes.add(nomclientes.get(i));
	        	this.clientes.add(parclientes.get(i));
	        	this.pedidos.add(new ArrayList<Pedido>());
	        }	        	        
	    }

	    /**
	     * A general add method, that allows you to add a Vehicle to this list
	     * 
	     * Depending on if the category opf the vehicle is present or not,
	     * the corresponding item will either be added to an existing group if it 
	     * exists, else the group will be created and then the item will be added
	     * @param vehicle
	     */

	    
	    public void addItem(Pedido pedido){	
	    	int index=0;
	    	for(int j=0;j<clientes.size();j++){
	    		if(clientes.get(j).compareTo(pedido.getIdCliente().toString())==0){	    		
	    			index=j;
	    			break;
	    		}
	    	}	    	
	    	pedidos.get(index).add(pedido);	    		    
	    }
	    	   
	    public Object getChild(int groupPosition, int childPosition) {
	        return pedidos.get(groupPosition).get(childPosition);
	    }	    
	    	    
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	    	   
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {
	        Pedido pedido = (Pedido) getChild(groupPosition, childPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.c_clientes_child, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);	        
	        tv.setText("Pedido Nro: " +pedido.getIdPedido().toString()+"    S/. "+pedido.getMontoTotalPedido().toString());	      
	        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);	        
	        return convertView;
	    }


	    public int getChildrenCount(int groupPosition) {
	        return pedidos.get(groupPosition).size();
	    }



	    public Object getGroup(int groupPosition) {
	        return clientes.get(groupPosition);
	    }

	    public Object getGroupNomb(int groupPosition) {
	        return this.nomclientes.get(groupPosition);
	    }

	    public int getGroupCount() {
	        return clientes.size();
	    }

	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    // Return a group view. You can load your custom layout here.
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,ViewGroup parent) {
	        String group = (String) getGroup(groupPosition);
	        String groupNom = (String) getGroupNomb(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.c_clientes_group, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
	        TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
	        tv.setText(group);	        
	        tvNombre.setText(groupNom);
	        return convertView;
	    }


	    public boolean hasStableIds() {
	        return true;
	    }

	    public boolean isChildSelectable(int arg0, int arg1) {
	        return true;
	    }

	    
}