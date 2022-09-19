package com.example.clonecoding_instagram

data class ContentSet(var imageUrl: String? = null,
                      var uid : String? = null, // 유저식별
                      var userEmail : String? = null) //유저이메일 중복가능성때문에 uid저장
