package com.devmina.droid_ant.colorit.color_canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ColorViewModel : ViewModel(){

  /*  private var _listColor=MutableLiveData<MutableList<ListofColor>>()
    val listColor:LiveData<MutableList<ListofColor>>
        get() = _listColor*/

    private var _chosenColor = MutableLiveData<Int>()
    val chosenColor:LiveData<Int>
    get() = _chosenColor




    init{
      // insert()
   }


   /* private fun insert(){

        val colors: MutableList<ListofColor> = mutableListOf()
        colors.add(ListofColor(Color.RED))
        colors.add(ListofColor(Color.BLACK))
        colors.add(ListofColor(Color.YELLOW))
        colors.add(ListofColor(Color.GREEN))
        _listColor.postValue(colors)


    }*/

    fun changeColor(color: Int){
         _chosenColor.postValue(color)
    }
    fun completedChangeColor(){
        _chosenColor.postValue(null)
    }




}