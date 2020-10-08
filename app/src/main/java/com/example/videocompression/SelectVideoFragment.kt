package com.example.videocompression

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.videocompression.databinding.FragmentSelectVideoBinding
import kotlinx.android.synthetic.main.fragment_select_video.*
import java.net.URI
private const val REQUEST_CODE=101

class SelectVideoFragment : Fragment() {

    lateinit var viewModel: CompressViewModel
    lateinit var binding:FragmentSelectVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel=(activity as MainActivity).viewModel
        binding=DataBindingUtil.inflate<FragmentSelectVideoBinding>(inflater,
            R.layout.fragment_select_video,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            openGalleryForVideo()
        }
    }
    private fun openGalleryForVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Video"),REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK&&requestCode==101){
            if(data!=null){
                val uri=data.data
                if (uri != null) {
                    viewModel.setVideo(uri)
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_selectVideoFragment_to_compressVideoFragment) }
                }


            }
        }
    }




}