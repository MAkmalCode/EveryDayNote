package com.redebug.everydaynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.redebug.everydaynote.adapter.ViewPagerAdapater
import com.redebug.everydaynote.fragment.NotesFragment
import com.redebug.everydaynote.fragment.TodoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tablayout)
        val viewPager: ViewPager2 = findViewById(R.id.vp_note)
        val fragmentList = listOf<Fragment>(NotesFragment(), TodoFragment())

        val adapter = ViewPagerAdapater(this@MainActivity, fragmentList)
        viewPager.adapter = adapter

        val title = listOf<String>("Note", "Todo")

        TabLayoutMediator(tabLayout, viewPager){ tab, position->
            tab.text = title[position]
        }.attach()
    }
}