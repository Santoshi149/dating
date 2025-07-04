package com.mydating.dating.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mydating.dating.entity.User;
import com.mydating.dating.repository.UserRepository;
import com.mydating.dating.util.UserGender;

@Repository
public class UserDao {

    @Autowired
     UserRepository rep;

    public User savedUser(User user) {
        return rep.save(user);
    }

	public List<User> findAllMaleUsers() {
		
		return rep.findByGender(UserGender.MALE);
	}

	public List<User> findAllFemaleUsers() {
		return rep.findByGender(UserGender.FEMALE);
		
	}

	public Optional<User> findUserById(int id) {
        return rep.findById(id);
    }

	public List<User> searchByName(String letters) {
		
		return rep.searchByName(letters);
	}

public List<User> searchByemail(String letters) {
		
		return rep.searchByemail(letters);
	}
    
    

	/*
	 * public void deleteById(int id) { rep.deleteById(id); }
	 * 
	 * public boolean existsById(int id) { return rep.existsById(id); }
	 */

}