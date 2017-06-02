package com.br.boaviagem;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Objects;

/**
 * Created by marco on 25/05/2017.
 */

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    private List<Map<String,Object>> viagens;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Método antigo
        /*setListAdapter(new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, listarViagens()));
                ListView listView = getListView();
                listView.setOnItemClickListener(this);*/

        // Método novo
        String[] de = {"imagem", "destino", "data", "valor"};
        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor};

        SimpleAdapter adapter = new SimpleAdapter(this,listarViagens(),R.layout.lista_viagem,de,para);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Método antigo
        /*TextView textView = (TextView) view;
        String mensagem = "Viagem Selecionada: " +textView.getText();*/
        Map<String,Object> map = viagens.get(position);
        String destino = (String) map.get("destino");
        String mensagem = "Viagem Selecionada: " + destino;
        Toast.makeText(getApplicationContext(), mensagem,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GastoListActivity.class));

    }

    private List<Map<String, Object>> listarViagens(){

        // Método anterior
        /*return Arrays.asList("São Paulo", "Bonito", "Maceió");*/

        viagens = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<String, Object>();

        item.put("imagem", R.drawable.negocios);
        item.put("destino", "São Paulo");
        item.put("data", "02/02/2017 a 04/02/2017");
        item.put("valor","Gasto Total de R$ 565,78 ");
        viagens.add(item);

        item = new HashMap<String, Object>();

        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Maceió");
        item.put("data", "14/05/2017 a 21/05/2017");
        item.put("valor","Gasto Total de R$ 2382,12 ");
        viagens.add(item);

        return viagens;

    }
}
