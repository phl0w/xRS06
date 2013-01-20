package rs.lulz.net;

import rs.lulz.utilities.Constants;
import rs.lulz.utilities.Logging;

public class RS06Crawler extends Crawler {

    public RS06Crawler() {
        super(Constants.PROJECT_RS_06_URL_BASE_CLIENT);
        //Logging.setAppName("NRCrawler");
    }

    public String getMainClassName() {

        //Find the class name.
        String s = find("<param name=\"code\" value=\"(.+?)\" />", 1);

        //Print out the class name
        Logging.log("Main class = " + s, RS06Crawler.class);

        return s;
    }

    public String getJarURL() {

        //Create a new StringBuilder
        StringBuilder sb = new StringBuilder();

        //Append the website base
        sb.append(Constants.PROJECT_RS_06_URL_BASE_CLIENT);

        //Find the JAR name
        String s = find("<param name=\"archive\" value=\"(.+?)\" />", 1);

        //Append that name to the StringBuilder
        sb.append(s);

        //Print out the URL
        Logging.log("Suspected URL = " + sb.toString().replaceAll("play.php", ""), RS06Crawler.class);

        //Return the String, which is the direct link to the JAR.
        return sb.toString().replaceAll("play.php", "");
    }

}