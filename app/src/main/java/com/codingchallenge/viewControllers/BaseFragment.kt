package com.codingchallenge.viewControllers

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codingchallenge.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

 open class BaseFragment : Fragment(), CoroutineScope {

     //Kotlin Coroutines job set for easier switching on Kotlin Coroutines
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

     override fun onDestroy() {
         super.onDestroy()
         job.cancel()
     }

     protected fun addFragmentWithBackStack(id: Int, fragment: Fragment) {
         if (context == null || !isAdded)
             return
         (requireContext() as AppCompatActivity)
             .supportFragmentManager
             .beginTransaction()
             .add(id, fragment)
             .addToBackStack(fragment.tag)
             .commit()
     }

     protected fun popUpFragment() {
         if (context == null || !isAdded)
             return
         (requireContext() as AppCompatActivity)
             .supportFragmentManager
             .popBackStack()
     }

     protected fun backgroundDrawable(drawable: Int): Drawable {
         return ContextCompat.getDrawable(requireContext(), drawable)!!
     }

     protected fun getColor(color: Int): Int {
         return ContextCompat.getColor(requireContext(), color)
     }
}