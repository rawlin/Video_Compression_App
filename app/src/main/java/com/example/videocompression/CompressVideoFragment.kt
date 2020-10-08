package com.example.videocompression

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.videocompression.databinding.FragmentCompressVideoBinding
import kotlinx.android.synthetic.main.fragment_compress_video.*

class CompressVideoFragment : Fragment() {
    lateinit var viewModel: CompressViewModel
    lateinit var binding:FragmentCompressVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel=(activity as MainActivity).viewModel
        val binding = DataBindingUtil.inflate<FragmentCompressVideoBinding>(inflater,
            R.layout.fragment_compress_video,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.result.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    val uri=response.data
                    hideProgressBar()
                    val bundle=Bundle().apply {
                        putSerializable("videoUri",uri)
                    }
                    Navigation.findNavController(view).navigate(R.id.action_compressVideoFragment_to_videoFragment,bundle)
                }
                is Resource.Loading->{
                    showProgressBar()
                }
                is Resource.Error->{
                    Toast.makeText(context,response.message,Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showProgressBar(){
        progressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        progressBar.visibility=View.INVISIBLE
    }




}