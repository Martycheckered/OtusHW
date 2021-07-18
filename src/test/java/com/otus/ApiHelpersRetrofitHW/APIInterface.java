package com.otus.ApiHelpersRetrofitHW;

import com.otus.ApiHelpersRetrofitHW.pojo.CreateUserRequest;
import com.otus.ApiHelpersRetrofitHW.pojo.CreateUserResponse;
import com.otus.ApiHelpersRetrofitHW.pojo.SingleUserResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

public interface APIInterface {
    @GET ("2")
    Call<SingleUserResponse> getUserById();

    @POST("users")
    Call<CreateUserResponse> createUser (@Body CreateUserRequest body);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @PATCH ("users/{id}")
    Call<CreateUserResponse> updateUser (@Path("id") int id, @Body CreateUserRequest body );


}
