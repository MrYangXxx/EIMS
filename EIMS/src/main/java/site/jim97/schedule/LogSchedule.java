package site.jim97.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import site.jim97.service.LogService;

@EnableScheduling
@Component
public class LogSchedule {
	@Autowired
	private LogService logService;
	
	/**
	 * 使用定时任务删除一月内的日志保存，删除记录
	 */
	@Scheduled(cron="0 0 3 15 1-12 ? ") //每月15号的凌晨三点
	public void deleteSaveAndDeleteLog(){
		logService.deleteByMonth();
	}

	/**
	 * 使用定时任务删除一周前的查询记录
	 */
	@Scheduled(cron="0 0 4 1/7 1-12 ? ") //从每月一号开始，每7天执行一次
	public void deleteSelectLog(){
		logService.deleteByWeek();
	}
}
