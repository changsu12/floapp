package com.example.practice.ui.main.home

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.practice.OnButtonClickListener
import com.example.practice.R
import com.example.practice.Service
import com.example.practice.SongDatabase
import com.example.practice.data.entities.Album
import com.example.practice.data.entities.Song

import com.example.practice.databinding.ActivityHomeFragmentBinding
import com.example.practice.ui.main.MainActivity
import com.example.practice.ui.main.album.AlbumFragment
import com.example.practice.ui.main.album.AlbumRecyclerViewAdapter
import com.google.gson.Gson


class HomeFragment : Fragment() {
    private var listener: OnButtonClickListener? = null
    private val handler: Handler =  Handler()
    private var currentPage = 0
    private val DELAY_MS: Long = 3000  // 3초마다 페이지 변경
    lateinit var binding : ActivityHomeFragmentBinding
    private var albumdatas = ArrayList<Album>()
    private var currentPlayingPosition: Int = -1
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private lateinit var songDB: SongDatabase
    private var song: Song = Song()
    private lateinit var db : SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityHomeFragmentBinding.inflate(inflater,container,false)


        songDB = SongDatabase.getInstance(requireContext())!!
        albumdatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.
        Log.d("albumlist", albumdatas.toString())

        //서비스
        binding.serviceStartButton.setOnClickListener{
            serviceStart(it)
         }
        binding.serviceEndButton.setOnClickListener{
            serviceStop(it)
        }
        val albumRVAdapter = AlbumRecyclerViewAdapter(albumdatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        albumRVAdapter.setmyitemclickListener(object : AlbumRecyclerViewAdapter.MyItemClickListener {
            override fun onitemClick(album: Album) {
                chagedeAlbimFragement(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })


        //banner
        val bannerAdapter = bannerVpAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeViewpager.adapter = bannerAdapter
        binding.homeViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        return binding.root
    }


    fun serviceStart(view: View){
        val intent = Intent(activity, Service::class.java)
        ContextCompat.startForegroundService(requireActivity(),intent)
    }
    fun serviceStop(view: View){
        val intent = Intent(activity, Service::class.java)
        requireActivity().stopService(intent)
    }
    private fun chagedeAlbimFragement(album : Album){
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply{
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumjson = gson.toJson(album)
                    putString("album",albumjson)
                }
            })
            .commitAllowingStateLoss()
    }


    private val update: Runnable = object : Runnable {
        override fun run() {
            val totalpage = binding.homePannelVp.adapter?.itemCount ?:0
            if(currentPage>=totalpage){
                currentPage =0
            }
            binding.homePannelVp.setCurrentItem(currentPage++,true)
            binding.homePannelVp.postDelayed(this,DELAY_MS)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homepanneladapter = HomepannelVpAdapter(requireActivity())
        val viewpager2 = binding.homePannelVp
        val indicator = binding.homePannelIndicator
        val totalpage = binding.homePannelVp.adapter?.itemCount ?:0
        if(currentPage>=totalpage){
            currentPage = 0
        }
        viewpager2.adapter = homepanneladapter
        indicator.setViewPager(viewpager2)

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(update, DELAY_MS)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(update)
    }


}