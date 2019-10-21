package com.example.vod.main.ui.allvideos.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vod.R
import com.example.vod.databinding.VideoFragmentBinding
import com.example.vod.main.model.ErrorModel
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.allvideos.interfaces.IOnVideoClickedListener
import com.example.vod.main.ui.allvideos.viewmodel.AllVideosViewModel
import kotlinx.android.synthetic.main.video_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentAllVideos : Fragment(), IOnVideoClickedListener {

    private val mViewModel: AllVideosViewModel by viewModel()
    private val mAdapter: VideosAdapter by inject()
    private var binding: VideoFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<VideoFragmentBinding>(inflater, R.layout.video_fragment, container, false)
                .also {
                    it.viewModel = mViewModel
                    it.lifecycleOwner = viewLifecycleOwner
                    it.listener = this@FragmentAllVideos
                }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context)
        videosRecyclerView.layoutManager = linearLayoutManager
        videosRecyclerView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.moviesResponse.observe(this, Observer<PagedList<MovieModel?>> { setAdapterData(it) })
        mViewModel.onErrorOccurred.observe(this, Observer<ErrorModel> { setError(it) })
    }


    override fun onVideoInfoClicked(video: MovieModel) {
        val bundle = bundleOf("video" to video)
        this.findNavController().navigate(R.id.action_fragmentAllVideos_to_fragmentVideoDetails, bundle)

    }

    private fun setAdapterData(pageList: PagedList<MovieModel?>) {
        mAdapter.submitList(pageList)
    }

    private fun setError(errorModel: ErrorModel) {
        Toast.makeText(activity, errorModel.status_message, Toast.LENGTH_SHORT).show()
    }


}
