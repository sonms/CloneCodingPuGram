package com.example.clonecoding_instagram

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.clonecoding_instagram.navigation.AccountFragment
import com.example.clonecoding_instagram.navigation.CameraFragment
import com.example.clonecoding_instagram.navigation.HomeFragment
import com.example.clonecoding_instagram.navigation.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class TestActivity : AppCompatActivity() {
    private lateinit var main_content : LinearLayout
    private lateinit var bottom_navigationview : BottomNavigationView
    private var auth: FirebaseAuth? = null
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it-> changeFragment(CameraFragment(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        auth = FirebaseAuth.getInstance()
        /*val btn : Button = findViewById(R.id.sign_out)
        btn.setOnClickListener {
            signOOut()
        }*/
        initSet()
        initBottomNavigationBar()
        ActivityCompat.requestPermissions(this@TestActivity,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }
    private fun initSet() {
        main_content = findViewById(R.id.main_content)
        bottom_navigationview = findViewById(R.id.bottom_navigationview)
    }
    private fun initBottomNavigationBar() {
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
                        if (ContextCompat.checkSelfPermission(this@TestActivity.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                            launcher.launch("image/*")  //갤러리로 이동하는 런처 실행.
                        } else {    //앱이 갤러리에 접근햐는 것을 허용하지 않았을 경우
                            Toast.makeText(this@TestActivity,
                                "갤러리 접근 권한이 거부돼 있습니다. 설정에서 접근을 허용해 주세요.",
                                Toast.LENGTH_SHORT).show()
                        }}
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


}