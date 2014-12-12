package pl.mkorwel.angularjs.sample.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.mkorwel.angularjs.sample.domain.User;
import pl.mkorwel.angularjs.sample.domain.UserFilter;
import pl.mkorwel.angularjs.sample.domain.UserStatus;

public class UserStore {

	private long idGenerator = 2;

	private Map<Long, User> userMap = new HashMap<>();

	{
		userMap.put(1L, new User(1L, "User1", "Name", "Surname"));
		userMap.put(2L, new User(2L, "User2", "Name", "Surname"));

	}

	public List<User> getAll(UserFilter filter) {
		return userMap
				.values()
				.stream()
				.filter((user) -> nameIsCorrect(filter, user)
						&& statusIsCorrect(filter, user))
				.collect(Collectors.toList());
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

	private boolean statusIsCorrect(UserFilter filter, User user) {
		return filter.getStatus() == null
				|| user.getStatus() == filter.getStatus();
	}

	private boolean nameIsCorrect(UserFilter filter, User user) {
		if (filter.getFilterName() == null) {
			return true;
		}
		String filterName = filter.getFilterName().toLowerCase();
		return user.getName().toLowerCase().contains(filterName)
				|| user.getSurname().toLowerCase().contains(filterName)
				|| user.getUsername().toLowerCase().contains(filterName);
	}

}
