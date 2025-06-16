// https://developer.android.com/develop/ui/views/layout/recyclerview
// https://www.geeksforgeeks.org/android-recyclerview/
// https://www.youtube.com/watch?v=bOd3wO0uFr8
// https://www.youtube.com/watch?v=zCDB-OqOzfY
// https://github.com/chetanvaghela457/Android-Kotlin-DataBinding-WIth-BindingAdapter-Recyclerview-Binding
// https://github.com/emanuelnlopez/android-kotlin-crud

package com.example.mobileapplication.ui.create_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R
import com.example.mobileapplication.db.Recipe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateFragment: Fragment() {
    private lateinit var myViewModel: CreateViewModel
    private lateinit var myAdapter: RecipesAdapter
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        myRecyclerView = view.findViewById(R.id.recipeRecyclerView)
        myAdapter = RecipesAdapter(
            onEditClick = { recipe -> editRecipe(recipe) },
            onDeleteClick = { recipe -> deleteRecipe(recipe) },
            myViewModel.userId
        )
        myRecyclerView.adapter = myAdapter
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<FloatingActionButton>(R.id.addRecipeButton).setOnClickListener { createRecipe() }
        myViewModel.allRecipes.observe(viewLifecycleOwner, Observer { list -> myAdapter.submitList(list) })
    }

    fun createRecipe() {
        val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.create_recipe, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Create Recipe")
            .setView(dialog)
            .setPositiveButton("Create") { _, _ ->
                val title = dialog.findViewById<EditText>(R.id.editRecipeTitle).text.toString()
                val desc = dialog.findViewById<EditText>(R.id.editDescription).text.toString()
                val serves = dialog.findViewById<EditText>(R.id.editServes).text.toString().toIntOrNull()
                val time = dialog.findViewById<EditText>(R.id.editPrepTime).text.toString().toFloatOrNull() ?: 0f
                myViewModel.createRecipe(title, desc, serves, 1, time)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun editRecipe(recipe: Recipe) {
        val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.create_recipe, null)
        dialog.findViewById<EditText>(R.id.editRecipeTitle).setText(recipe.title)
        dialog.findViewById<EditText>(R.id.editDescription).setText(recipe.description)
        dialog.findViewById<EditText>(R.id.editServes).setText(recipe.serves?.toString())
        dialog.findViewById<EditText>(R.id.editPrepTime).setText(recipe.prepTime.toString())

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Recipe")
            .setView(dialog)
            .setPositiveButton("Update") { _, _ ->
                val title = dialog.findViewById<EditText>(R.id.editRecipeTitle).text.toString()
                val desc = dialog.findViewById<EditText>(R.id.editDescription).text.toString()
                val serves = dialog.findViewById<EditText>(R.id.editServes).text.toString().toIntOrNull()
                val time = dialog.findViewById<EditText>(R.id.editPrepTime).text.toString().toFloatOrNull() ?: 0f
                myViewModel.updateRecipe(recipe.copy(title = title, description = desc, serves = serves, prepTime = time))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun deleteRecipe(recipe: Recipe) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Recipe")
            .setMessage("Delete ${recipe.title}?")
            .setPositiveButton("Delete") { _, _ -> myViewModel.deleteRecipe(recipe) }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
