package application.formValidators;

import javax.validation.constraints.NotNull;

public class EditAirportForm {
    @NotNull
    private String iataCode;

    @NotNull
    private String city;

    @NotNull
    private String longitude;

    @NotNull
    private String latitude;

    @NotNull
    private String deletedId;

    @NotNull
    private String editId;

    public EditAirportForm() {
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
