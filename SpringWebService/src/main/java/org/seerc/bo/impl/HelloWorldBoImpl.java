package org.seerc.bo.impl;

import org.seerc.bo.HelloWorldBo;

public class HelloWorldBoImpl implements HelloWorldBo {

    @Override
    public String getHelloWorld() {
        return "JAX-WS + Spring!";
    }

}