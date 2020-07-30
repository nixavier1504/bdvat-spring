/**
 * Author: Amit Kumar
 * amitkumar647
 * 11:29:13 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.bd;

import org.apache.livy.Job;
import org.apache.livy.JobContext;

/**
 * @author amitkumar647
 *
 */
public class HDFSlist implements Job<String[]> {

	private String path;
	public HDFSlist(String Path) {
		this.path=Path;
	}
	@Override
	public String[] call(JobContext jb) throws Exception {
		// TODO Auto-generated method stub
		//write HDFS listing code
		return null;
	}

}
