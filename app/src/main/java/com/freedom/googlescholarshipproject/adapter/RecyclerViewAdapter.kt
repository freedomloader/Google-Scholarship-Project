package com.freedom.googlescholarshipproject.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freedom.googlescholarshipproject.R
import com.freedom.googlescholarshipproject.adapter.RecyclerViewAdapter.CustomViewHolder
import com.freedom.googlescholarshipproject.model.DataModel
import com.freedom.googlescholarshipproject.model.MyClickListener
import java.util.*

open class RecyclerViewAdapter internal constructor(private val mContext: Context?) : RecyclerView.Adapter<CustomViewHolder>() {
    var mOnItemClickListener: MyClickListener? = null
    var selected_items: SparseBooleanArray
    var mData: ArrayList<DataModel>? = null
    private var current_selected_idx = -1
    fun setOnClickListener(onClickListener: MyClickListener?) {
        mOnItemClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.listview_row_item, parent, false)
        return CustomViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {}
    val selectedItemCount: Int
        get() = selected_items.size()

    val selectedItems: List<Int>
        get() {
            val items: MutableList<Int> = ArrayList(selected_items.size())
            for (i in 0 until selected_items.size()) {
                items.add(selected_items.keyAt(i))
            }
            return items
        }

    private fun resetCurrentIndex() {
        current_selected_idx = -1
    }

    override fun getItemCount(): Int {
        return if (null != mData) mData!!.size else 0
    }

    val datas: List<DataModel>?
        get() = mData

    fun getData(position: Int): DataModel {
        return mData!![position]
    }

    fun removeData(position: Int) {
        mData!!.removeAt(position)
        resetCurrentIndex()
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, OnLongClickListener {
        var tv_title: TextView
        var tv_content: TextView
        var image: ImageView
        var container: RelativeLayout
        override fun onClick(v: View) {
            val model = getData(adapterPosition)
            onItemClick(v, model, adapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            val model = getData(adapterPosition)
            onItemLongClick(v, model, adapterPosition)
            return true
        }

        init {
            container = itemView.findViewById(R.id.container)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_content = itemView.findViewById(R.id.tv_description)
            image = itemView.findViewById(R.id.image)
            container.setOnClickListener(this)
            container.setOnLongClickListener(this)
        }
    }

    open fun onItemClick(v: View?, model: DataModel?, position: Int) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(v, model, position)
        }
    }

    fun onItemLongClick(v: View?, model: DataModel?, position: Int) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemLongClick(v, model, position)
        }
    }

    init {
        selected_items = SparseBooleanArray()
    }
}