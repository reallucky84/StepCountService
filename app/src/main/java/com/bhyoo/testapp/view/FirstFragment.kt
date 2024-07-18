package com.bhyoo.testapp.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bhyoo.testapp.databinding.FragmentFirstBinding
import com.bhyoo.testapp.service.StepCounterService
import com.bhyoo.testapp.viewmodel.StepViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var binding: FragmentFirstBinding? = null
    private val viewModel: StepViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            if(savedInstanceState == null){
                val intent = Intent(activity, StepCounterService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity.startForegroundService(intent)
                } else {
                    activity.startService(intent)
                }

                viewModel.getTodayStepCount().observe(activity) { stepCount ->
                    binding?.stepCount?.text = stepCount.toString()
                }

                binding?.stopService?.setOnClickListener {
                    activity.stopService(intent)
                }
            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(bundle: Bundle? = null): FirstFragment {
            return FirstFragment().apply {
                arguments = bundle
            }
        }
    }
}