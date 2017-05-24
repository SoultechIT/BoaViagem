package com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by marco on 11/05/2017.
 */

public class DashboardActivity extends Activity {

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.dashboard);
    }

    public void selecionarOpcao (View view){

        switch (view.getId()){
            case R.id.novaViagem:
                startActivity(new Intent(this,ViagemActivity.class));
                break;

            case R.id.novoGasto:
                startActivity(new Intent(this,GastoActivity.class));
                break;

            default:
                TextView textView = (TextView) view;
                String opcao = "Opção: " + textView.getText().toString();
                Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
