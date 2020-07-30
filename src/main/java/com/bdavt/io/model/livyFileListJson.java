/**
 * Author: Amit Kumar
 * amitkumar647
 * 11:09:07 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
public class livyFileListJson {
	
	private String path;
    private String fileNames;
    
    public livyFileListJson(String Path, String FileNames) {
    	this.fileNames=FileNames;
    	this.path=Path;
    }
    
    public void setFilename(String fileNames) {
    	this.fileNames=fileNames;
    }
    public String getFilename() {
		return fileNames;
    }
    
    public void setPath(String path) {
    	this.path=path;
    }
    public String getPath() {
		return path;
    }

}
