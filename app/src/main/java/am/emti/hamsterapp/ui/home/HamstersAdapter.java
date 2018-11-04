package am.emti.hamsterapp.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import am.emti.hamsterapp.R;
import am.emti.hamsterapp.model.Hamster;

/**
 * Created by Tigran Mkhitaryan on 26.10.2018.
 */

public class HamstersAdapter extends RecyclerView.Adapter<HamstersAdapter.ViewHolder> {
    private List<Hamster> items;
    private List<Hamster> filteredItems;
    private HamstersItemListners mHamstersItemListners;
    private boolean mIsFromDb;

    public HamstersAdapter(HamstersItemListners hamstersItemListners) {
        mHamstersItemListners = hamstersItemListners;
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ViewHolder(inflater.inflate(R.layout.card_hamster, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(filteredItems.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void setData(List<Hamster> hamsters) {
        items.addAll(hamsters);
        filteredItems.addAll(hamsters);
        notifyDataSetChanged();
    }


    public void setData(List<Hamster> hamsters , boolean fromDb) {
        mIsFromDb = fromDb;
        items.addAll(hamsters);
        filteredItems.addAll(hamsters);
        notifyDataSetChanged();
    }

    public void filterData(String query) {
        filteredItems.clear();
        for (Hamster h : items) {
            if (h.getTitle().toLowerCase().contains(query.toLowerCase()))
                filteredItems.add(h);
        }
        notifyDataSetChanged();
    }

    private Bitmap convertByteArrayToBitmap(byte[] bitmapdata){
      return   BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    public interface HamstersItemListners {
        void onShareButtonClicked(Hamster hamster, ImageView hamsterImage);

        void onItemClicked(Hamster hamster, ImageView hamsterImage);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView hamsterImage;
        private TextView titleLabel;
        private TextView descriptionLabel;
        private ImageButton shareAction;
        private View rootView;


        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            hamsterImage = itemView.findViewById(R.id.card_hamster_image);
            titleLabel = itemView.findViewById(R.id.card_hamster_title);
            descriptionLabel = itemView.findViewById(R.id.card_hamster_description);
            shareAction = itemView.findViewById(R.id.card_hamster_share_action);

        }

        void bindView(Hamster hamster) {
            titleLabel.setText(hamster.getTitle());
            descriptionLabel.setText(hamster.getDescription());
            if(mIsFromDb){
                if(hamster.getImageArray() != null)
                hamsterImage.setImageBitmap(convertByteArrayToBitmap(hamster.getImageArray()));
            }else {
                Glide.with(itemView.getContext())
                        .load(hamster.getImageUrl())
                        .into(hamsterImage);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHamstersItemListners != null) {
                        mHamstersItemListners.onItemClicked(hamster, hamsterImage);
                    }

                }
            });

            shareAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mHamstersItemListners != null) {
                        mHamstersItemListners.onShareButtonClicked(hamster, hamsterImage);
                    }
                }
            });

        }


    }
}
