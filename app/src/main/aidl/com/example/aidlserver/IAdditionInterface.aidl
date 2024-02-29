// IAdditionInterface.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface IAdditionInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(in int firstNum, in int secondNum);
}