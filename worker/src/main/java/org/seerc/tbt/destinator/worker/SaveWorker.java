package org.seerc.tbt.destinator.worker;

import io.iron.ironworker.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.seerc.tbt.destinator.beans.Destination;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Worker class for saving destinations
 */
public class SaveWorker {

    /** Logger */
    private static Logger _logger = LogManager.getRootLogger();

    /**
     * Main
     * 
     * @param aArgs arguments
     */
    public static void main(String[] aArgs) {

        for (String arg : aArgs) {
            System.out.println(arg);
        }

        // obtain the filename from the passed arguments
        int payloadPos = -1;
        for (int i = 0; i < aArgs.length; i++) {
            if (aArgs[i].equals("-payload")) {
                payloadPos = i + 1;
                break;
            }
        }
        if (payloadPos >= aArgs.length) {
            System.err.println("Invalid payload argument.");
            System.exit(1);
        }
        if (payloadPos == -1) {
            System.err.println("No payload argument.");
            System.exit(1);
        }

        // read the contents of the file to a string
        String payload = "";
        try {
            payload = readFile(aArgs[payloadPos]);
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }

        // parse the string as JSON
        Gson gson = new Gson();
        // JsonParser parser = new JsonParser();
        // JsonObject passed_args = parser.parse(payload).getAsJsonObject();

        // print the output of the "arg1" property of the passed JSON object
        Destination destination = gson.fromJson(payload, Destination.class);

        _logger.info("Saving destination: " + destination.toString());

        // TODO save
    }

    /**
     * Reads the payload
     * 
     * @param aPath path to the payload
     * @return json object
     * @throws IOException exception
     */
    private static String readFile(String aPath) throws IOException {
        FileInputStream stream = new FileInputStream(new File(aPath));
        try {
            FileChannel chan = stream.getChannel();
            MappedByteBuffer buf =
                    chan.map(FileChannel.MapMode.READ_ONLY, 0, chan.size());
            return Charset.defaultCharset().decode(buf).toString();
        } finally {
            stream.close();
        }
    }

}
