package com.example.practice.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practice.ui.signin.LoginActivity
import com.example.practice.databinding.ActivityLockerFragmentBinding
import com.example.practice.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {
    lateinit var binding : ActivityLockerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityLockerFragmentBinding.inflate(inflater,container,false)
        val adapter = lockerVpAdapter(this)
        binding.lockerViewpager.adapter = adapter
        TabLayoutMediator(binding.lockerTab, binding.lockerViewpager){ tab,position->
            //val tabView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
            when(position){
                0-> tab.text = "저장한곡"
                1-> tab.text = "음악파일"
                else->tab.text = "저장앨범"
            }
            //tab.customView =tabView
        }.attach()
        binding.loginTextTv.setOnClickListener {

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initview()
    }
    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }
    private fun initview(){
        val jwt : Int = getJwt()
        if(jwt ==0){
            binding.loginTextTv.text = "로그인"
            binding.loginTextTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }else{
            binding.loginTextTv.text = "로그아웃"
            binding.loginTextTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }
    private fun logout(){
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}