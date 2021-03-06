package com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by marco on 11/05/2017.
 */

public class BoaViagemActivity extends Activity {

    private static final String MANTER_CONECTADO = "manter_conectado";

    private EditText usuario;
    private EditText senha;
    private CheckBox manterConectado;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.login);

        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);

        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean conectado = preferencias.getBoolean(MANTER_CONECTADO, false);

        if(conectado){
            startActivity(new Intent(this,DashboardActivity.class));
        }
    }

    public void entrarOnClick(View V){

        String usuarioInformado = usuario.getText().toString();
        String senhainformada = senha.getText().toString();

        if(usuarioInformado.equalsIgnoreCase("leitor") &&
                senhainformada.equalsIgnoreCase("123")){

            SharedPreferences preferencias = getPreferences(MODE_PRIVATE);

            SharedPreferences.Editor editor = preferencias.edit();
            editor.putBoolean(MANTER_CONECTADO, manterConectado.isChecked());
            editor.commit();

            startActivity(new Intent(this,DashboardActivity.class));
        }else{
            String msgErroAutenticacao = getString(R.string.erroAutenticacao);
            Toast toast = Toast.makeText(this,msgErroAutenticacao,Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
