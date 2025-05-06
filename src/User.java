public class User {
    private String name, login, password, email;
    private int age;

    public User(String name, String login, String password, int age, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}