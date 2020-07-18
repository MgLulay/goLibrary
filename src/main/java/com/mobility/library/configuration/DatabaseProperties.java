/**
 * 
 */
package com.mobility.library.configuration;
import org.springframework.stereotype.Component;
/**
 * @author Admin
 *
 */
@Component
public class DatabaseProperties {
	private String title;
	private String databaseurl;
	
	public String getTitle() {
		return title;
	}
	public String getDatabaseurl() {
		return databaseurl;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDatabaseurl(String databaseurl) {
		this.databaseurl = databaseurl;
	}
}
