import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UserNotFoundException, AccessDeniedException, IOException {
        Scanner scanner = new Scanner(System.in);

        // Загрузка конфигурации
        Properties config = loadConfig("config.properties");

        System.out.println("Введите логин");
        String login = scanner.nextLine();
        System.out.println("Введите пароль");
        String password = scanner.nextLine();

        User user = getUserByLoginAndPassword(config, login, password);
        validateUser(user, config);

        System.out.println("Доступ предоставлен.");

        scanner.close();
    }

    public static Properties loadConfig(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        return properties;
    }

    public static User[] getUsers(Properties config) {
        String[] userStrings = config.getProperty("users").split(";");
        User[] users = new User[userStrings.length];

        for (int i = 0; i < userStrings.length; i++) {
            String[] userData = userStrings[i].split(",");
            String name = userData[0];
            String login = userData[1];
            String password = userData[2];
            int age = Integer.parseInt(userData[3]);
            String email = userData[4];
            users[i] = new User(name, login, password, age, email);
        }

        return users;
    }

    public static User getUserByLoginAndPassword(Properties config, String login, String password) throws UserNotFoundException {
        User[] users = getUsers(config);
        for (User user : users) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                System.out.println("Введены данные пользователя:");
                System.out.println("Имя: " + user.getName() + " Почта: " + user.getEmail());
                return user;
            }
        }
        throw new UserNotFoundException("Пользователь не зарегистрирован.");
    }

    public static void validateUser(User user, Properties config) throws AccessDeniedException {
        int minAccessAge = Integer.parseInt(config.getProperty("minAccessAge"));
        if (user.getAge() < minAccessAge) {
            throw new AccessDeniedException("В доступе отказано. Возрастное ограничение.");
        }
    }
}