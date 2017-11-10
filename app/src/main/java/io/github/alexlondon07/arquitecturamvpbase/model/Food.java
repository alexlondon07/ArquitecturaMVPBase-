package io.github.alexlondon07.arquitecturamvpbase.model;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * Created by alexlondon07 on 11/10/17.
 */

public class Food  implements Serializable{


    @Element(name = "name")
    private String name;

    @Element(name = "price")
    private String price;

    @Element(name = "description")
    private String description;

    @Element(name = "calories")
    private  String calories;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
