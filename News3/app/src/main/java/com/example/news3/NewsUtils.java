package com.example.news3;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsUtils {

    public static int index;
    public static Context maincontext;
    public static List<News> UtilsNewsList = new ArrayList<>();
    public static int state=0;

    private static final int  ITEM_SOCIETY= 1;
    private static final int  ITEM_COUNTY= 2;
    private static final int  ITEM_INTERNATION= 3;
    private static final int  ITEM_FUN= 4;
    private static final int  ITEM_SPORT= 5;


    public List<News> getlist (int ind)
    {
        state=0;
        System.out.println("state:"+state);
        index=ind;
        requestNew(index);

        while(true)
        {
            System.out.println("在循环");
            if(state!=0)
            {
                break;
            }
        }
        System.out.println("返回"+index);
        for(int i=0;i<UtilsNewsList.size();i++)
        {
            System.out.println(i+":"+UtilsNewsList.get(i).title);
        }
        return UtilsNewsList;
    }

    public static NewsList parseJsonWithGson(final String requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText, NewsList.class);
    }

    /**
     * 请求处理数据
     */
    public void requestNew(int itemName){
        // 根据返回到的 URL 链接进行申请和返回数据
        String address = response(itemName);    // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("失败1");
                state=-1;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsList newlist = parseJsonWithGson(responseText);
                final int code = newlist.code;
                final String msg = newlist.msg;
                if (code == 200){
                    System.out.println("成功");
                    UtilsNewsList=newlist.newsList;
                    state=1;
                    System.out.println("state:"+state);
                }else{
                    System.out.println("失败2");
                    state=-2;
                }
            }
        });


    }

    /**
     * 输入不同的类型选项，返回对应的 URL 链接
     */
    private String response(int itemName){
        String address = "http://api.tianapi.com/social/index?key=9f186879634a4d5f73d9dc72c2fb2a8a&num=10";
        switch(itemName){
            case ITEM_SOCIETY:
                break;
            case ITEM_COUNTY:
                address = address.replaceAll("social","guonei");

                break;
            case ITEM_INTERNATION:
                address = address.replaceAll("social","world");

                break;
            case ITEM_FUN:
                address = address.replaceAll("social","huabian");

                break;
            case ITEM_SPORT:
                address = address.replaceAll("social","tiyu");

                break;

            default:
        }
        return address;
    }

    /**
     * 通过 actionbar.getTitle() 的参数，返回对应的 ItemName
     */
    private int parseString(String text){
        if (text.equals("社会新闻")){
            return ITEM_SOCIETY;
        }
        if (text.equals("国内新闻")){
            return ITEM_COUNTY;
        }
        if (text.equals("国际新闻")){
            return ITEM_INTERNATION;
        }
        if (text.equals("娱乐新闻")){
            return ITEM_FUN;
        }
        if (text.equals("体育新闻")){
            return ITEM_SPORT;
        }

        return ITEM_SOCIETY;
    }


    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        NewsUtils.index = index;
    }

    public static Context getMaincontext() {
        return maincontext;
    }

    public static void setMaincontext(Context context) {
        NewsUtils.maincontext = context;
    }
}
