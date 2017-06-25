package com.br.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener,
        DialogInterface.OnClickListener, SimpleAdapter.ViewBinder{

    private List<Map<String,Object>> viagens;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;
    private int viagemSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Método antigo
        /*setListAdapter(new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, listarViagens()));
                ListView listView = getListView();
                listView.setOnItemClickListener(this);*/

        // Método novo
        String[] de = {"imagem", "destino", "data", "valor", "barraProgresso"};
        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor, R.id.barraProgresso};

        SimpleAdapter adapter = new SimpleAdapter(this,listarViagens(),R.layout.lista_viagem,de,para);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        this.alertDialog = criaAlertDialog();
        this.dialogConfirmacao = criaAlertDialogConfirmacao();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Método 1
        /*TextView textView = (TextView) view;
        String mensagem = "Viagem Selecionada: " +textView.getText();*/

        // Método 2
        /*Map<String,Object> map = viagens.get(position);
        String destino = (String) map.get("destino");
        String mensagem = "Viagem Selecionada: " + destino;
        Toast.makeText(getApplicationContext(), mensagem,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GastoListActivity.class));*/

        this.viagemSelecionada = position;
        alertDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int item){
        switch (item){
            case 0:
                startActivity(new Intent(this, ViagemActivity.class));
                break;

            case 1:
                startActivity(new Intent(this, GastoActivity.class));
                break;

            case 2:
                startActivity(new Intent(this, GastoListActivity.class));
                break;

            case 3:
                dialogConfirmacao.show();
                /*viagens.remove(this.viagemSelecionada);
                getListView().invalidateViews();*/
                break;

            case DialogInterface.BUTTON_POSITIVE:
                viagens.remove(this.viagemSelecionada);
                getListView().invalidateViews();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;
        }
    }

    private AlertDialog criaAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
                getString(R.string.remover)};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.opcoes);
            builder.setItems(items, (DialogInterface.OnClickListener) this);
            return builder.create();
    }

    private AlertDialog criaAlertDialogConfirmacao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirma_exclusao_viagem);
        builder.setPositiveButton(getString(R.string.sim),this);
        builder.setNegativeButton(getString(R.string.nao), this);
        return builder.create();
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
        item.put("barraProgresso", new Double[]{700.0, 650.0, 565.78});
        viagens.add(item);

        item = new HashMap<String, Object>();

        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Maceió");
        item.put("data", "14/05/2017 a 21/05/2017");
        item.put("valor","Gasto Total de R$ 2382,12 ");
        item.put("barraProgresso", new Double[]{3000.0, 2500.0, 2382.12});
        viagens.add(item);

        return viagens;

    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation  ){
        if(view.getId()==R.id.barraProgresso){
            Double valores[] = (Double[]) data;
            ProgressBar progressBar = (ProgressBar) view;
            progressBar.setMax(valores[0].intValue());
            progressBar.setSecondaryProgress(valores[1].intValue());
            progressBar.setProgress(valores[2].intValue());
            return true;
        }else{
            return false;
        }
    }
}
