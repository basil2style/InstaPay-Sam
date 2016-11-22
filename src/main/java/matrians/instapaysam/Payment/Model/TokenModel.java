package matrians.instapaysam.Payment.Model;

/**
 * Created by basil on 2016-11-22.
 */

public class TokenModel {
    private String lastFourCard;
    private String tokenId;


    public String getLastFourCard() {
        return lastFourCard;
    }

    public void setLastFourCard(String lastFourCard) {
        this.lastFourCard = lastFourCard;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
