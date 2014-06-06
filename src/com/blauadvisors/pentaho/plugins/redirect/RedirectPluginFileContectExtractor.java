package com.blauadvisors.pentaho.plugins.redirect;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.pentaho.platform.util.xml.dom4j.XmlDom4JHelper;

public class RedirectPluginFileContectExtractor {
	private String url;
	private Boolean urlIncludesParameters; 
	private Boolean includeRoles;
	private String rolesParameterName;
	private Boolean includeUserName;
	private String userNameParameterName;
	private Boolean includeTimeStamp;
	private String title;
	private String description;
	private String author;
	
	
	public RedirectPluginFileContectExtractor(InputStream fileStream) throws DocumentException, IOException {
		Document doc = XmlDom4JHelper.getDocFromStream(fileStream);
		url = doc.selectSingleNode("/redirect/url") == null ? "" : doc.selectSingleNode("/redirect/url").getText();
		urlIncludesParameters = url.indexOf("?") < 0 ? Boolean.FALSE : Boolean.TRUE;
		includeRoles = doc.selectSingleNode("/redirect/includeRoles") == null ? Boolean.FALSE : Boolean.parseBoolean(doc.selectSingleNode("/redirect/includeRoles").getText());
		rolesParameterName = doc.selectSingleNode("/redirect/rolesParameterName") == null ? "roles" : doc.selectSingleNode("/redirect/rolesParameterName").getText();
		includeUserName = doc.selectSingleNode("/redirect/includeUserName") == null ? Boolean.FALSE : Boolean.parseBoolean(doc.selectSingleNode("/redirect/includeUserName").getText());
		userNameParameterName = doc.selectSingleNode("/redirect/userNameParameterName") == null ? "user" : doc.selectSingleNode("/redirect/userNameParameterName").getText();
		includeTimeStamp = doc.selectSingleNode("/redirect/includeTimeStamp") == null ? Boolean.FALSE : Boolean.parseBoolean(doc.selectSingleNode("/redirect/includeTimeStamp").getText());
		title = doc.selectSingleNode("/redirect/title") == null ? "" : doc.selectSingleNode("/redirect/title").getText();
		description = doc.selectSingleNode("/redirect/description") == null ? "" : doc.selectSingleNode("/redirect/description").getText();
		author = doc.selectSingleNode("/redirect/author") == null ? "" : doc.selectSingleNode("/redirect/author").getText();
	}


	public String getUrl() {
		return url;
	}


	public Boolean getUrlIncludesParameters() {
		return urlIncludesParameters;
	}


	public Boolean getIncludeRoles() {
		return includeRoles;
	}


	public String getRolesParameterName() {
		return rolesParameterName;
	}


	public Boolean getIncludeUserName() {
		return includeUserName;
	}


	public String getUserNameParameterName() {
		return userNameParameterName;
	}


	public Boolean getIncludeTimeStamp() {
		return includeTimeStamp;
	}


	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public String getAuthor() {
		return author;
	}

}
