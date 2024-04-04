package com.dicoding.picodiploma.findmeongithub.dataApp.localData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoOfYayUser {
    @Insert
    suspend fun addToFavorite(yayUser: YayUser)

    @Query("SELECT * FROM yay_user")
    fun getYayUser(): LiveData<List<YayUser>>

    @Query("SELECT count(*) FROM yay_user WHERE yay_user.id = :id")
    suspend fun userCheck(id: Int): Int
//    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM yay_user WHERE yay_user.id = :id) THEN 1 ELSE 0 END AS is_favorite")
//    suspend fun userCheck(id: Int): Int
//    @Query("SELECT EXISTS(SELECT 1 FROM yay_user WHERE yay_user.id = :id)")
//    suspend fun userCheck(id: Int): Boolean

    @Query("DELETE FROM yay_user WHERE yay_user.id = :id")
    suspend fun removeFromYay(id: Int): Int
}