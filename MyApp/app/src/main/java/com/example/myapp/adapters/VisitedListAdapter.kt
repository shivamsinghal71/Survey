package com.example.myapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.fragmants.VisitedDataFragment
import com.example.myapp.model.SurveyDataModel
import de.hdodenhof.circleimageview.CircleImageView

class VisitedListAdapter : RecyclerView.Adapter<VisitedListAdapter.ViewHolder> {
    var context: Context? = null
    private var itemObjectListener: VisitedListAdapter.PropertyItemListener? = null
    private var mList : List<SurveyDataModel>? = null


    constructor()

    constructor(utilityModels: List<SurveyDataModel>?, context: Context){
        this.mList = utilityModels
    }

    fun setItemListener(itemObjectListener: VisitedListAdapter.PropertyItemListener){
        this.itemObjectListener = itemObjectListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.visited_list_rec, parent, false)

        context= parent.context
        return ViewHolder(  itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val surveyDataModel: SurveyDataModel? = getItem(position)
        if (surveyDataModel != null )
        {
           if (!Utility.isNullOrEmpty(surveyDataModel.PROPERTY_NO)) {
               holder.property_no.setText("संपत्ति क्र. : " + surveyDataModel.PROPERTY_NO)
           }
            if (!Utility.isNullOrEmpty(surveyDataModel.RESPONDENT_NAME)) {
                holder.name.setText("उत्तरदाता का नाम : " + surveyDataModel.RESPONDENT_NAME)
            }
            if (!Utility.isNullOrEmpty(surveyDataModel.PARCEL_NAME)) {
                holder.parcel_no.setText("पार्सेल क्र. : " + surveyDataModel.PARCEL_NAME)
            }

            if (!Utility.isNullOrEmpty(surveyDataModel.PROPERTY_IMAGE)) {
                holder.property_image_rec.setImageBitmap(
                    Utility.getBitmapByStringImage(
                        surveyDataModel?.PROPERTY_IMAGE
                    )
                )
            }
        }

        holder.viewDetails.setOnClickListener(View.OnClickListener
        {
            itemObjectListener!!.OnItemClick(position)
        })
    }

    override fun getItemCount(): Int
    {
        return mList!!.size
    }
    fun getItem(position: Int): SurveyDataModel?
    {
        return mList!!.get(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val property_no: TextView = itemView.findViewById(R.id.property_no)
        val name: TextView = itemView.findViewById(R.id.name)
        val parcel_no: TextView = itemView.findViewById(R.id.parcel_no)
        val viewDetails: TextView = itemView.findViewById(R.id.viewDetails)
        val property_image_rec: CircleImageView = itemView.findViewById(R.id.property_image_rec)



    }

    interface PropertyItemListener{
        fun OnItemClick(position: Int)
    }


}