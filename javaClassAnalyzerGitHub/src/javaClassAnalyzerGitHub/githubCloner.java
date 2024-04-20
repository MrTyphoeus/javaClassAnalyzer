
/**
* This class provides functionality to clone GitHub repositories.
* It includes methods to start the cloning process and list Java files in the cloned repository.
* Author: Furkan Kesicioğlu furkan.kesicioglu@ogr.sakarya.edu.tr
* @since 05.04.2024
* <p>
* 2B
* </p>
*/


package javaClassAnalyzerGitHub;

import java.io.File;
import java.util.Scanner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class githubCloner {
	
	/**
	* Starts the process of cloning a GitHub repository.
	* It prompts the user to enter the URL of the repository.
	* The cloned repository is saved in the 'src/cloned_repository' directory.
	*/
	public static void startClone() {
		
	        Scanner scanner = new Scanner(System.in);

	        // Prompt the user to enter the GitHub Repository URL
	        System.out.print("Enter the GitHub Repository URL: ");
	        String repositoryUrl = scanner.nextLine();

	        // Define the directory path where the repository will be cloned
	        String directoryPath = "src/cloned_repository";
	        File directory = new File(directoryPath);
	        
	        // Create the directory if it doesn't exist
	        if (!directory.exists()) {
	            directory.mkdir();
	        }

	        try {
	            // Clone the repository
	            Git.cloneRepository()
	                    .setURI(repositoryUrl)
	                    .setDirectory(directory)
	                    .call();

	        } catch (GitAPIException e) {
	            // Handle exceptions if the cloning process fails
	            System.out.println("Error: " + e.getMessage());
	            System.out.println("There might be a file with the same name.");
	        }
		}

	    
	    /**
	    * Recursively lists all Java files in the given directory.
	    * @param directory The directory to search for Java files
	    */
	    private static void listJavaFiles(File directory) {
	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                // If the file is a Java file, print its name
	                if (file.isFile() && file.getName().endsWith(".java")) {
	                    System.out.println(file.getName());
	                } else if (file.isDirectory()) {
	                    // If the file is a directory, recursively search for Java files
	                    listJavaFiles(file);
	                }
	            }
	        }
	    }
	    
	

}
