package com.example.news3.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.news3.News;
import com.example.news3.NewsAdapter;
import com.example.news3.NewsUtils;
import com.example.news3.R;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<List<News>> mText = Transformations.map(mIndex, new Function<Integer, List<News>>() {
        @Override
        public List<News> apply(Integer input) {

            System.out.println("请求了"+input);
            NewsUtils newsUtils=new NewsUtils();
            List<News> newsList=newsUtils.getlist(input);
            System.out.println(input+":新闻数："+newsList.size());
//            NewsAdapter newsAdapter=new NewsAdapter(newsList);
            return newsList;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List<News>> getText() {
        return mText;
    }
}