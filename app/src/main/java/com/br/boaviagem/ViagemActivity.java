package com.br.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by marco on 21/05/2017.
 */

public class ViagemActivity extends Activity {

    private Date dataChegada, dataSaida;
    private int ano, mes, dia;
    private Button dataChegadaButton, dataSaidaButton;
    private DataBaseHelper helper;
    private EditText destino, quantidadePessoas, orcamento;
    private RadioGroup radioGroup;
    private String id;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.novaviagem);

        id = getIntent().getStringExtra(Constantes.VIAGEM_ID);
        helper = new DataBaseHelper(this);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataChegadaButton = (Button) findViewById(R.id.dataChegada);
        dataChegadaButton.setText(dia + "/" + (mes+1) + "/" + ano);
        dataSaidaButton = (Button) findViewById(R.id.dataSaida);
        dataSaidaButton.setText(dia + "/" + (mes+1) + "/" + ano);

        destino = (EditText) findViewById(R.id.destino);
        quantidadePessoas = (EditText) findViewById(R.id.quantidadePessoas);
        orcamento = (EditText) findViewById(R.id.orcamento);
        radioGroup = (RadioGroup) findViewById(R.id.tipoViagem);

        if(id!=null){
            prepararEdicao();
        }

    }

    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }

    public void salvarViagem(View view){

        SQLiteDatabase db = helper.getWritableDatabase();
        long resultado;

        ContentValues values = new ContentValues();
        values.put("destino", destino.getText().toString());
        values.put("data_chegada", dataChegada.getTime());
        values.put("data_saida", dataSaida.getTime());
        values.put("orcamento", orcamento.getText().toString());
        values.put("quantidade_pessoas", quantidadePessoas.getText().toString());

        int tipo = radioGroup.getCheckedRadioButtonId();

        if (tipo==R.id.lazer){
            values.put("tipo_viagem", Constantes.VIAGEM_LAZER);
        } else{
            values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);
        }

        if(id==null){
            resultado = db.insert("viagem", null, values);
        }else {
            resultado = db.update("viagem", values, "_id = ?", new String[]{id});
        }

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

    private void prepararEdicao(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT tipo_viagem, destino, " +
                "data_chegada, data_saida, quantidade_pessoas, orcamento FROM viagem WHERE _id = ?", new String[]{id});

        cursor.moveToFirst();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (cursor.getInt(0)==Constantes.VIAGEM_LAZER){
            radioGroup.check(R.id.lazer);
        }else{
            radioGroup.check(R.id.negocios);
        }

        destino.setText(cursor.getString(1));
        dataChegada = new Date(cursor.getLong(2));
        dataSaida = new Date(cursor.getLong(3));
        dataChegadaButton.setText(dateFormat.format(dataChegada));
        dataSaidaButton.setText(dateFormat.format(dataSaida));
        quantidadePessoas.setText(cursor.getString(4));
        orcamento.setText(cursor.getString(5));
        cursor.close();
    }

    public void selecionarData(View view){
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id){

        switch (id){
            case R.id.dataChegada:
                return new DatePickerDialog(this, dataChegadaListner, ano, mes, dia);

            case R.id.dataSaida:
                return new DatePickerDialog(this, dataSaidaListner, ano, mes, dia);

        }


     /*   if(R.id.data == id){
            return new DatePickerDialog(this, dataChegadaListner, ano, mes, dia);
        }*/
        return null;
    }

    private DatePickerDialog.OnDateSetListener dataChegadaListner = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            /*ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;*/
            dataChegada = criarData(year, monthOfYear, dayOfMonth);
            dataChegadaButton.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    };

    private DatePickerDialog.OnDateSetListener dataSaidaListner = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            /*ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;*/
            dataSaida = criarData(year, monthOfYear, dayOfMonth);
            dataSaidaButton.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    };

    private Date criarData(int ano, int mes, int dia){
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);
        return calendar.getTime();
    }
}