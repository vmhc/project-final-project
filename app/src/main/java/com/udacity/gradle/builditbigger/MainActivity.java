package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.test.espresso.IdlingResource;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.IdlingResource.ITestingCallbacks;
import com.udacity.gradle.builditbigger.IdlingResource.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements ITestingCallbacks {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    private ITestingCallbacks iTestingCallbacks;
    private TextView tv_request_joker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_request_joker = findViewById(R.id.tv_request_joker);
        iTestingCallbacks = this;
        getIdlingResource();
    }

    @VisibleForTesting
    @Nullable
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mIdlingResource.setIdleState(false);
        new EndpointsAsyncTask(iTestingCallbacks).execute(new Pair<Context, String>(this, "Manfred"));
        // startActivity(JokerActivity.newIntent(this, JokerMain.randomJoker()));
    }

    @Override
    public void play(String result) {
        tv_request_joker.setText(result);
        mIdlingResource.setIdleState(true);
    }

    static class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private static MyApi myApiService = null;
        private Context context;
        private ITestingCallbacks iTestingCallbacks;

        public EndpointsAsyncTask(ITestingCallbacks iTestingCallbacks) {
            this.iTestingCallbacks = iTestingCallbacks;
        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("https://ferrous-weaver-255301.appspot.com/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            iTestingCallbacks.play(result);
        }
    }


}
