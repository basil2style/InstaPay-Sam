package matrians.instapaysam.Payment;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import matrians.instapaysam.Payment.Model.TokenModel;

/**
 * Created by basil on 2016-11-22.
 */

public class SharedPreference {

    public static final String FAVORITES = "PRODUCT_APP";
    public static final String CARD = "Card_details";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, List<TokenModel> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(CARD, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }
    public void addFavorite(Context context, TokenModel product) {
        List<TokenModel> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<TokenModel>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, TokenModel product) {
        ArrayList<TokenModel> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }


    public ArrayList<TokenModel> getFavorites(Context context) {
        SharedPreferences settings;
        List<TokenModel> favorites;

        settings = context.getSharedPreferences(CARD,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            TokenModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    TokenModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<TokenModel>(favorites);
        } else
            return null;

        return (ArrayList<TokenModel>) favorites;
    }
}
