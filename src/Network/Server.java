package Network;

import gameEngine.GameEngine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ObjectOutputStream out1, out2;
    private ObjectInputStream in1, in2;

    private boolean gameOver = false;

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);

        Socket first = serverSocket.accept();
        Socket second = serverSocket.accept();

        out1 = new ObjectOutputStream(first.getOutputStream());
        out2 = new ObjectOutputStream(second.getOutputStream());

        in1 = new ObjectInputStream(first.getInputStream());
        in2 = new ObjectInputStream(second.getInputStream());

        Thread firstPlayer = new Thread() {
            @Override
            public void run() {
                try {
                    firstPlayerListener();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread secondPlayer = new Thread() {
            @Override
            public void run() {
                try {
                    secondPlayerListener();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        firstPlayer.start();
        secondPlayer.start();
    }

    private void firstPlayerListener() throws Exception{
        while (!gameOver) {
            String input = in1.readUTF();

            if (input.charAt(0) == 'X') {
                gameOver = true;

                out1.writeUTF(input);
                out2.writeUTF(input);

                out1.flush();
                out2.flush();
            } else {
                out1.writeUTF("S" + input);
                out2.writeUTF("O" + input);

                out1.flush();
                out2.flush();
            }
        }
    }

    private void secondPlayerListener() throws Exception{
        while (!gameOver) {
            String input = in2.readUTF();

            if (input.charAt(0) == 'X') {
                gameOver = true;

                out2.writeUTF(input);
                out1.writeUTF(input);

                out1.flush();
                out2.flush();
            } else {
                out2.writeUTF("S" + input);
                out1.writeUTF("O" + input);

                out1.flush();
                out2.flush();
            }
        }
    }
}
