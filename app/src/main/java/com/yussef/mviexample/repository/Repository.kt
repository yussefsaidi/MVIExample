package com.yussef.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.yussef.mviexample.api.RetrofitBuilder
import com.yussef.mviexample.ui.main.state.MainViewState
import com.yussef.mviexample.util.ApiEmptyResponse
import com.yussef.mviexample.util.ApiErrorResponse
import com.yussef.mviexample.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts(): LiveData<MainViewState>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()){ apiResponse ->
                object: LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){
                            is ApiSuccessResponse ->{
                                value = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }

                            is ApiErrorResponse ->{
                                value = MainViewState() // Handle error?
                            }

                            is ApiEmptyResponse ->{
                                value = MainViewState() // Handle empty/error?
                            }


                        }
                    }
                }
            }
    }


    fun getUser(userId: String): LiveData<MainViewState>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)){ apiResponse ->
                object: LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){
                            is ApiSuccessResponse ->{
                                value = MainViewState(
                                    user = apiResponse.body
                                )
                            }

                            is ApiErrorResponse ->{
                                value = MainViewState() // Handle error?
                            }

                            is ApiEmptyResponse ->{
                                value = MainViewState() // Handle empty/error?
                            }


                        }
                    }
                }
            }
    }

}