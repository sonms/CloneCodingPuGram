package com.example.clonecoding_instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.clonecoding_instagram.databinding.ActivityTestBinding
import com.example.clonecoding_instagram.navigation.AccountFragment
import com.example.clonecoding_instagram.navigation.CameraFragment
import com.example.clonecoding_instagram.navigation.HomeFragment
import com.example.clonecoding_instagram.navigation.SearchFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_test.*
import kotlin.math.sign

class TestActivity : AppCompatActivity() {
    private lateinit var main_content : LinearLayout
    private lateinit var bottom_navigationview : BottomNavigationView
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        /*val btn : Button = findViewById(R.id.sign_out)
        btn.setOnClickListener {
            signOOut()
        }*/
        init()
        initNavigationBar()

    }
    private fun init() {
        main_content = findViewById(R.id.main_content)
        bottom_navigationview = findViewById(R.id.bottom_navigationview)
    }
    private fun initNavigationBar() {
        bottom_navigationview.run {
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
            selectedItemId = R.id.home
        }
    }
    private fun changeFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, fragment)
            .commit()
    }
    private fun signOOut() {
        auth.signOut()
        finish()
    }
}