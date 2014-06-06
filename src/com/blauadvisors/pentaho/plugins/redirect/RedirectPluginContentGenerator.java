/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package com.blauadvisors.pentaho.plugins.redirect;

import java.io.OutputStream;
import java.util.Date;
import java.util.ListIterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.IUserRoleListService;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.services.solution.SimpleContentGenerator;
import org.pentaho.platform.util.logging.Logger;

@SuppressWarnings("serial")
public class RedirectPluginContentGenerator extends SimpleContentGenerator {
	private IPentahoSession session;
	private IUnifiedRepository repository;

	@Override
	public void createContent(OutputStream out) throws Exception {

	}

	public void createContent() throws Exception {
		this.session = PentahoSessionHolder.getSession();
		this.repository = PentahoSystem.get(IUnifiedRepository.class, session);

		RepositoryFile repositoryFile = (RepositoryFile) parameterProviders
				.get("path").getParameter("file");

		SimpleRepositoryFileData data = repository.getDataForRead(
				repositoryFile.getId(), SimpleRepositoryFileData.class);
 
		RedirectPluginFileContectExtractor fileData =  new RedirectPluginFileContectExtractor(data.getInputStream());

		/*
		 * Redirect to Specified URL
		 */
		try {
			// Get informations about user context
			IUserRoleListService service = PentahoSystem
					.get(IUserRoleListService.class);
			String roles = "";
			ListIterator li = service.getRolesForUser(null, session.getName()).listIterator();
			while (li.hasNext()) {
				roles = roles + li.next().toString() + ",";
			}

			// Build URL
			String redirectURL=fileData.getUrl();
			String parameters="";
			parameters+=(fileData.getIncludeRoles() ? "&" + fileData.getRolesParameterName() + "=" + roles : "");
			parameters+=(fileData.getIncludeUserName() ? "&" + fileData.getUserNameParameterName() + "=" + session.getName() : "");
			parameters+=(fileData.getIncludeTimeStamp() ?  "&" + "timeStamp=" + (new Date()).toString() : "");
			redirectURL+=(!fileData.getUrlIncludesParameters() ? "?" : "") + parameters;

			
			// Redirect
			HttpServletResponse response = (HttpServletResponse) this.parameterProviders.get("path").getParameter("httpresponse");
			response.sendRedirect(redirectURL);
		} catch (Exception e) {
			Logger.error(getClass().getName(), e.getMessage());
		}
	}

	@Override
	public String getMimeType() {
		return "text/html";
	}

	@Override
	public Log getLogger() {
		return LogFactory.getLog(RedirectPluginContentGenerator.class);
	}

}
