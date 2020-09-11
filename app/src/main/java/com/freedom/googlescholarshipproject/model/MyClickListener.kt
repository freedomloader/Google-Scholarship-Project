package com.freedom.googlescholarshipproject.model

import android.view.View

interface MyClickListener {
    fun onItemClick(view: View?, model: DataModel?, pos: Int)
    fun onItemLongClick(view: View?, model: DataModel?, pos: Int)
}