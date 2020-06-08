package com.yussef.mviexample.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yussef.mviexample.R
import com.yussef.mviexample.ui.DataStateListener
import com.yussef.mviexample.ui.main.state.MainStateEvent
import com.yussef.mviexample.util.DataState
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException

class MainFragment : Fragment(){

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run{
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid activity")

        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("DEBUG: DataState: ${dataState}")

            // handle loading and message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let{ event ->

                event.getContentIfNotHandled()?.let{ mainViewState ->
                    mainViewState.blogPosts?.let{blogPosts ->
                        // set BlogPosts data
                        viewModel.setBlogListData(blogPosts)
                    }

                    mainViewState.user?.let{user ->
                        // set User data
                        viewModel.setUser(user)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let{
                println("DEBUG: Setting blog posts to RecyclerView: ${it}")
            }

            viewState.user?.let{
                println("DEBUG: Setting user data: ${it}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}