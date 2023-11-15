package com.app.market.util;

import org.springframework.stereotype.Component;

import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.Ad;
import com.app.market.model.entity.Report;
import com.app.market.repository.AdRepository;
import com.app.market.repository.BannedUserRepository;
import com.app.market.repository.CategoryRepository;
import com.app.market.repository.FileRepository;
import com.app.market.repository.RatingRepository;
import com.app.market.repository.ReportRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.UserService;

@Component
public class TestDataService {

	
	private final AdRepository adRepository;
	private final BannedUserRepository bannedUserRepository;
	private final CategoryRepository categoryRepository;
	private final RatingRepository ratingRepository;
	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final FileRepository fileRepository;
	
	private final UserService userService;
	
	public TestDataService(AdRepository adRepository, BannedUserRepository bannedUserRepository,
			CategoryRepository categoryRepository, RatingRepository ratingRepository, ReportRepository reportRepository,
			UserRepository userRepository, UserService userService, FileRepository fileRepository) {
		this.adRepository = adRepository;
		this.bannedUserRepository = bannedUserRepository;
		this.categoryRepository = categoryRepository;
		this.ratingRepository = ratingRepository;
		this.reportRepository = reportRepository;
		this.userRepository = userRepository;
		this.fileRepository = fileRepository;
		this.userService = userService;
	}
	
	public long getAdIdByTitleAndOwner(String title, String ownerName) {
		for(Ad ad : adRepository.findByOwner(userRepository.findByUsername(ownerName))) {
			if(ad.getName().equals(title)) return ad.getId();
		}
		return 1;
	}
	
	public void initUsers() {
		UserRegisterDto userRegisterDto = new UserRegisterDto("Ivan", "ivan@abv.bg", "0824896772", "somePassword", "somePassword");
		UserRegisterDto userRegisterDto2 = new UserRegisterDto("Dragan", "dragan@abv.bg", "0818536772", "somePassword", "somePassword");

		userService.registerUser(userRegisterDto);
		userService.registerUser(userRegisterDto2);
	}
	
	public void tearDownDB() {
		fileRepository.deleteAll();
		adRepository.deleteAll();
		bannedUserRepository.deleteAll();
		categoryRepository.deleteAll();
		ratingRepository.deleteAll();
		reportRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	public long getUserIdByUsername(String username) {
		return this.userRepository.findByUsername(username).getId();
	}
	
	public long getReportIdByUsersIds(long reportedId, long reporterId) {
		for (Report report : reportRepository.findAll()) {
			if(report.getReportedUser().getId() == reportedId && report.getReporterUser().getId() == reporterId) return report.getId();
		}
		return 1;
	}
}
