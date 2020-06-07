package com.yussef.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.yussef.mviexample.ui.main.state.MainViewState
import com.yussef.mviexample.util.*
import com.yussef.mviexample.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

abstract class NetworkBoundResource <ResponseObject, ViewStateType>{

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init{

        result.value = DataState.loading(true)

        GlobalScope.launch(IO){

            delay(TESTING_NETWORK_DELAY)

            withContext(Main){
                val apiResponse = createCall()

                result.addSource(apiResponse){response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }

    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }

            is ApiErrorResponse ->{
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }

            is ApiEmptyResponse ->{
                println("DEBUG: NetworkBoundResource: HTTP 204. Returned NOTHING!")
                onReturnError("HTTP 204. Returned NOTHING!")
            }


        }
    }

    fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

}