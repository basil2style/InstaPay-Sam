package matrians.instapaysam.Payment.Fragments;

import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrians.instapaysam.R;
import matrians.instapaysam.TokenList;

public class TokenListFragment extends ListFragment implements TokenList{

/**
 Team Matrians
 **/
 
    List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
    SimpleAdapter adapter;

    @Override
    public void onViewCreated(android.view.View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SimpleAdapter(getActivity(),
                listItems,
                R.layout.list_item_layout,
                new String[]{"last4", "tokenId"},
                new int[]{R.id.last4, R.id.tokenId});
        setListAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    @Override
    public void addToList(Token token) {
        String endingIn = getResources().getString(R.string.endingIn);
        Map<String, String> map = new HashMap<String, String>();
        map.put("last4", endingIn + " " + token.getCard().getLast4());
        map.put("tokenId", token.getId());
        listItems.add(map);
        adapter.notifyDataSetChanged();
    }

}
