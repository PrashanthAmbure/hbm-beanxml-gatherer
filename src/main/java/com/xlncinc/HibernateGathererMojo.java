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
 * Gathers hibernate mapping files into a single list
 * 
 * @goal gather-hibernate-hbm-xmls
 * @description creates text file hibernate-hbm-listing.txt in the root of the
 *              java source directory listing all of the hibernate hbm files in
 *              the project
 * @phase generate-resources
 */
public class HibernateGathererMojo extends AbstractMojo {
	/**
	 * The source directory to be searched for 
	 * 
	 * @parameter expression="${project.build.sourceDirectory}"
	 * @required
	 */
	private File sourceDirectory;

    /**
     * The version of the project being built
     * 
     * @parameter expression="${project.version}"
     */
    private String version;

	public void execute() throws MojoExecutionException {
		final File outputListing = new File(sourceDirectory,
				"hibernate-hbm-listing.txt");
		final PrintWriter writer;
		try {
			writer = new PrintWriter(new FileWriter(outputListing));
			try {
				writeHbms(sourceDirectory, writer, "");
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new MojoExecutionException(
					"Could not generate list of hibernate hbms.", e);
		}
		getLog().info("Hibernate files gathered successfully, executing on version# "+version);
	}

	void writeHbms(File directory, PrintWriter output, String currentPackage)
			throws IOException {
	    final File[] files = directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory()
						&& !pathname.getName().equals(".svn")
						|| pathname.getName().endsWith(".hbm")
						|| pathname.getName().endsWith(".hbm.xml");
			}
		});

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) {
					final String newPackage = currentPackage + file.getName()  + "/" ;
					
					writeHbms(file, output, newPackage);
				}
                else
                {
                    output.println(currentPackage + file.getName());
                }
			}
		}
	}
}