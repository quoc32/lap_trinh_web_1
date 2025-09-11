package quoc.com.btweb.service;

import quoc.com.btweb.model.User;
import quoc.com.btweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User foundUser = userOpt.get();
            if (foundUser.getPassword().equals(password)) {
                return userOpt;
            }
        }

        return Optional.empty();
    }
}
