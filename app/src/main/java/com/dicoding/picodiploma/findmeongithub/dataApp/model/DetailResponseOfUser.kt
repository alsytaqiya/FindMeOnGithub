package com.dicoding.picodiploma.findmeongithub.dataApp.model

data class DetailResponseOfUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val location: String,
    val company: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val public_repos: Int
)
