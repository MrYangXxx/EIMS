package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.jim97.entity.Log;
import site.jim97.mapper.LogMapper;

@Service
public class LogService extends BaseService<Log>{
	@Autowired
	private LogMapper mapper;
	
	public void deleteByWeek(){
		mapper.deleteByWeek();
	}
	
	public void deleteByMonth(){
		mapper.deleteByMonth();
	}
}
