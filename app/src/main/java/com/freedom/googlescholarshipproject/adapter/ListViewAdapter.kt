package com.freedom.googlescholarshipproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.freedom.googlescholarshipproject.R
import com.freedom.googlescholarshipproject.model.DataModel
import java.util.*

class ListViewAdapter(private val mContext: Context?, mData: ArrayList<DataModel>?,
                      modelType: String, fromLocal: Boolean) : RecyclerViewAdapter(mContext) {
    private val modelType: String
    private val fromLocal: Boolean
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomViewHolder {
//        val layout: View
        val layout = LayoutInflater.from(mContext).inflate(R.layout.listview_row_item, viewGroup, false)
        return CustomViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.image.animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation)
        holder.container.animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation)
        val data = mData!![position]

        holder.tv_title.text = data.name

        if (modelType == "skill") {
            holder.tv_content.text = data.hours + " skill IQ Score, " + data.country
            if (data.badgeUrl != null) {
                Glide.with(mContext!!)
                        .load(data.badgeUrl)
                        .fitCenter()
                        .into(holder.image)
            }
        } else {
            holder.tv_content.text = data.hours + " learning hours, " + data.country
            if (data.badgeUrl != null) {
                Glide.with(mContext!!)
                        .load(data.badgeUrl)
                        .transform(CenterCrop(), RoundedCorners(30))
                        .into(holder.image)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (null != mData) mData!!.size else 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onItemClick(v: View?, model: DataModel?, position: Int) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(v, model, position)
        }
    }

    init {
        this.mData = mData
        this.modelType = modelType
        this.fromLocal = fromLocal
    }
}