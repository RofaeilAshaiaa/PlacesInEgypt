package rofaeil.ashaiaa.idea.placesinegypt.sights;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.R;
import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;
import rofaeil.ashaiaa.idea.placesinegypt.databinding.FeaturedSightItemBinding;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 20/10/17.
 */

public class FeaturedSightsAdapter extends RecyclerView.Adapter<FeaturedSightsAdapter.SightViewHolder>{

    final private FeaturedListItemClickListener itemClickListener;
    private List<Sight> mFeaturedSightsList;
    private Context mContext;

    public FeaturedSightsAdapter(FeaturedListItemClickListener itemClickListener, List<Sight> mFeaturedSightsList, Context mContext) {
        this.itemClickListener = itemClickListener;
        this.mFeaturedSightsList = mFeaturedSightsList;
        this.mContext = mContext;
    }

    @Override
    public SightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        FeaturedSightItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.featured_sight_item, parent, false);
        return new SightViewHolder( view);
    }

    @Override
    public void onBindViewHolder(SightViewHolder holder, int position) {

        String imageUrl = mFeaturedSightsList.get(position).getImage().getUrl();
        Log.d("tag", "onBindViewHolder: "+imageUrl);

        //there is something wrong with the images url sent by the api,
        // they don't load successfully, despite the code works perfect with another url
        Picasso.with(mContext)
//                .load(imageUrl)
                .load("http://i.imgur.com/DvpvklR.png")
                .resize( mFeaturedSightsList.get(position).getImage().getWidth(),
                        mFeaturedSightsList.get(position).getImage().getHeight())
                .placeholder(R.drawable.ic_autorenew_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.itemBinding.imageOfSight);


//        Glide.with(mContext).load("http://goo.gl/gEgYUd").into(holder.itemBinding.imageOfSight);


        holder.itemBinding.sightPrice.setText(mFeaturedSightsList.get(position).getPrice()+"$");
    }

    @Override
    public int getItemCount() {
        return mFeaturedSightsList.size();
    }

    public interface FeaturedListItemClickListener {
        void onFeaturedListItemClicked(int clickedItemIndex);
    }

    public class SightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public FeaturedSightItemBinding itemBinding;


        public SightViewHolder(FeaturedSightItemBinding itemView) {
            super(itemView.getRoot());

            itemBinding = itemView;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            itemClickListener.onFeaturedListItemClicked(clickedPosition);
        }
    }
}
