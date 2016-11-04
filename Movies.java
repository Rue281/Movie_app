package com.example.android.movies;

/**
 */
public class Movies {
    String Mposter_path;
    Long Mposter_id;
    String Mtitle;
    String Moverview;
    String Mdate;
    Double Mvote;

    public Movies(String poster_path, Long poster_id, String movieTitle, String overview, String date, Double vote) {
        this.Mposter_path = poster_path;
        this.Mposter_id = poster_id;
        this.Mtitle=movieTitle;
        this.Moverview=overview;
        this.Mdate=date;
        this.Mvote=vote;
    }


    public String getMoverview() {
        return Moverview;
    }

    public void setMoverview(String moverview) {
        Moverview = moverview;
    }

    public Double getMvote() {
        return Mvote;
    }

    public void setMvote(Double mvote) {Mvote = mvote;}

    public String getMtitle() {
        return Mtitle;
    }

    public void setMtitle(String mtitle) {
        Mtitle = mtitle;
    }

    public String getMdate() {
        return Mdate;
    }

    public void setMdate(String mdate) {
        Mdate = mdate;
    }

    public String getMposter_path() {return Mposter_path;}

    public void setMposter_path(String mposter_path) {
        Mposter_path = mposter_path;
    }

    public Long getMposter_id() {
        return Mposter_id;
    }

    public void setMposter_id(Long mposter_id) {
        Mposter_id = mposter_id;
    }
}
