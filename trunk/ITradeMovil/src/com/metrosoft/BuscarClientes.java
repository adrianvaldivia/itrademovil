package com.metrosoft;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.metrosoft.db.DAOCliente;
import com.metrosoft.model.Cliente;

public class BuscarClientes extends ListActivity{
	DAOCliente daoCliente =null;
	private Button button_vermapa;
	Cliente cliente= new Cliente();
	List<Cliente> listaCliente =null;
	public int idempleado;
	int boolVer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarclientes);

        Bundle bundle=getIntent().getExtras();
        idempleado = bundle.getInt("idempleado");
        boolVer=bundle.getInt("boolVer");//booleano indica desde donde fue llamado el activity
        setTitle("iTrade - Clientes");
        daoCliente = new DAOCliente();
        button_vermapa = (Button) findViewById(R.id.buttonvermapa);

          
        listaCliente = daoCliente.getAllClientes(0); //obtiene los clientes

        List<String> lista =null;
        
        lista= this.Convierte();
        ListView lv = getListView(); 
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista));
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.lista, lista)); 
	    button_vermapa.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(BuscarClientes.this, "pruebaa", Toast.LENGTH_LONG).show();
//				Bundle bundle = getIntent().getExtras();
				
				Intent intent = new Intent(BuscarClientes.this, VerMapaActivity.class);

				intent.putExtra("idruta", 0);
				intent.putExtra("idunidad", 0);
				
				startActivity(intent);		
			}
	 	});
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
     // TODO Auto-generated method stub
     //super.onListItemClick(l, v, position, id);
     String selection = l.getItemAtPosition(position).toString();
     //Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
     //inicio cambios chichan
     this.encuentraCliente(selection);
     Intent intent = new Intent(BuscarClientes.this, DetalleCliente.class);
     intent.putExtra("nombre", cliente.getNombre());
     intent.putExtra("apellidos", cliente.getApellidos());
     intent.putExtra("idcliente", cliente.getIdcliente());
     intent.putExtra("idempleado", idempleado);
     intent.putExtra("boolVer", boolVer);//boolean que indica quien llamo a la ventana
     startActivity(intent);
     
    }    
    
	private void encuentraCliente(String selection) {
		for(int i=0;i<listaCliente.size();i++){
			if (selection.compareTo(listaCliente.get(i).getNombre().toString()) == 0){
				cliente=listaCliente.get(i);
				break;
			}
		}
	}

	public List<String> Convierte(){
		
		List<String> lista=new ArrayList<String>();;
		for(int i=0;i<listaCliente.size();i++){
			lista.add(listaCliente.get(i).getNombre().toString());
		}
		return lista;
	}
	
}