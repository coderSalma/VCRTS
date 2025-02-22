public class Account {
    private String username;
    private String password;
    private boolean type; // true - vehicle owner, false - job owner

    public Account(String username, String password, boolean type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAccountType() {
        return type;
    }
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
