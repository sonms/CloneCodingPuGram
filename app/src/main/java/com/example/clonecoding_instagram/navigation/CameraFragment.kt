package com.example.clonecoding_instagram.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.clonecoding_instagram.ContentSet
import com.google.firebase.storage.FirebaseStorage
import com.example.clonecoding_instagram.R
import com.example.clonecoding_instagram.TestActivity
import com.example.clonecoding_instagram.databinding.FragmentCameraBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_camera.view.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class CameraFragment(uri: Uri?) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var uri: Uri? = uri
    private lateinit var mBinding: FragmentCameraBinding
    private var auth: FirebaseAuth? = null
    private var store : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCameraBinding.inflate(inflater, container, false)

        //????????? ?????????
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()

        //????????? ???????????? imageview?????? ?????????
        mBinding.uploadImage.setImageURI(uri)

        mBinding.uploadBtn.setOnClickListener {
            uploadImageFirebase(uri!!)
        }

        // Inflate the layout for this fragment
        return mBinding.root
    // inflater.inflate(R.layout.fragment_camera, container, false)

    }
    private fun uploadImageFirebase(uri : Uri) {
        val storage : FirebaseStorage? = FirebaseStorage.getInstance() //firebasestorage??????????????????
        //?????? ?????? ??????.
        val fileName = "IMAGE_${SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())}_.png"
        //?????? ?????????, ????????????, ??????, ??????????????? ???????????? ?????? ??????????????? ?????? ?????? ????????? ??????.
        //????????? ???????????? ????????? ???????????? ??????????????? ??? ??? ??????.

        //var storageRef : StorageReference = storage.getReference() ?????? ????????? ?????? ???????????? ????????????
        //var putRef : StorageReference = storageRef.child("images/").child(fileName)
        var imagesRef = storage!!.reference.child("images/").child(fileName)    //?????? ?????? ??????/images/${fileName}

        //var uploadTask : UploadTask = putRef.putFile(uri)
        /*imagesRef.putFile(uri).addOnSuccessListener {
            Toast.makeText(activity, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                println(it)
                Toast.makeText(activity, getString(R.string.upload_fail), Toast.LENGTH_SHORT).show()
            }*/
        imagesRef.putFile(uri!!).continueWithTask { task:Task<UploadTask.TaskSnapshot> ->
            //storage??? ???????????? ???????????? downloadurl??????
            return@continueWithTask imagesRef.downloadUrl
        }.addOnSuccessListener {
            Toast.makeText(activity,"??????", Toast.LENGTH_SHORT).show()
            var contentSet : ContentSet = ContentSet()
            contentSet.imageUrl = it.toString()
            contentSet.userEmail = auth!!.currentUser!!.email
            contentSet.uid = auth!!.currentUser!!.uid

            //FireStore??? ????????? ?????????
            store!!.collection("posts").document().set(contentSet)

            (activity as TestActivity).changeFragment(HomeFragment())

        }.addOnFailureListener {
            Toast.makeText(activity, getString(R.string.upload_fail), Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CameraFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment(uri = null).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}