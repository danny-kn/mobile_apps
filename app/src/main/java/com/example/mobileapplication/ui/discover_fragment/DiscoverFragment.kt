package com.example.mobileapplication.ui.discover_fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.mobileapplication.R
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mobileapplication.ui.recipe_list_component.RecipeListView
import com.example.mobileapplication.utils.ShakeDetector
import kotlinx.coroutines.launch

class DiscoverFragment: Fragment() {

    private var recipeListView: RecipeListView? = null
    private val viewModel: DiscoverViewModel by viewModels()

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var shakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "DiscoverFragment: onCreate()")

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        shakeDetector = ShakeDetector {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.refreshRecipes()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Lifecycle", "DiscoverFragment: onCreateView()")
        return inflater.inflate(R.layout.discover_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeListView = view.findViewById(R.id.discoverRecipes)

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeListView?.setRecipes(recipes)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Log.e("Recipe Error", error)
        }

        viewModel.refreshRecipes()

    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "DiscoverFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "DiscoverFragment: onResume()")
        recipeListView?.restartAnimations()

        accelerometer?.let {
            sensorManager.registerListener(shakeDetector, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "DiscoverFragment: onPause()")
        recipeListView?.clearAnimations()
        sensorManager.unregisterListener(shakeDetector)
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "DiscoverFragment: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "DiscoverFragment: onDestroy()")
    }

    override fun onDestroyView() {
        recipeListView?.clearAnimations()
        recipeListView?.setOnLoadMoreListener{}
        recipeListView = null
        super.onDestroyView()
        Log.d("Lifecycle", "DiscoverFragment: onDestroyView()")
    }
}
