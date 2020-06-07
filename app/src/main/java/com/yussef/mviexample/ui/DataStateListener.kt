package com.yussef.mviexample.ui

import com.yussef.mviexample.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}