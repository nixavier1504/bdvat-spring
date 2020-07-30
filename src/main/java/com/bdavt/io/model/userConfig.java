/**
 * Author: Amit Kumar
 * amitkumar647
 * 2:20:44 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;


/**
 * @author amitkumar647
 *
 */
public class userConfig {
	
		//private long id;
		private long id;
		private String username;
		private String source;
		private String dataset;
		private String kerb;

		/**
		 * @return the kerb
		 */
		public String getKerb() {
			return kerb;
		}

		/**
		 * @param kerb the kerb to set
		 */
		public void setKerb(String kerb) {
			this.kerb = kerb;
		}

		public userConfig() {

		}

		public userConfig(String username, String source,String dataset,String kerb) {
			this.username = username;
			this.source = source;
			this.dataset = dataset;
			this.kerb = kerb;
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		public String getusername() {
			return username;
		}
		public void setusername(String username) {
			this.username = username;
		}
		
		public String getsource() {
			return source;
		}
		public void setsource(String source) {
			this.source = source;
		}
		
		public String getdataset() {
			return dataset;
		}
		public void setdataset(String dataset) {
			this.dataset = dataset;
		}



		@Override
		public String toString() {
			return "Config [username=" + username + ", source= "+source+", dataset=" + dataset  
					+ "]";
		}

}
