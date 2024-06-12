package com.kodnest.tunehub.serviceimplmnt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.repository.UserRepository;
import com.kodnest.tunehub.service.UserService;


@Service
public class UserServiceImplmnt implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public String addUser(User user) {
		userRepository.save(user);
		return "User Added Successfully";

	}
	//To check the Duplicate entries 
		public boolean emailExists(String email) {
			if(userRepository.findByEmail(email)!=null) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean validateUser(String email, String password) {
			
			User user = userRepository.findByEmail(email);
			
			String heyy = user.getPassword();
			if(password.equals(heyy)) {
				return true;
			}
			return false;
		}

		@Override
		public String getRole(String email) {
			User user = userRepository.findByEmail(email);
			return user.getRole();
		}
		
		public User getUser(String email) {
			return userRepository.findByEmail(email);
		}
		
		public void updateUser(User user) {
			userRepository.save(user);	
		}

    
}
