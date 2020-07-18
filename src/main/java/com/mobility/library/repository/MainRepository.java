/**
 * 
 */
package com.mobility.library.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.info.AuthorizedUserMapper;
import com.mobility.library.repository.impl.IMainRepository;

/**
 * @author AKT
 *
 */
@Repository
public class MainRepository implements IMainRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public AuthorizedUser loginUser(String userid, String password) {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, password, passcode, userrole, phone, email, deletedstatus FROM U001 WHERE code =? AND password=? ";
		AuthorizedUser authorizedUser = jdbcTemplate.queryForObject(sql, new AuthorizedUserMapper(), userid, password);
		return authorizedUser;
	}

}
