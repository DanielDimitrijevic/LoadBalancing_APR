package Impl;

import java.rmi.*;

public interface Calculator extends Remote {
    public String pi (int iterations, Long id)throws RemoteException;
}