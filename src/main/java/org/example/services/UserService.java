package org.example.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserService {
    private static List<User> users;
    private static final Path USERS_PATH = FileSystemService.getPathToFile(new String[]{"config", "users.json"});

    public UserService() {
    }

    public static void loadUsersFromFile() throws IOException {
        if (!Files.exists(USERS_PATH, new LinkOption[0])) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        users = (List)objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        users.add(new User(username, encodePassword(username, password), role));
        persistUsers();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        Iterator var1 = users.iterator();

        User user;
        do {
            if (!var1.hasNext()) {
                return;
            }

            user = (User)var1.next();
        } while(!Objects.equals(username, user.getUsername()));

        throw new UsernameAlreadyExistsException(username);
    }

    private static void persistUsers() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), users);
        } catch (IOException var1) {
            throw new CouldNotWriteUsersException();
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return (new String(hashedPassword, StandardCharsets.UTF_8)).replace("\"", "");
    }

    private static MessageDigest getMessageDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md;
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
    }
}
