package com.GOMAN.gomankurir.KoneksiInternet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers(
            {

            "Content-Type:application/json",
            "Authorization:key=AAAAOxdYJCM:APA91bGmTVUJTQpcC4IdVaVEERHCnUET2AeVFpAWpkt3KD1_KNaP1At8mnKe_APfXmeoJY0B2RJd9E7US6po1Z3-QfkYkmlOV75_qTB-Fh41fUHMXwgrdWrbx9DMQbA4uoI0JOiWNfZA"
    }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
