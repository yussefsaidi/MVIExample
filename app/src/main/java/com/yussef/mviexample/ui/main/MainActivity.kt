package com.yussef.mviexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yussef.mviexample.R
import com.yussef.mviexample.ui.DataStateListener
import com.yussef.mviexample.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener {

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMainFragment()
    }

    fun showMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
            .commit()
    }



    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let{

            // handle loading
            showProgressBar(it.loading)

            // handle message
            it.message?.let{ message ->
                showToast(message)
            }
        }
    }

    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(isVisible: Boolean){
        if(isVisible){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }
}
