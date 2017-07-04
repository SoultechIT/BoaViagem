package com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

            case R.id.minhasViagens:
                startActivity(new Intent(this,ViagemListActivity.class));
                break;

            case R.id.configuracoes:
                startActivity(new Intent(this, ConfiguracoesActivity.class));
                break;

            default:
                TextView textView = (TextView) view;
                String opcao = "Opção: " + textView.getText().toString();
                Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        finish();
        return true;
    }
}
