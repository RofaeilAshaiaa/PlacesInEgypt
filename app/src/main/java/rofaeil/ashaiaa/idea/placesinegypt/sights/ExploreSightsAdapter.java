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
import rofaeil.ashaiaa.idea.placesinegypt.databinding.SightItemBinding;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 20/10/17.
 */

public class ExploreSightsAdapter extends RecyclerView.Adapter<ExploreSightsAdapter.SightViewHolder> {

    final private ExploreListItemClickListener itemClickListener;
    private List<Sight> mExploreSightsList;
    private Context mContext;

    public ExploreSightsAdapter(ExploreListItemClickListener itemClickListener, List<Sight> mExploreSightsList, Context mContext) {
        this.itemClickListener = itemClickListener;
        this.mExploreSightsList = mExploreSightsList;
        this.mContext = mContext;
    }

    @Override
    public SightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        SightItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.sight_item, parent, false);
        return new SightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SightViewHolder holder, int position) {

        String imageUrl = mExploreSightsList.get(position).getImage().getUrl();
        Log.d("tag", "onBindViewHolder: "+imageUrl);

        //there is something wrong with the images url sent by the api,
        // they don't load successfully, despite the code works perfect with another url
        Picasso.with(mContext)
//                .load(mExploreSightsList.get(position).getImage().getUrl())
                .load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.drawable.ic_autorenew_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .resize( mExploreSightsList.get(position).getImage().getWidth(),
                        mExploreSightsList.get(position).getImage().getHeight())
                .into(holder.itemBinding.imageOfSight);

        holder.itemBinding.sightPrice.setText(mExploreSightsList.get(position).getPrice()+"$");
        holder.itemBinding.descriptionTv.setText(mExploreSightsList.get(position).getPlaceDescription());

    }

    @Override
    public int getItemCount() {
        return mExploreSightsList.size();
    }

    public interface ExploreListItemClickListener {
        void onExploreListItemClicked(int clickedItemIndex);
    }

    public class SightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SightItemBinding itemBinding;


        public SightViewHolder(SightItemBinding itemView) {
            super(itemView.getRoot());

            itemBinding = itemView;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            itemClickListener.onExploreListItemClicked(clickedPosition);
        }
    }

}
