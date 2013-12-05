package Client;

import Impl.Calculator;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Client {

    public static void main (String[] args)
    {
        try {
                if(System.getSecurityManager() == null)
                                System.setSecurityManager(new SecurityManager());
                Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
            Calculator c =
                (Calculator)reg.lookup ("Calculator");
            System.out.println (c.pi(100000000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}