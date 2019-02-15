package com.prekju.fallennymous.prekjutelulasserver.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.prekju.fallennymous.prekjutelulasserver.Model.Request;
import com.prekju.fallennymous.prekjutelulasserver.Model.User;
import com.prekju.fallennymous.prekjutelulasserver.Remote.APIService;
import com.prekju.fallennymous.prekjutelulasserver.Remote.FCMRetrofitClient;
import com.prekju.fallennymous.prekjutelulasserver.Remote.IGeoCordinates;
import com.prekju.fallennymous.prekjutelulasserver.Remote.RetrofitClient;

import java.util.BitSet;

import retrofit2.Retrofit;

/**
 * Created by fallennymous on 14/08/2018.
 */


    public class Common {
        public static User currentUser;
        public static Request currentRequest;

        public static final String UPDATE = "perbaharui";
        public static final String DELETE = "hapus";

        // Request to upload image
        public static final int PICK_IMAGE_REQUEST = 71;

        public static final String baseUrl = "https://maps.googleapis.com";
        public static final String fcmUrl = "https://fcm.googleapis.com/";

        public static String convertCodeStatus(String status){
            switch (status) {
                case "0":
                    return "Pesanan diproses";
                case "1":
                    return "Pesanan dalam perjalanan";
                default:
                    return "pesanan diterima";
            }}

        public static IGeoCordinates getGeoCodeService(){
            return RetrofitClient.getClient(baseUrl).create(IGeoCordinates.class);
        }
       public static APIService getFCMClient(){
            return FCMRetrofitClient.getClient(fcmUrl).create(APIService.class);
    }

        public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth,int newHeigth)
        {
            Bitmap scaleBotmap = Bitmap.createBitmap(newWidth,newHeigth,Bitmap.Config.ARGB_8888);

            float scaleX = newWidth/(float)bitmap.getWidth();
            float scaleY = newHeigth/(float)bitmap.getHeight();
            float pivotX=0,pivotY=0;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

            Canvas canvas = new Canvas(scaleBotmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

            return scaleBotmap;
        }
    }


