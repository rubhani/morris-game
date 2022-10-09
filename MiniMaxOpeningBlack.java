import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MiniMaxOpeningBlack {
    static int positions;//number of position checked
    static ArrayList<node5> immidiateMoves = new ArrayList<>(); //global array to store the first ply nodes.(all the positions after 1 move)
    static int inputPly;

    public static void main(String[] args) throws IOException {
        String inputF = args[0];
        String outputF = args[1];
        inputPly = Integer.parseInt(args[2]);

        File myObj = new File(inputF);
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        myReader.close();

        FileWriter myWriter = new FileWriter(outputF);
        node5 root = new node5(data);
        node5 a = (minMax(root, inputPly));
        String answer = getAnswer(immidiateMoves, a.value);
       // System.out.println(print(data));
        //myWriter.write(print(answer) + "\n"); //if you want a drawing of the pos uncomment this
        myWriter.write("program position: " + answer + "\nPositions evaluated by static estimation: " + positions + "\nMINIMAX estimate: " + a.value);
        myWriter.close();
    }

    //node class, has a position and a value
    static class node5 {
        String position;
        int value;

        node5(String pos){
            this.position = pos;
        }
    }

    //prints to screen and output file
    static String print(String positionArr) {
        String positionA = positionArr.replace('x', '-');
        String positionBoard = positionA.charAt(18) + "------------" + positionA.charAt(19) + "------------" + positionA.charAt(20) + "\n"
                + "|\\           |           /| \n" + "| \\          |          / |\n" + "|  " + positionA.charAt(15)
                + "---------" + positionA.charAt(16) + "---------" + positionA.charAt(17) +
                "  |\n" + "|  |\\        |        /|  | \n" + "|  | \\       |       / |  |\n" + "|  |  " + positionA.charAt(12)
                + "------" + positionA.charAt(13) + "------" + positionA.charAt(14) + "  |  |\n" + "|  |  |             |  |  |\n" +
                positionA.charAt(6) + "--" + positionA.charAt(7) + "--" + positionA.charAt(8) + "             " +
                positionA.charAt(9) + "--" + positionA.charAt(10) + "--" + positionA.charAt(11) +
                "\n|  |  |             |  |  |\n" + "|  |  " + positionA.charAt(4)
                + "-------------" + positionA.charAt(5) + "  |  |\n" +
                "|  | /               \\ |  |\n" + "|  |/                 \\|  |\n" +
                "|  " + positionA.charAt(2) + "-------------------" + positionA.charAt(3) + "  |\n" +
                "| /                     \\ |\n" + "|/                       \\|\n" + positionA.charAt(0)
                + "-------------------------" + positionA.charAt(1);

        return positionBoard;
    }

    //return an array of ints corespodin to neighbors
    static int[] neighbors(int location) {
        return switch (location) {
            case 0 -> new int[]{1, 2, 6};
            case 1 -> new int[]{0, 3, 11};
            case 2 -> new int[]{0, 3, 4, 7};
            case 3 -> new int[]{1, 2, 5, 10};
            case 4 -> new int[]{2, 5, 8};
            case 5 -> new int[]{3, 4, 9};
            case 6 -> new int[]{0, 7, 18};
            case 7 -> new int[]{2, 6, 8, 15};
            case 8 -> new int[]{4, 7, 12};
            case 9 -> new int[]{5, 10, 14};
            case 10 -> new int[]{3, 9, 11, 17};
            case 11 -> new int[]{1, 10, 20};
            case 12 -> new int[]{8, 13, 15};
            case 13 -> new int[]{12, 14, 16};
            case 14 -> new int[]{9, 13, 17};
            case 15 -> new int[]{7, 12, 16, 18};
            case 16 -> new int[]{13, 15, 17, 19};
            case 17 -> new int[]{14, 16, 20};
            case 18 -> new int[]{6, 15, 19};
            case 19 -> new int[]{16, 18, 20};
            case 20 -> new int[]{11, 17, 19};
            default -> new int[]{0, 0, 0};
        };
    }

    //returns true if a mill was closed
    static boolean closeMill(int location, char color, String pos) {
        return switch (location) {
            case 0 -> (pos.charAt(2) == color && pos.charAt(4) == color) || (pos.charAt(6) == color && pos.charAt(18) == color);
            case 1 -> (pos.charAt(3) == color && pos.charAt(5) == color) || (pos.charAt(11) == color && pos.charAt(20) == color);
            case 2 -> (pos.charAt(0) == color && pos.charAt(4) == color) || (pos.charAt(7) == color && pos.charAt(15) == color);
            case 3 -> (pos.charAt(1) == color && pos.charAt(5) == color) || (pos.charAt(10) == color && pos.charAt(17) == color);
            case 4 -> (pos.charAt(2) == color && pos.charAt(0) == color) || (pos.charAt(8) == color && pos.charAt(12) == color);
            case 5 -> (pos.charAt(1) == color && pos.charAt(3) == color) || (pos.charAt(9) == color && pos.charAt(14) == color);
            case 6 -> (pos.charAt(18) == color && pos.charAt(0) == color) || (pos.charAt(7) == color && pos.charAt(8) == color);
            case 7 -> (pos.charAt(6) == color && pos.charAt(8) == color) || (pos.charAt(2) == color && pos.charAt(15) == color);
            case 8 -> (pos.charAt(6) == color && pos.charAt(7) == color) || (pos.charAt(4) == color && pos.charAt(12) == color);
            case 9 -> (pos.charAt(5) == color && pos.charAt(14) == color) || (pos.charAt(10) == color && pos.charAt(11) == color);
            case 10 -> (pos.charAt(9) == color && pos.charAt(11) == color) || (pos.charAt(3) == color && pos.charAt(17) == color);
            case 11 -> (pos.charAt(9) == color && pos.charAt(10) == color) || (pos.charAt(1) == color && pos.charAt(20) == color);
            case 12 -> (pos.charAt(8) == color && pos.charAt(4) == color) || (pos.charAt(13) == color && pos.charAt(14) == color) ||
                    (pos.charAt(15) == color && pos.charAt(18) == color);
            case 13 -> (pos.charAt(12) == color && pos.charAt(14) == color) || (pos.charAt(16) == color && pos.charAt(19) == color);
            case 14 -> (pos.charAt(5) == color && pos.charAt(9) == color) || (pos.charAt(13) == color && pos.charAt(12) == color) ||
                    (pos.charAt(17) == color && pos.charAt(20) == color);
            case 15 -> (pos.charAt(2) == color && pos.charAt(7) == color) || (pos.charAt(16) == color && pos.charAt(17) == color) ||
                    (pos.charAt(12) == color && pos.charAt(18) == color);
            case 16 -> (pos.charAt(13) == color && pos.charAt(19) == color) || (pos.charAt(15) == color && pos.charAt(17) == color);
            case 17 -> (pos.charAt(3) == color && pos.charAt(10) == color) || (pos.charAt(15) == color && pos.charAt(16) == color) ||
                    (pos.charAt(14) == color && pos.charAt(20) == color);
            case 18 -> (pos.charAt(0) == color && pos.charAt(6) == color) || (pos.charAt(19) == color && pos.charAt(20) == color) ||
                    (pos.charAt(15) == color && pos.charAt(12) == color);
            case 19 -> (pos.charAt(13) == color && pos.charAt(16) == color) || (pos.charAt(18) == color && pos.charAt(20) == color);
            case 20 -> (pos.charAt(1) == color && pos.charAt(11) == color) || (pos.charAt(18) == color && pos.charAt(19) == color) ||
                    (pos.charAt(14) == color && pos.charAt(17) == color);
            default -> false;
        };
    }

    //adding pieces in the opening, returns array of positions created
    static ArrayList<String> generateAdd(String position, char color) {
        ArrayList<String> L = new ArrayList<>();
        String board;
        for (int i = 0; i < position.length(); i++) {
            if (position.charAt(i) == 'x') {
                board = position.substring(0, i) + color + position.substring(i + 1);
                if (closeMill(i, color, board)) {
                    generateRemove(board, L, color);
                } else {
                    L.add(board);
                }
            }
        }
        return L;
    }

    //when a mill is closed this is called to remove a piece and update L.
    static void generateRemove(String board, ArrayList<String> L, char color) {
        char colorRemove = 'x';
        if(color == 'W'){colorRemove= 'B';}
        else if(color == 'B'){colorRemove = 'W';}
        int size = L.size();
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == colorRemove) {
                if (!closeMill(i, colorRemove, board)) {
                    L.add(board.substring(0, i) + "x" + board.substring(i + 1));
                }
            }
        }
        if(size == L.size()){
            L.add(board);
        }
    }

    //num white - num black
    static int openingSestimation(String position) {
        int estimation = 0;
        for (int i = 0; i < position.length(); i++) {
            if (position.charAt(i) == 'W') {
                estimation++;
            } else if (position.charAt(i) == 'B') {
                estimation--;
            }
        }
        return estimation;
    }

    //checks the final global array and returns the first highest value position
    static String getAnswer(ArrayList<node5> arr, int value){

        for(int i = 0; i < arr.size(); i++){
            if(value == arr.get(i).value){
                return arr.get(i).position;
            }
        }
        //never gets here if no erros prior
        return "0";
    }

    //returns the node with the higher value
    static node5 checkMax(node5 first, node5 second){
        int max = Math.max(first.value, second.value);
        if(max == second.value){
            first.value = max;
        }
        return first;
    }

    //returns the node with the lower value
    static node5 checkMin(node5 first, node5 second){
        int min = Math.min(first.value, second.value);
        if(min == second.value){
            first.value = min;
        }
        return first;
    }

    static node5 maxMin(node5 x, int ply) {

        if (ply == 0) {
            x.value = openingSestimation(x.position);
            return x;
        }

        else{
            node5 temp = new node5("xxxxxxxxxxxxxxxxxxxxx");
            temp.value = Integer.MIN_VALUE;
            ArrayList<String> children = generateAdd(x.position, 'W');
            positions += children.size();
            for(int i = 0; i < children.size(); i++){
                node5 child = new node5(children.get(i));
                temp = checkMax(temp, minMax(child, ply -1));
            }

            return temp;
        }

    }

    static node5 minMax(node5 x, int ply) {

        if (ply == 0) {
            x.value = openingSestimation(x.position);
            return x;
        }

        else{
            node5 temp = new node5("xxxxxxxxxxxxxxxxxxxxx");
            temp.value = Integer.MAX_VALUE;
            ArrayList<String> children = generateAdd(x.position, 'B');
            positions += children.size();
            for(int i = 0; i < children.size(); i++){
                node5 child = new node5(children.get(i));
                temp = checkMin(temp, maxMin(child, ply -1));

                //enters here to get the nodes for the first ply
                if(ply == inputPly){

                    node5 comeOOOOn = new node5(children.get(i));
                    comeOOOOn.value = temp.value;
                    immidiateMoves.add(comeOOOOn);
                }
            }
            return temp;
        }
    }
}
