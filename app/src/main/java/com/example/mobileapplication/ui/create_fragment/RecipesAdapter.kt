// https://developer.android.com/develop/ui/views/layout/recyclerview
// https://www.geeksforgeeks.org/android-recyclerview/
// https://www.youtube.com/watch?v=bOd3wO0uFr8
// https://www.youtube.com/watch?v=zCDB-OqOzfY
// https://github.com/chetanvaghela457/Android-Kotlin-DataBinding-WIth-BindingAdapter-Recyclerview-Binding
// https://github.com/emanuelnlopez/android-kotlin-crud

package com.example.mobileapplication.ui.create_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mobileapplication.R
import com.example.mobileapplication.db.Recipe
import com.google.android.material.button.MaterialButton
import android.widget.ImageView
import android.content.Intent
import com.example.mobileapplication.ui.recipe_detail_activity.RecipeDetailActivity
import com.example.mobileapplication.db.ApiRecipe
import com.google.android.material.card.MaterialCardView
import com.example.mobileapplication.db.User
import java.util.UUID

private fun Recipe.toApiRecipe() = ApiRecipe(
    recipeId = recipeId,
    title = title,
    description = description ?: "No description available",
    serves = serves,
    difficulty = difficulty,
    prepTime = prepTime,
    imageId = imageId ?: "",
    ingredients = emptyList(),
    instructions = emptyList(),
    author = User(
        userId = authorId,
        username = "user",
        displayName = "user",
        profileImageUrl = null,
        firebaseId = "null"
    )
)

class RecipesAdapter(
    private val onEditClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit,
    private val userId: UUID
): ListAdapter<Recipe, RecyclerView.ViewHolder>(DiffCallback) {

    inner class RecipeViewHolder(v: View): RecyclerView.ViewHolder(v) {
        private val titleText: TextView = v.findViewById(R.id.titleText)
        private val descriptionText: TextView = v.findViewById(R.id.descriptionText)
        private val prepTimeText: TextView = v.findViewById(R.id.prepTimeText)
        private val servesText: TextView = v.findViewById(R.id.servesText)
        private val difficultyText: TextView = v.findViewById(R.id.difficultyText)
        private val recipeImage: ImageView = v.findViewById(R.id.recipe_image)
        private val editButton: MaterialButton = v.findViewById(R.id.editButton)
        private val deleteButton: MaterialButton = v.findViewById(R.id.deleteButton)
        private val cardView = v as MaterialCardView

        fun onBind(recipe: Recipe, position: Int, canEdit: Boolean) {
            titleText.text = recipe.title
            descriptionText.text = recipe.description
            prepTimeText.text = "Prep: ${recipe.prepTime} min"

            servesText.text = if (recipe.serves != null) {
                "Serves: ${recipe.serves}"
            } else {
                "Serves: N/A"
            }

            difficultyText.text = "Difficulty: ${recipe.difficulty}"

            if (recipe.imageId != null) {
                val baseUrl = itemView.context.getString(R.string.api_url)
                val imageUrl = "${baseUrl}img/${recipe.imageId}/"
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(recipeImage)
            } else {
                recipeImage.setImageResource(R.drawable.image_placeholder)
            }

            cardView.setOnClickListener {
                val intent = Intent(itemView.context, RecipeDetailActivity::class.java)
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe.toApiRecipe())
                itemView.context.startActivity(intent)
            }

            editButton.setOnClickListener {
                onEditClick(recipe)
            }

            if (canEdit)
                editButton.setOnClickListener {
                    onEditClick(recipe)

                }
            editButton.isEnabled = canEdit
            editButton.visibility = if (canEdit) View.VISIBLE else View.INVISIBLE

            deleteButton.setOnClickListener {
                onDeleteClick(recipe)
            }
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recipeHolder = holder as RecipeViewHolder
        val recipe = getItem(position)
        recipe.let {
            recipeHolder.onBind(it, position, recipe.authorId == userId)
        }
    }

    
}
