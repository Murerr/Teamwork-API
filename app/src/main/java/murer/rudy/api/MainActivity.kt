package murer.rudy.api

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import murer.rudy.api.home.HomeFragment
import murer.rudy.api.project.ProjectFragment

class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener (loadFragment(HomeFragment()))
            }
            R.id.navigation_projects -> {
                return@OnNavigationItemSelectedListener (loadFragment(ProjectFragment()))
            }
            /*R.id.navigation_tasks -> {
                return@OnNavigationItemSelectedListener (loadFragment(NotificationFragment()))
            }*/
        }
        false
    }
    private val mOnNavigationItemReSelectedListener = BottomNavigationView.OnNavigationItemReselectedListener {}
    private lateinit var model: MainActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        if (!this::model.isInitialized) {
            model = MainActivityModel.newInstance(this)
        }
        setContentView(R.layout.activity_main)
        if(null == savedInstanceState) {
            loadFragment(HomeFragment()) // OnCreate get last Fragment selected
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReSelectedListener)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
