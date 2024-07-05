package com.example.practice.ui.main.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practice.databinding.ActivityLockerMusicfileFragmentBinding

class locker_musicfile_fragment : Fragment() {
    lateinit var binding: ActivityLockerMusicfileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityLockerMusicfileFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
}