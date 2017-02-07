package entities;

import org.springframework.data.annotation.Id;

public class Aircraft {

    @Id
    private String id;

    private String manufacturer;
    private String model;
    private String subModel;
    private int range;
    private int capacity;

    public Aircraft() {
    }

    public Aircraft(String manufacturer, String model, String subModel, int range, int capacity) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.subModel = subModel;
        this.range = range;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return String.format("%s %s-%s", manufacturer, model, subModel);
    }
}
