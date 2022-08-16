package com.example.clonecoding_instagram

import android.content.Intent
import android.content.pm.LauncherActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.clonecoding_instagram.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth

class MainActivity : AppCompatActivity() {
    val GET_GALLERY_IMAGE = 200
    private var googleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private val TAG = "GoogleActivity"
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    // [END declare_auth]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            finish()
        }
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // [END config_signin]

        //구글로그인
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val btn: SignInButton = findViewById(R.id.Login_Button)
        btn.setOnClickListener {
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            //googleSignIn()
        }
        // [END signin]

        //일반이메일 로그인
        editTextEmail = findViewById(R.id.login_Text)
        editTextPassword = findViewById(R.id.password_Text)

        val emailloginbtn: Button = findViewById(R.id.account_btn)
        emailloginbtn.setOnClickListener {
            createAccount(editTextEmail.text.toString(), editTextPassword.text.toString())
        }

        //갤러리 이동 코드

        //val gallerybtn : Button = findViewById(R.id.gallery_button)
        //gallerybtn.setOnClickListener {
          //  val intent = Intent(Intent.ACTION_PICK)
           // intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            //startActivityForResult(intent,GET_GALLERY_IMAGE)
        //}


    }
    /*fun googleSignIn() {
        LauncherActivityInfo
    }*/
    /*public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            goHomeFragment(auth.currentUser)
        }
    }*/



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun createAccount(email: String, password: String) { //6월27일 추가
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "회원가입 성공",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    loginUser(email, password)
                    // If sign in fails, display a message to the user.
                    /*Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()*/
                    //updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "이메일 로그인 완료", task.exception)
                    Toast.makeText(this@MainActivity, "이메일로그인완료",
                        Toast.LENGTH_SHORT).show()
                    auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser
                    val intent = Intent(this, TestActivity::class.java)
                    startActivity(intent)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "로그인 실패",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                    goHomeFragment(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    // [END auth_with_google]
    fun goHomeFragment(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}