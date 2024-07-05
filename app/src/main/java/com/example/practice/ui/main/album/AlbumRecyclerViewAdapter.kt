
package com.example.practice.ui.main.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.data.entities.Album
import com.example.practice.databinding.ItemAlbumBinding

class AlbumRecyclerViewAdapter(private val albumList: ArrayList<Album>) :
    RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>() {
    interface MyItemClickListener {
        fun onitemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mltemClickListener: MyItemClickListener
    fun setmyitemclickListener(itemClickListener: MyItemClickListener) {
        mltemClickListener = itemClickListener
    }

    fun addItem(album: Album) {
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = albumList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlbumBinding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mltemClickListener.onitemClick(albumList[position]) }
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverimg!!)

        }
    }
}