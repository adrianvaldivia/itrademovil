package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.itrade.db.DAOCliente;


import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Prospecto;
import com.itrade.model.ProspectoDao;
import com.itrade.R;
import com.itrade.model.DaoMaster.DevOpenHelper;

public class BuscarProspectosGreenDao extends ListActivity{
	
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProspectoDao prospectoDao;

    private Cursor cursor;
    SimpleCursorAdapter adapter;
    
    private EditText editText;
	DAOCliente daoCliente =null;
	private Button button_vermapa;
	private Button button_registrar;
	Prospecto prospecto= new Prospecto();
	List<Prospecto> listaProspecto;
	
	public int idempleado;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarprospectos);
        //inicio green Dao
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        prospectoDao = daoSession.getProspectoDao();
        
        // Segunda parte
        String textColumn = ClienteDao.Properties.Razon_Social.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(prospectoDao.getTablename(), prospectoDao.getAllColumns(), null, null, null, null, orderBy);
        String[] from = { textColumn, ClienteDao.Properties.RUC.columnName };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);
        // Fin green Day
        Bundle bundle=getIntent().getExtras();
        idempleado = bundle.getInt("idempleado");
        setTitle("I Trade - Prospectos");
        
        button_vermapa = (Button) findViewById(R.id.buttonvermapa);
        button_registrar = (Button) findViewById(R.id.buttonbuscar);
        editText = (EditText) findViewById(R.id.editTextCliente);
//        editText.setInputType(InputType.TYPE_NULL);



//        List<String> lista =null;
        
//        lista= this.Convierte();
//        ListView lv = getListView(); 
//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
	    button_vermapa.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(BuscarClientesGreenDao.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
				
				Intent intent = new Intent(BuscarProspectosGreenDao.this, VerMapaActivity.class);
				
				intent.putExtra("idruta", 0);
				intent.putExtra("idunidad", 0);
				
				startActivity(intent);		
			}
	 	});
	    button_registrar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent i = new Intent(BuscarProspectosGreenDao.this, RegistrarProspecto.class);
				startActivity(i);
				Toast.makeText(BuscarProspectosGreenDao.this, "Agregar", Toast.LENGTH_LONG).show();
				addCliente();
			}
	 	});
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
     Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
     //inicio cambios chichan
     this.encuentraCliente(id);
     int temp=safeLongToInt(prospecto.getId());
     Intent intent = new Intent(BuscarProspectosGreenDao.this, DetalleCliente.class);
     intent.putExtra("nombre", prospecto.getRazon_Social());
     intent.putExtra("apellidos", prospecto.getRUC());
     intent.putExtra("idcliente", temp);
     intent.putExtra("idempleado", idempleado);
     startActivity(intent);
     
    }    
    
	private void encuentraCliente(long id) {
//	private void encuentraCliente(String selection) {
//		for(int i=0;i<listaCliente.size();i++){
//			if (selection.compareTo(listaCliente.get(i).getNombre().toString()) == 0){
//				cliente=listaCliente.get(i);
//				break;
//			}
//		}
		prospecto=prospectoDao.loadByRowId(id);
	}

	public List<String> Convierte(){
		
		List<String> lista=new ArrayList<String>();;
		for(int i=0;i<listaProspecto.size();i++){
			lista.add(listaProspecto.get(i).getRazon_Social().toString());
		}
		return lista;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.opcion1:{
	        	Toast.makeText(this, "Cargando BD!", Toast.LENGTH_LONG).show();
	        	cargarBaseLocal();	        	
	                            break;
	                           }
	        case R.id.opcion2:     Toast.makeText(this, "Presionaste Opcion 2!", Toast.LENGTH_LONG).show();
	                            break;
	        case R.id.opcion3: Toast.makeText(this, "Presionaste Opcion 3!", Toast.LENGTH_LONG).show();
	                            break;
	    }
	    return true;
	}
    private void cargarBaseLocal() {
//        daoCliente = new DAOCliente();  
//        listaProspecto = daoCliente.getAllClientes(0); //obtiene los clientes
//        Double x;
//		Double y;
//		prospectoDao.deleteAll();
//        
//		for(int i=0;i<listaProspecto.size();i++){
//			x=listaProspecto.get(i).getLatitud();
//			y=listaProspecto.get(i).getLongitud();
//			Prospecto prospectoAux = new Prospecto(null,listaProspecto.get(i).getIdPersona(),listaProspecto.get(i).getRazon_Social(),listaProspecto.get(i).getRUC(),x,y,listaProspecto.get(i).getDireccion(),listaProspecto.get(i).getIdCobrador(),listaProspecto.get(i).getIdUsuario(),listaProspecto.get(i).getActivo());
//	        prospectoDao.insert(prospectoAux);
//	        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());
//		}
//        cursor.requery();
//    	setListAdapter(adapter);
		
	}

	private void addCliente() {
        String texto = editText.getText().toString();
        editText.setText("");
        double i,j;
        i=(double) 55.12;
        j=(double) 33.12;
        Prospecto prospectoAux = new Prospecto (null,null,1,texto,"HardCoded",i,j,"HardCoded",1,1,"1");
        prospectoDao.insert(prospectoAux);
        //Log.d("DaoExample", "Inserted new note, ID: " + cliente.getId());

        cursor.requery();
    }
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}
	@Override
	protected void onDestroy() { 
		db.close();
		cursor.close();
	    super.onDestroy();
	}


}