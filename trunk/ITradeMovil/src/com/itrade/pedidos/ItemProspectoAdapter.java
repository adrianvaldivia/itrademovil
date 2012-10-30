package com.itrade.pedidos;

import java.util.ArrayList;

import com.itrade.R;
import com.itrade.model.Cliente;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemProspectoAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<Cliente> prospectos;
	private ViewHolder holder;
	
	class ViewHolder {
        TextView razonsocial;
    }
	public ItemProspectoAdapter(Activity activity, ArrayList<Cliente> prospectos) {
		this.activity = activity;
		this.prospectos = prospectos;
	}



	public int getCount() {
		return prospectos.size();
	}



	public Object getItem(int position) {
		return prospectos.get(position);
	}



	public long getItemId(int position) {
		return prospectos.get(position).getId();
	}



	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		
        if(vi == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.buscarprospectosfusion, null);
        	
        	holder = new ViewHolder();
          
          //holder.razonsocial = (TextView) vi.findViewById(R.id.empty2);
          vi.setTag(holder);          

        }
        else {
         holder = (ViewHolder) vi.getTag();
      }
            
        Cliente item = prospectos.get(position);
        
        holder.razonsocial.setText(item.getRazon_Social());
////      
////      return v;
//        
//        ImageView image = (ImageView) vi.findViewById(R.id.imagen);
//       // int imageResource = activity.getResources().getIdentifier(item.getRutaImagen(), null, activity.getPackageName());
//        //image.setImageDrawable(activity.getResources().getDrawable(imageResource));
//        
//        TextView nombre = (TextView) vi.findViewById(R.id.nombre);
//        nombre.setText(item.getNombre());
//        
//        TextView tipo = (TextView) vi.findViewById(R.id.tipo);
//        //tipo.setText(item.getTipo());

        return vi;
   
	}
}
