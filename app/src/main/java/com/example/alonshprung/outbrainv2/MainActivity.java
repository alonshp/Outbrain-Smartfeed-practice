package com.example.alonshprung.outbrainv2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.outbrain.OBSDK.Entities.OBRecommendation;
import com.outbrain.OBSDK.Outbrain;
import com.outbrain.OBSDK.SmartFeed.OBSmartFeed;
import com.outbrain.OBSDK.SmartFeed.OBSmartFeedListener;

public class MainActivity extends AppCompatActivity implements OBSmartFeedListener {

    private final String OUTBRAIN_WIDGET_ID = "SFD_MAIN_1";
    private final String POST_URL = "http://mobile-demo.outbrain.com/2013/12/15/test-page-2";
    private final String PUBLISHER_NAME = "CNN";

    private OBSmartFeed obSmartFeed;
    private SmartFeedAdapter smartFeedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private final String LOG_TAG = "SmartFeedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register Outbrain
        Outbrain.register(this, "AndroidSampleApp2014");
        Outbrain.setTestMode(true);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        smartFeedAdapter = new SmartFeedAdapter();

        // setting up smart feed
        this.obSmartFeed = new OBSmartFeed(POST_URL, OUTBRAIN_WIDGET_ID, recyclerView, PUBLISHER_NAME, R.drawable.cnn_logo, this);
        smartFeedAdapter.setOBSmartFeed(obSmartFeed);
        this.obSmartFeed.setupInfiniteScroll();

        // setting up widgetId and post url on the adapter
        smartFeedAdapter.setOutbrainWidgetId(OUTBRAIN_WIDGET_ID);
        smartFeedAdapter.setOutbrainUrl(POST_URL);

        // set recyclerView adapter
        recyclerView.setAdapter(smartFeedAdapter);
    }

    @Override
    public void userTappedOnRecommendation(OBRecommendation rec) {
        CustomTabsUtils.createCustomTabIntent(MainActivity.this, Uri.parse(Outbrain.getUrl(rec)));
    }

    @Override
    public void userTappedOnAdChoicesIcon(String url) {
        CustomTabsUtils.createCustomTabIntent(MainActivity.this, Uri.parse(url));
    }
}
