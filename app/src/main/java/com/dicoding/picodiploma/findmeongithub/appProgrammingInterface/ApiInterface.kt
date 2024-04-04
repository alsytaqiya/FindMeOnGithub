package com.dicoding.picodiploma.findmeongithub.appProgrammingInterface

import com.dicoding.picodiploma.findmeongithub.dataApp.model.DetailResponseOfUser
import com.dicoding.picodiploma.findmeongithub.dataApp.model.ResponseOfUser
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    @Headers("Authorization: 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")

    fun getUserSearch(
        @Query("q") query: String
    ): Call<ResponseOfUser>

    @GET("users/{username}")
    @Headers("Authorization: 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")

    fun getDetailOfUser(
        @Path("username") githubUsername: String
    ): Call<DetailResponseOfUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowers(
        @Path("username") githubUsername: String
    ): Call<ArrayList<UserModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(
        @Path("username") githubUsername: String
    ): Call<ArrayList<UserModel>>

}