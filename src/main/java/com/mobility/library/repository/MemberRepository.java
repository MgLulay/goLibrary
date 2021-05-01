package com.mobility.library.repository;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.MemberInfoMapper;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository implements IMemberRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from Member", Integer.class);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Member set deletedstatus = 4 WHERE systemkey = ? ", id);
	}

	@Override
	public ReturnFormat<MemberInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		ReturnFormat<MemberInfo> returnFormat = new ReturnFormat<>();
		List<MemberInfo> dataList = new ArrayList<>();
		String whereclause = "";
		int totalcount = 0;
		String sql = " SELECT systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno, dob, gender, phone, email, deletedstatus FROM Member "
				+ " WHERE deletedstatus <> 4";
		if (!listCriteriaInfo.getCode().equals("")) {
			whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%'";
		}
		if (!listCriteriaInfo.getName().equals("")) {
			whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
		}
		sql += whereclause;
		int currentpage = listCriteriaInfo.getCurrentpage();
		int pagesize = listCriteriaInfo.getPagesize();
		int start = (currentpage - 1) * pagesize;
		if (pagesize > 0) {
			sql += " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY ";
		} else {
			sql += " ORDER BY systemkey ";
		}
		dataList = jdbcTemplate.query(sql, new MemberInfoMapper());
		returnFormat.setList(dataList);

		sql = "SELECT Count(systemkey) count FROM Member WHERE deletedstatus <> 1 ";
		sql += whereclause;
		totalcount = jdbcTemplate.queryForObject(sql, Integer.class);
		returnFormat.setTotalcount(totalcount);

		return returnFormat;
	}

	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM Member WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[] { code, id }, Integer.class);
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int save(MemberInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO Member(systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno, dob, gender, phone, email, deletedstatus ) values "
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		return jdbcTemplate.update(sql, data.getSystemkey(), data.getCreateddate(), data.getModifieddate(),
				data.getCode(), data.getName(), data.getStarteddate(), data.getAddress(), data.getNrcno(), 
				data.getDob(), data.getGender(), data.getPhone(), data.getEmail(), data.getDeletedstatus());
	}

	@Override
	public int update(MemberInfo data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE Member set modifieddate=?, code=?, name=?, starteddate=? ,address=? , nrcno=?, dob=?, gender=?, phone=?, email=?, deletedstatus=? WHERE "
				+ " systemkey = ? ";

		return jdbcTemplate.update(sql, data.getModifieddate(), data.getCode(), data.getName(), data.getStarteddate(),
				data.getAddress(), data.getNrcno(), data.getDob(), data.getGender(), data.getPhone(), data.getEmail(), 
				data.getDeletedstatus(), data.getSystemkey());
	}

	@Override
	public List<MemberInfo> findAll() {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey,starteddate, code, name, address, nrcno, dob, gender, phone, email, deletedstatus FROM Member WHERE deletedstatus <> 4 ";
		return jdbcTemplate.query(sql, new MemberInfoMapper());
	}

	@Override
	public MemberInfo findById(String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey,starteddate, code, name, address, nrcno, dob, gender, phone, email, deletedstatus FROM Member WHERE deletedstatus <> 1 AND systemkey=? ";
		RowMapper<MemberInfo> rowMapper = new BeanPropertyRowMapper<MemberInfo>(MemberInfo.class);
		MemberInfo member = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return member;
	}

}
