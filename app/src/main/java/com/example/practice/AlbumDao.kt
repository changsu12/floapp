package com.example.practice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM AlbumTable") // 테이블의 모든 값을 가져와라
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album

    @Insert
    fun likeAlbum(like : Like)

    @Query("SELECT id FROM liketable WHERE userId = :userId AND albumId = :albumId ")
    fun islikeAlbum(userId:Int,albumId:Int) : Int?

    @Query("DELETE FROM liketable WHERE userId = :userId AND albumId = :albumId ")
    fun dislikeAlbum(userId:Int,albumId:Int)

    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN albumtable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId ")
    fun getLikedAlbums(userId:Int) : List<Album>
}
