package com.br.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by marco on 23/05/2017.
 */

public class GastoActivity extends Activity {

    private int ano, mes, dia;
    private Button dataGasto;
    private Spinner categoria;
    private DataBaseHelper helper;
    private EditText valor, descricao, local;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto);
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataGasto = (Button) findViewById(R.id.data);
        dataGasto.setText(dia + "/" + mes + "/" + ano);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categoria_gasto,
                android.R.layout.simple_spinner_item);
        categoria = (Spinner) findViewById(R.id.categoria);
        categoria.setAdapter(adapter);

        valor = (EditText) findViewById(R.id.valor);
        descricao = (EditText) findViewById(R.id.descricao);
        local = (EditText) findViewById(R.id.local);

        // Recupera ID da Viagem
        id = getIntent().getStringExtra(Constantes.VIAGEM_ID);

        helper = new DataBaseHelper(this);

    }

    public void selecionarData(View view){
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(R.id.data == id){
            return new DatePickerDialog(this,listener, ano, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataGasto.setText(dia + "/" + (mes+1) + "/" + ano);
        }
    };

    public void registrarGasto(View view){

        SQLiteDatabase db = helper.getWritableDatabase();
        long resultado;

        ContentValues values = new ContentValues();
        values.put("categoria", categoria.getSelectedItem().toString());
        values.put("valor", valor.getText().toString());
        values.put("data", dataGasto.getText().toString());
        values.put("descricao", descricao.getText().toString());
        values.put("local", local.getText().toString());

        if(id!=null){
            values.put("viagem_id", id);
        }
        else{
            values.put("viagem_id", 1);
        }

        resultado = db.insert("gasto", null, values);

        if(resultado != -1){
            Toast toast = Toast.makeText(this,getString(R.string.registro_salvo),Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this,getString(R.string.erro_salvar),Toast.LENGTH_LONG);
            toast.show();
        }

        /*String msg = getResources().getString(R.string.nao_implementado);
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();*/
    }
}
