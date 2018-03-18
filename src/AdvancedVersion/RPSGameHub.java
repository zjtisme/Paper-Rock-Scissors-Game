package AdvancedVersion;

import java.io.IOException;

import common.Hub;

public class RPSGameHub extends Hub{

    private RPSGameState state;

    public RPSGameHub(int port) throws IOException {
        super(port);
        state = new RPSGameState();
        setAutoreset(true);
    }

    protected void messageReceived(int playerID, Object message) {
        state.applyMessage(playerID, message);
        sendToAll(state);
    }

    protected void playerConnected(int playerID) {
        if(getPlayerList().length == 2) {
            shutdownServerSocket();
            state.startVSPlayerGame();
            sendToAll(state);
        }
    }

    protected void playerDisconnected(int playerID) {
        state.playerDisconnected = true;
        sendToAll(state);
        if(FileHandler.checkFileExist(FileHandler.logFileName)) {
            FileHandler.deleteLoginLogs();
        }
    }
}
