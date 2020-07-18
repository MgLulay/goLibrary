/**
 * 
 */
package com.mobility.library.repository.impl;

import com.mobility.library.info.AuthorizedUser;

/**
 * @author AKT
 *
 */
public interface IMainRepository {

	AuthorizedUser loginUser(String userid,String password);
}
