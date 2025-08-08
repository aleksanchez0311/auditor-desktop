package cu.lacumbre.auditor.model;

import java.util.TreeMap;
import org.postgresql.util.PGobject;

public class AttendanceRecord extends PaymentRecord {

    public AttendanceRecord(int id, Worker worker, PGobject pGobject, CustomDocument document) {
        super(id, worker, pGobject, document);
    }

    public AttendanceRecord(Worker worker, TreeMap<Integer, Boolean> map, CustomDocument document) {
        super(worker, document);
    }

}
