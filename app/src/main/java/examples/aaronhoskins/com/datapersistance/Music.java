package examples.aaronhoskins.com.datapersistance;

import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable {
    private String title;
    private String genre;
    private String artist;
    private String date;

    public Music() {
    }

    public Music(String title, String genre, String artist, String date) {
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.date = date;
    }

    protected Music(Parcel in) {
        title = in.readString();
        genre = in.readString();
        artist = in.readString();
        date = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(genre);
        parcel.writeString(artist);
        parcel.writeString(date);
    }
}
