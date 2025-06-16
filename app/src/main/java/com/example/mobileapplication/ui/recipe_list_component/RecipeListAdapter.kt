package com.example.mobileapplication.ui.recipe_list_component

import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mobileapplication.R
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.ui.recipe_detail_activity.RecipeDetailActivity

class RecipeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLoading = true
    private var recipes: List<ApiRecipe> = emptyList()
    private var nPlaceholders = 10
    private val VIEW_TYPE_SKELETON = 0
    private val VIEW_TYPE_DATA = 1

    fun setRecipes(recipes: List<ApiRecipe>, isLoading: Boolean = false) {
        this.recipes = recipes
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (isLoading) nPlaceholders else recipes.size

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SKELETON else VIEW_TYPE_DATA
    }

    // https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_SKELETON) {
            val view = inflater.inflate(R.layout.item_recipe_skeleton, parent, false)
            SkeletonViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_recipe, parent, false)
            RecipeViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SkeletonViewHolder) {
            holder.bind()
        } else if (holder is RecipeViewHolder) {
            holder.bind(recipes[position])
        }
    }

//    fun restartSkeletonAnimations() {
//        Log.d("Lifecycle", "started restarting")
//        for (i in 0 until recyclerView.childCount) {
//            val view = recyclerView.getChildAt(i)
//            val holder = recyclerView.getChildViewHolder(view)
//            if (holder is RecipeListAdapter.SkeletonViewHolder) {
//                holder.bind()
//            }
//        }
//    }
//
//    fun clearSkeletonAnimations() {
//        Log.d("Lifecycle", "started clearing")
//        for (i in 0 until recyclerView.childCount) {
//            recyclerView.getChildAt(i)?.clearAnimation()
//        }
//    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is RecipeViewHolder) {
            Glide.with(holder.itemView.context).clear(holder.imageView)
        }
        holder.itemView.clearAnimation()
    }

    inner class SkeletonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val skeletonViews: List<View> = listOf(
            view.findViewById(R.id.skeleton_image),
            view.findViewById(R.id.skeleton_title),
            view.findViewById(R.id.skeleton_description),
            view.findViewById(R.id.skeleton_tag1),
            view.findViewById(R.id.skeleton_tag2),
            view.findViewById(R.id.skeleton_tag3)
        )

        fun bind() {
            skeletonViews.forEach { startSkeletonAnimation(it) }
        }
    }

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.recipe_image)

        fun bind(recipe: ApiRecipe) {

            val titleView = itemView.findViewById<TextView>(R.id.recipe_title)
            val descView = itemView.findViewById<TextView>(R.id.recipe_description)
            val tagContainer = itemView.findViewById<LinearLayout>(R.id.recipe_tags_container)

            titleView.text = recipe.title
            descView.text = recipe.description ?: "No description available"

            // build the url

            val baseUrl = itemView.context.getString(R.string.api_url)
            val imageUrl = recipe.imageId?.let { "${baseUrl}img/$it" }

            Log.d("Recipe Api", "Recipe Image: ${imageUrl}")
            if (imageUrl != null) {
                // https://github.com/bumptech/glide
                val requestOptions = RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)

                // https://github.com/bumptech/glide/issues/898
                Glide.with(itemView.context.applicationContext)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .override(imageView.width, imageView.height)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.image_placeholder)
            }

            // we will put all of the small info in its own tag
            tagContainer.removeAllViews()
            val tags = mutableListOf<String>()
            // Tag section (add more in future)
            if (recipe.serves != null) tags.add("Serves ${recipe.serves}")
            // Handle Prep time. Stored in float minutes
            tags.add("${recipe.prepTime} min")
            // Recipe difficulty (may be incorrect diff mappings)
            tags.add(when (recipe.difficulty) {
                in 0..1 -> "Easy"
                in 2..5 -> "Medium"
                in 6..8 -> "Hard"
                in 9..10 -> "Expert"
                else -> "Unknown"
            })
            // Allow 3 max tags, pack all extras into a (+ n more) tag
            val maxTags = 3
            for (i in 0 until minOf(tags.size, maxTags)) {
                tagContainer.addView(createTagView(tags[i]))
            }
            if (tags.size > maxTags) {
                tagContainer.addView(createTagView("+${tags.size - 3} more"))
            }

            // Handle recipe item onclick
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RecipeDetailActivity::class.java)
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe)
                itemView.context.startActivity(intent)
            }
        }

        private fun createTagView(text: String): TextView {
            val tagView = TextView(itemView.context)

            tagView.text = text
            // Styling
            // https://www.geeksforgeeks.org/how-to-increase-or-decrease-textview-font-size-in-android-programmatically/
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            tagView.setPadding(12, 6, 12, 6)
            tagView.setBackgroundResource(R.drawable.tag_background) // Rounded background
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(4, 4, 4, 4)
            tagView.layoutParams = params

            return tagView
        }
    }

    private fun startSkeletonAnimation(view: View) {
        // activate the skeleton animation
        // https://developer.android.com/develop/ui/views/animations/view-animation
        val anim = AnimationUtils.loadAnimation(view.context, R.anim.pulse)
        view.startAnimation(anim)
    }

//    private fun clearAnimation(view: View) {
//        view.clearAnimation()
//    }
}