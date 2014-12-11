package pl.mkorwel.angularjs.sample.store;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import pl.mkorwel.angularjs.sample.domain.User;
import pl.mkorwel.angularjs.sample.domain.UserStatus;

public class UserStore {

	private long idGenerator = 2;

	private Map<Long, User> userMap = new HashMap<>();

	{
		userMap.put(1L, new User(1L, "User1", "Name", "Surname"));
		userMap.put(2L, new User(2L, "User2", "Name", "Surname"));

	}

	public List<User> getAll() {
		return new LinkedList<User>(userMap.values());
	}

	public User get(long id) {
		return userMap.get(id);
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++idGenerator);
		}
		userMap.put(user.getId(), user);
		return user;
	}

	public void delete(long id) {
		userMap.remove(id);
	}

	public void activate(long id) {
		userMap.get(id).setStatus(UserStatus.ACTIVATED);
	}

}
