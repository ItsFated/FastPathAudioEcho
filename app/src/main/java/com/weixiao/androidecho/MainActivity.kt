package com.weixiao.androidecho

import android.Manifest
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.permissionx.guolindev.PermissionX
import com.weixiao.androidecho.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var playState: Boolean = false
        set(value) {
            field = value
            if(value)
            {
                binding.btnStart.text = "Stop"
            }
            else
            {
                binding.btnStart.text = "Start"
            }
        }

    private var sampleRate: Int = 0
        set(value) {
            field = value
            binding.tvSampleRate.text = value.toString()
        }

    private var framesPerBuffer: Int = 0
        set(value) {
            field = value
            binding.tvFramesPerBuffer.text = value.toString()
        }

    private var selectedApi: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化 binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 使用 binding.root 替代原来的 setContentView(R.layout.activity_main)
        setContentView(binding.root)

        PermissionX.init(this).permissions(Manifest.permission.RECORD_AUDIO).request{
                allGranted, grantedList, deniedList ->
            if (allGranted) {
                //Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
            }
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        sampleRate = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE).toInt()
        framesPerBuffer = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER).toInt()


        playState = false

        nInit(sampleRate, framesPerBuffer, 3)
        binding.btnStart.setOnClickListener{
            if(playState)
            {
                nStop()
            }
            else
            {
//                val tempApi = when{
//                    rb_sl.isChecked -> 0
//                    rb_aaudio.isChecked -> 1
//                    rb_oboe_sl.isChecked -> 2
//                    else -> 3
//                }
//                if(tempApi != selectedApi)
//                {
//                    nDestroy()
//                    if(!nInit(sampleRate, framesPerBuffer, tempApi))
//                    {
//                        Log.e(TAG, "init echo error")
//                    }
//                }
//                selectedApi = tempApi
                nStart()

            }
            playState = !playState
        }

    }

    override fun onDestroy() {
        nDestroy()
        super.onDestroy()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun nInit(sampleRate: Int, framePerBuffer: Int, api: Int): Boolean
    external fun nDestroy()
    external fun nStart()
    external fun nStop()

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }

        private const val TAG = "MainActivity"
    }
}