package com.example.clonecoding_instagram

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.clonecoding_instagram.databinding.ActivityHomeBinding
import com.example.clonecoding_instagram.navigation.AccountFragment
import com.example.clonecoding_instagram.navigation.CameraFragment
import com.example.clonecoding_instagram.navigation.HomeFragment
import com.example.clonecoding_instagram.navigation.SearchFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var homeFragment : HomeFragment
    private lateinit var cameraFragment: CameraFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var binding: ActivityHomeBinding
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //각 네비게이션바의 메뉴탭을 화면이 전환 되도록함
        //initNavigationBar()

       
    }
    /*fun initNavigationBar() {
        var homeFragment = HomeFragment()
        var cameraFragment = CameraFragment()
        binding.bottomNavigationview.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> {
                        changeFragment(HomeFragment())
                    }
                    R.id.search -> {
                        changeFragment(SearchFragment())
                    }
                    R.id.camera -> {
                        changeFragment(CameraFragment())
                    }
                    R.id.account -> {
                        changeFragment(AccountFragment())
                    }
                }
                true
            }
            //default = home
            selectedItemId = R.id.home
        }
    }
    private fun changeFragment(fragment : Fragment) {
    //fragment 변경 
        supportFragmentManager
            .beginTransaction()
            .replace(com.google.android.material.R.id.container, fragment)
            .commit()
    }*/
    
}
