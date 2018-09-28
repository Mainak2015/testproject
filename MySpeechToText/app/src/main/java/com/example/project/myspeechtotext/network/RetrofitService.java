package com.example.project.myspeechtotext.network;


import com.example.project.myspeechtotext.ui.dashboard.entity.response.DictionaryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;


public interface RetrofitService {

   @GET("dictionary-v2.json")
   Observable<DictionaryResponse> getDictionaryRawdata();

}
