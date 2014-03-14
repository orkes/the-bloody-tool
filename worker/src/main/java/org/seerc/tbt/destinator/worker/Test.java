package org.seerc.tbt.destinator.worker;

import io.iron.ironworker.client.Client;
import io.iron.ironworker.client.entities.TaskEntity;
import io.iron.ironworker.client.builders.Params;
import io.iron.ironworker.client.APIException;

/**
 * 
 */
public class Test {

    /**
     * @param args
     * @throws APIException
     */
    public static void main(String[] args) throws APIException {

        Client client =
                new Client("vv1kjcZxAOZhDTKJP2e4yj0BKuY",
                        "531dd73d8819ec000900005e");
        TaskEntity t =
                client.createTask(
                        "worker",
                        Params.add("arg1", "Test").add("another_arg",
                                new String[] {"apples", "oranges"}));
        System.out.println(t.getId());

    }

}
