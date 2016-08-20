package com.example.widy.widy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    public String Current_URL ="http://api.openweathermap.org/data/2.5/weather?id=3838583&appid=c8ca9280dc6e2ebeb82e7fa63b2104fb";

    TextView tv, descrip,tempe,min_max,ciudad;
    Button button;
    NetworkImageView view ;
    ProgressDialog pd ;
    String name, id,country,icon ,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.date);
        descrip=(TextView) findViewById(R.id.description);
        tempe = (TextView) findViewById(R.id.temp);
        min_max = (TextView) findViewById(R.id.min_max);
        ciudad = (TextView) findViewById(R.id.name);
        button = (Button) findViewById(R.id.button);

        view= (NetworkImageView) findViewById(R.id.icon);
        button.setOnClickListener(this);

        String time = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        tv.setText(time);

    }
    public void makejsonreq(String full_url) {

        pd.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, Current_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    name = response.getString("name");
                    id = response.getString("id");

                    JSONObject main = response.getJSONObject("main");

                    int temp, temp_max, temp_min;
                    temp = (int) (main.getDouble("temp") - 273.15);
                    temp_max = (int) (main.getDouble("temp_max") - 273.15);
                    temp_min = (int) (main.getDouble("temp_min") - 273.15);

                    JSONObject sys = response.getJSONObject("sys");

                    country = sys.getString("country");

                    JSONArray weather = response
                            .getJSONArray("weather");

                    JSONObject jo = weather.getJSONObject(0);

                    description = jo.getString("description");
                    icon = jo.getString("icon");

                    // icon url
                    String icon_url = "http://openweathermap.org/img/w/"
                            + icon + ".png";

                    ImageLoader imageLoader = MyApplication
                            .getInstance().getImageLoader();

                    ciudad.setText(name + "," + country);

                    tempe.setText(temp + "\u2103");
                    min_max.setText(temp_min + "\u2103 /" + temp_max
                            + "\u2103");
                    descrip.setText(description);

                    view.setImageUrl(icon_url, imageLoader);

                    // forecast_btn.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

                pd.dismiss();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
            }
        });
       MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");
    }
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading.....");
            pd.setCancelable(false);

            // String city = et.getText().toString();

            String full_url = Current_URL ;

            makejsonreq(full_url);
            break;
        }


    }


}