package com.dreamscape.rjmangubat.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
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
    private String mResult,
                   mFirstName,
                   mLastname;
    private JSONObject mResponse;
    private ParseObject parseObject;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();


    }

    private void initComponents(){

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        parseObject = new ParseObject();
        mJoke = new Jokes();

    }

    private void RequestData(String url){

        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
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

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
        );
        mQueue.add(request);
    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnRandomJoke:

                    RequestData(API_URL);
                    mJoke.setJoke(mResult);

                Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("joke", mJoke);
                    startActivity(intent);

                break;
            case R.id.btnNameJoke:
                messageDialog(this);
                break;
            case R.id.btnCategoryJoke:

                Toast.makeText(this, mResult, Toast.LENGTH_SHORT).show();
            default:
                break;
        }

    }

    protected void messageDialog(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter your Name");
        final EditText etFirstName = new EditText(activity);
        alert.setMessage("First Name:");
        final EditText etLastName = new EditText(activity);
        alert.setMessage("Last Name:");

        alert.setView(etFirstName);
        alert.setView(etLastName);

        alert.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                mFirstName = etFirstName.getText().toString();


            }
        });

        /*alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });*/

        alert.show();
    }



}
