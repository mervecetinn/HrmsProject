package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.business.EmailCheckService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager implements EmployerService {

	private EmployerDao employerDao;
	private EmailCheckService emailCheckService;
	
	@Autowired
	public EmployerManager(EmployerDao employerDao, EmailCheckService emailCheckService) {
		super();
		this.employerDao = employerDao;
		this.emailCheckService=emailCheckService;
	}
	
	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),Messages.DataListed);
	}

	@Override
	public DataResult<Employer> getByEmail(String email) {
		return new SuccessDataResult<Employer>(this.employerDao.getByEmail(email));
	}

	@Override
	public Result add(Employer employer,String passwordConfirmation) {
		var result=BusinessRules.run(checkIfRealCompany(employer.getWebsite(),employer.getEmail()),checkIfEmailExists(employer.getEmail()),checkIfRealEmail(employer.getEmail()),checkIfPasswordsMatch(employer.getPassword(),passwordConfirmation));
		
		if(result!=null) 
		{
			return result;
		}
		this.employerDao.save(employer);
		return new SuccessResult("Employer added.");
	}
	
	private Result checkIfRealCompany(String website,String email) {
		
		String emailDomain=email.split("@")[1];	
		if(website.contains(emailDomain))		
		{
			return new SuccessResult();
		}
		return new ErrorResult(Messages.DomainsMustBeSame);
		
		}
    private  Result checkIfEmailExists(String email) {
    	var result=this.employerDao.getByEmail(email);
    	if(result!=null) 
    	{
    		return new ErrorResult(Messages.EmailAlreadyExists);
    	}
    	return new SuccessResult();
    }
    
    private Result checkIfRealEmail(String email) {
    	if(this.emailCheckService.checkIfRealEmail(email).isSuccess())
    	{
    		return new SuccessResult();
    	}
    	return new ErrorResult(Messages.EmailNotConfirmed);
    }
    private Result checkIfPasswordsMatch(String password,String passwordConfirmation) {
		if(!password.equals(passwordConfirmation)) {
			return new ErrorResult(Messages.PasswordsNotMatch);
		}
		return new SuccessResult();
	}
	
	
}
