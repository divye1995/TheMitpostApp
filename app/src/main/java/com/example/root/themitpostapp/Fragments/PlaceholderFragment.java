package com.example.root.themitpostapp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.root.themitpostapp.Adapters.ArticlesAdapter;
import com.example.root.themitpostapp.Application.ArticleWebViewActivity2;
import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.Model.Article;
import com.example.root.themitpostapp.R;
import com.example.root.themitpostapp.Util.ArticleService;
import com.example.root.themitpostapp.Util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    //TODO:implement recyclerview in the fragment
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private ArrayList<Article> articles;
    private ArticlesAdapter adapter;
    ProgressDialog progressDialog;
    Context context;
    String category;
    SwipeRefreshLayout swipeRefreshLayout;
    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber,String sectionName) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_NAME,sectionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();
        articles=new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView=(ListView)rootView.findViewById(R.id.listViewMainFragment);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.listViewSwipeRefresh);

        Bundle args=getArguments();

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage(Constant.PROGRESS_PLEASE_WAIT);
        progressDialog.setCancelable(false);

        category=args.getString(ARG_SECTION_NAME);

        //ArticleAdapter-CustomAdapter
        //TODO : Add Method to retrieve the articleList for CategoryName
        adapter=new ArticlesAdapter(context,articles);
        listView.setAdapter(adapter);
        if(Singleton.getInstance(getActivity()).getRequestQueue().getCache().get(ArticleService.getRequestURL(category))!=null){
            Log.i(Constant.TAG_MAIN_ACTIVITY, "***PlacementFragment caching***");
         }
        else
            Log.i(Constant.TAG_MAIN_ACTIVITY,"***PlacementFragment caching***");

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                Singleton.getInstance(getActivity()).addToRequestQueue(makeJsonRequest(category));
            }
        });

        Toast.makeText(context,Constant.REFRESH_HELP,Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i(Constant.TAG_MAIN_ACTIVITY, adapter.getItem(position).getArticle_title());
                Article article = adapter.getItem(position);
                if (article != null && !article.getArticle_url().equals(""))
                    startArticleReader(article.getArticle_url());
                else
                    Toast.makeText(context, Constant.ERROR_URL_UNAVAILABLE, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


    public void startArticleReader(String URL){
        Intent intent=new Intent(context, ArticleWebViewActivity2.class);
        intent.putExtra(Constant.JSON_OBJECT_URL,URL);
        intent.putExtra(Constant.CATEGORY_KEY,category);
        startActivity(intent);
    }
    public void showPDialog(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    public void hidePDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    public JsonObjectRequest makeJsonRequest(String category){
        //showPDialog();
        swipeRefreshLayout.setRefreshing(true);
        Log.i(Constant.TAG_MAIN_ACTIVITY, "Requesting...");
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,ArticleService.getRequestURL(category), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray(Constant.JSON_RESPONSE_KEY_POSTS);
                            for (int i=0;i<jsonArray.length();i++){
                                //individual objects
                                JSONObject object=jsonArray.getJSONObject(i);
                                Article article=new Article(object);
                                //check for duplication of articles
                                articles.add(article);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.e(Constant.TAG_MAIN_ACTIVITY,"****Error in Volley Request****");
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        //hidePDialog();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Constant.TAG_MAIN_ACTIVITY, "***Error in Volley Network Request***");
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context,Constant.ERROR_UNABLE_TO_REFRESH,Toast.LENGTH_SHORT).show();
                        //hidePDialog();

                    }
                }
        );
        request.setShouldCache(true);
        request.setTag(Constant.TAG_MAIN_ACTIVITY);
        request.setRetryPolicy(new DefaultRetryPolicy(
                Constant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));


        return request;
    }

    @Override
    public void onRefresh() {
        articles.clear();
        Singleton.getInstance(getActivity()).addToRequestQueue(makeJsonRequest(category));
    }

}
