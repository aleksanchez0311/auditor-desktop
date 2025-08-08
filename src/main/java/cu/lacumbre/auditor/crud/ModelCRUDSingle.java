package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.model.Superclass;
import java.sql.SQLException;

public interface ModelCRUDSingle {

    public Superclass get(int id);

    public void save(Object object) throws SQLException;

    public void update(Object object) throws SQLException;

    public void delete(Object object) throws SQLException;
}
