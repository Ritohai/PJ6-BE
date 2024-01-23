package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.request.UserRequestLogin;
import backendshop.model.dto.request.UserRequestRegister;
import backendshop.model.dto.response.JwtReponse;
import backendshop.service.impl.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestRegister userRequestRegister) throws CustomersException {
        return new ResponseEntity<>(userService.hanldeRegister(userRequestRegister), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtReponse> login(@RequestBody UserRequestLogin userRequestLogin) throws CustomersException{
        return new ResponseEntity<>(userService.handleLoging(userRequestLogin), HttpStatus.OK);
    }

}
