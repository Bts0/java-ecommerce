import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;
import java.util.regex.*;
import java.security.SecureRandom;
import java.security.MessageDigest;

public class User {
    private UUID uuid;
    private String username;
    private String role;
    private String name;
    private String email;
    private String last_login;
    private UUID auth_token;
    private boolean authenticated;

    private String saltedPassword;

    private String salt;

    private void setSalt(String salt) {
        this.salt = salt;
    }
    private String getSalt() {
        if(salt == null) {
            SecureRandom sr;
            try {
                sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            }

            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            setSalt(salt.toString());
            return salt.toString();
        }
        return salt;
    }

    private static String getSecurePassword(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return generatedPassword;
    }

    public void setSaltedPassword(String password) {
        String salt = getSalt();
        saltedPassword = getSecurePassword(password, salt);
    }

    public String getSaltedPassword() {
        return saltedPassword;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID u) {
        uuid = u;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String u) {
        if (validUsernameCheck(u)) {
            username = u;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String r) {
        if (validRoleCheck(r)) {
            role = r;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        if (validNameCheck(n)) {
            name = n;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        if (validEmailCheck(e)) {
            email = e;
        }
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String ll) {
        last_login = ll;
    }

    public UUID getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(UUID u) {
        auth_token = u;
    }

    private boolean validUsernameCheck(String u) {
        // If a valid username, return true.
        String usernameRegex = "^[a-z]{3,15}[0-9]{0,3}";
        if (u == null) {
            return false;
        }
        else if (u.length() < 3 || u.length() > 18) {
            return false;
        }

        Pattern p  = Pattern.compile(usernameRegex);
        return p.matcher(u).matches();
    }

    private boolean validRoleCheck(String r) {
        // If a valid role, return true.
        List<String> roles = Arrays.asList("Administrator", "Seller", "Customer");
        return roles.contains(r);
    }

    private boolean validNameCheck(String n) {
        // If a valid name, return true.
        String nameRegex = "^[A-Z]{1}[a-z]{2,15}$";
        if (n == null) {
            return false;
        }
        else if (n.length() < 3 || n.length() > 15) {
            return false;
        }

        Pattern p = Pattern.compile(nameRegex);
        return p.matcher(n).matches();
    }

    private boolean validEmailCheck(String e) {
        // If a valid email, return true.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        if (e == null) {
            return false;
        }

        return p.matcher(e).matches();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    private void setAuthenticated(boolean a) {
        authenticated = a;
    }
}
