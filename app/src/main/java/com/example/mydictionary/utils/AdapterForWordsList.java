package com.example.mydictionary.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mydictionary.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterForWordsList extends ListAdapter<Word,AdapterForWordsList.ViewHolder> {

    public AdapterForWordsList() {
        super(Word.DIFF_CALLBACK);
        Log.d("MyTAG","adapter is create"+ Word.DIFF_CALLBACK.toString());
    }

    @NonNull
    @NotNull
    @Override
    public AdapterForWordsList.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view_layout,parent,false);
        Log.d("MyTAG","create holder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterForWordsList.ViewHolder holder, int position) {
        Word word = getItem(position);
        Log.d("MyTAG","Bind view");
        holder.bindTo(word);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView word;
        private final RatingBar ratingBar;
        private final TextView translation;
        private final AlternateLayoutWords auxTranslations;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Log.d("MyTAG","holder");
            word = itemView.findViewById(R.id.word);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            translation = itemView.findViewById(R.id.translation);
            auxTranslations = itemView.findViewById(R.id.aux_translations);

        }

        public void bindTo(Word item){
            Log.d("MyTAG","Bind");
            word.setText(item.getWord());
            ratingBar.setRating((float) item.getRating());
            translation.setText(item.getTranslation());
            ArrayList<String> listOfAuxTranslations =item.getAuxTranslationsList();
            if(listOfAuxTranslations!=null){
                for(String str: listOfAuxTranslations ){
                    auxTranslations.addWord(str);
                }
            }

        }
    }
}


