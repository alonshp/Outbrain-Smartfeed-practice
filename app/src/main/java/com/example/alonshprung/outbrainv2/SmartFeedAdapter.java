package com.example.alonshprung.outbrainv2;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.outbrain.OBSDK.Outbrain;
import com.outbrain.OBSDK.SmartFeed.OBSmartFeed;
import com.outbrain.OBSDK.Viewability.OBTextView;

public class SmartFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OBSmartFeed obSmartFeed;
    private static final int ORIGINAL_RECYCLE_VIEW_SIZE = 6;
    private static final int ARTICLE_HEADER_VIEW_TYPE = 1;
    private static final int ARTICLE_DETAILS_VIEW_TYPE = 2;
    private static final int ARTICLE_TEXT_TYPE = 3;
    private static final int ARTICLE_IMAGE_TYPE = 4;
    private static final int OUTBRAIN_HEADER_VIEW_TYPE = 5;

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

    public class TextItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public View layout;

        public TextItemViewHolder(View v) {
            super(v);
            layout = v;
            textView = (TextView) v.findViewById(R.id.article_text_view);
        }
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        public ImageItemViewHolder(View v) {
            super(v);
            layout = v;
        }
    }

    public class HeaderItemViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        public HeaderItemViewHolder(View v) {
            super(v);
            layout = v;
        }
    }

    public class DetailItemViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        public DetailItemViewHolder(View v) {
            super(v);
            layout = v;
        }
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
            case ARTICLE_HEADER_VIEW_TYPE:
                v = inflater.inflate(R.layout.article_header, parent, false);
                HeaderItemViewHolder ivh = new HeaderItemViewHolder(v);
                return ivh;
            case ARTICLE_DETAILS_VIEW_TYPE:
                v = inflater.inflate(R.layout.article_details, parent, false);
                DetailItemViewHolder dvh = new DetailItemViewHolder(v);
                return dvh;
            case ARTICLE_TEXT_TYPE:
                v = inflater.inflate(R.layout.article_text, parent, false);
                TextItemViewHolder tvh = new TextItemViewHolder(v);
                return tvh;
            case ARTICLE_IMAGE_TYPE:
                v = inflater.inflate(R.layout.article_image, parent, false);
                ImageItemViewHolder imvh = new ImageItemViewHolder(v);
                return imvh;
            case OUTBRAIN_HEADER_VIEW_TYPE:
                v = inflater.inflate(R.layout.outbrain_sfeed_header, parent, false);
                return new OutbrainHeaderViewHolder(v);
            default:
                return this.obSmartFeed.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextItemViewHolder tvh;

        switch (position) {
            // add here more view holders for another view types
            case 0:
                break;
            case 1:
                break;
            case 2:
                tvh = (TextItemViewHolder)holder;
                tvh.textView.setText(tvh.textView.getContext().getResources().getString(R.string.article_text_1));
                break;
            case 3:
                break;
            case 4:
                tvh = (TextItemViewHolder)holder;
                tvh.textView.setText(tvh.textView.getContext().getResources().getString(R.string.article_text_2));
                break;
            case 5:
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
                return ARTICLE_HEADER_VIEW_TYPE;
            case 1:
                return ARTICLE_DETAILS_VIEW_TYPE;
            case 2:
                return ARTICLE_TEXT_TYPE;
            case 3:
                return ARTICLE_IMAGE_TYPE;
            case 4:
                return ARTICLE_TEXT_TYPE;
            case 5:
                return OUTBRAIN_HEADER_VIEW_TYPE;
            default:
                return this.obSmartFeed.getItemViewType(position, ORIGINAL_RECYCLE_VIEW_SIZE);
        }
    }
}
