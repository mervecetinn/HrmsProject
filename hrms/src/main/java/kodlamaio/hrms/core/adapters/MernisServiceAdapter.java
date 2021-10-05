package kodlamaio.hrms.core.adapters;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.business.UserCheckService;
import kodlamaio.hrms.core.entities.User;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;

@Service
public class MernisServiceAdapter implements UserCheckService {

	@Override
	public Result checkIfRealPerson(User user) {
		return new SuccessResult();
	}

}
