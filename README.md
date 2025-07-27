# QAPROJECT-TRIVAGO
Basic Java selenium UI tests for Trivago WebSite

## Setup Requirements
1) have Java SE 21 installed: https://www.oracle.com/java/technologies/downloads/#java21
   Confirm by running '% java --version' and see the latest version downloaded
3) have Eclipse, preferrably Java Developers version installed: https://www.eclipse.org/downloads/packages/release/2025-06/r/eclipse-ide-java-developers

## Import Maven Project from Github to your local Eclipse editor
7 steps from: https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-to-import-a-Maven-project-from-GitHub-into-Eclipse
1) Copy the project GitHub URL to the clipboard: https://github.com/spyro005/QAPROJECT-TRIVAGO.git
2) Open Eclipse and choose Import –> Projects from Git (with smart import)
3) Choose the Clone URI option in the Git import wizard and click Next
4) Confirm the URI, Host and Repository path parameters and click Next
5) Choose the Git branches to clone from the remote repository and click Next
6) Confirm the Directory into which the repository will be cloned and click Next
7) Choose the Maven project to import into Eclipse from GitHub and click Finish

On the pom.xml you can see the libraries used from Maven to run the UI tests

## Run Tests 

1) On the project folder explorer of Eclipse select the page tests you want to run under /trivago/src/test/java/com.demo.qa.trivago.pagesTests .
  E.g. 'MainPageTests.java'
<img width="800" height="500" alt="image" src="https://github.com/user-attachments/assets/9b03b8b4-b8c8-4791-b44a-bf50cef38dcb" />

2) Right click on the selected Tests and click 'Run As' 'JUnit Test'
   <img width="1200" height="900" alt="image" src="https://github.com/user-attachments/assets/24af8653-d07a-4459-8716-c60ae17dba9a" />  

3) See the results on the JUnit test report
   <img width="900" height="400" alt="image" src="https://github.com/user-attachments/assets/ad68e43e-758c-4701-9790-30b841190603" />




