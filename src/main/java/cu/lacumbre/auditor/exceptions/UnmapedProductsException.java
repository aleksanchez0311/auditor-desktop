package cu.lacumbre.auditor.exceptions;

import java.util.ArrayList;

public class UnmapedProductsException extends Exception {
    private ArrayList<String> unmappedItemsList;

    public UnmapedProductsException(String message, ArrayList<String> unmappedItemsList) {
        super(message);
        this.unmappedItemsList = unmappedItemsList;
    }

    public ArrayList<String> getUnmappedItemsList() {
        return unmappedItemsList;
    }
    
}
