package com.example.vasiliy.downloadinfofromsite;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FilmObjectForDownload {

    private final String FILM_TAG = "filmObject";

    private String title;
    private String year;
    private List<String> ganres;
    private List<String> countrys;
    private String director;
    private List<String> actors;
    private String description;

    public FilmObjectForDownload() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getGanres() {
        return ganres;
    }

    public void setGanres(List<String> ganres) {
        this.ganres = ganres;
    }

    public void addGanre(String ganre) {
        if(ganres == null) {
            ganres = new ArrayList<>();
        }
        ganres.add(ganre);
    }

    public String getStringGanres() {
        String result;

        result = ganres.get(0);

        for(int i = 1; i < ganres.size(); ++i) {
            result += ", " + ganres.get(i);
        }

        return result;
    }

    public List<String> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<String> countrys) {
        this.countrys = countrys;
    }

    public void addCountry(String country) {
        if(countrys == null) {
            countrys = new ArrayList<>();
        }
        countrys.add(country);
    }

    public String getStringCountry() {
        String result;

        result = countrys.get(0);

        for(int i = 1; i < countrys.size(); ++i) {
            result += ", " + countrys.get(i);
        }

        return result;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void addActor(String actor) {
        if(actors == null) {
            actors = new ArrayList<>();
        }
        actors.add(actor);
    }

    public String getStringActors() {
        String result;

        result = actors.get(0);

        for(int i = 1; i < actors.size(); ++i) {
            result += ", " + actors.get(i);
        }

        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    void writeToLog() {
        Log.d(FILM_TAG, "-----FILM-----");
        Log.d(FILM_TAG, getTitle());
        Log.d(FILM_TAG, getYear());
        Log.d(FILM_TAG, getStringGanres());
        Log.d(FILM_TAG, getStringCountry());
        Log.d(FILM_TAG, getDirector());
        Log.d(FILM_TAG, getStringActors());
        Log.d(FILM_TAG, getDescription());
        Log.d(FILM_TAG, "--------------");
    }
}
