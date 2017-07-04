package com.br.boaviagem;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by marco on 02/07/2017.
 */

public class ConfiguracoesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
