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
import com.example.clonecoding_instagram.databinding.ActivityTestBinding
import com.example.clonecoding_instagram.navigation.AccountFragment
import com.example.clonecoding_instagram.navigation.CameraFragment
import com.example.clonecoding_instagram.navigation.HomeFragment
import com.example.clonecoding_instagram.navigation.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class TestActivity : AppCompatActivity() {
    private lateinit var main_content : LinearLayout //xml의 content를 담는 layout
    private lateinit var bottom_navigationview : BottomNavigationView
    private var auth: FirebaseAuth? = null
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it-> changeFragment(CameraFragment(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        auth = FirebaseAuth.getInstance()

        initBottomNavigationBar()
        ActivityCompat.requestPermissions(this@TestActivity,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1) //권한
    }

    private fun initBottomNavigationBar() {
        main_content = findViewById(R.id.main_content)
        bottom_navigationview = findViewById(R.id.bottom_navigationview)

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
                        //권한 체크    
                        if (ContextCompat.checkSelfPermission(this@TestActivity.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                            launcher.launch("image/*")
                        } else {
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
    fun changeFragment(fragment : Fragment) {
        //프래그먼트를 교체 하는 작업을 수행할 수 있게 해줍니다.
        //그러나 replace라 fragment전환 시 데이터가 replace됨으로 
        //상황에 맞게 hide를 사용하도록 하기
        //또는 add사용하기
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, fragment) //activity_test의 main content에 replace
            .commit()
    }


}
