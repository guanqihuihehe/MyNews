package com.example.news3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.news3.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.ThreadMode;

import static com.example.news3.NewsUtils.maincontext;

public class MainActivity extends AppCompatActivity {

//    public MyHandler myHandler;
    private List<Fragment> mFragments;

    public SectionsPagerAdapter sectionsPagerAdapter;
    int current_page_index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maincontext=this;
        if	(ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   )	{
            ActivityCompat.requestPermissions(MainActivity.this,	new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE	},	1);
        }
        if(ContextCompat.checkSelfPermission (MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED   )	{
            ActivityCompat.requestPermissions(MainActivity.this,	new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE	},	1);
        }
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("社会"));
        tabs.addTab(tabs.newTab().setText("国内"));
        tabs.addTab(tabs.newTab().setText("国际"));
        tabs.addTab(tabs.newTab().setText("娱乐"));
        tabs.addTab(tabs.newTab().setText("体育"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 被选中的时候
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                current_page_index= tab.getPosition();
                viewPager.setCurrentItem(current_page_index);
                System.out.println("nowpage:"+current_page_index);
            }
            // 没有被选中的时候
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            // 重现被选中的时候
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        sectionsPagerAdapter.notifyDataSetChanged();
//        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sectionsPagerAdapter.fragmentsUpdateFlag[current_page_index] = true;
                sectionsPagerAdapter.notifyDataSetChanged();
            }
        });

        //注册订阅者
        EventBus.getDefault().register(this);


    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onEventMainThread(String tex) {
        System.out.println("eventindex:"+Integer.valueOf(tex));
            refreshfragment(Integer.valueOf(tex));
            // 可以做一些其他业务,比如获取网络连接,刷新页面...
    }
//    public final class MyHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            System.out.println("收到了");
//            if(msg.what==1)
//            {
//                System.out.println("更新UI");
//                System.out.println("size:"+getSupportFragmentManager().getFragments().size());
//                NewsUtils newsUtils=new NewsUtils();
//                List<News> templist=newsUtils.getlist(3);
//                NewsAdapter newsAdapter=new NewsAdapter(templist);

//            }
//        }
//    }

    public void refreshfragment(int index)
    {
        index=index-1;
        System.out.println("执行了刷新:"+index);
        sectionsPagerAdapter.fragmentsUpdateFlag[index] = true;
        sectionsPagerAdapter.notifyDataSetChanged();
    }


    public Handler myhandler=new Handler() {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        System.out.println("收到了");
        if (msg.what == 1) {
            System.out.println("更新UI");

        }
    }
    };
}

