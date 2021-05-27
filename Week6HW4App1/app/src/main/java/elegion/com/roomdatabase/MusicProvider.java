package elegion.com.roomdatabase;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import elegion.com.roomdatabase.database.Album;
import elegion.com.roomdatabase.database.AlbumSong;
import elegion.com.roomdatabase.database.MusicDao;
import elegion.com.roomdatabase.database.MusicDatabase;
import elegion.com.roomdatabase.database.Song;

public class MusicProvider extends ContentProvider {

    private static final String TAG = MusicProvider.class.getSimpleName();

    private static final String AUTHORITY = "com.elegion.roomdatabase.musicprovider";
    private static final String TABLE_ALBUM = "album";
    private static final String TABLE_SONG = "song";
    private static final String TABLE_LINK = "albumsong";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ALBUM_TABLE_CODE = 100;
    private static final int ALBUM_ROW_CODE = 101;
    private static final int SONG_TABLE_CODE = 102;
    private static final int SONG_ROW_CODE = 103;
    private static final int LINK_TABLE_CODE = 104;
    private static final int LINK_ROW_CODE = 105;

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM, ALBUM_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM + "/*", ALBUM_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG, SONG_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG + "/*", SONG_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_LINK, LINK_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_LINK + "/*", LINK_ROW_CODE);
    }

    private MusicDao mMusicDao;

    public MusicProvider() {
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            mMusicDao = Room.databaseBuilder(getContext().getApplicationContext(), MusicDatabase.class, "music_database")
                    .build()
                    .getMusicDao();
            return true;
        }

        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case ALBUM_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_ALBUM;
            case ALBUM_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUM;
            case SONG_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_SONG;
            case SONG_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_SONG;
            case LINK_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_LINK;
            case LINK_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_LINK;
            default:
                throw new UnsupportedOperationException("not yet implemented");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int code = URI_MATCHER.match(uri);

        if (code != ALBUM_ROW_CODE && code != ALBUM_TABLE_CODE &&
            code != SONG_TABLE_CODE && code != SONG_ROW_CODE &&
            code != LINK_TABLE_CODE && code != LINK_ROW_CODE) return null;

        Cursor cursor = null;

        switch (code){
            case ALBUM_TABLE_CODE:
                cursor = mMusicDao.getAlbumsCursor();
                break;
            case ALBUM_ROW_CODE:
                cursor = mMusicDao.getAlbumWithIdCursor((int) ContentUris.parseId(uri));
                break;
            case SONG_TABLE_CODE:
                cursor = mMusicDao.getSongsCursor();
                break;
            case SONG_ROW_CODE:
                cursor = mMusicDao.getSongWithIdCursor((int) ContentUris.parseId(uri));
                break;
            case LINK_TABLE_CODE:
                cursor = mMusicDao.getLinkCursor();
                break;
            case LINK_ROW_CODE:
                cursor = mMusicDao.getLinkWithIdCursor((int)  ContentUris.parseId(uri));
                break;
        }


        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == ALBUM_TABLE_CODE && isValuesValid(values)) {
            Album album = new Album();
            int id = values.getAsInteger("param1");
            album.setId(id);
            album.setName(values.getAsString("param2"));
            album.setReleaseDate(values.getAsString("param3"));
            mMusicDao.insertAlbum(album);
            return ContentUris.withAppendedId(uri, id);
        } else if(URI_MATCHER.match(uri) == SONG_TABLE_CODE && isValuesValid(values)){
            Song song = new Song();
            int id = values.getAsInteger("param1");
            song.setId(id);
            song.setName(values.getAsString("param2"));
            song.setDuration(values.getAsString("param3"));
            mMusicDao.insertSong(song);
            return ContentUris.withAppendedId(uri, id);
        }else if(URI_MATCHER.match(uri) == LINK_TABLE_CODE && isValuesValid(values)){
            AlbumSong albumSong = new AlbumSong();
            int id = values.getAsInteger("param1");
            albumSong.setId(id);
            boolean thereIsSongId = false;
            for (Song song : mMusicDao.getSongs()) {
                if (song.getId() == values.getAsInteger("param3")){
                    thereIsSongId = true;
                    break;
                }
            }
            boolean thereIsAlbumId = false;
            for (Album album : mMusicDao.getAlbums()) {
                if (album.getId() == values.getAsInteger("param2")){
                    thereIsAlbumId = true;
                    break;
                }
            }
            if (thereIsAlbumId && thereIsSongId){
                albumSong.setAlbumId(values.getAsInteger("param2"));
                albumSong.setSongId(values.getAsInteger("param3"));
                mMusicDao.insertLink(albumSong);
            } else{
                Log.i(TAG, "insert: THERE IS NO SUCH SONG OR ALBUM");
            }

            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new IllegalArgumentException("");
        }
    }

    private boolean isValuesValid(ContentValues values) {
        return values.containsKey("param1") && values.containsKey("param2") && values.containsKey("param3");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE && isValuesValid(values)) {
            Album album = new Album();
            int id = (int) ContentUris.parseId(uri);
            album.setId(id);
            album.setName(values.getAsString("param2"));
            album.setReleaseDate(values.getAsString("param3"));
            return mMusicDao.updateAlbumInfo(album);
        } else if(URI_MATCHER.match(uri) == SONG_ROW_CODE && isValuesValid(values)){
            Song song = new Song();
            int id = values.getAsInteger("id");
            song.setId(id);
            song.setName(values.getAsString("param2"));
            song.setDuration(values.getAsString("param3"));
            return mMusicDao.updateSongInfo(song);
        }else if(URI_MATCHER.match(uri) == LINK_ROW_CODE && isValuesValid(values)){
            AlbumSong albumSong = new AlbumSong();
            int id = values.getAsInteger("id");
            albumSong.setId(id);
            boolean thereIsSongId = false;
            for (Song song : mMusicDao.getSongs()) {
                if (song.getId() == values.getAsInteger("param3")){
                    thereIsSongId = true;
                    break;
                }
            }
            boolean thereIsAlbumId = false;
            for (Album album : mMusicDao.getAlbums()) {
                if (album.getId() == values.getAsInteger("param2")){
                    thereIsAlbumId = true;
                    break;
                }
            }
            if (thereIsAlbumId && thereIsSongId){
            albumSong.setAlbumId(values.getAsInteger("param2"));
            albumSong.setSongId(values.getAsInteger("param3"));
            return mMusicDao.updateLinkInfo(albumSong);
            }
            else{
                Log.i(TAG, "insert: THERE IS NO SUCH SONG OR ALBUM");
                return -1;
            }
        } else {
            throw new IllegalArgumentException("");
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
//            boolean thereIsInAnotherTable = false;
//            int anotherIndex = -1;
//            for (AlbumSong link : mMusicDao.getLinks()) {
//                if (link.getAlbumId() == id){
//                    thereIsInAnotherTable = true;
//                    anotherIndex = link.getId();
//                    break;
//                }
//            }
//            if (thereIsInAnotherTable){
//                mMusicDao.deleteLinkById(anotherIndex);
//            }
            return mMusicDao.deleteAlbumById(id);
        } else if(URI_MATCHER.match(uri) == SONG_ROW_CODE){
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteSongById(id);
        }else if(URI_MATCHER.match(uri) == LINK_ROW_CODE){
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteLinkById(id);
        } else {
            throw new IllegalArgumentException("");
        }
    }
}
