package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.model.IDSuperClass;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ModelCRUDBatchID {

    public Object get(int id);

    public void saveWithId(ArrayList<? extends IDSuperClass> iDSuperclaseds) throws SQLException;

    public void saveWithId(Object object) throws SQLException;

    public void update(ArrayList<? extends IDSuperClass> iDSuperclaseds) throws SQLException;

    public void update(Object object) throws SQLException;

    public void delete(ArrayList<? extends IDSuperClass> iDSuperclaseds) throws SQLException;
}
