package com.itrade;

import java.util.ArrayList;
import com.itrade.models.Access;
import com.itrade.models.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Home extends Activity {

	
	private String direccion="http://10.0.2.2/";
	private ArrayList<Access> accessList = new ArrayList<Access>();
	private ArrayAdapter<String> _menuAdapter;
	private ArrayList<String> _menuItems;

	public ArrayList<String> get_menuItems() {
		return _menuItems;
	}

	public void set_menuItems(ArrayList<String> _menuItems) {
		this._menuItems = _menuItems;
	}

	
	
    public ArrayAdapter<String> get_menuAdapter() {
		return _menuAdapter;
	}

	public void set_menuAdapter(ArrayAdapter<String> _menuAdapter) {
		this._menuAdapter = _menuAdapter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.home);
        
        Intent i = getIntent();
        Login login = (Login)i.getSerializableExtra("User_Login");
        TextView textview = (TextView) findViewById(R.id.textView1);
        textview.setText(login.toString());
        /*
        WBhelper helper = new WBhelper(direccion);				
		List<NameValuePair> params = new ArrayList<NameValuePair>();				
		String responseBody=helper.obtainResponse("Android_ws/users/get_access_by_id/",params);
		Log.d("RESPONSE=",responseBody);		
		if (responseBody!="error"){
			Gson gson = new Gson();	
			this.accessList = gson.fromJson(responseBody, new TypeToken<List<Access>>(){}.getType());					
			fillItems();
			Integer inte=this.accessList.size();
			Log.d("ACCESSLIST=",inte.toString());			
			if (inte>0){				
				 if (_menuItems.size()>0){
					 ListView lv = (ListView) findViewById(R.id.listView1);			        
				        set_menuAdapter(new ArrayAdapter<String>(this,R.layout.row, get_menuItems())
				                {

				                    @Override
				                    public View getView(int position, View convertView, ViewGroup parent) {
				                        View v= convertView;
				                        if(v==null)
				                        {

				                            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				                             v = vi.inflate(R.layout.row, null);
				                        }
				                        String it = getItem(position);
				                        if (it != null || it!="") 
				                        {
				                                TextView tt = (TextView) v.findViewById(R.id.row_string);
				                                tt.setText(getItem(position));

				                        }
				                        return v;
				                    }
				                });
				        lv.setAdapter(this.get_menuAdapter());
				 }
				 				
			}
					    		   														
		}
        */
      
        
    }

	public void fillItems() {
		this._menuItems= new ArrayList<String>();
		for (Access access : this.accessList) {
			 Log.d("AccessItem=",access.toString());
			 this._menuItems.add(access.toString());
		 }
	}
}
