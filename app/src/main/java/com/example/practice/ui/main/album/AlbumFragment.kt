package com.example.practice.ui.main.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practice.ui.main.home.HomeFragment
import com.example.practice.R
import com.example.practice.SharedViewModel
import com.example.practice.SongDatabase
import com.example.practice.data.entities.Album
import com.example.practice.data.entities.Like

import com.example.practice.databinding.FragmentAlbumBinding
import com.example.practice.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보","영상")
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val gson : Gson = Gson()
    private var isLiked : Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        isLiked = isLikedAlbum(album.id)
        if (albumJson != null) {
            val album = gson.fromJson(albumJson, Album::class.java)
            isLiked = isLikedAlbum(album.id)
            setinit(album)
            setOnClickListeners(album)

            val albumVpAdapter = AlbumVpAdapter(this)
            binding.albumContentVp.adapter = albumVpAdapter
            TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
                tab.text = information[position]
            }.attach()
            sharedViewModel.imageResource.observe(viewLifecycleOwner) { resourceId ->
                binding.albumAlbumIv.setImageResource(resourceId)
            }
        } else {
            Log.e("AlbumFragment", "Album data is null")
        }


        return binding.root
    }
    private fun setinit(album : Album){
        binding.albumMusicTitleTv.text = album.title
        binding.albumSingerNameTv.text = album.singer
        binding.albumAlbumIv.setImageResource(album.coverimg!!)
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumMoreIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0) ?:0
    }
    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }
    private fun isLikedAlbum(albumId: Int) : Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        if (songDB != null) {
            val userId = getJwt()
            val likeId: Int? = songDB.albumDao().islikeAlbum(userId, albumId)
            return likeId != null
        } else {
            Log.e("AlbumFragment", "SongDatabase instance is null")
            return false
        }
    }
    private fun disLikedAlbum(albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        if (songDB != null) {
            val userId = getJwt()
            songDB.albumDao().dislikeAlbum(userId, albumId)
        } else {
            Log.e("AlbumFragment", "SongDatabase instance is null")
        }
    }
    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id )
            }
        }
    }

}