package com.yussef.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.yussef.mviexample.api.RetrofitBuilder
import com.yussef.mviexample.model.BlogPost
import com.yussef.mviexample.model.User
import com.yussef.mviexample.ui.main.state.MainViewState
import com.yussef.mviexample.util.*

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>>{
       return object: NetworkBoundResource<List<BlogPost>, MainViewState>(){
           override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
               result.value = DataState.data(
                   data = MainViewState(
                       blogPosts = response.body
                   )
               )
           }

           override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
               return RetrofitBuilder.apiService.getBlogPosts()
           }

       }.asLiveData()
    }


    fun getUser(userId: String): LiveData<DataState<MainViewState>>{
        return object: NetworkBoundResource<User, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }

}