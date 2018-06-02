package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String name = "name";
    private static final String mainName = "mainName";
    private static final String alsoKnownAs = "alsoKnownAs";
    private static final String placeOfOrigin = "placeOfOrigin";
    private static final String description = "description";
    private static final String image = "image";
    private static final String ingredients = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJSONObject = new JSONObject(json);

            JSONObject name = sandwichJSONObject.getJSONObject(JsonUtils.name);
            sandwich.setMainName(name.getString(JsonUtils.mainName));

            JSONArray alsoKnownAsJSONArray = name.getJSONArray(JsonUtils.alsoKnownAs);
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJSONArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsJSONArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            sandwich.setPlaceOfOrigin(sandwichJSONObject.getString(JsonUtils.placeOfOrigin));
            sandwich.setDescription(sandwichJSONObject.getString(JsonUtils.description));
            sandwich.setImage(sandwichJSONObject.getString(JsonUtils.image));

            JSONArray ingredientsJSONArray = sandwichJSONObject.getJSONArray(JsonUtils.ingredients);
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                ingredientsList.add(ingredientsJSONArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sandwich;
    }
}