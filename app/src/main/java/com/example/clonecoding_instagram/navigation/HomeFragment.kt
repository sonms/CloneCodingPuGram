package com.example.clonecoding_instagram.navigation

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonecoding_instagram.ContentSet
import com.example.clonecoding_instagram.R
import com.example.clonecoding_instagram.databinding.FragmentHomeBinding
import com.example.clonecoding_instagram.databinding.PostItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.post_item.*


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
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mBinding: FragmentHomeBinding
    private val data : MutableList<ContentSet> = mutableListOf()
    private var adapter : MyAdapter?  = null

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

        /*val iv = mBinding.ivContent

        val targetImage : StorageReference = FirebaseStorage.getInstance().reference.child("images/").child("mainjelly.png")
        targetImage.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(iv)
        }.addOnFailureListener {
            Toast.makeText(this.context, "다운로드실패", Toast.LENGTH_SHORT).show()
        }*/

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
        //adapter = RecyclerAdapter()
        //mBinding.recyclerviewContent.adapter = adapter

        //mBinding.recyclerviewContent.layoutManager = LinearLayoutManager(activity)

        data.add(ContentSet("email1"))
        data.add(ContentSet("email2"))
        data.add(ContentSet("email3"))
        data.add(ContentSet("email4"))
        adapter = MyAdapter()
        adapter!!.listData = data
        mBinding.recyclerviewContent.adapter = adapter
        mBinding.recyclerviewContent.setHasFixedSize(true)
        mBinding.recyclerviewContent.layoutManager = LinearLayoutManager(activity)
        mBinding.recyclerviewContent.addItemDecoration(postItemDecoration())
        navController(mBinding.recyclerviewContent, requireActivity().bottom_navigationview)

        /*val onScrollListener = object:RecyclerView.OnScrollListener() {
            var temp : Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                bottom_navigationview.visibility = View.VISIBLE
                temp = 1
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (temp == 1) {
                    super.onScrolled(recyclerView, dx, dy)
                    bottom_navigationview.visibility = View.GONE
                }
            }
        }
        mBinding.recyclerviewContent.setOnScrollListener(onScrollListener)*/


        return mBinding.root
    }

    private fun navController(
        mRecyclerView: RecyclerView,
        bottomNav : BottomNavigationView
    ) {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0) {
                    bottomNav.visibility = View.GONE
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    bottomNav.visibility = View.VISIBLE
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
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

    /*inner class RecyclerAdapter() : RecyclerView.Adapter<ViewHolder>() {
        private lateinit var postItemBinding: PostItemBinding

        //뷰홀더가 처음 생성될때, 여기선 post_item.xml 아이템 뷰 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            //getSystemService() : 메모리내에 클래스의 인스턴스를 생성하여 프로그램 구현하는 방법이 아니라
            // 시스템에서 제공하는 디바이스나 안드로이드 프레임워크내 기능을
            // 다른 어플과 공유하고자 시스템으로 부터 객체를 얻을 대 사용한다.
            //여기서 Layout_inflate_service를 요청하면 반환되는 객체로는 레이아웃 리소스를 inflate하는 layoutflate를 반환한다.
            //그러면 require는 layout인데 현재는 any?가 나온다 이를 위해 as로 캐스팅해준다.
            var inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var inflater1 : View = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

            postItemBinding = PostItemBinding.inflate(inflater,parent,false)

            return ViewHolder(postItemBinding)
        }

        //재활용해주는 곳 , 값을 넣어주는 곳
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.setData(contentSet[position],position)
        }

        //총 몇개의 아이템이 추가되는지 size확인
        override fun getItemCount(): Int {
            return contentSet.size
        }

    }

    //
    inner class ViewHolder(var postItemBinding: PostItemBinding) : RecyclerView.ViewHolder(postItemBinding.root) {
        private var position : Int? = null

        fun setData(content : ContentSet, position: Int) {
            this.position = position
            postItemBinding.accountEmail.text = content.userEmail
            Glide.with(postItemBinding.root).load(content.imageUri).into(postItemBinding.contentImage)
        }
    }*/

    inner class postItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val offset = 20

            outRect.top = offset
            outRect.bottom = offset
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<ViewHolder>() {
        private lateinit var postItemBinding: PostItemBinding
        private var store : FirebaseFirestore = FirebaseFirestore.getInstance()
        private var auth : FirebaseAuth = FirebaseAuth.getInstance()
        var listData = mutableListOf<ContentSet>() //post의 모든 리스트 데이터
        private var userSet : ArrayList<String>? = null //유저 정보
        private var contentSetId = arrayListOf<String>()
        /*init {
            store.collection("posts")
                .whereIn("userEmail", userSet!!)
                .addSnapshotListener { //Starts listening to this query.
                posts, errorHandle ->
                if (errorHandle != null){
                    val c : Context? = null
                    c!!.applicationContext
                    Toast.makeText(c, R.string.upload_fail, Toast.LENGTH_SHORT).show()
                } else {
                    listData.clear()
                    contentSetId.clear()
                    for (post in posts!!.documents) { //indices
                        //firestore에서 받은 데이터를 바로 객체로 변환해서 받기
                        var content = post.toObject(ContentSet::class.java)
                        listData.add(content!!)
                        contentSetId.add(post.id)
                    }
                }
                    notifyDataSetChanged()
            }
        }*/

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            postItemBinding = PostItemBinding.inflate(inflater,parent,false)

            return ViewHolder(postItemBinding)
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(listData[position], position)
        }

        override fun getItemCount(): Int {
            return listData.size
        }

    }

    //그냥 class사용도 되지만 그냥 class 시용시 static처리가 되어
    //쓸데없이 메모리를 잡아먹게된다.
    inner class ViewHolder(var postItemBinding: PostItemBinding) : RecyclerView.ViewHolder(postItemBinding.root) {
        private var position : Int? = null



        fun setData(content : ContentSet, position: Int) {
            this.position = position
            postItemBinding.accountEmail.text = content.userEmail
            Glide.with(postItemBinding.root).load(content.imageUrl).into(postItemBinding.contentImage)
        }
    }
}