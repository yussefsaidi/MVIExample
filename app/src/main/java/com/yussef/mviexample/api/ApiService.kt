package com.yussef.mviexample.api

import com.yussef.mviexample.model.BlogPost
import com.yussef.mviexample.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path( "userId") userId: String
    ): User


    @GET("placeholder/blogs")
    fun getBlogPosts(): List<BlogPost>

}