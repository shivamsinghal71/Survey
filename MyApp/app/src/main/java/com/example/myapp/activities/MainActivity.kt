package com.example.myapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.fragmants.StartSurveyFragment
import com.example.myapp.fragmants.VisitDetailsFragment
import com.example.myapp.fragmants.VisitedDataFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var mainBinding: ActivityMainBinding
    private var actionBar: ActionBar? = null
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navigationView = mainBinding.navView
        val header = navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.empname)
        val Uid = header.findViewById<TextView>(R.id.empId)



       // name.setText("hdjh")

        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null //====for color=========

        val toggle = ActionBarDrawerToggle(
            this,
            mainBinding.drawerLayout,
            mainBinding.appBarHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mainBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar!!.setHomeButtonEnabled(true)
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.black)
        }


        Utility.addFragment(StartSurveyFragment(mainBinding), supportFragmentManager, R.id.layout_fragment)

      //  updateUI()
    }





    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = item.isChecked
        mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
        var fragment: Fragment? = null
        when (item.itemId) {

            R.id.navigation_visitedList -> {
//                actionBar!!.title = "Visited List"
//                actionBar!!.subtitle = ""
                fragment = VisitedDataFragment(mainBinding!!)
                Utility.replaceFragment(
                    fragment,
                    supportFragmentManager,
                    R.id.layout_fragment
                )
            }

            R.id.navigation_Logout-> {

                UserDataHelper.deleteAll(this)
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        return true
    }


    companion object {
        @JvmField
        var bottom_Navigation: BottomNavigationView? = null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            try {
                val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START)
                } else {
                    val fragments = supportFragmentManager.backStackEntryCount
                    val size: Int = supportFragmentManager.fragments.size
                    val fragment: Fragment?
                    var otherfragment: Fragment? = null
                    if (supportFragmentManager.fragments[size - 1] is SupportRequestManagerFragment) {
                        if (size > 1) {
                            otherfragment = supportFragmentManager.fragments[size - 2]
                        }
                    } else {
                        otherfragment = supportFragmentManager.fragments[size - 1]
                    }
                    fragment = otherfragment
                    if (fragment is VisitedDataFragment || fragment is StartSurveyFragment || fragment is VisitDetailsFragment) {
                        onBackPressed()
                        return true
                    }
                    else  {
                        return false
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
        return false
        // Disable back button..............
    }

}