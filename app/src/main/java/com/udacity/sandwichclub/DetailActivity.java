package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView ingredientsIv;
    TextView origin_tv;
    TextView description_tv;
    TextView ingredients_tv;
    TextView also_known_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         ingredientsIv = findViewById(R.id.image_iv);
         origin_tv=findViewById(R.id.origin_tv);
         description_tv=findViewById(R.id.description_tv);
         ingredients_tv=findViewById(R.id.ingredients_tv);
         also_known_tv=findViewById(R.id.also_known_tv);

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

        if (sandwich.getImage().isEmpty()){
            Toast.makeText(this, "There is No Image", Toast.LENGTH_SHORT).show();
        }else {

            Picasso.with(this).load(sandwich.getImage())
                    .into(ingredientsIv);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            origin_tv.setText("There is no place of origin");
        }else {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getDescription().isEmpty()){
            description_tv.setText("There is no description");
        }else {
            description_tv.setText(sandwich.getDescription());
        }
        if (android.text.TextUtils.join(",",sandwich.getIngredients()).isEmpty()){
            ingredients_tv.setText("there is no ingredients");
        }else {
            ingredients_tv.setText(android.text.TextUtils.join(",",sandwich.getIngredients()));
        }
        if (android.text.TextUtils.join(",",sandwich.getAlsoKnownAs()).isEmpty()){
            also_known_tv.setText("There is no Other Names for it");
        }else {
            also_known_tv.setText(android.text.TextUtils.join(",",sandwich.getAlsoKnownAs()));
        }


    }
}
