package ramos.adriano.raul.metaspessoais

import java.util.*

/**
 * Created by Raul on 12/04/2018.
 */
class Metas {

    //variaveis  das metas
    var dataInicial : String
    var dataFinal : String
    var titulo : String
    var descricao : String
    var situacao : String

    // construtor da classe
    constructor(dataInicial: String, dataFinal: String, titulo: String, descricao: String, situacao: String) {
        this.dataInicial = dataInicial
        this.dataFinal = dataFinal
        this.titulo = titulo
        this.descricao = descricao
        this.situacao = situacao
    }


    //Exibir na listview
    override fun toString(): String {
        return  "\n\nTitulo -> $titulo\n " +
                "Descricao -> $descricao\n"+
                "Data de lançamento -> $dataInicial \n" +
                "Data de Finalizada -> $dataFinal\n"+
                "Situação -> $situacao\n"
    }
}