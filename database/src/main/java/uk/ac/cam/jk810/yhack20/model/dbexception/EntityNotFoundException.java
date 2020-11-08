package uk.ac.cam.jk810.yhack20.model.dbexception;

public class EntityNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String entity;

    public EntityNotFoundException(String entity) {
        super("No entities of type "+entity+" were found");
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
