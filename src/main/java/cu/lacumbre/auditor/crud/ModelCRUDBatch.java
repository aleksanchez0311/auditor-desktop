package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.model.Superclass;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ModelCRUDBatch {

    public Object get(int id);

    public void save(ArrayList<? extends Superclass> iDSuperclaseds) throws SQLException;

    public void update(ArrayList<? extends Superclass> iDSuperclaseds) throws SQLException;

    public void delete(ArrayList<? extends Superclass> iDSuperclaseds) throws SQLException;
}
