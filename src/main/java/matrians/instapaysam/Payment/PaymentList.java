package matrians.instapaysam.Payment;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrians.instapaysam.Payment.Model.TokenModel;
import matrians.instapaysam.R;

public class PaymentList extends AppCompatActivity {

    FloatingActionButton fab;
    SharedPreference sharedPreference;
    List<TokenModel> tokenModelList;
    ListView favoriteList;
    PaymentAdapter paymentAdapter;
    private static final String API_KEY = "sk_test_H7XJ6W6LIoUud0tMUm5mOSjy";

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
                        chargeCustomer();
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

    public void chargeCustomer() {



      /*  // Create a Customer
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("source", token);
        customerParams.put("description", "First Customer");
        customerParams.put("email","basiltalias@gmail.com");


        Customer customer = Customer.create(customerParams);
*/
        final Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 4000);
        chargeParams.put("currency", "usd");
        chargeParams.put("card", token.getId()); // obtained with Stripe.js
//        chargeParams.put("customer",customer);

        new AsyncTask<Void, Void, Void>() {

            Charge charge;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    com.stripe.Stripe.apiKey = API_KEY;
                    charge = Charge.create(chargeParams);

                    Log.i("IsCharged", charge.getCreated().toString());



                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    showAlert("Exception while charging the card!",
                            e.getLocalizedMessage());
                }
                return null;
            }

            protected void onPostExecute(Void result) {

                //  Toast.makeText(OnlinePaymentActivity.this, "Card Charged : " + charge.getCreated() + "\nPaid : " +charge.getPaid(), Toast.LENGTH_LONG).show();
            };

        }.execute();

    }


}
