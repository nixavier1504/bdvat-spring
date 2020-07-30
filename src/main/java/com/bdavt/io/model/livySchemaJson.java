/**
 * Author: Amit Kumar
 * amitkumar647
 * 10:28:41 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
public class livySchemaJson {
	
	private String path;
    private String schema;
    
    public livySchemaJson() {
    	
    }
    
    public livySchemaJson(String Path, String Schema) {
    	this.schema=Schema;
    	this.path=Path;
    }
    
    public void setSchema(String Schema) {
    	this.schema=Schema;
    }
    public String getSchema() {
		return schema;
    }
    public void setPath(String path) {
    	this.path=path;
    }
    public String getPath() {
		return path;
    }
    
}
