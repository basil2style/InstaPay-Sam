package matrians.instapaysam.Payment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import matrians.instapaysam.Payment.Model.TokenModel;
import matrians.instapaysam.R;

/**
 * Created by basil on 2016-11-22.
 */

public class PaymentAdapter extends ArrayAdapter {
    private Context context;
    List<TokenModel> tokenModelList;
    SharedPreference sharedPreference;



    public PaymentAdapter(Context context, List<TokenModel> tokenModelList) {
        super(context, R.layout.activity_temp_list, tokenModelList);
        this.context = context;
        this.tokenModelList = tokenModelList;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
        TextView cardNumber;
        TextView cardId;
    }

    @Override
    public int getCount() {
        return tokenModelList.size();
    }

    @Override
    public TokenModel getItem(int position) {
        return tokenModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.payment_items, null);
            holder = new ViewHolder();
            holder.cardNumber = (TextView) convertView
                    .findViewById(R.id.cardNum);
            holder.cardId = (TextView) convertView
                    .findViewById(R.id.tokenId);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TokenModel product = (TokenModel) getItem(position);
        holder.cardNumber.setText(product.getLastFourCard());
        holder.cardId.setText(product.getTokenId());



        return convertView;
    }

}
