package com.example.androidbase.presentation.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.androidbase.presentation.extensions.hideProgress
import com.example.androidbase.presentation.ui.MainActivity

abstract class BaseFragment<ViewBinding : ViewDataBinding>(@LayoutRes protected val contentLayoutId: Int) :
    Fragment() {

    protected lateinit var binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return binding.root
    }

    protected abstract fun setUpUi()

    open fun observerViewModel() {

    }

    open fun configureToolbar() = MainActivity.ToolbarConfiguration()

    open fun configSearchView() = MainActivity.SearchViewConfig()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
        (requireActivity() as MainActivity).apply {
            setToolbarConfiguration(configureToolbar())
            invalidateOptionsMenu()
            showSearchView(configSearchView())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgress()
    }
}