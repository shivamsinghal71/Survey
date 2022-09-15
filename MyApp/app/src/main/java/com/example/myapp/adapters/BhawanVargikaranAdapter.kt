package com.example.myapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class BhawanVargikaranAdapter : RecyclerView.Adapter<BhawanVargikaranAdapter.ViewHolder> {
    var context: Context? = null
    private var itemObjectListener: UtilityItemListener? = null
    private lateinit var bhawanVargikaranList :ArrayList<HashMap<String,String>>


    constructor()

    constructor(bhawanVargikaranList: ArrayList<HashMap<String, String>>, context: Context) {
        this.context = context
        this.bhawanVargikaranList=bhawanVargikaranList
    }


    fun setItemListener(itemObjectListener: UtilityItemListener){
        this.itemObjectListener = itemObjectListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bhawan_vargikaran_rec_layout, parent, false)

        context= parent.context
        return ViewHolder(  itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

        holder.txtManjilRec.setText(bhawanVargikaranList.get(position).get("manjil_tal_code").toString())
        holder.txtYearRec.setText(bhawanVargikaranList.get(position).get("construction_year_c").toString())
        holder.txtBhawanCodeRec.setText(bhawanVargikaranList.get(position).get("bhawan_code").toString())
        holder.txtFloorRec.setText(bhawanVargikaranList.get(position).get("floor_code").toString())
        holder.txtAreaRec.setText(bhawanVargikaranList.get(position).get("area_c").toString())
        holder.txtUpyogRec.setText(bhawanVargikaranList.get(position).get("majil_uses_code").toString())
    }


    override fun getItemCount(): Int
    {
        return bhawanVargikaranList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtAreaRec: TextView = itemView.findViewById(R.id.txtAreaRec)
        val txtYearRec: TextView = itemView.findViewById(R.id.txtYearRec)
        val txtManjilRec: TextView = itemView.findViewById(R.id.txtManjilRec)
        val txtUpyogRec: TextView = itemView.findViewById(R.id.txtUpyogRec)
        val txtFloorRec: TextView = itemView.findViewById(R.id.txtFloorRec)
        val txtBhawanCodeRec: TextView = itemView.findViewById(R.id.txtBhawanCodeRec)



    }


    interface UtilityItemListener{
        fun OnItemClick(position: Int)
    }

}