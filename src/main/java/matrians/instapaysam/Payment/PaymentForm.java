package matrians.instapaysam.Payment;

/**
 * Created by basil on 2016-11-17.
 
 /**
 Team Matrians
 **/
 
public interface PaymentForm {
    public String getCardNumber();
    public String getCvc();
    public Integer getExpMonth();
    public Integer getExpYear();
    public String getCurrency();
}
