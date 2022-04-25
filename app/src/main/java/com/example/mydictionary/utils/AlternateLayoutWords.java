package com.example.mydictionary.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mydictionary.R;

import java.util.ArrayList;
import java.util.List;

public class AlternateLayoutWords extends LinearLayout implements View.OnClickListener {

    private double freeSpace;
    private ArrayList<LinearLayout> lineList;
    private ArrayList<TextView> wordsListAsView;
    private List<String> wordsListAsText;
    private ViewWidth viewWidth;
    private Context context;
    private MutableLiveData<String> data;
    private ContextThemeWrapper contextThemeWrapper;
    private boolean flagClickable = true;

    public AlternateLayoutWords(Context context) {
        super(context);
        init(context);

    }

    public AlternateLayoutWords(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void setClickable(boolean flag){
        this.flagClickable = flag;
    }
    public void addWord(String word ){
        TextView text = new TextView(contextThemeWrapper);
        text.setText(word);
        text.setOnClickListener(this);
        text.setClickable(flagClickable);
        text.setBackground(getResources().getDrawable(R.drawable.oval));
        wordsListAsView.add(text);
        wordsListAsText.add(word);
        updateView();
    }
    public void removeWord(String text){
        for(int i = 0; i< wordsListAsView.size(); i++){
              TextView textView =  wordsListAsView.get(i);
              String str = textView.getText().toString();
              if(str.equals(text)){
                  wordsListAsView.remove(i);
                  wordsListAsText.remove(i);
              }
        }
            updateView();
    }

    public LiveData<String> getText(){
        return data;
    }
    public List<String> getListOfViewAsText(){
        return wordsListAsText;
    }
    public void clearList(){
        wordsListAsText.clear();
        wordsListAsView.clear();
        updateView();
    }

    private void setText(String text){
        data.setValue(text);
    }

    private void init(Context context){
        this.context = context;
        viewWidth = new ViewWidth(context);
        wordsListAsView = new ArrayList<>();
        wordsListAsText = new ArrayList<>();
        lineList = new ArrayList<>();
        data = new MutableLiveData<>();
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.alternate_layout,this);
        contextThemeWrapper = new ContextThemeWrapper(this.getContext(),R.style.secondary_text_style);
        lineList.add(findViewById(R.id.line_1));
        lineList.add(findViewById(R.id.line_2));
        lineList.add(findViewById(R.id.line_3));
        lineList.add(findViewById(R.id.line_4));
        lineList.add(findViewById(R.id.line_5));

    }
    private void updateView(){
        int i = 0;
        freeSpace = 90;
        for(LinearLayout line: lineList){
            line.removeAllViews();
        }
            double width = 0;
            for (TextView text : wordsListAsView) {
                if (!(freeSpace > (width = viewWidth.getMeasureWidthPercent(text)))) {
                    if (i < lineList.size() - 1) {
                        i++;
                        freeSpace = 90;
                    } else return;
                }
                lineList.get(i).addView(text);
                freeSpace -= width;

            }
    }

    @Override
    public void onClick(View view) {
       if(view instanceof TextView){
           TextView textView = (TextView) view;
           String text = textView.getText().toString();
           setText(text);
           removeWord(text);
       }
    }
}
