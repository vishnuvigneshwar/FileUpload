package com.example.android.testfileupload.rest;

import com.example.android.testfileupload.data.Justsend;
import com.example.android.testfileupload.data.MailAttachment;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;


/**
 * Created by Triton-PC on 02-11-2016.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("driver_accept1.php")
    Call<Justsend> daccept1(@Field("name") String driver_id);

   /* @Multipart
    @POST("index.php")
    Call<Justsend> uploadImage(@Part MultipartBody.Part file);
*/
    @Multipart
    @POST("sendmail")
    Call<MailAttachment> uploadImage(@Part List<MultipartBody.Part> file, @Part("from_app") RequestBody from_app, @Part("to_app") RequestBody  to_app, @Part("from_id") RequestBody  from_id, @Part("to_id") RequestBody  to_id, @Part("child_id") RequestBody  child_id, @Part("title") RequestBody  title, @Part("body") RequestBody  body);


   /* @GET("servicelist.php")
    Call<Content> getservicelist();



    @FormUrlEncoded
    @POST("analytics.php")
    Call<Analytics> analyticsdate(@Field("userid") String userid, @Field("description") String description, @Field("serviceid") String serviceid,
                                  @Field("shopid") String shopid, @Field("viewedport") String viewedport,
                                  @Field("viewtime") String viewtime, @Field("clicked") String clicked,
                                  @Field("lat") String lat, @Field("lon") String lon);

*/
}
