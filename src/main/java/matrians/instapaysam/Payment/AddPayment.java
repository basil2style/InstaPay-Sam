package matrians.instapaysam.Payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.braintreepayments.cardform.view.CardForm;

import matrians.instapaysam.R;

public class AddPayment extends AppCompatActivity {

    private CardForm cardForm;
    private Button checkout_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        cardForm = (CardForm) findViewById(R.id.bt_card_form);
        checkout_button = (Button) findViewById(R.id.checkout_card);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel("Save")
                .setup(this);

    }
}
