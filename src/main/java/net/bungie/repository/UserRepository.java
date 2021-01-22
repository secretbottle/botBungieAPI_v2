package net.bungie.repository;

import net.bungie.model.User;

import java.util.List;

public interface UserRepository {

    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(long id);

    //false if not found
    boolean deleteByTelegramID(long telegramId);

    // null if not found
    User get(long id);

    // null if not found
    User getByTelegramId(long id);

    List<User> getAll();

}
