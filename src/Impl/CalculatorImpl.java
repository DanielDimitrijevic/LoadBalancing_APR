package Impl;

import java.lang.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class CalculatorImpl implements Calculator {
    public CalculatorImpl ()
        throws RemoteException
    {
    }

    public String pi (int iterations)
        throws RemoteException
    {
        double res = 0;
        for (int i = 1; i < iterations; i += 4) {
            res += 1.0/i - 1.0/(i+2);
        }
        /*
          try {
          PrintStream o = new PrintStream (
          new FileOutputStream ("/dev/pts/1"));
          o.println ("pi");
          Thread.sleep (iterations*1000);
          } catch (Exception e) {
          }
        */
        return "" +4*res;
    }
};
