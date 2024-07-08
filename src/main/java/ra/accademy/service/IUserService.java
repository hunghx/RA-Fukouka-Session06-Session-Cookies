package ra.accademy.service;

import ra.accademy.dto.request.FormAddUser;
import ra.accademy.dto.request.FormLogin;
import ra.accademy.model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    void create(FormAddUser request);
    User login(FormLogin formLogin);
}
