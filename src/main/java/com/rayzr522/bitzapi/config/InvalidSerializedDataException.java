
package com.rayzr522.bitzapi.config;

public class InvalidSerializedDataException extends Exception {

    private static final long serialVersionUID = 2814748032345712504L;

    public InvalidSerializedDataException() {
        super("The serialized data provided was invalid!");
    }

}
