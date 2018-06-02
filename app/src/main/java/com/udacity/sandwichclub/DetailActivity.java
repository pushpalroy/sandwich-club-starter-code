package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView ingredientsIv;

    @BindView(R.id.tv_also_known)
    TextView alsoKnownAsTv;

    @BindView(R.id.tv_also_known_label)
    TextView alsoKnownAsLabelTv;

    @BindView(R.id.tv_origin)
    TextView placeOfOriginTv;

    @BindView(R.id.tv_origin_label)
    TextView placeOfOriginLabelTv;

    @BindView(R.id.tv_description)
    TextView descriptionTv;

    @BindView(R.id.tv_description_label)
    TextView descriptionLabelTv;

    @BindView(R.id.tv_ingredients)
    TextView ingredientsTv;

    @BindView(R.id.tv_ingredients_label)
    TextView ingredientsLabelTv;

    public static String removeCharAt(String s, int pos) {
        if (!s.equals(""))
            return s.substring(0, pos) + s.substring(pos + 1);
        return "";
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // bind the view using ButterKnife
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder alsoKnownAs = new StringBuilder();
            for (String alsoKnownAsItem : sandwich.getAlsoKnownAs())
                alsoKnownAs.append(alsoKnownAsItem).append("\n");
            alsoKnownAsTv.setText(removeCharAt(alsoKnownAs.toString(), alsoKnownAs.lastIndexOf("\n")));
        } else {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsLabelTv.setVisibility(View.GONE);
        }

        if (!sandwich.getPlaceOfOrigin().equals(""))
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        else {
            placeOfOriginTv.setVisibility(View.GONE);
            placeOfOriginLabelTv.setVisibility(View.GONE);
        }

        if (!sandwich.getDescription().equals(""))
            descriptionTv.setText(sandwich.getDescription());
        else {
            descriptionTv.setVisibility(View.GONE);
            descriptionLabelTv.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients().size() > 0) {
            StringBuilder ingredients = new StringBuilder();
            for (String ingredientsItem : sandwich.getIngredients())
                ingredients.append(ingredientsItem).append("\n");

            ingredientsTv.setText(removeCharAt(ingredients.toString(), ingredients.lastIndexOf("\n")));
        } else {
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabelTv.setVisibility(View.GONE);
        }
    }
}