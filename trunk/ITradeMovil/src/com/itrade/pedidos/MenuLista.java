package com.itrade.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.itrade.R;
import com.itrade.model.PedidoLinea;
import com.itrade.db.DAOPedido;
import com.itrade.model.Cliente;
import com.itrade.model.ClienteDao;
import com.itrade.model.DaoMaster;
import com.itrade.model.DaoSession;
import com.itrade.model.Pedido;
import com.itrade.model.PedidoDao;
import com.itrade.model.UsuarioDao;
import com.itrade.model.DaoMaster.DevOpenHelper;
import com.itrade.model.PedidoDao.Properties;
import com.itrade.model.PedidoLineaDao;
import com.itrade.modelo.ItemMenu;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//import android.widget.Toast;
import android.widget.Toast;


public class MenuLista extends ListActivity{
	DAOPedido daoPedido =null;
	private Button button_salirmenu;
	public Bundle bundle;// = getIntent().getExtras();
	public String nombre="";
	public String apellidos="";
	public long idusuario;
	private TextView textView_NombreUsuario;
    //green Dao
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    //fin green dao
	private PedidoDao pedidoDao;
	private PedidoLineaDao pedidoLineaDao;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulista);
        
	    //inicio green DAO 
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "itrade-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pedidoDao = daoSession.getPedidoDao();
        pedidoLineaDao=daoSession.getPedidoLineaDao();
        //fin green dao
        
        
        
        
        
        textView_NombreUsuario  = (TextView) findViewById(R.id.nombreusuario);

        bundle = getIntent().getExtras();
		idusuario=bundle.getLong("idusuario");
		nombre =bundle.getString("nombre");
		apellidos=bundle.getString("apellidos");
		setTitle("iTrade - Bienvenido");
		textView_NombreUsuario.setText(nombre+" "+apellidos);
    
        button_salirmenu = (Button) findViewById(R.id.salirmenu);
        
        ListView lv = getListView();
        ArrayList<ItemMenu> items = obtenerItems();        
        ItemMenuAdapter adapter = new ItemMenuAdapter(this, items);        
        lv.setAdapter(adapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
//     Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
     if (selection.compareTo("Clientes")==0){
			Intent intent = new Intent(MenuLista.this, BuscarClientesGreenDao.class);		
			intent.putExtra("idusuario", idusuario);
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Productos")==0){
    	 	Intent intent = new Intent(MenuLista.this, BuscarProductos.class);
    	 	intent.putExtra("boolVer", 0);
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Pedidos")==0){
			Intent intent = new Intent(MenuLista.this, BuscarPedidos.class);
			intent.putExtra("idusuario", idusuario);
			startActivity(intent);
    	 
     }
     if (selection.compareTo("Mi Meta")==0){
			Intent intent = new Intent(MenuLista.this, MiMeta.class);
			startActivity(intent);		
     }
     if (selection.compareTo("Mi Agenda")==0){
			Intent intent = new Intent(MenuLista.this, SimpleCalendarViewActivity.class);
			intent.putExtra("idusuario", idusuario);
			startActivity(intent);		
     }
     if (selection.compareTo("Prospectos")==0){
			Intent intent = new Intent(MenuLista.this, BuscarProspectos.class);
			intent.putExtra("idusuario", idusuario);
			intent.putExtra("boolVer", 1);
			startActivity(intent);		
     }
     if (selection.compareTo("Mi Ubicacion")==0){			
			Intent intent = new Intent(MenuLista.this, Picker.class);
//			intent.putExtra("idusuario", idusuario);
			startActivity(intent);		
     }
    }    
    
	
    private ArrayList<ItemMenu> obtenerItems() {
    	ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
    	
    	items.add(new ItemMenu(1, "Clientes", "Ver Listado de Clientes", "drawable/clientes"));
    	items.add(new ItemMenu(2, "Pedidos", "Gestiona los pedidos", "drawable/pedidos"));
    	items.add(new ItemMenu(3, "Mi Agenda", "Organizador y eventos", "drawable/agenda"));
    	items.add(new ItemMenu(4, "Mi Meta", "Evolucion de mis ventas", "drawable/excelencia"));
    	items.add(new ItemMenu(5, "Prospectos", "Gestionar Prospectos", "drawable/clientes"));
    	items.add(new ItemMenu(5, "Productos", "Ver lista productos", "drawable/productos"));
    	items.add(new ItemMenu(5, "Mi Ubicacion", "Mi Ubicacion Actual", "drawable/marker"));
    	
    	
    	return items;
    }
    
	@Override
	public void onBackPressed() {
				
	    new AlertDialog.Builder(this)
        .setTitle("Cerrar Sesion")
        .setMessage("Desea cerrar la Sesion?")
        .setNegativeButton("Cancelar", null)
        .setNeutralButton("Minimizar", new OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
//            	Toast.makeText(MenuLista.this, "Yaaaa", Toast.LENGTH_SHORT).show();
            	Minimizar();
            	
            }
        })
        .setPositiveButton("Cerrar Sesion", new OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            		sincronizarBaseSubida();
            		MenuLista.super.onBackPressed();
            	
            }
        }).create().show();	
	}
	@Override
	public void finish(){
		super.finish();		
	}
	private void Minimizar() {
		// TODO Auto-generated method stub
		this.moveTaskToBack(true);		
	}
	private void sincronizarBaseSubida() {
		long idPedido=0;
		long idPedidoLocal;
		int tam=0;
        daoPedido = new DAOPedido(MenuLista.this);
        List<Pedido> pedidosAux = pedidoDao.queryBuilder()
        		.where(com.itrade.model.PedidoDao.Properties.NumVoucher.eq("N"))
        		.orderAsc(com.itrade.model.PedidoDao.Properties.Id).list();
        tam=pedidosAux.size();
        for(int i=0;i<tam;i++){
        	Pedido pedidoTemp=pedidosAux.get(i);
        	pedidoTemp.setNumVoucher("A");;
        	pedidoDao.deleteByKey(pedidoTemp.getId());
        	pedidoDao.insert(pedidoTemp);
        	idPedidoLocal=pedidoTemp.getId();
        	idPedido=daoPedido.registrarPedido(pedidoTemp);//id de bd externa
            List<PedidoLinea> pedidosLineaAux = pedidoLineaDao.queryBuilder()
            		.where(com.itrade.model.PedidoLineaDao.Properties.IdPedido.eq(idPedidoLocal))
            		.orderAsc(com.itrade.model.PedidoLineaDao.Properties.Id).list();
            for(int j=0;j<pedidosLineaAux.size();j++){
            	PedidoLinea pedidoLineaTemp=pedidosLineaAux.get(j);
            	pedidoLineaTemp.setIdPedido(idPedido);//lo setteo con el Id de BD externa
            	daoPedido.registrarPedidoLinea(pedidoLineaTemp);
            }
        }
	}
}