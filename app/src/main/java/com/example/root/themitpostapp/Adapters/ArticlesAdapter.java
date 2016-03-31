package com.example.root.themitpostapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.Model.Article;
import com.example.root.themitpostapp.R;
import com.example.root.themitpostapp.Util.ArticleService;
import com.example.root.themitpostapp.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by root on 27/2/16.
 */
public class ArticlesAdapter extends ArrayAdapter<Article> {
    Context context;
    final private static String ASSET_PATH="/root/AndroidStudioProjects/mitpostApp/app/src/main/res/";
    ImageLoader imageLoader;
    LayoutInflater inflater;

    public ArticlesAdapter(Context context,ArrayList<Article> articles){
        super(context,0,articles);
        this.context=context;
        imageLoader=Singleton.getInstance(context).getImageLoader();

    }
    static class ViewHolder{
        private TextView articleTitle;
        private TextView articlePreview;
        private TextView articleDate;
        private NetworkImageView articlePreviewImage;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Article article=getItem(position);
        ViewHolder viewHolder;
        //getting articles for position
        if(inflater==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
        if(convertView==null){
            convertView= inflater.inflate(R.layout.list_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.articleTitle=(TextView)convertView.findViewById(R.id.list_item_article_title);
            viewHolder.articlePreview=(TextView)convertView.findViewById(R.id.list_item_text_view);
            viewHolder.articleDate=(TextView)convertView.findViewById(R.id.list_item_article_time);
            viewHolder.articlePreviewImage=(NetworkImageView)convertView.findViewById(R.id.list_item_image_view);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if(imageLoader==null){
            imageLoader=Singleton.getInstance(this.context).getImageLoader();
        }

        viewHolder.articlePreviewImage.setImageUrl(article.getArticleImageRef(),imageLoader);
        viewHolder.articlePreview.setText(Html.fromHtml(article.getArticlePreview()));
        viewHolder.articleTitle.setText(Html.fromHtml(article.getArticle_title()));
        viewHolder.articleDate.setText(ArticleService.formatArticleDate(article.getArticle_date()));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Article getItem(int position) {
        Log.i(Constant.TAG_ARTICLE_ADAPTER,super.getItem(position).getArticle_title());
        return super.getItem(position);
    }
}
