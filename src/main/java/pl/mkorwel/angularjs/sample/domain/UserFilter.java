package pl.mkorwel.angularjs.sample.domain;

import java.io.Serializable;

public class UserFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filterName;

	private UserStatus status;

	public UserFilter(String filterName, String status) {
		super();
		this.filterName = filterName;
		this.status = status == null || status.isEmpty() ? null : UserStatus
				.valueOf(status);
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
