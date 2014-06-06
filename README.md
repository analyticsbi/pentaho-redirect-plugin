pentaho--plugin
==================

The redirect plugin for Pentaho is designed to access content on other systems fro Pentaho Repository. 

This version of the plugin forwards Pentaho request to the remote URL with posibilities to include information like userName, userRoles, timestamp. Future versions will include proxy settings and basic authentication in case the remote url required authentication 
 
Building
--------
The REDIRECT plugin is built with Apache Ant.

Building the Plugin:

	1) rename build.properties.example to build.properties 

	2) set environment properties in build.properties to point to Pentaho server location. 

	3) run "ant package" to build the distribution package


Deploy plugin:

	1) run "and deploy" to copy the plugin into Pentaho´s System folder.

	2) modify file importexport.xml located at pentaho-solutions/system/importexport.xml and add the following entries
	
	<entry key="redirect" value-ref="streamConverter"/> 
	<entry key="redirect" value="text/xml"/>
	<value>.redirect</value>  


Testing the Plugin:

	1) Start pentaho.
	
	2) Create a file.redirect based on the example included in this package
	
	3) Upload the file to the Pentaho repository 

	4) open on the uploaded file and see it working....




License
-------
Licensed under the Apache License, Version 2.0. See LICENSE for more information.
