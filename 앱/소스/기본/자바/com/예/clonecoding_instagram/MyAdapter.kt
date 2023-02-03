package com.example.clonecoding_instagram

/*import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.clonecoding_instagram.databinding.PostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private lateinit var postItemBinding: PostItemBinding
    private var store : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    var listData = mutableListOf<ContentSet>() //post의 모든 리스트 데이터
    private var userSet : ArrayList<String>? = null //유저 정보
    private var contentSetId = arrayListOf<String>()
    /*init {
        //firestore연결
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


    //그냥 class사용도 되지만 그냥 class 시용시 static처리가 되어
    //쓸데없이 메모리를 잡아먹게된다.
    inner class ViewHolder(var postItemBinding: PostItemBinding) : RecyclerView.ViewHolder(postItemBinding.root) {
        private var position : Int? = null


        //position 으로 리사이클러뷰 아이템의 위치파악
        fun setData(content : ContentSet, position: Int) {
            this.position = position
            postItemBinding.accountEmail.text = content.userEmail
            Glide.with(postItemBinding.root).load(content.imageUrl).into(postItemBinding.contentImage)
        }
    }

}
*/


