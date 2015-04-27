package com.dreamscape.rjmangubat.helpers;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjmangubat on 4/26/2015.
 */
public class Value {


    @Expose
    private Integer id;

    @Expose
    private String joke;

    @Expose
    private List<String> categories = new ArrayList<String>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The joke
     */
    public String getJoke() {
        return joke;
    }

    /**
     *
     * @param joke
     * The joke
     */
    public void setJoke(String joke) {
        this.joke = joke;
    }

    /**
     *
     * @return
     * The categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }



}