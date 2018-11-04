package am.emti.hamsterapp.api;

import com.google.gson.JsonObject;

import java.util.List;

import am.emti.hamsterapp.model.Hamster;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
     @GET("test3")
     Single<List<Hamster>> loadHamsters();

}
