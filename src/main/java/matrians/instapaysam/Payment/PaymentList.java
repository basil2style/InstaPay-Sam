package matrians.instapaysam.Payment;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import matrians.instapaysam.Payment.Model.TokenModel;
import matrians.instapaysam.R;

public class PaymentList extends AppCompatActivity {

    FloatingActionButton fab;
    SharedPreference sharedPreference;
    List<TokenModel> tokenModelList;
    ListView favoriteList;
    PaymentAdapter paymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_list);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        sharedPreference = new SharedPreference();
        tokenModelList = sharedPreference.getFavorites(getApplicationContext());

        if (tokenModelList == null) {
            showAlert(getResources().getString(R.string.no_favoritesitems),
                    getResources().getString(R.string.no_favorites_msg));
        } else {

            if (tokenModelList.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }

            favoriteList = (ListView) findViewById(R.id.list_product);
            if (tokenModelList != null) {
                paymentAdapter = new PaymentAdapter(getApplicationContext(), tokenModelList);
                favoriteList.setAdapter(paymentAdapter);

                favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View arg1,
                                            int position, long arg3) {

                    }
                });

            }
        }

    }

    public void showAlert(String title, String message) {

            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();

    }
}
