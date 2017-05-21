package com.br.boaviagem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by marco on 21/05/2017.
 */

public class ViagemActivity extends Activity {

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.novaviagem);
    }

    public void salvarViagem(View V){
        String msg = getResources().getString(R.string.nao_implementado);
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }
}
