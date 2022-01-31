This project is the first larger sized Java project I have worked on, and 
I started it a couple of years ago. In the future, I would like to convert it 
into a Web application with the help of Spring MVC.

The game is one which we like to play in our friend group. We must all think a 
predetermined amount of words, write them down on small pieces of paper, then 
shuffle all the slips together and decide teams randomly. This game consists 
of 3 rounds, each being different to each other, but the goal is the same. 
Try to guess as many words as possible as a team to win the game.

Since Java Fx allows you to design with MVC, that is what I did, 
with the use of FXML and Java. The Model consisted of the objects I created, 
like the Player and Team. The view was written in FXML, but I also used 
SceneBuilder to build them more quickly. As for the Controller, each View has 
its own Controller where the attributes and behaviors are defined.

I also added an SQLite database with the help of JDBC to save words, so they can be used later.

JAVAFX COMPATIBLILTY ISSUES If you are using Java 11 or later, you must configure your IDE to 
be able to run and write JAVAFX applications. <br />  
Step 1: Download the JavaFX SDK from the following site for your operating system: https://gluonhq.com/products/javafx/ <br />  
Step 2: Extract the folder to a location you will be able to find easily, and one that you will not move it from.<br />  
Step 3: Open your IDE and open a JAVAFX project.<br />  
Step 4: Go into Project Structure - Libraries and add a new Java Library. Locate the folder you just extracted, 
        open it, and select the Lib folder. Click OK.<br />  
Step 5: At the top of the window, go into Run - Edit Configurations. If you do not see your VM options here,
        click on Modify Options, and add VM Options. 
        Copy this into the VM Options textbox: --module-path "/path/to/javafx/sdk" --add-modules javafx.controls,javafx.fxml.
        Make sure that in between the quotation marks, you specify the absolute path on your computer to the Lib folder you selected before.<br />  
Step 6: Make sure that in the Project Structure - Project, the Project SDK and Project Language Level are both the same version,
        as well as in Project Settings - Modules - Module SDK.<br />  
        
Everything should be working now. :)
        
