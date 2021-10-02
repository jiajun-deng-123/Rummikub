package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



public class gameServer {
    public static List<Socket> list = Collections.synchronizedList(new ArrayList());
    public static List<serverThread> serverThreadlist = Collections.synchronizedList(new ArrayList());
    public static int playerCount = 1;
    public static pool p;
    public static LinkedList<meld> shareTable;
    public static int handindex = 0;
    public static int[] score;
    public static LinkedList<LinkedList<tile>> handcard;
    public static String lastplayer;

    public static void main(String[] args) throws Exception {
        p = new pool();
        for (int i = 0; i < 3; i++){
            score[i] = 0;
        }
        handcard = new LinkedList<LinkedList<tile>>();

        ServerSocket ss = new ServerSocket(30000);
        while (list.size() < 4) {
            Socket socket = ss.accept();
            list.add(socket);
            serverThreadlist.add(new serverThread(socket, playerCount));
            serverThreadlist.get(playerCount - 1).run();
            playerCount++;
        }

    }
}