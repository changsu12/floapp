package com.example.practice.ui.main.locker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class lockerVpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> locker_savemusic_fragment()
            1-> locker_musicfile_fragment()
            else -> savedAlbumfragment()

        }
    }
}