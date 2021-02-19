package net.bungie.repository.impl;

import lombok.AllArgsConstructor;
import net.bungie.model.User;
import net.bungie.repository.CrudUserRepository;
import net.bungie.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final CrudUserRepository userRepository;

  @Override
  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public boolean delete(long id) {
    return userRepository.deleteById(id) != 0;
  }

  @Override
  public boolean deleteByTelegramID(long telegramId) {
    return userRepository.deleteByTelegram_id(telegramId) != 0;
  }

  @Override
  public User get(long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public User getByTelegramId(long id) {
    return userRepository.findByTelegram_id(id).orElse(null);
  }

  @Override
  public List<User> getAll() {
    List<User> users = new ArrayList<>();
    for (User u : userRepository.findAll()) {
      users.add(u);
    }
    return users;
  }
}
