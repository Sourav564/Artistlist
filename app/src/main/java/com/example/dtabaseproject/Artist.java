package com.example.dtabaseproject;

public class Artist {
    String artistid;
    String artistname;
    String artistGenre;

    public Artist()
    {

    }

    public Artist(String artistid, String artistname, String artistGenre) {
        this.artistid = artistid;
        this.artistname = artistname;
        this.artistGenre = artistGenre;
    }

    public String getArtistid() {
        return artistid;
    }

    public String getArtistname() {
        return artistname;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
