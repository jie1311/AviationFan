package application.formValidators;

import javax.validation.constraints.NotNull;

public class AircraftForm {

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

    public AircraftForm() {
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
}
