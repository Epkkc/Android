package elegion.com.roomdatabase.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

/**
 * @author Azret Magometov
 */

@Dao
public interface MusicDao {
    // Методы INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setLinksAlbumSongs(List<AlbumSong> linksAlbumSongs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLink(AlbumSong albumSong);
    // Методы Query

    @Query("select * from album")
    List<Album> getAlbums();

    @Query("select * from album")
    Cursor getAlbumsCursor();

    @Query("select * from album where id = :albumId")
    Cursor getAlbumWithIdCursor(int albumId);

    @Query("select * from song")
    List<Song> getSongs();

    //получить список песен переданного id альбома
    @Query("select * from song inner join albumsong on song.id = albumsong.song_id where album_id = :albumId")
    List<Song> getSongsFromAlbum(int albumId);

    @Query("select * from song where id = :songId")
    Cursor getSongWithIdCursor(int songId);

    @Query("select * from song")
    Cursor getSongsCursor();

    @Query("select * from albumsong")
    List<AlbumSong> getLinks();

    @Query("select * from albumsong")
    Cursor getLinkCursor();

    @Query("select * from albumsong where id = :linkId")
    Cursor getLinkWithIdCursor(int linkId);

    // Методы DELETE
    @Delete
    void deleteAlbum(Album album);
    //удалить альбом по id
    @Query("DELETE FROM album where id = :albumId")
    int deleteAlbumById(int albumId);
    @Delete
    void deleteSong(Song song);
    //удалить псеню по id
    @Query("DELETE FROM song where id = :songId")
    int deleteSongById(int songId);
    @Delete
    void deleteLink(AlbumSong albumSong);
    //удалить альбом по id
    @Query("DELETE FROM AlbumSong where id = :linkId")
    int deleteLinkById(int linkId);

    // Методы Update

    //обновить информацию об альбоме
    @Update
    int updateAlbumInfo(Album album);

    //обновить информацию об альбоме
    @Update
    int updateSongInfo(Song song);

    //обновить информацию об альбоме
    @Update
    int updateLinkInfo(AlbumSong albumSong);

}
