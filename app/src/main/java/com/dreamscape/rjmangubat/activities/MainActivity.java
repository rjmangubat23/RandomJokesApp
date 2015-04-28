package com.dreamscape.rjmangubat.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dreamscape.rjmangubat.R;
import com.dreamscape.rjmangubat.helpers.Jokes;
import com.dreamscape.rjmangubat.helpers.ParseObject;
import com.dreamscape.rjmangubat.helpers.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private final String API_URL = "http://api.icndb.com/jokes/random";
    private Jokes mJoke;
    private String mResult = "",
                   mFirstName,
                   mLastName,
                   mCategoryChosen;
    private Intent intent;
    private View v;
    private EditText etFirstName,
                     etLastName;
    private JSONObject mResponse;
    private ParseObject parseObject;
    protected ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();


    }

    private void initComponents(){

        parseObject = new ParseObject();
        mJoke = new Jokes();

        progressBar = new ProgressDialog(this);

        progressBar.setIndeterminate(true);
        progressBar.setMessage("Loading picture...");
        progressBar.setCancelable(true);
        progressBar.setCanceledOnTouchOutside(true);

    }

    private void RequestData(String url){
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.show();
                        mResponse = response;
                        Gson gson = new Gson();
                        parseObject = gson.fromJson(mResponse.toString(),ParseObject.class);
                        if("success".equals(parseObject.getType())) {
                            mResult = parseObject.getValue().getJoke().replace("&amp;","&" )
                                                                      .replace("&lt;","<" )
                                                                      .replace("&gt;", ">" )
                                                                      .replace("&apos;","'" )
                                                                      .replace("&quot;", "\"" );
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Response failed!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.dismiss();
                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnRandomJoke:

                RequestData(API_URL);

                mJoke.setJoke(mResult);

                intent = new Intent(this, ResultActivity.class);
                intent.putExtra("joke", mJoke);
                startActivity(intent);
                Toast.makeText(this, mResult, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnNameJoke:
                messageNameDialog();
                Toast.makeText(this, mResult, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCategoryJoke:
                messageCategoryDialog();
                Toast.makeText(this, mResult, Toast.LENGTH_SHORT).show();
            default:
                break;
        }


    }

    protected void messageNameDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        v = inflater.inflate(R.layout.name_dialog, null);

        new AlertDialog.Builder(this)
                .setView(v)
                .setTitle("Enter your Name:")
                .setNegativeButton("Go", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        etFirstName = (EditText) v.findViewById(R.id.etFirstName);
                        etLastName = (EditText) v.findViewById(R.id.etLastName);

                        mFirstName = etFirstName.getText().toString().trim();
                        mLastName = etLastName.getText().toString().trim();


                        RequestData(API_URL+"?firstName="+mFirstName+"&lastName="+mLastName);

                        mJoke.setJoke(mResult);

                        intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("joke", mJoke);
                        startActivity(intent);
                        //msg = "result: "  + etLastName.getText().toString().trim() + etFirstName.getText().toString().trim();
                       // Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_SHORT).show();

                    }
                })
        .show();
    }

    protected void messageCategoryDialog(){

        final CharSequence[] items = {"Explicit", "Nerdy"};

        new AlertDialog.Builder(this)
            .setTitle("Select The Difficulty Level")
            .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {


                    switch (item) {
                        case 0:
                            mCategoryChosen = "explicit";
                            break;
                        case 1:
                            // Your code when 2nd  option seletced
                            mCategoryChosen = "nerdy";
                            break;
                        default:
                            break;
                    }

                }
            }).setNegativeButton("Go", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    RequestData(API_URL+"?limitTo=["+mCategoryChosen+"]");

                    mJoke.setJoke(mResult);

                    intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("joke", mJoke);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), API_URL+"?limitTo=["+mCategoryChosen+"]" , Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
            }
        }).show();


    }

}
