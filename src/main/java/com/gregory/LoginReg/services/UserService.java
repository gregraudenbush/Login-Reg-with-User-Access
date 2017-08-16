package com.gregory.LoginReg.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gregory.LoginReg.models.Role;
import com.gregory.LoginReg.models.User;
import com.gregory.LoginReg.repositories.RoleRepository;
import com.gregory.LoginReg.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public List<User> allUsers(){
    		return (List<User>) userRepository.findAll();
    }
    public void deleteByUser(User user) {
    		userRepository.delete(user);
    }
    public User findById(Long id) {
		User user = userRepository.findOne(id);
		return user;
}
    public User findByUserName(String username) {
  		User user = userRepository.findByUsername(username);
  		return user;
  }

    
    public List<Role> findRoleByName(String name){
		return roleRepository.findByName(name);
}
    
    public List<User> userList(Role role){
    		return (List<User>) role.getUsers();
    }
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

