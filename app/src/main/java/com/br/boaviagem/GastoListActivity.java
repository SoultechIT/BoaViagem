package com.br.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marco on 25/05/2017.
 */

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    private List<Map<String,Object>> gastos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String[] de = {"data", "descricao", "valor", "categoria"};

        int[] para = {R.id.data, R.id.descricao, R.id.valor, R.id.categoria};

        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(), R.layout.lista_gasto, de, para);
        adapter.setViewBinder(new GastoViewBinder());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        // Insert do menu de contexto
        registerForContextMenu(getListView());

        // Método antigo
        /*setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listarGastos()));
        ListView listView = getListView();
        listView.setOnItemClickListener(this);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*        TextView textView = (TextView) view;
        String mensagem = "Gasto Selecionado: " +textView.getText();*/

        Map<String, Object> map = gastos.get(position);
        String gasto = (String) map.get("descricao");
        String mensagem = "Gasto Selecionado: " + gasto;
        Toast.makeText(getApplicationContext(), mensagem,
                Toast.LENGTH_SHORT).show();

    }

    private List<Map<String,Object>> listarGastos(){

        gastos = new ArrayList<Map<String,Object>>();

        Map<String,Object> item = new HashMap<>();
        item.put("data", "03/02/2017");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 138,40");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        item = new HashMap<>();
        item.put("data", "04/02/2017");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 120,30");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        item = new HashMap<>();
        item.put("data", "04/02/2017");
        item.put("descricao", "Sanduba");
        item.put("valor", "R$ 19,90");
        item.put("categoria", R.color.categoria_alimentacao);
        gastos.add(item);

        item = new HashMap<>();
        item.put("data", "04/02/2017");
        item.put("descricao", "Metrô");
        item.put("valor", "R$ 5,50");
        item.put("categoria", R.color.categoria_transporte);
        gastos.add(item);

        item = new HashMap<>();
        item.put("data", "04/02/2017");
        item.put("descricao", "Original");
        item.put("valor", "R$ 12,50");
        item.put("categoria", R.color.categoria_outros);
        gastos.add(item);

        item = new HashMap<>();
        item.put("data", "05/02/2017");
        item.put("descricao", "Rollmops");
        item.put("valor", "R$ 12,50");
        item.put("categoria", R.color.categoria_outros);
        gastos.add(item);

        return gastos;
        //return Arrays.asList("Sanduba, R$ 19,00", "Original, R$ 12,00", "Rollmops, R$ 3,50");
    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{

        private String dataAnterior = "";

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            if(view.getId() == R.id.data){
                if(!dataAnterior.equals(data)){
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if(view.getId() == R.id.categoria){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }

            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gasto_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.remover){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            gastos.remove(info.position);
            getListView().invalidateViews();
            //remover do banco de dados
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
