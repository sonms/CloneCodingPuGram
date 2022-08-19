package com.example.clonecoding_instagram.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.clonecoding_instagram.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private var param1: String? = null
    private var param2: String? = null

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
        mBinding = FragmentHomeBinding.inflate(inflater,container,false)

        val iv = mBinding.ivContent

        val targetImage : StorageReference = FirebaseStorage.getInstance().reference.child("images/").child("mainjelly.png")
        targetImage.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(iv)
        }.addOnFailureListener {
            Toast.makeText(this.context, "다운로드실패", Toast.LENGTH_SHORT).show()
        }
        /*listRef.listAll()
            .addOnSuccessListener {
                var item : StorageReference
                for (item in it.items) {
                    var layout : LinearLayout = mBinding.mainlayout

                    val iv = ImageView(mBinding.root.context)
                    val setiv = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    iv.layoutParams = setiv
                    layout.addView(iv)

                    item.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Glide.with(mBinding.root.context).load(task.getResult()).into(iv)
                        } else {
                            Toast.makeText(mBinding.root.context, "error", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {

                    }
                }
            }*/
        /*val btn : Button = mBinding.root.findViewById(R.id.sign_out)
        btn.setOnClickListener(this)*/
        return mBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /*override fun onClick(v: View?) {
            auth.signOut()
        activity?.finish()
    }*/
}