package com.uxap.lyrical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeScn extends AppCompatActivity {
    RequestQueue reqQue;
    private EditText edtArtistName, edtSongName;
    private ScrollView scvLyricalView;
    private TextView txtLyricalView;
    private CardView cvLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scn);
        edtArtistName = findViewById(R.id.edtArtistName);
        edtSongName = findViewById(R.id.edtSongName);
        scvLyricalView = findViewById(R.id.scvLyricalView);
        txtLyricalView = findViewById(R.id.txtLyricsView);
        cvLyrics = findViewById(R.id.cvLyrics);
    }
    public void onClickSearch(View v){
        txtLyricalView.setText("");
        final String URL = ("https://api.lyrics.ovh/v1/"+edtArtistName.getText().toString().toLowerCase().trim()+"/"+edtSongName.getText().toString().toLowerCase().trim())
                .replace(" ", "%20").replace(",", "&");
        Log.i("URL", URL);
        reqQue = Volley.newRequestQueue(this);
        JsonObjectRequest jObjReq = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("JSON_RESPONSE", response+"");
                cvLyrics.setVisibility(View.VISIBLE);
                try {
                    txtLyricalView.setText(response.getString("lyrics"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cvLyrics.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "Error Loading the Lyrics. \nPlease Check Internet Connection or the Spelling.", Toast.LENGTH_LONG).show();
            }
        });
        reqQue.add(jObjReq);
    }
}