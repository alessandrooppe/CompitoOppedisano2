package itspiemonte.compitooppedisano;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText query;
    private TextView url;
    private TextView response;

    ArrayList articleNames = new ArrayList<>(Arrays.asList("Article 1", "Gianni russo e' morto", "Torino bufera accidentale"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CompitinoAdapter adapter = new CompitinoAdapter(this, articleNames);
        recyclerView.setAdapter(adapter);

        query = (EditText) findViewById(R.id.et_query);
        url = (TextView) findViewById(R.id.tv_url);
        response = (TextView) findViewById(R.id.tv_response);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                Log.d("NEWS APP", "Hai premuto il primo tasto");
                return true;
            case R.id.menu_item_2:
                Log.d("NEWS APP", "Hai premuto il secondo tasto");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            String githubUrl = createGithubUrl();
            String responseJson = "";
            url.setText(githubUrl);
            GithubAsyncTask task = new GithubAsyncTask();
            try {
                task.execute(new URL(githubUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class GithubAsyncTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... url) {
            String responseJson = null;
            try {
                responseJson = utilities.Network.getResponseFromHttpUrl(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseJson;
        }

        @Override
        protected void onPostExecute(String responseJson) {
            super.onPostExecute(responseJson);
            try {
                JSONObject githubResult = new JSONObject(responseJson);
                Log.d("INTERNET total count = ", String.valueOf(githubResult.get("total_count")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            response.setText(responseJson);
        }
    }

    private String createGithubUrl() {
        String queryString = query.getText().toString();
        return utilities.Network.buildUrl(queryString).toString();
    }
}




