package com.hm.userservice.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.CreditCardEligiblePeoples;
import com.hm.userservice.entity.People;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("peopleBulkService")
public class PeopleBulkServiceImpl {
	
	private int card_eligibility = 1;

	private final JdbcTemplate jdbcTemplate;

	public PeopleBulkServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void updateCardEligibilPeoples(List<People> allLoanEligibilPeopleData, int batchNumber) {
		String sql = "UPDATE demo.people SET credit_card_eligibility = ? WHERE people_id = ?";

		StopWatch timer = new StopWatch();
		timer.start();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				People person = allLoanEligibilPeopleData.get(i);
				ps.setInt(1, person.getCreditCardEligibility()); // Set credit_card_eligibility to 1
				ps.setInt(2, person.getPeopleId()); // Set people_id
			}

			@Override
			public int getBatchSize() {
				return allLoanEligibilPeopleData.size();
			}
		});
		timer.stop();
		//log.info("Batch " + batchNumber + " Finished in " + timer.getTotalTimeSeconds());
	}

	@Transactional
	public void bulkInsertCreditCardEligibleMembers(List<CreditCardEligibleMembers> creditCardEligibleMemborsList) {
		String sql = "INSERT INTO demo.credit_card_eligible_members(account_id, card_number, loan_ammount, name) VALUES (?, ?, ?, ?)";

		StopWatch timer = new StopWatch();
		timer.start();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CreditCardEligibleMembers model = creditCardEligibleMemborsList.get(i);
				ps.setObject(1, model.getAccountId());
				ps.setObject(2, model.getCardNumber());
				ps.setObject(3, model.getLoanAmmount());
				ps.setObject(4, model.getName());
			}

			@Override
			public int getBatchSize() {
				return creditCardEligibleMemborsList.size();
			}
		});
		timer.stop();
		//log.info("Batch Finished in " + timer.getTotalTimeSeconds() + " For file ");
	
	}
	
	@Transactional
	public void bulkInsertCreditCardEligiblePeopls(List<CreditCardEligiblePeoples> creditCardEligiblePeopleList) {
		String sql = "INSERT INTO demo.card_eligibility_people (account_id, people_id, credit_card_eligibility, name) VALUES (?, ?, ?, ?)";

		StopWatch timer = new StopWatch();
		timer.start();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CreditCardEligiblePeoples model = creditCardEligiblePeopleList.get(i);
				ps.setObject(1, model.getAccountId());
				ps.setObject(2, model.getPeopleId());
				ps.setObject(3, model.getCreditCardEligibility());
				ps.setObject(4, model.getName());
			}

			@Override
			public int getBatchSize() {
				return creditCardEligiblePeopleList.size();
			}
		});
		timer.stop();
		//log.info("Batch Finished in " + timer.getTotalTimeSeconds() + " For file ");
	
	}
}
