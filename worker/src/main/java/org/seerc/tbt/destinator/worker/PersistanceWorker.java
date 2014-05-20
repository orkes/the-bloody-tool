package org.seerc.tbt.destinator.worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.seerc.tbt.destinator.beans.Destination;
import org.seerc.tbt.destinator.persistance.SessionFactoryUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Worker class for saving destinations
 */
public class PersistanceWorker {

    /** Logger */
    private static Logger _logger = LogManager.getRootLogger();

    /** Payload flag */
    private final static String PAYLOAD_FLAG = "-payload";

    /**
     * Main
     * 
     * @param aArgs arguments
     */
    public static void main(String[] aArgs) {

        for (String arg : aArgs) {
            _logger.info(arg);
        }

        // obtain the filename from the passed arguments
        int payloadPos = -1;
        for (int i = 0; i < aArgs.length; i++) {
            if (aArgs[i].equals(PAYLOAD_FLAG)) {
                payloadPos = i + 1;
                break;
            }
        }
        if (payloadPos >= aArgs.length) {
            System.err.println("Invalid PAYLOAD_FLAG argument.");
            System.exit(1);
        }
        if (payloadPos == -1) {
            System.err.println("No PAYLOAD_FLAG argument.");
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

        System.out.println(payload);

        // parse the string as JSON
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject passed_args = parser.parse(payload).getAsJsonObject();

        // print the output of the "arg1" property of the passed JSON object
        String json = passed_args.get("arg1").getAsString();
        Destination destination = gson.fromJson(json, Destination.class);

        Session session =
                SessionFactoryUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        session.merge(destination);
        session.getTransaction().commit();

        _logger.info("Saved destination: " + json);
    }

    /**
     * Reads the PAYLOAD_FLAG
     * 
     * @param aPath path to the PAYLOAD_FLAG
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
