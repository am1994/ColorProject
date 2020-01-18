package com.devmina.droid_ant.colorit.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel : ViewModel(){


    private val _click =MutableLiveData<Boolean>()

    val click: LiveData<Boolean>
        get() = _click


     fun  onClickEvent(){
         _click.postValue(true)

     }
    fun CompleteClick(){
        _click.postValue(false)

    }


}