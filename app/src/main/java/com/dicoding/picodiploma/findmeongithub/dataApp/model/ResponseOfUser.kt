package com.dicoding.picodiploma.findmeongithub.dataApp.model

import com.google.gson.annotations.SerializedName

data class ResponseOfUser(
    @SerializedName("items")
    val itemUser: ArrayList<UserModel>
)
