package com.my.bookingsystem.service.serviceimpl;

import com.my.bookingsystem.model.entity.User;
import com.my.bookingsystem.model.request.LoginRequest;
import com.my.bookingsystem.model.response.LoginResponse;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.model.shared.TokenInfo;
import com.my.bookingsystem.model.shared.UserInfo;
import com.my.bookingsystem.repository.UserRepository;
import com.my.bookingsystem.service.AuthService;
import com.my.bookingsystem.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    public ResponseFormat authenticateUser(LoginRequest loginRequest) {
        ResponseFormat responseFormat=null;
        LoginResponse response=new LoginResponse();
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
       UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
       final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
       final String refreshToken=jwtTokenUtil.generateRefreshToken(userDetails);

            TokenInfo tokenInfo =new TokenInfo();
            tokenInfo.setAccessToken(accessToken);
            tokenInfo.setRefreshToken(refreshToken);
            tokenInfo.setAccessTokenExpire(jwtTokenUtil.getAccessTokenExpirationDate());
            tokenInfo.setRefreshTokenExpire(jwtTokenUtil.getRefreshTokenExpirationDate());
            Optional<User> userOptional=userRepository.findByEmail(loginRequest.getEmail());
            if(userOptional.isPresent()){
                User user=userOptional.get();
                UserInfo profileResponse=new UserInfo();
                profileResponse.setName(user.getName());
                profileResponse.setEmail(user.getEmail());
                profileResponse.setGuid(user.getGuid());
                profileResponse.setUserRole(user.getRole().getName());
                profileResponse.setStatus(user.getStatus());
                response.setUserInfo(profileResponse);
            }

            response.setTokenInfo(tokenInfo);
            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("Auth Success"));
            responseFormat.setData( Optional.of(response) );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFormat
                    .failedResponse()
                    .message("Login failed, please try again later!")
                    .data("Login failed, please try again later!")
                    .build();
        }
      return  responseFormat;



    }
}
