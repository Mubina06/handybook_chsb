package com.example.handybookchsb

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.handybookchsb.databinding.FragmentSplashFirstBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFirstFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var progressStatus = 0
    private var handler = Handler()
    private var limit=0
    private lateinit var binding: FragmentSplashFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSplashFirstBinding.inflate(inflater,container,false)
            Thread(Runnable {
                while (progressStatus < 100){
                    progressStatus +=1
                    Thread.sleep(50)

                    handler.post {
                        binding.progressBar.progress = progressStatus
                        binding.pr.text = "$progressStatus"
                        limit=progressStatus
                    }
                }
                if(limit==100){
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main, SplashTwoFragment())
                        .commit()
                }
            }).start()



        return binding.root
    }

}