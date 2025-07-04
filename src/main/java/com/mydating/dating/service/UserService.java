package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydating.dating.dao.UserDao;
import com.mydating.dating.dto.MatchingUser;
import com.mydating.dating.entity.User;
import com.mydating.dating.util.UserGender;
import com.mydating.dating.util.UserSorting;


@Service
public class UserService {

    @Autowired
     UserDao userDao;

    public ResponseEntity<?> saveUser(User user) {  // Remove static
        User savedUser = userDao.savedUser(user);   // Use the autowired instance
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

	public ResponseEntity<?> findAllMaleUsers() {
		List<User> maleUsers=userDao.findAllMaleUsers();
		if(maleUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No male users present in the database table");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(maleUsers);
		}
	}

	public ResponseEntity<?> findAllFemaleUsers() {
		List<User> femaleUsers=userDao.findAllFemaleUsers();
		if(femaleUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No female users present in the database table");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(femaleUsers);
		}
		
	}

	public ResponseEntity<?> findBestMatch(int id, int top) {
		Optional<User> optional=userDao.findUserById(id);
		if(optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("invalid user id unable to find best matches");
		}
		User user=optional.get();

		List<User> users=null;//userlist by defalut null
		if(user.getGender().equals(UserGender.MALE)) {//to check male or not
			users=userDao.findAllFemaleUsers();//beacuse gender is male if it is false else will execute
		}else {
			users=userDao.findAllMaleUsers();
		}
		//by these for male female or for female male will match
		/*to display in console
		 * for(User u:users) System.out.println(u);
		 */
		
		List<MatchingUser> matchingUsers=new ArrayList<>();//new arraylist so zero
		for(User u:users){//we r getting one one user
			MatchingUser mu=new MatchingUser();
			mu.setId(u.getId());
			mu.setName(u.getName());
			mu.setEmail(u.getEmail());
			mu.setPhno(u.getPhno());
			mu.setAge(u.getAge());
			mu.setInterests(u.getInterests());
			mu.setGender(u.getGender());
			
			mu.setAgeDiff(Math.abs(user.getAge()-u.getAge()));//to check the age difference
			
			List<String> interests1=user.getInterests();//dhoni intrest
			List<String> interests2=u.getInterests();//to check all other intrest with dhoni
			
			int mic=0;
			
			
			for(String s:interests1) { //dhonis intrest
				if(interests2.contains(s)) {//smirthis intrest
					mic++;//if it(contains) is matching then increase
				}
			}
			
			mu.setMic(mic);//matching intrest count
			
			matchingUsers.add(mu);
			
			
		}
		//sorting to find min and max
		Comparator<MatchingUser> c=new UserSorting();
		Collections.sort(matchingUsers,c);
		
		for(MatchingUser mu:matchingUsers)
			System.out.println(mu);
		List<MatchingUser> result=new ArrayList<>();
		for(MatchingUser mu:matchingUsers) {
			if(top==0) {
				break;
			}
			else {
				result.add(mu);
				top--;
			}
		}
		
        return ResponseEntity.status(HttpStatus.OK).body(result);
		
		
	}

	public ResponseEntity<?> searchByName(String letters) {
		List<User> users= userDao.searchByName("%"+letters+"%");
		if(users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with letter:"+letters);
		}
		else {
			
		
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	}
	
	public ResponseEntity<?> searchByemail(String letters) {
		List<User> users= userDao.searchByemail("%"+letters+"%");
		if(users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with letter:"+letters);
		}
		else {
			
		
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	}
	
    
	/* to delete user
	 * public ResponseEntity<?> deleteUser(int id) { if (userDao.existsById(id)) {
	 * userDao.deleteById(id); return
	 * ResponseEntity.ok("User deleted successfully"); } else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); } }
	 */

    
}