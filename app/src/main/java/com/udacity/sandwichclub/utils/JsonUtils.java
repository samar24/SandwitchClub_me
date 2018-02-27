package com.udacity.sandwichclub.utils;


import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static  String NAME = "name";
    private static  String MAIN_NAME= "mainName";
    private static  String ALSO_KNOWN_AS= "alsoKnownAs";
    private static  String PLACE_ORIGIN= "placeOfOrigin";
    private static  String DESCRIPTION= "description";
    private static  String IMAGE= "image";
    private static  String INGREDIENTS= "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        JSONObject data;
        String mainName="";
        String placeOfOrigin="";
        JSONArray alsoKnownAs;
        List<String> stringArray_AlsoKnownAs=new ArrayList<>();
        String description="";
        String image="";
        JSONArray ingredients;
        List<String> stringArray_Ingredients=new ArrayList<>();
        try {
            data = new JSONObject(json);
            JSONObject jsonObjectName = data.getJSONObject(NAME);
            mainName=jsonObjectName.optString(MAIN_NAME);

            placeOfOrigin=data.optString(PLACE_ORIGIN);
            description=data.optString(DESCRIPTION);
            image=data.optString(IMAGE);
            ingredients = data.getJSONArray(INGREDIENTS);
            alsoKnownAs=jsonObjectName.getJSONArray(ALSO_KNOWN_AS);
            stringArray_AlsoKnownAs = FromJsonArraytoArrayList(alsoKnownAs);
            stringArray_Ingredients = FromJsonArraytoArrayList(ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(stringArray_AlsoKnownAs);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setIngredients(stringArray_Ingredients);
        return sandwich;
    }
    private static List<String> FromJsonArraytoArrayList(JSONArray Jarray){
        List<String> list = new ArrayList<>(0);
        if (Jarray!=null){
            for(int i=0; i<Jarray.length();i++){
                try {
                    list.add(Jarray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}



