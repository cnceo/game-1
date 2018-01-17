package com.game.qt.socket;

import java.io.Serializable;

/**
 * Created by heshangqiu on 2017/3/22 11:47
 */
public class RequestMessage implements Serializable {

    private static final long serialVersionUID = -681606425954480535L;

    private int command;

    private String data;

    public RequestMessage() {

    }

    public RequestMessage(int command, String data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
