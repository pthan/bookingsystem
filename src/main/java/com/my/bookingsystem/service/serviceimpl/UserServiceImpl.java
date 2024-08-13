package com.my.bookingsystem.service.serviceimpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.my.bookingsystem.model.entity.Role;
import com.my.bookingsystem.model.entity.User;
import com.my.bookingsystem.model.request.UserRequest;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.repository.RoleRepository;
import com.my.bookingsystem.repository.UserRepository;
import com.my.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    /**
     * To save user information
     * @param userRequest
     * @return
     */
    @Override
    public ResponseFormat saveUser(UserRequest userRequest) {
        ResponseFormat responseFormat=null;
        try{
            String email=userRequest.getEmail();
            if(email!=null && !email.trim().isEmpty()){
                Optional<User> userOptional=userRepository.findByEmail(email);
                if (userOptional.isPresent()){
                return ResponseFormat
                        .failedResponse()
                        .message("Registration Failed!")
                        .data("User Already Registered!")
                        .build();
                  }
            }
            User user=new User();
            user.setEmail(userRequest.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
            user.setPassword(encryptedPassword);
            Optional<Role> optionalRole=roleRepository.findByName(Role.ROLE_USER);
           if(optionalRole.isPresent()){
               user.setRole(optionalRole.get());
           }
           user.setName(userRequest.getName());
           user.setStatus(User.USER_STATUS_ACTIVE);
            user.setGuid(UUID.randomUUID().toString());
            user.setCreatedOn(ZonedDateTime.now());
            user.setStatus(Role.ROLE_STATUS_ACTIVE);
            userRepository.save(user);
            responseFormat = new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage( Optional.of("User account creating successful") );
         //   responseFormat.setData( Optional.of(user.getGuid()) );

        }catch (Exception e){
            e.printStackTrace();
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        return  responseFormat;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
