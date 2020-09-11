package com.freedom.googlescholarshipproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.freedom.googlescholarshipproject.MainActivity
import com.freedom.googlescholarshipproject.fragment.AppFragment
import com.freedom.googlescholarshipproject.fragment.LearningLeaders
import com.freedom.googlescholarshipproject.fragment.SkillIQLeaders
import com.freedom.googlescholarshipproject.fragment.TabAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    private var menu: Menu? = null
    private var adapter: TabAdapter? = null
    private var viewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    private var btnSubmit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        viewPager = findViewById(R.id.viewPager)
        mTabLayout = findViewById(R.id.tabLayout)
        btnSubmit = findViewById(R.id.btn_submit)
        setSupportActionBar(toolbar)

        btnSubmit!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, Submission::class.java)
            startActivity(i)
        })
//        val i = intent
        adapter = TabAdapter(supportFragmentManager)
        adapter!!.addFragment(LearningLeaders(), "Learning Leaders")
        adapter!!.addFragment(SkillIQLeaders(), "Skill IQ Leaders")
        setupWithViewPager()
    }

    fun setCurrentTab(position: Int) {
        setCurrentTab(position, false)
    }

    private var mLoadInstagram = false
    fun setCurrentTab(position: Int, loadInstagram: Boolean) {
        mLoadInstagram = loadInstagram
        viewPager!!.currentItem = position
    }

    private fun setupWithViewPager() {
        viewPager!!.adapter = adapter
        mTabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                if (adapter!!.getItem(position) is AppFragment) {
                    val fragmentToShow = adapter!!.getItem(position) as AppFragment
                    fragmentToShow.onResumeFragment()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        //        getMenuInflater().inflate(R.menu.main, menu);
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        return super.onOptionsItemSelected(item)
//    }

    private var mBackPressed: Long = 0
    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        var sectionType = 0
        private const val TIME_INTERVAL = 2000
    }
}