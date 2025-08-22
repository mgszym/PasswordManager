public class Password {

    private String name;
    private String login;
    private String password;
    private String category;

    public Password(String name, String login, String password, String category) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
