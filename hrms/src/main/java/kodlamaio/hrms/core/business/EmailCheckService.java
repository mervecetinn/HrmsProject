package kodlamaio.hrms.core.business;

import kodlamaio.hrms.core.utilities.results.Result;

public interface EmailCheckService {
	Result checkIfRealEmail(String email);
}
