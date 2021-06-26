package engine.serviece;

import engine.model.User;
import engine.repositiry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public boolean save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> foundUser = userRepository.findById(user.getEmail());
        if (foundUser.isPresent()) { return false; }

        userRepository.save(user);
        return true;
    }

    public Optional<User> findUser(String email) {
        return userRepository.findById(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
