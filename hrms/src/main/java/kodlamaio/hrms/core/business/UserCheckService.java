package kodlamaio.hrms.core.business;

import kodlamaio.hrms.core.entities.User;
import kodlamaio.hrms.core.utilities.results.Result;

public interface UserCheckService {
	Result checkIfRealPerson(User user);
}
