package com.dicoding.picodiploma.findmeongithub.dataApp.localData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "yay_user")
data class YayUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
) : Serializable
