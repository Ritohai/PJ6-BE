package backendshop.service.impl.user;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.request.UserRequestLogin;
import backendshop.model.dto.request.UserRequestRegister;
import backendshop.model.dto.response.JwtReponse;
import backendshop.model.entity.Role;
import backendshop.model.entity.Users;
import backendshop.repository.RoleRepository;
import backendshop.repository.UserRepository;
import backendshop.security.jwt.JwtProvider;
import backendshop.security.userPrincipal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public JwtReponse handleLoging(UserRequestLogin userRequestLogin) throws CustomersException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequestLogin.getUsername(), userRequestLogin.getPassword()));
        } catch (AuthenticationException ax) {
            throw new CustomersException("Tài khoản hoặc mật khẩu sai.");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUsers().getStatus()) {
            throw new CustomersException("Tài khoản bị khóa.");
        }
        String token = jwtProvider.generateToken(userPrincipal);
        JwtReponse jwtReponse = JwtReponse.builder()
                .users(userPrincipal.getUsers())
                .token(token)
                .roles(userPrincipal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toSet()))
                .build();
        return jwtReponse;
    }

    @Override
    public String hanldeRegister(UserRequestRegister userRequestRegister) throws CustomersException {
        if (userRepository.existsUsersByUsername(userRequestRegister.getUsername())) {
            throw new CustomersException("Tài khoản đã tồn tại.");
        }
        if (userRepository.existsUsersByEmail(userRequestRegister.getEmail())) {
            throw new CustomersException("Email đã tồn tại.");
        }
        Set< Role> roles = new HashSet<>();
        userRequestRegister.getRoles().forEach(item -> {
            roles.add(roleRepository.findByRoleName(item).orElseThrow(()-> new RuntimeException(item + " not found.")));
        });
        Users users = Users.builder()
                .email(userRequestRegister.getEmail())
                .username(userRequestRegister.getUsername())
                .password(passwordEncoder.encode(userRequestRegister.getPassword()))
                .status(true)
                .roles(roles)
                .build();
        userRepository.save(users);
        return "Đăng kí thành công.";
    }


}
