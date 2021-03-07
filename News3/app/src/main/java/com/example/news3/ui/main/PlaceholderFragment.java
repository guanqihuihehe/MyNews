package com.example.news3.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.news3.ContentActivity;
import com.example.news3.MainActivity;
//import com.example.news3.MyApp;
import com.example.news3.News;
import com.example.news3.NewsAdapter;
import com.example.news3.R;
import com.example.news3.RefreshableView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView templistview;
    private ViewGroup viewGroup;
    View temproot;
//    public Handler handler;
    private LayoutInflater layoutInflater;
    private List<News> fragment_newslist;

    private LifecycleOwner lifecycleOwner;

    private PageViewModel pageViewModel;

    private int page_index;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        page_index=index;
        System.out.println("pageindex:"+page_index);
        pageViewModel.setIndex(index);
    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        layoutInflater=inflater;
        viewGroup=container;
        View root = inflater.inflate(R.layout.fragment_main, container, false);
//        final TextView textView = root.findViewById(R.id.section_label);
//        final RecyclerView recyclerView=root.findViewById(R.id.newslist);
        temproot=root;
        final ListView listView=root.findViewById(R.id.newslist);
        templistview=listView;
        final MainActivity activity=(MainActivity) getActivity();
        final RefreshableView refreshableView = (RefreshableView) root.findViewById(R.id.refreshable_view);
        lifecycleOwner=this;
//        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        pageViewModel.getText().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> mylist) {
//                textView.setText(s);
                NewsAdapter newsAdapter=new NewsAdapter(mylist);
                listView.setAdapter(newsAdapter);
                fragment_newslist=mylist;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(activity, ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("position",String.valueOf(position));
                intent.putExtra("type",String.valueOf(fragment_newslist.get(position).getType()));
                intent.putExtra("uri",fragment_newslist.get(position).getUrl());
                startActivity(intent);
            }
        });



        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    Thread.sleep(500);
                    EventBus.getDefault().post(String.valueOf(page_index));
                    System.out.println("发送了"+"pageindex:"+page_index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },page_index );
        return root;
    }

}