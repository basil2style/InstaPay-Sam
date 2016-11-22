package matrians.instapaysam.Payment;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import java.util.HashMap;
import java.util.Map;

import matrians.instapaysam.R;
import matrians.instapaysam.TokenList;

public class PaymentPage extends FragmentActivity {

    public static final String PUBLISHABLE_KEY = "pk_test_2fSnV8n9LxbUr7khTyenCqOS";
    private static final String API_KEY = "sk_test_H7XJ6W6LIoUud0tMUm5mOSjy";

    private ProgressDialogFragment progressFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        progressFragment = ProgressDialogFragment.newInstance(R.string.progressMessage);

    }

    public void saveCreditCard(PaymentForm form) {

        Card card = new Card(
                form.getCardNumber(),
                form.getExpMonth(),
                form.getExpYear(),
                form.getCvc());
        card.setCurrency(form.getCurrency());


        boolean validation = card.validateCard();
        if (validation) {
            startProgress();
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            //getTokenList().addToList(token);
                            try {
                                chargeCustomer(token);
                            } catch (CardException e) {
                                e.printStackTrace();
                            } catch (APIException e) {
                                e.printStackTrace();
                            } catch (AuthenticationException e) {
                                e.printStackTrace();
                            } catch (InvalidRequestException e) {
                                e.printStackTrace();
                            } catch (APIConnectionException e) {
                                e.printStackTrace();
                            }
                            finishProgress();
                        }
                        public void onError(Exception error) {
                            handleError(error.getLocalizedMessage());
                            finishProgress();
                        }
                    });
        } else if (!card.validateNumber()) {
            handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            handleError("The CVC code that you entered is invalid");
        } else {
            handleError("The card details that you entered are invalid");
        }
    }

    private void startProgress() {
        progressFragment.show(getSupportFragmentManager(), "progress");
    }

    private void finishProgress() {
        progressFragment.dismiss();
    }

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
        fragment.show(getSupportFragmentManager(), "error");
    }
/*
    private TokenList getTokenList() {
        return (TokenList)(getSupportFragmentManager().findFragmentById(R.id.token_list));
    }
    */

    public void chargeCustomer(Token token) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {



        // Create a Customer
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("source", token);
        customerParams.put("description", "First Customer");
        customerParams.put("email","basiltalias@gmail.com");


        Customer customer = Customer.create(customerParams);

        final Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 4000);
        chargeParams.put("currency", "usd");
        //chargeParams.put("card", token.getId()); // obtained with Stripe.js
        chargeParams.put("customer",customer);

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

    private void showAlert(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
