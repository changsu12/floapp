package com.example.practice.ui.main.look

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice.albumfragmentviewpager.Songfragment

class lookVpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> chatfragment()
            1 -> Songfragment()
            else -> Songfragment()
        }
    }


}