package com.br.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by marco on 25/05/2017.
 */

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listarGastos()));
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        String mensagem = "Gasto Selecionado: " +textView.getText();

        Toast.makeText(getApplicationContext(), mensagem,
                Toast.LENGTH_SHORT).show();

    }

    private List<String> listarGastos(){
        return Arrays.asList("Sanduba, R$ 19,00", "Original, R$ 12,00", "Rollmops, R$ 3,50");
    }
}
