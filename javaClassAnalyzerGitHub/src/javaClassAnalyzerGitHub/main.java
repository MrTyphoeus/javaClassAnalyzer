/**
* Main class to execute the program.
* It clones a GitHub repository using githubCloner class and analyzes Java files using classAnalyzer class.
* Author: Furkan Kesicioğlu furkan.kesicioglu@ogr.sakarya.edu.tr
* @since 05.04.2024
* <p>
* 2B
* </p>
*/

package javaClassAnalyzerGitHub;

import java.io.File;

public class main {
    
    // Create an instance of githubCloner class
    githubCloner Cloner = new githubCloner();

    /**
    * Main method to start the program.
    * It clones a GitHub repository and analyzes the Java files in the cloned repository.
    * @param args Command-line arguments (not used)
    */
    public static void main(String[] args) {
          // Start the cloning process
          githubCloner.startClone();
          
          // Define the directory path where the repository is cloned
          String directoryPath = "src/cloned_repository";
          File directory = new File(directoryPath);

          // Analyze the Java files in the cloned repository
          analyzeFiles(directory);
    }   
    
    /**
    * Recursively analyzes Java files in the given directory.
    * @param directory The directory to search for Java files
    */
    private static void analyzeFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    // Check if the file represents a Java class before analyzing
                	if(classAnalyzer.isClass(file)) {
                        classAnalyzer.analyzeClass(file);
                    }
                } else if (file.isDirectory()) {
                    // If the file is a directory, recursively search for Java files
                    analyzeFiles(file);
                }
            }
        }
    }        
}
