package AI_mail.demo.service;

import AI_mail.demo.entity.User;
import AI_mail.demo.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User registerUser(User user){
        Optional<User> existing=userRepository.findByEmail(user.getEmail());

        if(existing.isPresent()){
            throw new RuntimeException("user already exists");
        }
        return userRepository.save(user);
    }
}
