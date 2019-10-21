package com.example.vod.main.ui.videodetails.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.vod.R
import com.example.vod.databinding.FragmentVideoDetailsBinding
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.videodetails.viewmodel.VideoDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentVideoDetails : Fragment() {

    private var binding: FragmentVideoDetailsBinding? = null
    private val mViewModel: VideoDetailsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentVideoDetailsBinding>(inflater, R.layout.fragment_video_details, container, false)
                .also {
                    it.viewModel = mViewModel
                    it.lifecycleOwner = viewLifecycleOwner
                }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val video = arguments?.get("video") as MovieModel
        mViewModel.setData(video)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
