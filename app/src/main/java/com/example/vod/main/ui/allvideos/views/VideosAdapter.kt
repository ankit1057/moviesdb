package com.example.vod.main.ui.allvideos.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vod.databinding.VideoItemBinding
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.allvideos.interfaces.IOnVideoClickedListener

class VideosAdapter(diffUtil: DiffUtil.ItemCallback<MovieModel>) : PagedListAdapter<MovieModel, VideosAdapter.ItemViewHolder>(diffUtil) {

    companion object {
        var onvideoClickedListener: IOnVideoClickedListener? = null
        @JvmStatic
        @BindingAdapter("app:setItemClickListener")
        fun setItemClickListener(recyclerView: RecyclerView, listener: IOnVideoClickedListener) {
            onvideoClickedListener = listener
        }

    }

    fun onVideoInfoClicked(view: View, video: MovieModel) {
        onvideoClickedListener?.onVideoInfoClicked(video)

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(parent, binding, this@VideosAdapter)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val videoEntity = getItem(position)
        holder.bind(videoEntity)
    }

    class ItemViewHolder constructor(val view: View, private val binding: VideoItemBinding, val adapter: VideosAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(videoItem: MovieModel?) {
            binding.videoItem = videoItem as MovieModel
            binding.videoAdapter = adapter
        }
    }
}

