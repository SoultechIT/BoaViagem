package com.br.boaviagem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marco on 04/07/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "BoaViagem";
    private static final int VERSAO = 1;

    public DataBaseHelper(Context context){
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  /*      db.execSQL("CREATE TABLE viagem (_id INTEGER PRIMARY_KEY, " +
                "destino TEXT, tipo_viagem INTEGER, " +
                "data_chegada DATE, data_saida DATE, " +
                "orcamento DOUBLE, quantidade_pessoas INTEGER);");
*/
        db.execSQL("CREATE TABLE VIAGEM (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " DESTINO TEXT, TIPO_VIAGEM INTEGER, DATA_CHEGADA DATE," +
                " DATA_SAIDA DATE, ORCAMENTO DOUBLE, QUANTIDADE_PESSOAS INTEGER);");

        /*db.execSQL("CREATE TABLE gasto (_id INTEGER PRIMARY_KEY, " +
                "categoria TEXT, data DATE, valor DOUBLE, " +
                "descricao TEXT, local TEXT, viagem_id INTEGER, " +
                "FOREIGN_KEY(viagem_id) REFERENCES viagem(_id));");
*/
        db.execSQL("CREATE TABLE GASTO (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " CATEGORIA TEXT, DATA DATE, VALOR DOUBLE, DESCRICAO TEXT," +
                " LOCAL TEXT, VIAGEM_ID INTEGER," +
                " FOREIGN KEY(VIAGEM_ID) REFERENCES VIAGEM(_ID));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
