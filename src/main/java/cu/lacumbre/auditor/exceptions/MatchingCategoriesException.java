package cu.lacumbre.auditor.view.utils;

import java.util.ArrayList;

public class MatchingCategoriesException extends Exception {

    public MatchingCategoriesException(String message) {
        super(message);
    }
    
    public MatchingCategoriesException() {
        this("No se puede establecer la correspondencia entre las categorias de la base de datos y las del programa");
    }

}
