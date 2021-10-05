package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.business.EmailCheckService;
import kodlamaio.hrms.core.business.UserCheckService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService {

	private CandidateDao candidateDao;
	private UserCheckService userCheckService;
	private EmailCheckService emailCheckService;
	private UserService userService;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserCheckService userCheckService, EmailCheckService emailCheckService, UserService userService) {
		super();
		this.candidateDao = candidateDao;
		this.userCheckService = userCheckService;
		this.emailCheckService=emailCheckService;
		this.userService=userService;
	}

	@Override
	public DataResult<List<Candidate>> getAll() {

		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Data listed.");
	}

	@Override
	public Result add(Candidate candidate) {
		var result=BusinessRules.run(checkIfRealPerson(candidate),checkIfUserExists(candidate),checkIfRealEmail(candidate.getEmail()));
		
		if(result!=null) 
		{
			return result;
		}
		this.candidateDao.save(candidate);
		return new SuccessResult(Messages.CandidateAdded);
	}

	private Result checkIfRealPerson(Candidate candidate) {
		if (this.userCheckService.checkIfRealPerson(candidate).isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.NotRealPerson);
	}
	
	private Result checkIfRealEmail(String email) {
		if(this.emailCheckService.checkIfRealEmail(email).isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.EmailNotConfirmed);
	}
	
	private Result checkIfUserExists(Candidate candidate) {
		var resultForEmail=this.userService.getByEmail(candidate.getEmail()).getData();
		var resultForIdentityNumber=this.getByIdentityNumber(candidate.getIdentityNumber()).getData();
		if(resultForEmail!=null||resultForIdentityNumber!=null) {
			return new ErrorResult(Messages.UserAlreadyExists);
		}
		return new SuccessResult();
		
	}

	@Override
	public DataResult<Candidate> getByIdentityNumber(String identityNumber) {
		return new SuccessDataResult<Candidate>(this.candidateDao.getByIdentityNumber(identityNumber));
	}

}
