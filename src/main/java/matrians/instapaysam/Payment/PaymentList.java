package matrians.instapaysam.Payment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import matrians.instapaysam.R;

public class PaymentList extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        fab = (FloatingActionButton) findViewById(R.id.fab);


    }
}
