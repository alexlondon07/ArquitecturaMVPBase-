package io.github.alexlondon07.arquitecturamvpbase.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by alexlondon07 on 11/9/17.
 */

@Root(name="breakfast_menu")
public class BreakfastMenu {


    @ElementList(entry = "food", inline = true)
    private ArrayList<Food> foodArrayList;


    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }
}
