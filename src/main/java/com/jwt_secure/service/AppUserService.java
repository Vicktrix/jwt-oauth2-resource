package com.jwt_secure.service;

import com.jwt_secure.model.AppUser;
import com.jwt_secure.model.LoginDto;
import com.jwt_secure.model.RegisterDto;
import com.jwt_secure.model.UserDetailModel;
import com.jwt_secure.repositories.AppUserRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private AppUserRepository repo;
    private PasswordEncoder encoder;

    public AppUserService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }
    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser appUser = repo.findByUsername(username);
//        if(appUser != null) {
//            return User.withUsername(appUser.getUsername())
//                    .password(appUser.getPassword())
//                    .roles(appUser.getRole())
//                    .build();                    
//        }
//        throw new UsernameNotFoundException(username + " by UserDetail not found!");
//    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<AppUser> user = repo.findByUsername(username);
        AppUser appUser = repo.findByUsername(username);
//        return user.map(UserDetailModel::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
        return Optional.of(appUser).map(UserDetailModel::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }
/*    
    public AppUser getUserFromDtoAndSaveToDB(RegisterDto dto) {
        
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        AppUser appUser = repo.findByUsername(dto.getUsername());
        AppUser appUser = repo.findByEmail(dto.getEmail());
        if(appUser != null)         // user with this email already exist in DB
            return null;
        appUser = new AppUser();
        appUser.setUsername(dto.getUsername());
        appUser.setFullName(dto.getFullName());
        appUser.setEmail(dto.getEmail());
        appUser.setCreateDate(new Date());
        appUser.setRole("user");
        appUser.setPassword(encoder.encode(dto.getPassword()));
        return repo.save(appUser);
//        return appUser;
    }
*/    
    public Optional<UserDetails> saveUserToDB(RegisterDto dto) {
        AppUser appUser = repo.findByEmail(dto.getEmail());
        if(appUser != null)         // user with this email already exist in DB
            return Optional.empty();
        appUser = new AppUser();
        appUser.setUsername(dto.getUsername());
        appUser.setFullName(dto.getFullName());
        appUser.setEmail(dto.getEmail());
        appUser.setCreateDate(new Date());
        appUser.setRole("user");
        appUser.setPassword(encoder.encode(dto.getPassword()));
        return Optional.ofNullable(repo.save(appUser)).map(UserDetailModel::new);
    }
/*    
    public UserDetails getUserIfLoginPassMatchesDB(LoginDto login) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails loadUserByUsername = loadUserByUsername(login.getUserName());
        if (encoder.encode(login.getPass()).equals(loadUserByUsername.getPassword()))
//            return repo.findByUsername(login.getUserName());
            return loadUserByUsername;
        return null;
    }
*/
    public Optional<UserDetails> loginUserFromDB(LoginDto login) {
        UserDetails user = loadUserByUsername(login.getUserName());
        System.out.println("login pass : "+login.getPass());
        System.out.println("user pass : " +user.getPassword());
        System.out.println("login pass encoded : "+encoder.encode(login.getPass()));
        System.out.println("matches passes : "+encoder.matches(login.getPass(), user.getPassword()));
        return Optional.ofNullable(user)
                .filter(u -> encoder.matches(login.getPass(), u.getPassword()));
    }

    public Optional<AppUser> getAppUserByName(String name) {
        return Optional.ofNullable(repo.findByUsername(name));
    }
}
