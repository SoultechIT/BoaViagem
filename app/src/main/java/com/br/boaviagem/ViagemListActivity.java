package com.br.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private DataBaseHelper helper;
    private SimpleDateFormat dateFormat;
    private Double valorLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Método antigo
        /*setListAdapter(new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, listarViagens()));
                ListView listView = getListView();
                listView.setOnItemClickListener(this);*/

        // Método novo

        helper = new DataBaseHelper(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

        String valor = preferencias.getString("valor_limite", "-1");
        valorLimite = Double.valueOf(valor);

        String[] de = { "imagem", "destino", "data", "valor", "barraProgresso"};
        int[] para = { R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor, R.id.barraProgresso};

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

        Intent intent;
        String id = (String) viagens.get(viagemSelecionada).get("id");

        switch (item){
            case 0: //Editar viagem
                intent = new Intent(this, ViagemActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID, id);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(this, GastoActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID, id);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(this, GastoListActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID, id);
                startActivity(intent);
                break;

            case 3:
                dialogConfirmacao.show();
                /*viagens.remove(this.viagemSelecionada);
                getListView().invalidateViews();*/
                break;

            case DialogInterface.BUTTON_POSITIVE:
                viagens.remove(this.viagemSelecionada);
                removerViagem(id);
                getListView().invalidateViews();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;
        }
    }

    private void removerViagem(String id){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("gasto", "viagem_id = ?", new String[]{id});
        db.delete("viagem", "_id = ?", new String[]{id});
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

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, " +
                "data_chegada, data_saida, orcamento FROM viagem", null);

        cursor.moveToFirst();

        viagens = new ArrayList<Map<String,Object>>();

        for (int i=0; i<cursor.getCount();i++) {

            Map<String, Object> item = new HashMap<String, Object>();

            String id = cursor.getString(0);
            int tipoViagem = cursor.getInt(1);
            String destino = cursor.getString(2);
            long dataChegada = cursor.getLong(3);
            long dataSaida = cursor.getLong(4);
            double orcamento = cursor.getDouble(5);

            item.put("id", id);

            if(tipoViagem == Constantes.VIAGEM_LAZER){
                item.put("imagem", R.drawable.lazer);
            }
            else{
                item.put("imagem", R.drawable.negocios);
            }

            item.put("destino", destino);

            Date dataChegadaDate = new Date(dataChegada);
            Date dataSaidaDate = new Date(dataSaida);

            String periodo = dateFormat.format(dataChegadaDate) +
                    " a " + dateFormat.format(dataSaidaDate);

            item.put("data", periodo);

            double totalGasto = calcularTotalGasto(db, id);
            item.put("valor", "Gasto Total de R$ " + totalGasto);

            double alerta = orcamento * valorLimite / 100;
            item.put("barraProgresso", new Double[] {orcamento, alerta, totalGasto});

            viagens.add(item);
            cursor.moveToNext();
        }

        cursor.close();

        /*
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
        viagens.add(item);*/

        return viagens;

    }

    private double calcularTotalGasto(SQLiteDatabase db, String id){

        double total;

        try {
            Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM gasto WHERE viagem_id = ?", new String[]{id});
            cursor.moveToFirst();
            total = cursor.getDouble(0);
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
            total = 0;
        }
        return total;
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
