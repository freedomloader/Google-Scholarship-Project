package com.freedom.googlescholarshipproject.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.freedom.googlescholarshipproject.R
import com.freedom.googlescholarshipproject.adapter.ListViewAdapter
import com.freedom.googlescholarshipproject.adapter.RecyclerViewAdapter
import com.freedom.googlescholarshipproject.model.DataModel
import com.freedom.googlescholarshipproject.model.MyClickListener
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.util.*

class SkillIQLeaders : AppFragment(), MyClickListener {
    private var dataList: RecyclerView? = null
    private var dataModelArrays: ArrayList<DataModel>? = null
    private var emptyView: View? = null
    private var swipeLayout: SwipeRefreshLayout? = null
    private var mItemAdapters: RecyclerViewAdapter? = null
    private var act: Activity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.learning_leaders, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        act = activity
        init(v)
        loadDatas()
    }

    private fun init(v: View) {
        emptyView = v.findViewById(R.id.empty_layout)
        dataList = v.findViewById(R.id.dataList)
        swipeLayout = v.findViewById(R.id.swipeToRefresh)
//        swipeLayout.setOnRefreshListener(OnRefreshListener { updateDatas() })
        dataModelArrays = ArrayList()
        loadListNdAdapter()
        mItemAdapters!!.setOnClickListener(this)
    }

    private fun loadListNdAdapter() {
        val padding = resources.getDimensionPixelSize(R.dimen.listview_padding)
        dataList!!.setPadding(padding, 0, padding, 0)
        dataList!!.layoutManager = LinearLayoutManager(act)
        dataList!!.setBackgroundColor(resources.getColor(R.color.listview_bg_color))
        mItemAdapters = ListViewAdapter(context, dataModelArrays, "skill", true)
        dataList!!.adapter = mItemAdapters
    }

    private fun checkAdapters() {
        if (dataList == null || dataList!!.adapter == null) {
            loadListNdAdapter()
            return
        }
        loadListNdAdapter()
    }

    private fun updateDatas() {
        loadDatas()
        mItemAdapters!!.notifyDataSetChanged()
    }

    private fun loadDatas() {
        checkAdapters()
        val url = "https://gadsapi.herokuapp.com/api/skilliq"
        val client = OkHttpClient()
        val request = Request.Builder().url(url)
                .header("Accept", "application/json").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val datas: MutableList<DataModel> = ArrayList()
                try {
                    val result = response.body!!.string()
                    val array = JSONArray(result)
                    for (i in 0 until array.length()) {
                        val json = array.getJSONObject(i)
                        val data = DataModel()
                        data.name = json.getString("name")
                        data.hours = json.getString("score")
                        data.country = json.getString("country")
                        data.badgeUrl = json.getString("badgeUrl")
                        datas.add(data)
                    }
                    act!!.runOnUiThread { loadAdapter(datas) }
                    //                    Log.d("LEADERS", result+"");
                } catch (e: Exception) {
                    Log.d("ERROR_LEARNINGLEADERS", e.message)
                }
            }
        })
    }

    private fun loadAdapter(models: List<DataModel>?) {
        if (models != null) {
            dataModelArrays!!.clear()
            dataModelArrays!!.addAll(models)
            mItemAdapters!!.notifyDataSetChanged()
            if (dataModelArrays!!.size > 0) {
                emptyView!!.visibility = View.GONE
            } else {
                emptyView!!.visibility = View.VISIBLE
            }
        } else {
            emptyView!!.visibility = View.VISIBLE
        }
        if (swipeLayout!!.isRefreshing) {
            swipeLayout!!.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        //        updateDatas();
    }

    override fun onResumeFragment() {
        super.onResumeFragment()
        //        updateDatas();
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onItemClick(view: View?, model: DataModel?, pos: Int) {}
    override fun onItemLongClick(view: View?, model: DataModel?, pos: Int) {}
}