package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class gameServer {
    public static List<Socket> list = Collections.synchronizedList(new ArrayList());
    public static List<serverThread> serverThreadlist = Collections.synchronizedList(new ArrayList());
    public static int playerCount = 1;
    public static pool p = new pool();
    public static LinkedList<meld> shareTable = new LinkedList<meld>();
    public static int handindex = 0;
    public static int[] score;
    public static boolean[] isFirstHand;
    public static LinkedList<LinkedList<tile>> handcard = new LinkedList<LinkedList<tile>>();
    public static String lastplayer;
    public static boolean isEnd = false;
    public static boolean isTurnEnd = false;

    public static void main(String[] args) throws Exception {
        handcard = new LinkedList<LinkedList<tile>>();
        boolean firstMsg = true;
        String choice = "";
        String content = "";

        ServerSocket ss = new ServerSocket(30000);
        while (list.size() < 3) {
            Socket socket = ss.accept();
            list.add(socket);
            serverThreadlist.add(new serverThread(socket, playerCount));
            serverThreadlist.get(playerCount - 1).run();
            playerCount++;
        }

        for (int i = 0; i < 3; i++){
            score[i] = 0;
            isFirstHand[i] = true;
        }

        firstDraw();

        while(!isEnd){
            firstMsg = true;
            serverThread st = serverThreadlist.get(handindex);
            while (!isTurnEnd) {
                content = "";
                System.out.println(printTurn());
                content += "\n\n\n";
                content += printTurn();
                content += "\n\n";
                content += printTable();
                content += "\n\n";
                content += printHandCard();
                content += "\n\n";

                st.ps.println(content);

                if (firstMsg) {
                    content = printOption1();
                    st.ps.println(content);
                    choice = st.readFromSocket();
                    firstMsg = false;
                }else{
                    choice = "1";
                }

                if (choice == "1") {

                    content = printOption2();

                    st.ps.println(content);
                    choice = st.readFromSocket();
                    if (choice == "1") {
                        st.ps.println("Please choose the tile your want to play and split them with space: \n");
                        meld m = new meld();
                        choice = st.readFromSocket();
                        String[] str = choice.split(" ");
                        String[] tiles = Arrays.stream(str)
                                .filter(value ->
                                        value != null && value.length() > 0
                                )
                                .toArray(size -> new String[size]);
                        for (int i = 0; i < tiles.length; i++){
                            m.addTile(new tile(tiles[i]));
                        }
                        if (m.isValid()) {
                            shareTable.add(m);
                            for (int i = 0; i < m.getSize(); i++){
                                removeFromHand(m.getTile(i));
                            }
                        }
                    }else if (choice == "2"){

                    }else if (choice == "3"){

                    }else{

                    }
                    st.ps.println(content);

                } else if (choice == "2") {
                    handcard.get(handindex).add(p.draw());
                    isTurnEnd = true;
                }
                checkEnd();
            }
            goNext();
        }
    }

    public static String printTurn(){
        return "Player " + (handindex + 1) + "'s turn";
    }

    public static String printTable(){
        String string = "Melds on the table:\n";
        if (shareTable == null){
            return string;
        }
        for (int i = 0; i < shareTable.size(); i++){
            string += shareTable.get(i).toString();
            if (i != shareTable.size() - 1){
                string += "and ";
            }
        }
        return string;
    }

    public static void goNext(){
        if (handindex == playerCount - 1){
            handindex = 0;
        }else{
            handindex++;
        }
    }

    public static void firstDraw(){
        for (int i = 0; i < playerCount; i++){
            handcard.add(p.handCard());
        }
    }

    public static LinkedList<tile> getHand(int i){
        return handcard.get(i);
    }

    public static String printHandCard(){
        String string = "Your hand card:\n";
        for (int i = 0; i < handcard.get(handindex).size(); i++){
            string += handcard.get(handindex).get(i).toString();
            string += " ";
        }
        return string;
    }

    public static void checkEnd(){
        if (handcard.get(handindex).size() == 0){
            isEnd = true;
        }
    }

    public static String printOption1(){
        return "\nChoose your action:\n1) Play\n2) Draw a card and skip the turn\n";
    }

    public static String printOption2(){
        String string = "\nChoose your action:\n1) Play a new meld\n";
        if (isFirstHand[handindex]){
            string += "2) I am done and finish the turn\n";
        }else{
            string += "2) I am done and finish the turn\n3) Move a tile from a meld\n4) Add new tile to a meld\n\n";
        }
        return string;
    }

    public static boolean removeFromHand(tile t){
        for (int i = 0; i < handcard.get(handindex).size(); i++){
            if (t.color == handcard.get(handindex).get(i).color && t.point == handcard.get(handindex).get(i).point){
                handcard.get(handindex).remove(i);
                return true;
            }
        }
        return false;
    }

}