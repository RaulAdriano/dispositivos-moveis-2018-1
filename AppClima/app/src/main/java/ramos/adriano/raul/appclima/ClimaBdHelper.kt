package ramos.adriano.raul.appclima

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.security.AccessControlContext

class ClimaBdHelper : SQLiteOpenHelper{
    override fun onCreate(bd: SQLiteDatabase) {
        val CREATE_TABLE_CLIMA = """
            CREATE TABLE ${ClimaContrato.Climas.TABELA} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ClimaContrato.Climas.COLUNA_DATA_HORA} INTEGER NOT NULL,
                ${ClimaContrato.Climas.COLUNA_CLIMA_ID} INTEGER NOT NULL,
                ${ClimaContrato.Climas.COLUNA_MIN_TEMP} REAL NOT NULL,
                ${ClimaContrato.Climas.COLUNA_MAX_TEMP} REAL NOT NULL,
                ${ClimaContrato.Climas.COLUNA_UMIDADE} REAL NOT NULL,
                ${ClimaContrato.Climas.COLUNA_PRESSAO} INTEGER NOT NULL,
                ${ClimaContrato.Climas.COLUNA_VEL_VENTO} REAL NOT NULL,
                ${ClimaContrato.Climas.COLUNA_GRAUS} REAL NOT NULL
            );
        """
        bd.execSQL(CREATE_TABLE_CLIMA)
    }

    override fun onUpgrade(bd: SQLiteDatabase,
                           versaoAnterior: Int, novaVersao: Int) {
        bd.execSQL("DROP TABLE IF EXISTS ${ClimaContrato.Climas.TABELA};")
        onCreate(bd)
    }

    companion object {
        val BD_NOME = "clima.db"
        val BD_VERSAO = 1
    }

    constructor(context: Context) :
            super(context, BD_NOME, null, BD_VERSAO) {
    }
}