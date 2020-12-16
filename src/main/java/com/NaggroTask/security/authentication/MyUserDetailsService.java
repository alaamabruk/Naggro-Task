package com.NaggroTask.security.authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.NaggroTask.security.authentication.UserPrinciple;
import com.NaggroTask.security.entity.Role;
import com.NaggroTask.security.entity.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	
	Map<String, User> users = new HashMap<>();

	@Autowired
	PasswordEncoder encoder;

	@PostConstruct
	void loadUsers() {
		users = new HashMap<>();
	
		Set<Role> admin = (new HashSet<Role>());
		admin.add(new Role(1l, "ROLE_ADMIN"));
		Set<Role> user = (new HashSet<Role>());
		user.add(new Role(2l, "ROLE_USER"));
		users.put("testadmin", new User("testadmin", admin, encoder.encode("adminpassword")));
		users.put("user", new User("testUser", user, encoder.encode("userpassword")));

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = users.values().stream().filter(u -> Objects.equals(username, u.getUsername())).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException(
						"Could not find user : " + username));
		System.out.println("user" + user);

		return UserPrinciple.build(user);
	}

    
    
    
}