package com.dreamscape.rjmangubat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dreamscape.rjmangubat.R;
import com.dreamscape.rjmangubat.helpers.Jokes;

public class ResultActivity extends ActionBarActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initComponents();
        displayJokes();

     }

    private void initComponents(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        tvResult = (TextView)findViewById(R.id.tvResult);
    }

    private void displayJokes(){
        Intent resultIntent = getIntent();
        Jokes currentJoke = (Jokes) resultIntent.getSerializableExtra("joke");
        tvResult.setText(currentJoke.getJoke());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_jokes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
