package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * BaseBean is an abstract JavaBean class that serves as the base for all
 * entity beans in the application. It is used to handle common audit and
 * identity fields shared across all database entity tables.
 *
 * This class contains common attributes such as id, createdBy, modifiedBy,
 * and timestamp information for record tracking.
 *
 * It implements {@link DropdownListBean} to support dropdown/UI list rendering
 * through key-value methods.
 *
 * @author Harshit Panchal
 */
public abstract class BaseBean implements DropdownListBean {

    /** Unique identifier for the entity record */
    protected long id;

    /** Username or identifier of the user who created the record */
    protected String createdBy;

    /** Username or identifier of the user who last modified the record */
    protected String modifiedBy;

    /** Timestamp indicating when the record was created */
    protected Timestamp createdDateTime;

    /** Timestamp indicating when the record was last modified */
    protected Timestamp modifiedDateTime;

    /**
     * Returns the unique ID of the entity record.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the entity record.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the username of the user who created the record.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the username of the user who created the record.
     *
     * @param createdBy the creator's username to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the username of the user who last modified the record.
     *
     * @return modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the username of the user who last modified the record.
     *
     * @param modifiedBy the modifier's username to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Returns the timestamp when the record was created.
     *
     * @return createdDateTime
     */
    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Sets the timestamp when the record was created.
     *
     * @param createdDataTime the creation timestamp to set
     */
    public void setCreatedDateTime(Timestamp createdDataTime) {
        this.createdDateTime = createdDataTime;
    }

    /**
     * Returns the timestamp when the record was last modified.
     *
     * @return modifiedDateTime
     */
    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    /**
     * Sets the timestamp when the record was last modified.
     *
     * @param modifiedDateTime the modification timestamp to set
     */
    public void setModifiedDateTime(Timestamp modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    /**
     * Returns the key of the entity record as a String.
     * Used by {@link DropdownListBean} to provide the key for dropdown/UI components.
     *
     * @return id converted to String
     */
    public String getKey() {
        return id + "";
    }
}