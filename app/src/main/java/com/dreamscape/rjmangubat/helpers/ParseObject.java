package com.dreamscape.rjmangubat.helpers;

import com.google.gson.annotations.Expose;

/**
 * Serves as model object for the Json to be parsed
 *  Created by rjmangubat on 4/26/2015.
 */
public class ParseObject {

    @Expose
    private String type;
    @Expose
    private Value value;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The value
     */
    public Value getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Value value) {
        this.value = value;
    }



}

