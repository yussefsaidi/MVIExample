package com.yussef.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yussef.mviexample.ui.main.state.MainViewState

class MainViewModel: ViewModel(){

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    
    /*
        Same thing as :
        fun observeViewState(): LiveData<MainViewState>{
        return _viewState }
     */
    val viewState: LiveData<MainViewState>
        get() = _viewState


}