/**
* Class to analyze Java source files.
* Author: Furkan Kesicioğlu furkan.kesicioglu@ogr.sakarya.edu.tr
* @since 05.04.2024
* <p>
* 2B
* </p>
*/

package javaClassAnalyzerGitHub;

import java.util.regex.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class classAnalyzer {

    /**
    * Analyzes the given Java source file.
    * @param javaFile The Java file to analyze
    */
    public static void analyzeClass(File javaFile) {
    	
        String className = javaFile.getName();
        String classNameExt = className.substring(0, className.lastIndexOf('.'));
        

        double javadocLines = 0;
        double otherComments = 0;
        double codeLines = 0;
        double loc = 0;
        double functionCount = 0;
        double YG = 0;
        double YH = 0;
        double deviation= 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
        	
            String line;
            boolean inComment = false;
            boolean inJavadoc = false;

            while ((line = reader.readLine()) != null) {
            	
                line = line.trim();
                loc++;
                
                String regexConstructor = "(?m)(?s)\\bpublic\\s+" + classNameExt + "\\s*\\([^\\)]*\\)\\s*\\{";
                String regexComment = "(//[^\\n]*|/\\*(.|\\n)*?\\*/|\".*\"\\s*//\\s*\".*\")";
                String regexFunc = "(?m)(?s)\\b(?:public|private|protected|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+)\\s*\\([^\\)]*\\)\\s*\\{|\\bpublic\\s+(\\w+)\\s*\\(\\)";
                
                Pattern patternComment = Pattern.compile(regexComment);
                Pattern patternFunc = Pattern.compile(regexFunc);
                Pattern patternConstructor = Pattern.compile(regexConstructor);
                
                Matcher matcherComment = patternComment.matcher(line);
                Matcher matcherFunc = patternFunc.matcher(line);
                Matcher matcherConstructor = patternConstructor.matcher(line);


                if (line.startsWith("/**")) {	//Javadoc check
                	javadocLines-=1;
                    inJavadoc = true;
                    
                    if (line.contains("*/")) {
                        inJavadoc = false;
                    }
                }
                
                
                else if (inJavadoc) {		//If Javadoc has not ended yet
                    javadocLines++;

                    if (line.endsWith("*/")) {
                        inJavadoc = false;
                    }
                }
                
                
                else if (line.startsWith("/*")) { //Multiple line comment check
                	otherComments-=1;
                    inComment = true;
                    if (line.contains("*/")) {
                        inComment = false;
                    }
                }
                
                
                else if (inComment) {		//If multiple line comment has not ended yet
                    otherComments++;
                    if (line.endsWith("*/")) {
                        inComment = false;
                    }
                }
                
                    
                else if (!line.isEmpty() && !inComment && !inJavadoc && !line.startsWith("//")) {
                    codeLines++;

                }
                
                               
                while (matcherComment.find()) { //Comment counter
                	otherComments++;
                } 
                
                
                while (matcherFunc.find()) { //Function counter
                    functionCount++;
                }
                
                
                while (matcherConstructor.find()) { //Also counts constructor function
                    functionCount++;
                }
                
                
            }                   
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        YG = ((javadocLines + otherComments) * 0.8) / functionCount;
        YH = (codeLines / functionCount)*0.3;
        deviation = ((100 * YG) / YH) - 100;

        System.out.println("Class: " + className);
        System.out.println("Javadoc Line Count: " + javadocLines);
        System.out.println("Comment Line Count: " + otherComments);
        System.out.println("Code Line Count: " + codeLines);
        System.out.println("LOC: " + loc);
        System.out.println("Function Count: " + functionCount);
        System.out.println("Comment Deviation Percentage: %" + deviation);
        System.out.println("-----------------------------------------");
    }

	
	/**
	* Checks if the given file represents a Java class.
	* @param javaFile The file to check
	* @return true if the file represents a Java class, false otherwise
	*/
	public static boolean isClass(File javaFile) {
        Pattern regexIsClass = Pattern.compile("(public|private|protected)\\s+class\\s+(\\w+)");
		try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                Matcher matcherIsClass = regexIsClass.matcher(line);
                while (matcherIsClass.find()) {
                    return true;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        return false;		
	}
	
}
