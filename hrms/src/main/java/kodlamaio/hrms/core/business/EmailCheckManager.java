package kodlamaio.hrms.core.business;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;

@Service
public class EmailCheckManager implements EmailCheckService {

	@Override
	public Result checkIfRealEmail(String email) {
		return new SuccessResult();
	}

}
