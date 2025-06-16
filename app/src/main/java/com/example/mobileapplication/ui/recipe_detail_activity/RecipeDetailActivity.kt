package com.example.mobileapplication.ui.recipe_detail_activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mobileapplication.R
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Ingredient
import com.example.mobileapplication.db.Instruction
import com.example.mobileapplication.db.Recipe

class RecipeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
    }

    private lateinit var titleView: TextView
    private lateinit var descView: TextView
    private lateinit var imageView: ImageView
    private lateinit var backButton: AppCompatImageButton
    private lateinit var likeButton: AppCompatImageButton
    private lateinit var instructionList: LinearLayout
    private lateinit var ingredientList: LinearLayout
    private val viewModel: RecipeDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_detail_activity)

        // https://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities
        supportActionBar?.hide()

        titleView = findViewById(R.id.recipe_detail_title)
        descView = findViewById(R.id.recipe_detail_description)
        imageView = findViewById(R.id.recipe_detail_image)
        backButton = findViewById(R.id.back_button)
        likeButton = findViewById(R.id.like_button)
        instructionList = findViewById(R.id.instructionList)
        ingredientList = findViewById(R.id.ingredientList)


        // Need this because my phone runs android 12
        //https://stackoverflow.com/questions/72571804/getserializableextra-and-getparcelableextra-are-deprecated-what-is-the-alternat
        val recipe: ApiRecipe? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_RECIPE, ApiRecipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_RECIPE) as? ApiRecipe
        }
        if (recipe != null) {
            titleView.text = recipe.title
            descView.text = recipe.description ?: "No description available"

            val baseUrl = getString(R.string.api_url)
            val imageUrl = if (!recipe.imageId.isNullOrBlank()) {
                "${baseUrl}img/${recipe.imageId}/"
            } else {
                null
            }

            // Original implementation
//                Glide.with(itemView)
//                    .load(imageUrl)
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .placeholder(R.drawable.image_placeholder)
//                    .error(R.drawable.image_placeholder)
//                    .into(imageView)

            // With optimizations
            val requestOptions = RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)

            Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .override(imageView.width, imageView.height)
                .into(imageView)

            backButton.setOnClickListener {
                finish()
            }

            likeButton.setOnClickListener {
                viewModel.saveRecipe(recipe) { isInserted ->
                    if (isInserted) {
                        Toast.makeText(
                            this,
                            "Recipe Saved Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "You already have this recipe saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            populateInstructions(recipe.instructions ?: emptyList())
            populateIngredients(recipe.ingredients ?: emptyList())

            viewModel.loadSavedRecipeDetails(recipe.recipeId) { savedIngredients: List<Ingredient>, savedInstructions: List<Instruction> ->
                if (savedIngredients.isNotEmpty()) {
                    populateIngredients(savedIngredients)
                }
                if (savedInstructions.isNotEmpty()) {
                    populateInstructions(savedInstructions)
                }
            }

            // Enable back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            finish()
        }

    }

    private fun populateInstructions(instructions: List<Instruction>) {
        if (instructions.isEmpty()) {
            addTextViewToLayout(instructionList, "No instructions available")
        } else {
            for (instruction in instructions) {
                addTextViewToLayout(
                    instructionList,
                    "${instruction.step}. ${instruction.value}"
                )
            }
        }
    }

    private fun populateIngredients(ingredients: List<Ingredient>) {
        if (ingredients.isEmpty()) {
            addTextViewToLayout(ingredientList, "No ingredients available")
        } else {
            for (ingredient in ingredients) {
                val amountText = ingredient.amount?.let {
                    if (it % 1 == 0f) it.toInt().toString() else it.toString()
                } ?: ""

                val unitText = ingredient.unit?.takeIf { it.isNotBlank() } ?: ""
                val nameText = ingredient.name.takeIf { it.isNotBlank() } ?: ""

                val ingredientText = buildString {
                    append("• ")
                    if (amountText.isNotEmpty()) append(amountText)
                    if (unitText.isNotEmpty()) append(" $unitText")
                    if (nameText.isNotEmpty()) {
                        if (amountText.isNotEmpty() || unitText.isNotEmpty()) append(" ")
                        append(nameText)
                    }
                }

                addTextViewToLayout(ingredientList, ingredientText)
            }
        }
    }

    private fun addTextViewToLayout(layout: LinearLayout, text: String) {
        val textView = TextView(this).apply {
            this.text = text
            textSize = 16f
            if (layout.childCount % 2 == 0) {
                setTextColor(android.graphics.Color.parseColor("#888888"))
            } else {
                setTextColor(android.graphics.Color.parseColor("#555555"))
            }
            setPadding(4, 4, 4, 4)
        }
        layout.addView(textView)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
