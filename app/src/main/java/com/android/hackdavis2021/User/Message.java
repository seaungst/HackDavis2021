
package com.android.hackdavis2021.User;

public class Message
{
    String message;
    String name;
    String key;

    public Message(){}

    public Message(String message, String name)
    {
        this.message= message;
        this.name= name;
        this.key= key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    @Override
    public String toString()
    {
        return "Message{"+
                "messages= "+ message + "/n"+
                ", name= " + name + "/n"+
                ", key= " +key +
                "}";
    }

}
