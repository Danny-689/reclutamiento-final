package main.java.edu.kinal.reclutamiento.dto;

public class LoginDto {

    private String correo;
    private String password;

    public LoginDto(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }
}
