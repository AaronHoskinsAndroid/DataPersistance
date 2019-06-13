package examples.aaronhoskins.com.datapersistance;

import java.util.Locale;

public class DatabaseContract {
    public static final String DATABASE_NAME = "db_database_name";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "music_table";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_GENRE = "genre";
    public static final String FIELD_ARTIST = "artist";
    public static final String FIELD_RELEASE_DATE = "date";


    public static String getCreateTableStatement() {
        return String.format(
                Locale.US,
                "CREATE TABLE %s(%s TEXT PRIMARY_KEY, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME,FIELD_TITLE, FIELD_GENRE, FIELD_ARTIST, FIELD_RELEASE_DATE);
    }

    public static String getSelectOneMusicItem(String title) {
        return String.format(Locale.US, "SELECT * FROM %s WHERE %s = \"%s\"", TABLE_NAME, FIELD_TITLE, title);
    }

    public static String getSelectAllMusicItems() {
        return String.format(Locale.US, "SELECT * FROM %s", TABLE_NAME);
    }

    public static String whereClauseForUpdate(String title) {
        return String.format(Locale.US, "WHERE %s = %s", FIELD_TITLE, title);
    }

}
