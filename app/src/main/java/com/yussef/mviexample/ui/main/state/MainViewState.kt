package com.yussef.mviexample.ui.main.state

import com.yussef.mviexample.model.BlogPost
import com.yussef.mviexample.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null

) {


}