package com.example.videocompression

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.videocompression.databinding.FragmentVideoBinding
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment() {

    private lateinit var viewModel: CompressViewModel
    private lateinit var binding:FragmentVideoBinding

    val args:VideoFragmentArgs by navArgs<VideoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel=(activity as MainActivity).viewModel
        binding=DataBindingUtil.inflate<FragmentVideoBinding>(inflater,
        R.layout.fragment_video,container,false)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mediaController=MediaController(context)
        mediaController.setAnchorView(videoView)
        val uri:Uri= Uri.parse(args.videoUri)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

    }
}