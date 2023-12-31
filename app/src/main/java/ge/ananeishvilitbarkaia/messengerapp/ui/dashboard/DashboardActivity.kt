package ge.ananeishvilitbarkaia.messengerapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ge.ananeishvilitbarkaia.messengerapp.R
import ge.ananeishvilitbarkaia.messengerapp.databinding.ActivityDashboardBinding
import ge.ananeishvilitbarkaia.messengerapp.ui.addusers.AddUsersActivity
import ge.ananeishvilitbarkaia.messengerapp.ui.profile.ProfileFragment
import ge.ananeishvilitbarkaia.messengerapp.ui.userlist.UserListFragment

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        val firstFragment= UserListFragment()
        val secondFragment= ProfileFragment()

        setCurrentFragment(firstFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.setting->setCurrentFragment(secondFragment)
            }
            true
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddUsersActivity::class.java))
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}