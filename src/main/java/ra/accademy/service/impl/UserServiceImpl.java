package ra.accademy.service.impl;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.accademy.dto.request.FormAddUser;
import ra.accademy.dto.request.FormLogin;
import ra.accademy.model.User;
import ra.accademy.repository.IUserRepository;
import ra.accademy.service.IUserService;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    // tiemf repository vào
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(FormAddUser request) {
        // chuyển đổi tu dto -> model
        User user = User.builder()
                .username(request.getUsername())
                .password(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt(5))) // mã hóa password : jbcrypt
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .build();
        userRepository.create(user); // thực thi jdbc

    }

    @Override
    public User login(FormLogin formLogin) {
       // tìm thông tin người dùng thông qua username
        // so khớp mật khẩu voi người dùng tìm đc
        User user = userRepository.findByUserName(formLogin.getUsername());
        if (user!=null){
            if (BCrypt.checkpw(formLogin.getPassword(),user.getPassword())){
                // kiểm tra mật kẩu có khớp
                return user;
            }
        }
        return null; // có thể ném ra ngoại lệ
    }
}
