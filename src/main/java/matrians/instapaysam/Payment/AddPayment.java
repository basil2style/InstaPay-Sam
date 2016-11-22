package matrians.instapaysam.Payment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.view.CardForm;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

import matrians.instapaysam.Payment.Model.TokenModel;
import matrians.instapaysam.R;

import static matrians.instapaysam.Payment.PaymentPage.PUBLISHABLE_KEY;

public class AddPayment extends AppCompatActivity {

    private CardForm cardForm;
    private Button save_button;
    SharedPreference sharedPreference;
    List<TokenModel> tokenModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        sharedPreference  = new SharedPreference();


        cardForm = (CardForm) findViewById(R.id.bt_card_form);
        save_button = (Button) findViewById(R.id.save_card);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel("Save")
                .setup(this);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    getStripeToken(cardForm.getCardNumber(), cardForm.getExpirationMonth(), cardForm.getExpirationYear(), cardForm.getCvv());

                } else {
                    cardForm.validate();
                }
            }
        });
        cardForm.setOnCardFormSubmitListener(new OnCardFormSubmitListener() {
            @Override
            public void onCardFormSubmit() {
                if (cardForm.isValid()) {
                    getStripeToken(cardForm.getCardNumber(), cardForm.getExpirationMonth(), cardForm.getExpirationYear(), cardForm.getCvv());
                } else {
                    cardForm.validate();
                }
            }
        });
    }

    private void getStripeToken(String cardNumber, String expirationMonth, String expirationYear, String cvv) {
        Card card = new Card(cardNumber, Integer.parseInt(expirationMonth), Integer.parseInt(expirationYear), cvv);
        Stripe stripe = null;
        //int cardNum = Integer.valueOf(cardNumber);
        //final int lastfour = cardNum % 10000;
        final String lastFour = cardNumber.substring(cardNumber.length()-4);

        try {
            stripe = new Stripe(PUBLISHABLE_KEY);
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {

                            // sendToServer(token.getId());
                            //sharedPreference.addFavorite(getApplicationContext(),);

                            TokenModel tokenModel = new TokenModel(lastFour,token.getId());

                            tokenModelList = new ArrayList<TokenModel>();
                            tokenModelList.add(tokenModel);

                            sharedPreference.addFavorite(getApplicationContext(),tokenModel);
                            Toast.makeText(AddPayment.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddPayment.this,PaymentList.class);
                            startActivity(intent);

                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(AddPayment.this, error.getLocalizedMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
