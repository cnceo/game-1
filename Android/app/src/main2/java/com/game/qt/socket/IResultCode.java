package com.game.qt.socket;

import java.io.Serializable;

public interface IResultCode extends Serializable {

    int getResultCode();

    String getResultMsg();

}
