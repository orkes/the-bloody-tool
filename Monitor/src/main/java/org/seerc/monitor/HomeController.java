package org.seerc.monitor;

import java.text.DateFormat;
import java.util.Date;

import org.seerc.monitor.messaging.MonitorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    /** Number of C-SPARQL queries */
    public static int NUM_QUERIES = 1;

    /** Initial threshold value */
    public static int INITIAL_THRESHOLD = 1;

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model aModel) {

        logger.info("Welcome home!");

        Date date = new Date();
        DateFormat dateFormat =
                DateFormat
                        .getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);
        aModel.addAttribute("serverTime", formattedDate);

        aModel.addAttribute(new QueryParams());

        return "home";
    }

    /**
     * Executes the web service invocation
     * 
     * @param aModel model
     * @param aParams invocation parameters
     * @return string
     */
    @RequestMapping(value = "/setQuery", method = RequestMethod.POST)
    public String setQuery(Model aModel, QueryParams aParams) {

        NUM_QUERIES = aParams.getNumberOfQueries();
        INITIAL_THRESHOLD = aParams.getInitialThreshold();
        MonitorListener.registerQueries();
        aModel.addAttribute("numberOfQueries", aParams.getNumberOfQueries());
        aModel.addAttribute("initialThreshold", aParams.getInitialThreshold());
        aModel.addAttribute("set", true);
        return home(aModel);
    }

}
