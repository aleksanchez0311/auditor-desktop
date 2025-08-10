package cu.lacumbre.auditor.exceptions;

import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import java.util.ArrayList;
import java.util.Map;

public class PriceChangedException extends Exception {
    private ArrayList<Map.Entry<Product, Double[]>> diferentPricesList;

    public PriceChangedException(String message, ArrayList<Map.Entry<Product, Double[]>> diferentPricesList) {
        super(message);
        this.diferentPricesList = diferentPricesList;
    }

    public ArrayList<Map.Entry<Product, Double[]>> getDiferentPricesList() {
        return diferentPricesList;
    }
    
}
