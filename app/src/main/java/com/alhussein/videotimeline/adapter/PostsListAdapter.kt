package com.alhussein.videotimeline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alhussein.videotimeline.model.Post
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.facebook.shimmer.ShimmerFrameLayout

import android.widget.TextView
import com.alhussein.videotimeline.R


class PostsListAdapter(var postListener: PostListener) :
    RecyclerView.Adapter<PostsListAdapter.ViewHolder>() {
    var data = listOf<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val shimmer =
        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(500) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.9f) //the alpha of the underlying children
            .setHighlightAlpha(0.2f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

    // This is the placeholder for the imageView
    private val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(com.alhussein.videotimeline.R.layout.post_list_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val coverImgUrl: String = item.media_base_url + item.recording_details.cover_img_url

        Glide.with(holder.itemView.context)
            .load(coverImgUrl)
            .centerCrop()
            .placeholder(shimmerDrawable)
            .into(holder.itemView.findViewById(R.id.image_view))

        holder.itemView.setOnClickListener {
            postListener.onPostClick(item.id)
        }

    }

    override fun getItemCount() = data.size


    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)


    interface PostListener {
        fun onPostClick(id: String)
    }

}

