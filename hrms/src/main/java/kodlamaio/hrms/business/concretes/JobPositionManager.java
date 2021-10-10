package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobPositionService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobPositionDao;
import kodlamaio.hrms.entities.concretes.JobPosition;

@Service
public class JobPositionManager implements JobPositionService {

	private JobPositionDao jobPositionDao;
	
	@Autowired
	public JobPositionManager(JobPositionDao jobPositionDao) {
		super();
		this.jobPositionDao = jobPositionDao;
	}

	@Override
	public DataResult<List<JobPosition>> getAll() {
		
		return new SuccessDataResult<List<JobPosition>>(this.jobPositionDao.findAll(),Messages.DataListed); 
	}
	
	@Override
	public DataResult<JobPosition> getByPositionName(String positionName) {
		return new SuccessDataResult<JobPosition>(this.jobPositionDao.getByPositionName(positionName));
	}

	@Override
	public Result add(JobPosition jobPosition) {
		var result=BusinessRules.run(checkIfJobPositionExists(jobPosition.getPositionName()));
		
		if(result!=null)
		{
			return result;
		}
		this.jobPositionDao.save(jobPosition);
		return new SuccessResult(Messages.JobPositionAdded);
	}
	
	private Result checkIfJobPositionExists(String positionName) {
		var result=this.jobPositionDao.getByPositionName(positionName);
		if(result!=null) 
		{
			return new ErrorResult(Messages.PositionAlredyExists);
		}
		return new SuccessResult();
	}

	

}
