package formValidators;

import javax.validation.constraints.NotNull;

public class EditAircraftForm {

    @NotNull
    private String manufacturer;

    @NotNull
    private String model;

    @NotNull
    private String subModel;

    @NotNull
    private String range;

    @NotNull
    private String capacity;

    @NotNull
    private String deletedId;

    @NotNull
    private String editId;

    public EditAircraftForm() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSubModel() {
        return subModel;
    }

    public void setSubModel(String subModel) {
        this.subModel = subModel;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDeletedId() {
        return deletedId;
    }

    public void setDeletedId(String deletedId) {
        this.deletedId = deletedId;
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }
}
