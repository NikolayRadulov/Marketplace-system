package com.app.market.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.entity.Report;
import com.app.market.model.entity.User;
import com.app.market.repository.ReportRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.impl.ReportServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {

	@Mock
	private ReportRepository reportRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ModelMapper modelMapper;
	
	private ReportServiceImpl toTest;
	
	@BeforeEach
	public void setUp() {
		toTest = new ReportServiceImpl(reportRepository, userRepository, modelMapper);
	}
	
	@Test
	public void testIssueReport() {
		//Arrange
		ImportReportDto importReportDto = Mockito.mock(ImportReportDto.class);
		User reportedUser = Mockito.mock(User.class);
		User reporterUser = Mockito.mock(User.class);
		Report report = Mockito.mock(Report.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(reportedUser));
		Mockito.when(userRepository.findById((long)2)).thenReturn(Optional.of(reporterUser));
		Mockito.when(reportRepository.findByReportedUserAndReporterUser(reportedUser, reporterUser)).thenReturn(null);
		Mockito.when(modelMapper.map(importReportDto, Report.class)).thenReturn(report);
		
		//Act
		toTest.issueReport(importReportDto, 1, 2);
		
		//Assert
		
		Mockito.verify(modelMapper).map(importReportDto, Report.class);
		Mockito.verify(report).setReportedUser(reportedUser);
		Mockito.verify(report).setReporterUser(reporterUser);
		Mockito.verify(reportRepository).save(any());
	}
	
	@Test
	public void testIssueReportRepeated() {
		//Arrange
		ImportReportDto importReportDto = Mockito.mock(ImportReportDto.class);
		User reportedUser = Mockito.mock(User.class);
		User reporterUser = Mockito.mock(User.class);
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(reportedUser));
		Mockito.when(userRepository.findById((long)2)).thenReturn(Optional.of(reporterUser));
		Mockito.when(reportRepository.findByReportedUserAndReporterUser(reportedUser, reporterUser)).thenReturn(Mockito.mock(Report.class));
		//Act
		toTest.issueReport(importReportDto, 1, 2);
		//Assert
		
		Mockito.verifyNoInteractions(modelMapper);
		Mockito.verify(reportRepository, times(0)).save(any());
	}
}
