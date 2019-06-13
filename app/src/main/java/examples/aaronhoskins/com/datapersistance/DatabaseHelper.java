package examples.aaronhoskins.com.datapersistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static examples.aaronhoskins.com.datapersistance.DatabaseContract.DATABASE_NAME;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.FIELD_ARTIST;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.FIELD_GENRE;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.FIELD_RELEASE_DATE;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.FIELD_TITLE;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.TABLE_NAME;
import static examples.aaronhoskins.com.datapersistance.DatabaseContract.whereClauseForUpdate;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //insert values into database
    public void insertMusic(Music musicToInsert) {
        //create content value which holds key value pairs, key
        //being the column in the db, and value being the value
        //associated with that column
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, musicToInsert.getTitle());
        contentValues.put(FIELD_ARTIST, musicToInsert.getArtist());
        contentValues.put(FIELD_GENRE, musicToInsert.getGenre());
        contentValues.put(FIELD_RELEASE_DATE, musicToInsert.getDate());

        //Need to get a writable database
        SQLiteDatabase writableDatabase = this.getWritableDatabase();

        //insert into the database
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public Music queryForOneMusicRecord(String title) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Music returnMusic = null;

        Cursor cursor = readableDatabase.rawQuery(DatabaseContract.getSelectOneMusicItem(title),null);

        if(cursor.moveToFirst()) {
            String titleFromDB = cursor.getString(cursor.getColumnIndex(FIELD_TITLE));
            String genreFromDB = cursor.getString(cursor.getColumnIndex(FIELD_GENRE));
            String artistFromDB = cursor.getString(cursor.getColumnIndex(FIELD_ARTIST));
            String dateFromDB = cursor.getString(cursor.getColumnIndex(FIELD_RELEASE_DATE));
            returnMusic = new Music(titleFromDB, genreFromDB, artistFromDB, dateFromDB);
        }
        readableDatabase.close();
        return returnMusic;
    }

    public ArrayList<Music> queryForAllMusicRecords() {
        ArrayList<Music> returnMusicList = null;
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(DatabaseContract.getSelectAllMusicItems(),null);

        if(cursor.moveToFirst()) {
            returnMusicList = new ArrayList<>();
            do {
                Music returnMusic = null;
                String titleFromDB = cursor.getString(cursor.getColumnIndex(FIELD_TITLE));
                String genreFromDB = cursor.getString(cursor.getColumnIndex(FIELD_GENRE));
                String artistFromDB = cursor.getString(cursor.getColumnIndex(FIELD_ARTIST));
                String dateFromDB = cursor.getString(cursor.getColumnIndex(FIELD_RELEASE_DATE));
                returnMusic = new Music(titleFromDB, genreFromDB, artistFromDB, dateFromDB);
                returnMusicList.add(returnMusic);
            } while(cursor.moveToNext());
        }
        readableDatabase.close();
        return returnMusicList;
    }

    public void updateMusic(Music music) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, music.getTitle());
        contentValues.put(FIELD_ARTIST, music.getArtist());
        contentValues.put(FIELD_GENRE, music.getGenre());
        contentValues.put(FIELD_RELEASE_DATE, music.getDate());

        writableDatabase.update(TABLE_NAME, contentValues, whereClauseForUpdate(music.getTitle()), null);
        writableDatabase.close();
    }

    public void deleteMusic(String title) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, whereClauseForUpdate(title), null);
        writableDatabase.close();
    }
}
