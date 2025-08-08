package cu.lacumbre.auditor.model;

public class IDSuperClass extends Superclass {

    protected final int id;

    public IDSuperClass(int id) {
        super();
        this.id = id;
    }

    public IDSuperClass() {
        this(0);
    }

    public int getId() {
        return id;
    }
    
    public Object get(String field){
        return switch (field) {
            case "id" -> getId();
            default -> null;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IDSuperClass othObject){
            if(othObject.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }
    
    
    

}
