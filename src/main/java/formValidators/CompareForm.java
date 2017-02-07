package formValidators;

import javax.validation.constraints.NotNull;

public class CompareForm {

    @NotNull
    private String orgAirportId;

    @NotNull
    private String desAirportId;

    @NotNull
    private String aircraftId;;

    public CompareForm() {
    }

    public String getOrgAirportId() {
        return orgAirportId;
    }

    public void setOrgAirportId(String orgAirportId) {
        this.orgAirportId = orgAirportId;
    }

    public String getDesAirportId() {
        return desAirportId;
    }

    public void setDesAirportId(String desAirportId) {
        this.desAirportId = desAirportId;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }
}
