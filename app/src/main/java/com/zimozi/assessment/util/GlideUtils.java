package com.zimozi.assessment.util;


import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Bertking on 2018/5/23.
 */
public class GlideUtils {
    public static void load(Activity activity,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        if (activity == null || activity.isFinishing()) return;
        Glide.with(activity)
                .load(url)
                .apply(options)
                .into(imageView);


    }

    /**
     * 加载 头像
     *
     * @param context
     * @param imageView
     */
//    public static void loadImage4OTC(Context context, String imgUrl, ImageView imageView) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.error(R.drawable.personal_headportrait_daytime).placeholder(R.drawable.personal_headportrait_daytime);
//        GlideUtils.load((Activity) context, getImageUrl(imgUrl), imageView, requestOptions);
//    }

    private static String getImageUrl(String url) {
        String imageUrl = "";
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (url.contains("https")) {
            imageUrl = url.replace("https", "http");
        } else {
            imageUrl = url;
        }
        return imageUrl;
    }

//    public static void loadImageHeader(Context context, String imgUrl, ImageView imageView) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.error(R.drawable.ic_default_head).placeholder(R.drawable.ic_default_head);
//        GlideUtils.load((Activity) context, getImageUrl(imgUrl), imageView, requestOptions);
//    }
}
