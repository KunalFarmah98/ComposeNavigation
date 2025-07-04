package com.apps.kunalfarmah.composenavigationexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _bottomNavCollapsedState = MutableSharedFlow<Boolean>(1)
    private val _topAppBarCollapsedState = MutableSharedFlow<Boolean>(1)
    private val _bottomTabTitle = MutableSharedFlow<String>(1)
    val bottomTabCollapsedState = _bottomNavCollapsedState.asSharedFlow()
    val topAppBarCollapsedState = _topAppBarCollapsedState.asSharedFlow()
    val bottomTabTitle = _bottomTabTitle.asSharedFlow()

    fun collapseBottomNav(){
        viewModelScope.launch {
            _bottomNavCollapsedState.emit(true)
        }
    }

    fun expandBottomNav(){
        viewModelScope.launch {
            _bottomNavCollapsedState.emit(false)
        }
    }

    fun collapseTopAppBar(){
        viewModelScope.launch {
            _topAppBarCollapsedState.emit(true)
        }
    }

    fun expandTopAppBar(){
        viewModelScope.launch {
            _topAppBarCollapsedState.emit(false)
        }
    }

    fun setBottomTabTitle(title: String) {
        viewModelScope.launch {
            _bottomTabTitle.emit(title)
        }
    }

}