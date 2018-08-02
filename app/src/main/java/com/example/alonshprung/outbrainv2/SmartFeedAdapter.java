package com.example.alonshprung.outbrainv2;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.outbrain.OBSDK.Outbrain;
import com.outbrain.OBSDK.SmartFeed.OBSmartFeed;
import com.outbrain.OBSDK.Viewability.OBTextView;

public class SmartFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OBSmartFeed obSmartFeed;
    private static final int ORIGINAL_RECYCLE_VIEW_SIZE = 1;
//    private static final int TEXT_VIEW_TYPE = 1;
//    private static final int ARTICLE_HEADER_VIEW_TYPE = 2;
    private static final int OUTBRAIN_HEADER_VIEW_TYPE = 3;

    private String outbrainWidgetId;
    private String outbrainUrl;

    public void setOBSmartFeed(OBSmartFeed obSmartFeed) {
        this.obSmartFeed = obSmartFeed;
    }

    // setters for registerOBTextView
    public void setOutbrainWidgetId(String outbrainWidgetId) {
        this.outbrainWidgetId = outbrainWidgetId;
    }

    public void setOutbrainUrl(String outbrainUrl) {
        this.outbrainUrl = outbrainUrl;
    }

    // view holder for outbrain header
    public class OutbrainHeaderViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        ImageButton outbrainLogoButton;
        OBTextView outbrainTextView;

        public OutbrainHeaderViewHolder(View v) {
            super(v);
            layout = v;
            this.outbrainLogoButton = v.findViewById(R.id.outbrain_logo_button);
            this.outbrainTextView = v.findViewById(R.id.outbrain_sponsored_obtextview);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        switch (viewType) {
            // add here more view holders for another view types
            case OUTBRAIN_HEADER_VIEW_TYPE:
                v = inflater.inflate(R.layout.outbrain_sfeed_header, parent, false);
                return new OutbrainHeaderViewHolder(v);
            default:
                return this.obSmartFeed.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            // add here more view holders for another view types
            case 0:
                // outbrain header view

                // setting up outbrain header click listener
                OutbrainHeaderViewHolder outbrainHeaderVH = (OutbrainHeaderViewHolder)holder;
                outbrainHeaderVH.outbrainLogoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String aboutOutbrainUrl = Outbrain.getOutbrainAboutURL(v.getContext());
                        CustomTabsUtils.createCustomTabIntent(v.getContext(), Uri.parse(aboutOutbrainUrl));
                    }
                });

                // register OBTextView
                Outbrain.registerOBTextView(outbrainHeaderVH.outbrainTextView, this.outbrainWidgetId, this.outbrainUrl);
                break;
            default:
                this.obSmartFeed.onBindViewHolder(holder, position, ORIGINAL_RECYCLE_VIEW_SIZE);
        }
    }

    @Override
    public int getItemCount() {
        return ORIGINAL_RECYCLE_VIEW_SIZE + this.obSmartFeed.getSmartFeedItems().size();
    }

    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return OUTBRAIN_HEADER_VIEW_TYPE;
            default:
                return this.obSmartFeed.getItemViewType(position, ORIGINAL_RECYCLE_VIEW_SIZE);
        }
    }
}
