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
    public static boolean[] isFirstHand = new boolean[3];
    public static LinkedList<LinkedList<tile>> handcard = new LinkedList<LinkedList<tile>>();
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
            serverThreadlist.get(playerCount - 1).ps.println("You joined the game as Player " + playerCount);
            playerCount++;
        }
        playerCount -= 1;

        //isFirstHand = new boolean[3];
        for (int i = 0; i < 3; i++){
            isFirstHand[i] = true;
        }

        firstDraw();

        while(!isEnd){
            firstMsg = true;
            isTurnEnd = false;
            for (int i = 0; i < playerCount; i++){
                serverThreadlist.get(i).ps.println(printTurn());
            }
            serverThread st = serverThreadlist.get(handindex);
            System.out.println(printTurn());
            while (!isTurnEnd) {
                content = "";
                content += "\n";
                content += printTable();
                content += "\n\n";
                content += printHandCard();
                content += "\n";

                st.ps.println(content);

                if (firstMsg) {
                    content = printOption1();
                    st.ps.println(content);
                    choice = st.readFromSocket();
                    firstMsg = false;
                }else{
                    choice = "1";
                }

                if (choice.equals("1")) {

                    content = printOption2();

                    st.ps.println(content);
                    choice = st.readFromSocket();
                    if (choice.equals("1")) {
                        st.ps.println("Please choose the tiles your want to play and split them with space: \n");
                        choice = st.readFromSocket();
                        addNewMeld(choice);
                    }else if (choice.equals("2")){
                        isTurnEnd = true;
                    }else if (choice.equals("3")){
                        st.ps.println("Please choose the meld that your want to move from: \n");
                        int c1 = Integer.parseInt(st.readFromSocket()) - 1;
                        st.ps.println("Please choose the tile your want to move (one tile only): \n");
                        choice = st.readFromSocket();
                        st.ps.println("Please choose the meld that your want to move to: \n");
                        int c2 = Integer.parseInt(st.readFromSocket()) - 1;
                        moveToMeld(choice, c1, c2);
                    }else if (choice.equals("4")){
                        st.ps.println("Please choose the meld that your want to move to: \n");
                        int c1 = Integer.parseInt(st.readFromSocket()) - 1;
                        st.ps.println("Please choose the tile your want to add (one tile only): \n");
                        choice = st.readFromSocket();
                        addOneMeld(choice, c1);
                    }
                    checkEnd();

                } else if (choice.equals("2")) {
                    drawOne();
                    isTurnEnd = true;
                }

            }
            refreshTable();

            goNext();
        }

        content = countScore();
        for (int i = 0; i < playerCount; i++){
            serverThreadlist.get(i).ps.println(content);
        }

        System.out.println(content);
    }

    public static void drawOne(){
        handcard.get(handindex).add(p.draw());
    }

    public static String printTurn(){
        return "\nPlayer " + (handindex + 1) + "'s turn\n";
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

    public static void addNewMeld(String choice){
        meld m = new meld();
        String[] str = choice.split(" ");
        String[] tiles = Arrays.stream(str)
                .filter(value ->
                        value != null && value.length() > 0
                )
                .toArray(size -> new String[size]);
        for (int i = 0; i < tiles.length; i++){
            tile t = new tile(tiles[i]);
            t.played = true;
            m.addTile(t);
        }
        if (m.isValid() && isInitialPass(m)) {
            shareTable.add(m);
            for (int i = 0; i < m.getSize(); i++){
                removeFromHand(m.getTile(i));
            }
        }
    }

    public static boolean isInitialPass(meld m){
        if (!isFirstHand[handindex]){
            return true;
        }else{
            int sum = 0;
            for (int i = 0; i < m.getSize(); i++){
                sum += m.getTile(i).point;
            }
            if (sum >= 30){
                isFirstHand[handindex] = false;
                return true;
            }else{
                serverThreadlist.get(handindex).ps.println("insufficient total for initial tiles");
                return false;
            }
        }
    }

    public static boolean removeFromHand(tile t){
        for (int i = 0; i < handcard.get(handindex).size(); i++){
            if (t.isJoker && handcard.get(handindex).get(i).isJoker){
                handcard.get(handindex).remove(i);
                return true;
            }
            if (t.color == handcard.get(handindex).get(i).color && t.point == handcard.get(handindex).get(i).point){
                handcard.get(handindex).remove(i);
                return true;
            }
        }
        return false;
    }

    public static void addOneMeld(String choice, int m){
        if (m > shareTable.size() - 1){
            return;
        }
        String[] str = choice.split(" ");
        tile t = new tile(str[0]);
        boolean flag = false;
        meld mc = shareTable.get(m).clone();
        mc.addTile(t);
        if (mc.isValid()){
            flag = true;
        }
        if (flag) {
            if (removeFromHand(t)){
                t.played = true;
                shareTable.get(m).addTile(t);
            }
        }
    }

    public static void moveToMeld(String choice, int pm, int nm){
        if ((pm > shareTable.size() - 1) || (nm > shareTable.size() - 1) || pm == nm){
            return;
        }
        String[] str = choice.split(" ");
        tile t = new tile(str[0]);
        boolean flag = false;
        meld m = shareTable.get(nm).clone();
        m.addTile(t);
        if (m.isValid()){
            flag = true;
        }
        if (flag) {
            if (shareTable.get(pm).moveTile(t)) {
                t.moved = true;
                shareTable.get(nm).addTile(t);
            }
        }
    }

    public static void refreshTable(){
        for (int i = 0; i < shareTable.size(); i++){
            for (int j = 0; j < shareTable.get(i).getSize(); j++){
                shareTable.get(i).getTile(j).played = false;
                shareTable.get(i).getTile(j).moved = false;
            }
        }
    }

    public static String countScore(){
        int[] score = new int [playerCount];
        String content = "Game over. Player ";
        int winner = 1;
        int finalScore = 0;
        for (int i = 0; i < handcard.size(); i++){
            if (handcard.get(i).size() == 0){
                winner = i + 1;
                content += winner + " is the winner of the game. \nFinal Score:\n";
            }else{
                for (int j = 0; j < handcard.get(i).size(); j++){
                    if (handcard.get(i).get(j).isJoker){
                        score[i] -= 30;
                        finalScore += 30;
                    } else {
                        score[i] -= handcard.get(i).get(j).point;
                        finalScore += handcard.get(i).get(j).point;
                    }
                }
            }
        }
        score[winner - 1] = finalScore;
        for (int i = 0; i < handcard.size(); i++){
            content += "Player " + (i + 1) +" (" + score[i] + ")\n";
        }
        return content;
    }

}