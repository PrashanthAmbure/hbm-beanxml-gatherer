package com.xlncinc;


/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Gathers spring configuration files into a single list
 * 
 * @goal gather-spring-bean-xmls
 * @description creates text file spring-config-listing.txt in the root of the
 *              java source directory listing all of the spring configuration
 *              files in the project
 * @phase generate-resources
 */
public class SpringGathererMojo extends AbstractMojo {
	/**
	 * The source directory to be searched for spring context files
	 * 
	 * @parameter 
	 * @required
	 */
	private File springDirectory;

	public void execute() throws MojoExecutionException {
		final File outputListing = new File(springDirectory,
				"spring-config-listing.txt");
		final PrintWriter writer;
		try {
			writer = new PrintWriter(new FileWriter(outputListing));
			try {
				writeConfigs(springDirectory, writer, "");
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new MojoExecutionException(
					"Could not generate list of spring configuration files.", e);
		}
		getLog().info("Spring files gathered successfully !");
	}

	void writeConfigs(File directory, PrintWriter output, String currentPackage)
			throws IOException {
		final File[] files = directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory()
						&& !pathname.getName().equals(".svn")
						|| pathname.getName().endsWith("-spring-context.xml");
			}
		});

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) {
					final String newPackage = currentPackage + file.getName()  + "/" ;
					
					writeConfigs(file, output, newPackage);
				} else {
					output.println(currentPackage + file.getName());
				}
			}
		}

	}
}

