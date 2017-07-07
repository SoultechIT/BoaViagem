package com.br.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.novaviagem);

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

        helper = new DataBaseHelper(this);

    }

    public void salvarViagem(View view){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("destino", destino.getText().toString());
        values.put("data_chegada", dataChegada.getTime());
        values.put("data_saida", dataSaida.getTime());
        values.put("orcamento", orcamento.getText().toString());
        values.put("quantidade_pessoas", quantidadePessoas.getText().toString());

        int tipo = radioGroup.getCheckedRadioButtonId();

        if (tipo==R.id.lazer){
            values.put("tipo_viagem", 1);
        } else{
            values.put("tipo_viagem", 2);
        }

        long resultado = db.insert("viagem", null, values);

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