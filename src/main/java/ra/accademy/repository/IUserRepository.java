package ra.accademy.repository;

import ra.accademy.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();
    void create(User user);
    User findByUserName(String username);
}
