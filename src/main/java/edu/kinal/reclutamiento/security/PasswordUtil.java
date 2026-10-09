
package main.java.edu.kinal.reclutamiento.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encriptar(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verificar(
            String password, String hash) {

        return BCrypt.checkpw(password, hash);
    }
}