package com.itrade.cobranzas;

import java.util.ArrayList;

import com.itrade.R;
import com.itrade.model.Cliente;
import com.itrade.model.Pedido;


import android.content.Context;
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
	    //private ArrayList<Cliente> listClientes;
	    private ArrayList<String> clientes; //lista de  nombres de los clientes
	    private ArrayList<ArrayList<Pedido>> pedidos;
	    public ExpandableListAdapter(Context context, ArrayList<String> parclientes,
	            ArrayList<ArrayList<Pedido>> pedidos) {
	        this.context = context;
	        this.clientes = parclientes;	        
	        this.pedidos= new ArrayList<ArrayList<Pedido>>();
	        /*
	        clientes.add("cli001");
	        clientes.add("cli002");
	        clientes.add("cli003");
	        clientes.add("cli004");	        	       
	        pedidos.add(new ArrayList<Pedido>());
	        pedidos.add(new ArrayList<Pedido>());
	        pedidos.add(new ArrayList<Pedido>());
	        pedidos.add(new ArrayList<Pedido>());
	        */
	        for(int i=0;i<parclientes.size();i++){
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
	    /*	
	    	if(!clientes.contains(pedido.getCliente())){
	    		clientes.add(pedido.getCliente());
	    	}
	    	*/
	    	int index=0;
	    	for(int j=0;j<clientes.size();j++){
	    		if(clientes.get(j).compareTo(pedido.getIdCliente().toString())==0){	    		
	    			index=j;
	    			break;
	    		}
	    	}
	    	/*	    		    
	    	for(int j=0;j<clientes.size();j++){
	    		if(clientes.get(j).compareTo(pedido.getIdCliente())==0){
	    			index=j;
	    			break;
	    		}
	    	}
	    	*/
	    	
	    	//int index = clientes.indexOf(pedido.getCliente());
	    	pedidos.get(index).add(pedido);
	    		    	
	    }
	    

	    
	    public Object getChild(int groupPosition, int childPosition) {
	        return pedidos.get(groupPosition).get(childPosition);
	    }
	    

	    	////////////////////////////////////////////////////
	    
	    
	    
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	    
	    // Return a child view. You can load your custom layout here.	    
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {
	        Pedido pedido = (Pedido) getChild(groupPosition, childPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.c_clientes_child, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);	        
	        tv.setText("   " + pedido.getFechaPedido().toString());


	        // Depending upon the child type, set the imageTextView01
	        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
	        /*if (vehicle instanceof Car) {
	            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.car, 0, 0, 0);
	        } else if (vehicle instanceof Bus) {
	            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bus, 0, 0, 0);
	        } else if (vehicle instanceof Bike) {
	            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bike, 0, 0, 0);
	        }*/
	        return convertView;
	    }


	    public int getChildrenCount(int groupPosition) {
	        return pedidos.get(groupPosition).size();
	    }



	    public Object getGroup(int groupPosition) {
	        return clientes.get(groupPosition);
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
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.c_clientes_group, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
	        tv.setText(group);
	        return convertView;
	    }


	    public boolean hasStableIds() {
	        return true;
	    }

	    public boolean isChildSelectable(int arg0, int arg1) {
	        return true;
	    }

	    
}