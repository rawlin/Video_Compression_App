package com.example.videocompression

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler
import nl.bravobit.ffmpeg.FFmpeg
import androidx.core.text.isDigitsOnly
import java.io.File
import java.util.*


class CompressViewModel (application: Application):AndroidViewModel(application){

    private val context=getApplication<Application>().applicationContext


    var bitrate=MutableLiveData<String>()
    private var file:File?=null
    var result:MutableLiveData<Resource<String>> = MutableLiveData()



    fun setVideo(uri: Uri){
        file?.delete()
        val stream=context.contentResolver.openInputStream(uri)
        file=File.createTempFile("Original_",".mp4",context.filesDir)
        stream?.let { file?.writeBytes(it.readBytes()) }
    }




     fun compressVideo(){
        val rate=bitrate.value?.trim()
        if (rate.isNullOrEmpty()) {
            Toast.makeText(context,"Enter Bitrate",Toast.LENGTH_LONG).show()
            return
        }
        if (!rate.isDigitsOnly()) {
            Toast.makeText(context,"Enter proper Bitrate",Toast.LENGTH_LONG).show()
            return
        }
        val outputPath = context.getExternalFilesDir("CompressedVideos")
        val outputFile = File.createTempFile("cmp_${Date().time}", ".mp4", outputPath)

        viewModelScope.launch {
            val ffmpeg=FFmpeg.getInstance(context)

            ffmpeg.execute(arrayOf("-i${file?.path} -b${rate}k ${outputFile.path}"),object :ExecuteBinaryResponseHandler(){
                override fun onStart() {
                    super.onStart()
                }

                override fun onFinish() {
                    super.onFinish()
                    //loading
                }

                override fun onSuccess(message: String?) {
                    super.onSuccess(message)
                    //showVideo()
                    result.postValue(Resource.Success(outputFile.path))
                    //navToVideo()
                }

                override fun onProgress(message: String?) {
                    super.onProgress(message)
                    result.postValue(Resource.Loading())
                }

                override fun onFailure(message: String?) {
                    super.onFailure(message)
                    result.postValue(Resource.Error("Error Occured"))
                }
            })
        }
        }




}