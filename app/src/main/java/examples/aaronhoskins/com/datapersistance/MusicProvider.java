package examples.aaronhoskins.com.datapersistance;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Locale;

import static examples.aaronhoskins.com.datapersistance.DatabaseContract.FIELD_TITLE;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.TABLE_NAME;
import static examples.aaronhoskins.com.datapersistance.MusicProviderContract.CONTENT_AUTHORITY;
import static examples.aaronhoskins.com.datapersistance.MusicProviderContract.PATH_MUSIC;

public class MusicProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    public static final UriMatcher uriMatcher = buildUriMatcher();
    public static final int MUSIC = 100;
    public static final int MUSIC_ITEM = 101;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MUSIC, MUSIC);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MUSIC + "/#", MUSIC_ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor retCursor = null;
        switch(uriMatcher.match(uri)) {
            case MUSIC:
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MUSIC_ITEM:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        MusicProviderContract.MusicEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MUSIC:
                return MusicProviderContract.MusicEntry.CONTENT_TYPE;
            case MUSIC_ITEM:
                return MusicProviderContract.MusicEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnUri;
        long _id;
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case MUSIC:
                _id = writableDatabase.insert(TABLE_NAME, null, contentValues);
                if(_id > 0){
                    returnUri =  MusicProviderContract.MusicEntry.buildMusicUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        int rowsAffected = writableDatabase.delete(
                TABLE_NAME,
                String.format(Locale.US, "WHERE %s = ", FIELD_TITLE),
                strings);
        if(rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase writeableDatabase = databaseHelper.getWritableDatabase();
        int rowsAffected =  writeableDatabase.update(
                TABLE_NAME,
                contentValues,
                String.format(Locale.US, "WHERE %s = ", FIELD_TITLE),
                strings);
        if(rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }
}
