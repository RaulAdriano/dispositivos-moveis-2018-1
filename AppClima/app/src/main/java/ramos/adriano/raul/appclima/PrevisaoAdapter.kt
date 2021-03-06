package ramos.adriano.raul.appclima

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Raul on 16/04/2018.
 */
class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private var dadosClima: Array<String?>?
    private var itemClickListener: ClimaItemClickListener

    constructor(dadosClima: Array<String?>?, itemClickListener: ClimaItemClickListener) {
        this.dadosClima = dadosClima
        this.itemClickListener = itemClickListener
    }


    inner class PrevisaoViewHolder: RecyclerView.ViewHolder{

        val tvDadosPrevisao : TextView?

        constructor(itemView: View?) : super(itemView) {


            tvDadosPrevisao = itemView?.findViewById<TextView>(R.id.tv_dados_previsao)

            itemView!!.setOnClickListener({
                itemClickListener.onItemClick(adapterPosition)
          })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PrevisaoViewHolder {
        val clima_list_item = LayoutInflater.from(parent?.context)
                .inflate(R.layout.previsao_lista_item,parent,false)
        val previsao_viewHolder = PrevisaoViewHolder(clima_list_item)
        return previsao_viewHolder
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder?, position: Int) {
        val posicao = dadosClima?.get(position)
        holder?.tvDadosPrevisao?.text = posicao.toString()

    }

    override fun getItemCount(): Int {
        if(dadosClima==null){
            return 0
        }else{
            return dadosClima!!.size
        }
    }

    fun setDadosClima(dados : Array<String?>?){
        dadosClima = dados
        notifyDataSetChanged()
    }

    interface ClimaItemClickListener {
         fun onItemClick(index: Int)
    }

    fun getDadosClima() : Array<String?>? {
            return dadosClima
    }

}