/**
 * Author: Amit Kumar
 * amitkumar647
 * 10:48:16 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.bd;

import org.apache.livy.Job;
import org.apache.livy.JobContext;


/**
 * @author amitkumar647
 *
 */
public class parquetReadSchema implements Job<String[]>{
	
	private String path;
	public parquetReadSchema(String Path) {
		this.path=Path;
	}
	@Override
	public String[] call(JobContext jb) throws Exception {
		// TODO Auto-generated method stub
		String[] ds = jb.sqlctx().read().parquet(path.toString()).schema().fieldNames();
		return ds;
	}
}
