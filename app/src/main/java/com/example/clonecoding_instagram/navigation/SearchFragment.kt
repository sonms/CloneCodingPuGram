package com.example.clonecoding_instagram.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonecoding_instagram.ContentSet
//import com.example.clonecoding_instagram.MyAdapter
import com.example.clonecoding_instagram.databinding.FragmentSearchBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val data : MutableList<ContentSet> = mutableListOf()
    //private var adapter : HomeFragment.MyAdapter? = null
    private lateinit var mBinding : FragmentSearchBinding

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
        mBinding = FragmentSearchBinding.inflate(inflater,container,false)

        //Share(Action-send) Test
        /*mBinding.shareTextBtn.setOnClickListener {
            shareContent()
        }*/
        initRecyclerView()


        return mBinding.root
    }

    private fun initRecyclerView() {
        data.add(ContentSet("email1"))
        data.add(ContentSet("email2"))
        data.add(ContentSet("email3"))
        data.add(ContentSet("email4"))
        //어댑터 생성
        //adapter = HomeFragment.MyAdapter()

        //데이터 연결
        //adapter!!.listData = data
        //어댑터 연결
        //mBinding.recyclerviewContent.adapter = adapter
        //리사이클러뷰 화면 크기고정
        mBinding.recyclerviewContent.setHasFixedSize(true)
        mBinding.recyclerviewContent.layoutManager = LinearLayoutManager(activity)
        //mBinding.recyclerviewContent.addItemDecoration(HomeFragment.postItemDecoration())

    }

    fun shareContent() {
        val userEmail = "email11"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, userEmail)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}