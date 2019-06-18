package examples.aaronhoskins.com.datapersistance;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MusicProviderContract {
    public static final String CONTENT_AUTHORITY = "examples.aaronhoskins.com.datapersistance";
    public static final Uri BASE_CONTENT_ID = Uri.parse("content://" +CONTENT_AUTHORITY);
    public static final String PATH_MUSIC = "music";

    public static final class MusicEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_MUSIC).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MUSIC;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MUSIC;

        public static Uri buildMusicUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
