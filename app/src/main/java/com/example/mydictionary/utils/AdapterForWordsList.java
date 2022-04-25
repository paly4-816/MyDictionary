package com.example.mydictionary.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mydictionary.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterForWordsList extends ListAdapter<Word,AdapterForWordsList.ViewHolder> {

    int selectionCounter = 0;
    MutableLiveData<List<Word>> liveData;
    List<Word> listOfCheckedWord = new ArrayList<>();

    public AdapterForWordsList() {
        super(Word.DIFF_CALLBACK);
        liveData = new MutableLiveData<>();
    }


    @NonNull
    @NotNull
    @Override
    public AdapterForWordsList.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterForWordsList.ViewHolder holder, int position) {
        Word word = getItem(position);
        holder.bindTo(word);


    }

    public LiveData<List<Word>> getSelectedWords() {
        return liveData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView word;
        private final RatingBar ratingBar;
        private final TextView translation;
        private final AlternateLayoutWords auxTranslations;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            translation = itemView.findViewById(R.id.translation);
            checkBox = itemView.findViewById(R.id.checkbox);
            auxTranslations = itemView.findViewById(R.id.aux_translations);
            auxTranslations.setClickable(false);
        }

        private void bindTo(Word item) {
            word.setText(item.getWord());
            ratingBar.setRating((float) item.getRating());
            translation.setText(item.getTranslation());
            List<String> words = item.getAuxTranslationsList();
            Log.d("MyTAG",item.getId()+ words.toString());
            auxTranslations.clearList();
            Log.d("MyTAG",item.getId()+ auxTranslations.getListOfViewAsText().toString());
            for (String str : words) {
                if (!str.equals("")) {
                    auxTranslations.addWord(str);
                    Log.d("MyTAG",str);
                }
            }
            setVisibleCheckBox(item);

            itemView.setOnLongClickListener(view -> {
                if(selectionCounter==0) {
                    check(item);
                }
                    return true;
            });
            itemView.setOnClickListener(view -> {
                if(selectionCounter==0){
                showDetailsAboutWord();
                } else {
                if(checkBox.isChecked()){
                    uncheck(item);
                } else {
                    check(item);
                }
                }
            });
        }
        private void setVisibleCheckBox(Word item){
            if(item.getMark()==Word.IS_MARKED){
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(true);
            } else {
                checkBox.setVisibility(View.INVISIBLE);
                checkBox.setChecked(false);
            }
        }
        private void check (Word item){
            selectionCounter++;
            item.setMark(Word.IS_MARKED);
            setVisibleCheckBox(item);
            listOfCheckedWord.add(item);
            liveData.setValue(listOfCheckedWord);
        }
        private void uncheck(Word item){
            selectionCounter--;
            item.setMark(Word.IS_NOT_MARKED);
            setVisibleCheckBox(item);
            listOfCheckedWord.remove(item);
            liveData.setValue(listOfCheckedWord);
        }
        private void showDetailsAboutWord(){

        }
    }
}


