package application.formValidators;

import javax.validation.constraints.NotNull;

public class EditUserForm {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String password2;

    @NotNull
    private String deletedId;

    public EditUserForm() {
    }

    public String getDeletedId() {
        return deletedId;
    }

    public void setDeletedId(String deletedId) {
        this.deletedId = deletedId;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
