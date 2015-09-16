//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//


package com.oose2015.ebridge2.hareandhounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;

/**
 * the application bootstrap
 * @author eric updated from given code by Dr. Smith
 *
 */
public class Bootstrap {
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 8080;

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    /**
     * the main method that creates and controls a series of games
     * @param args basic java formatting
     * @throws Exception because it can throw exceptions
     */
    public static void main(String[] args) throws Exception {
        //Specify the IP address and Port at which the server should be run
        ipAddress(IP_ADDRESS);
        port(PORT);
        //Specify the sub-directory from which to serve static resources (like html and css)
        staticFileLocation("/public");
        //Create the model instance and then configure and start the web service
       	GameRepo grepo = new GameRepo();
        new HareandhoundController(grepo);
    }
}
