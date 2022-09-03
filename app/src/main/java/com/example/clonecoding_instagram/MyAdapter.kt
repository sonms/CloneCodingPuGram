package com.example.clonecoding_instagram

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonecoding_instagram.databinding.PostItemBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private lateinit var postItemBinding: PostItemBinding
    var listData = mutableListOf<ContentSet>()

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



        fun setData(content : ContentSet, position: Int) {
            this.position = position
            postItemBinding.accountEmail.text = content.userEmail
            //Glide.with(postItemBinding.root).load(content.imageUrl).into(postItemBinding.contentImage)
        }
    }
}



