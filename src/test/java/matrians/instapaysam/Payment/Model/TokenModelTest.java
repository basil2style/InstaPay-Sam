package matrians.instapaysam.Payment.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by basil on 2016-11-25.
 */
public class TokenModelTest {
    @Test
    public void getLastFourCard() throws Exception {
        TokenModel tokenModel = new TokenModel("424242424242","133A332FFF");
        String CardNumber = tokenModel.getLastFourCard();
        String tokenId = tokenModel.getTokenId();

        assertEquals(CardNumber,tokenId);
    }

    @Test
    public void setLastFourCard() throws Exception {

    }

    @Test
    public void getTokenId() throws Exception {

    }

    @Test
    public void setTokenId() throws Exception {

    }

}