
import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;  // Import the IOException class to handle errorspackage com.example.homework;

import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;  // Import the IOException class to handle errors


public class search {

     static class Pair {
        int x;
        int y;
        public Pair(){

        }
        public Pair(int a, int b){
            this.x = a;
            this.y = b;
        }
        public boolean isEqual(Pair another){
            if(this.x == another.x && this.y == another.y){
                return true;
            }
            return false;
        }
    }
    static class Cell implements Comparable<Cell>{
        Pair coordinate;
        int height;
        Cell predecessor;
        int depth;
        int muddiness;
        int cost;
        int heuristic;
        boolean visited; //bfs & ucs closed
        boolean openQ;
        public Cell(Pair c){
            this.coordinate = c;
        }
        public Cell(Cell a){
            this.heuristic = a.heuristic;
            this.muddiness = a.muddiness;
            this.cost = a.cost;
            this.coordinate = a.coordinate;
            this.predecessor = a.predecessor;
            this.openQ = a.openQ;
            this.visited = a.visited;
            this.height = a.height;
            this.depth = a.depth;
        }

        @Override
        public int compareTo(Cell another){
            if((this.cost +  this.heuristic)== (another.cost + another.heuristic)){
                return 0;
            }
            return (this.cost +  this.heuristic) < (another.cost + another.heuristic)? -1 : 1;
        }
    }
    public boolean isPossiblePath(Cell current, Cell next, int max_steep){
        return Math.abs(current.height - next.height) <= max_steep ;
    }

    public StringBuilder traceBack(ArrayList<ArrayList<Cell>> map, Pair end, Pair start){
        StringBuilder temp = new StringBuilder();
        Pair current = end;
        temp.append(new StringBuffer(Integer.toString(end.x)).reverse());
        temp.append(",");
        temp.append(new StringBuffer(Integer.toString(end.y)).reverse());
        while(!map.get(current.x).get(current.y).predecessor.coordinate.isEqual(start)){
            current = map.get(current.x).get(current.y).predecessor.coordinate;
            temp.append(" ");
            temp.append(new StringBuffer(Integer.toString(current.x)).reverse());
            temp.append(",");
            temp.append(new StringBuffer(Integer.toString(current.y)).reverse());
        }
        temp.append(" ");
        temp.append(new StringBuffer(Integer.toString(start.x)).reverse());
        temp.append(",");
        temp.append(new StringBuffer(Integer.toString(start.y)).reverse());
        return temp.reverse();
    }
    public ArrayList<ArrayList<Cell>> deepCopyMap(ArrayList<ArrayList<Cell>> map){
         ArrayList<ArrayList<Cell>> result = new ArrayList<>();
         for (ArrayList<Cell> a: map){
             ArrayList<Cell> row = new ArrayList<>();
             for(Cell b : a){
                 row.add(new Cell(b));
             }
             result.add(row);
         }
         return result;
    }

    public ArrayList<ArrayList<Cell>> BFS(ArrayList<ArrayList<Cell>> map,int col_size, int row_size, Pair start, Pair end, int max_steep){
        //ArrayList<Pair> result = new ArrayList<>();
        ArrayList<ArrayList<Cell>> map_c = deepCopyMap(map);
        Queue<Cell> possible_state = new ArrayDeque<>();
        possible_state.offer(map_c.get(start.x).get(start.y));
        map_c.get(start.x).get(start.y).visited = true;
        while(!possible_state.isEmpty()){
            Cell current_state = possible_state.poll();

            if(current_state.coordinate.isEqual(end)){
                return map_c;
            }
            int current_x = current_state.coordinate.x;
            int current_y = current_state.coordinate.y;
            //Cell next_node;
            //add north
            if(current_x - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y), max_steep)&& !map_c.get(current_x-1).get(current_y).visited){
                map_c.get(current_x-1).get(current_y).predecessor = current_state;
                map_c.get(current_x-1).get(current_y).visited = true;
                possible_state.offer(map_c.get(current_x-1).get(current_y));

            }
            //add west
            if(current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x).get(current_y-1), max_steep)&& !map_c.get(current_x).get(current_y-1).visited){

                map_c.get(current_x).get(current_y-1).predecessor = current_state;
                map_c.get(current_x).get(current_y-1).visited = true;
                possible_state.offer(map_c.get(current_x).get(current_y-1));
            }
            //add east
            if(current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x).get(current_y+1), max_steep)&&!map_c.get(current_x).get(current_y+1).visited){
                map_c.get(current_x).get(current_y+1).predecessor = current_state;
                map_c.get(current_x).get(current_y+1).visited = true;
                possible_state.offer(map_c.get(current_x).get(current_y+1));
            }
            //add south
            if(current_x + 1 < row_size && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y), max_steep) && !map_c.get(current_x+1).get(current_y).visited){
                map_c.get(current_x+1).get(current_y).predecessor = current_state;
                map_c.get(current_x+1).get(current_y).visited = true;
                possible_state.offer(map_c.get(current_x+1).get(current_y));

            }
            //add north-west
            if(current_x - 1 >= 0 && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y-1), max_steep) && !map_c.get(current_x-1).get(current_y-1).visited){
                map_c.get(current_x-1).get(current_y-1).predecessor = current_state;
                map_c.get(current_x-1).get(current_y-1).visited = true;
                possible_state.offer(map_c.get(current_x-1).get(current_y-1));
            }
            //add north-east
            if(current_x - 1 >= 0 && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y+1), max_steep) && !map_c.get(current_x-1).get(current_y+1).visited){
                map_c.get(current_x-1).get(current_y+1).predecessor = current_state;
                map_c.get(current_x-1).get(current_y+1).visited = true;
                possible_state.offer(map_c.get(current_x-1).get(current_y+1));
            }
            //add south-west
            if(current_x + 1 < row_size && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y-1), max_steep)&& !map_c.get(current_x+1).get(current_y-1).visited){
                map_c.get(current_x+1).get(current_y-1).predecessor = current_state;
                map_c.get(current_x+1).get(current_y-1).visited = true;
                possible_state.offer(map_c.get(current_x+1).get(current_y-1));
            }
            //add south-east
            if(current_x + 1  < row_size && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y+1), max_steep) && !map_c.get(current_x+1).get(current_y+1).visited){
                map_c.get(current_x+1).get(current_y+1).predecessor = current_state;
                map_c.get(current_x+1).get(current_y+1).visited = true;
                possible_state.offer(map_c.get(current_x+1).get(current_y+1));
            }


        }
        return null;
    }

    public ArrayList<ArrayList<Cell>> UCS(ArrayList<ArrayList<Cell>> map,int col_size, int row_size, Pair start, Pair end, int max_steep){
        ArrayList<ArrayList<Cell>> map_c = deepCopyMap(map);

         PriorityQueue<Cell> open = new PriorityQueue<>();
         Queue<Cell> close = new ArrayDeque();

         map_c.get(start.x).get(start.y).openQ = true;
         open.offer(new Cell(map_c.get(start.x).get(start.y)));

         while(!open.isEmpty()){
             Cell current_state = open.poll();
             if(current_state.coordinate.isEqual(end)){
                 map_c.get(current_state.coordinate.x).get(current_state.coordinate.y).cost = current_state.cost; //new added for test
                 return map_c; //not sure
             }
             Queue<Cell> children = new ArrayDeque<>();

             int current_x = current_state.coordinate.x;
             int current_y = current_state.coordinate.y;
             //Cell next_node;
             //add north
             if(current_x - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y), max_steep)){
                 map_c.get(current_x-1).get(current_y).cost = current_state.cost + 10;
                 children.offer(map_c.get(current_x-1).get(current_y));
             }
             //add west
             if(current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x).get(current_y-1), max_steep)){
                 map_c.get(current_x).get(current_y-1).cost = current_state.cost + 10;
                 children.offer(map_c.get(current_x).get(current_y-1));
             }
             //add east
             if(current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x).get(current_y+1), max_steep)){
                 map_c.get(current_x).get(current_y+1).cost = current_state.cost + 10;
                 children.offer(map_c.get(current_x).get(current_y+1));
             }
             //add south
             if(current_x + 1 < row_size && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y), max_steep) ){
                 map_c.get(current_x+1).get(current_y).cost = current_state.cost + 10;
                 children.offer(map_c.get(current_x+1).get(current_y));
             }
             //add north-west
             if(current_x - 1 >= 0 && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y-1), max_steep)){
                 map_c.get(current_x-1).get(current_y-1).cost = current_state.cost + 14;
                 children.offer(map_c.get(current_x-1).get(current_y-1));
             }
             //add north-east
             if(current_x - 1 >= 0 && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x-1).get(current_y+1), max_steep)){
                 map_c.get(current_x-1).get(current_y+1).cost = current_state.cost + 14;
                 children.offer(map_c.get(current_x-1).get(current_y+1));
             }
             //add south-west
             if(current_x + 1 < row_size && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y-1), max_steep)){
                 map_c.get(current_x+1).get(current_y-1).cost = current_state.cost + 14;
                 children.offer(map_c.get(current_x+1).get(current_y-1));
             }
             //add south-east
             if(current_x + 1  < row_size && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x+1).get(current_y+1), max_steep) ){
                 map_c.get(current_x+1).get(current_y+1).cost = current_state.cost + 14;
                 children.offer(map_c.get(current_x+1).get(current_y+1));
             }
            while(!children.isEmpty()){
                Cell child = children.poll();
                if(!child.openQ && !child.visited){
                    map_c.get(child.coordinate.x).get(child.coordinate.y).openQ = true;
                    map_c.get(child.coordinate.x).get(child.coordinate.y).predecessor = current_state;
                    open.offer(new Cell(child));
                }
                else if(child.openQ){
                    Iterator<Cell> value = open.iterator();
                    while(value.hasNext()){
                        Cell temp = value.next();
                        if(child.coordinate.isEqual(temp.coordinate) && child.cost < temp.cost){
                            value.remove();
                            map_c.get(child.coordinate.x).get(child.coordinate.y).predecessor = current_state;
                            open.offer(new Cell(child));
                            break;
                        }
                    }
                }
                else
                    continue;  //?

            }
            close.offer(current_state);

         }
         return null;
    }

    public ArrayList<ArrayList<Cell>> aStar(ArrayList<ArrayList<Cell>> map,int col_size, int row_size, Pair start, Pair end, int max_steep) {
        ArrayList<ArrayList<Cell>> map_c = deepCopyMap(map);

        PriorityQueue<Cell> open = new PriorityQueue<>();
        Queue<Cell> close = new ArrayDeque();

        //calculate heuristic value
        for(int i = 0; i < row_size; i++){
            for(int j = 0; j < col_size; j++){
                map_c.get(i).get(j).heuristic = 10 * (int)Math.sqrt(Math.pow(Math.abs(end.x - i),2)+ Math.pow(Math.abs(end.y-j),2));
            }
        }
        map_c.get(start.x).get(start.y).openQ = true;
        open.offer(new Cell(map_c.get(start.x).get(start.y)));
        while(!open.isEmpty()) {
            Cell current_state = open.poll();
            if (current_state.coordinate.isEqual(end)) {
                map_c.get(current_state.coordinate.x).get(current_state.coordinate.y).cost = current_state.cost; //new added for test
                return map_c; //not sure
            }
            Queue<Cell> children = new ArrayDeque<>();

            int current_x = current_state.coordinate.x;
            int current_y = current_state.coordinate.y;
            //Cell next_node;
            //add north
            if (current_x - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x - 1).get(current_y), max_steep)) {
                map_c.get(current_x - 1).get(current_y).cost = current_state.cost + 10 + map_c.get(current_x - 1).get(current_y).muddiness + Math.abs(map_c.get(current_x - 1).get(current_y).height - current_state.height);
                children.offer(map_c.get(current_x - 1).get(current_y));
            }
            //add west
            if (current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x).get(current_y - 1), max_steep)) {
                map_c.get(current_x).get(current_y - 1).cost = current_state.cost + 10 + map_c.get(current_x).get(current_y - 1).muddiness + Math.abs(map_c.get(current_x).get(current_y - 1).height- current_state.height);
                children.offer(map_c.get(current_x).get(current_y - 1));
            }
            //add east
            if (current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x).get(current_y + 1), max_steep)) {
                map_c.get(current_x).get(current_y + 1).cost = current_state.cost + 10 + map_c.get(current_x).get(current_y + 1).muddiness + Math.abs(map_c.get(current_x).get(current_y + 1).height- current_state.height);
                children.offer(map_c.get(current_x).get(current_y + 1));
            }
            //add south
            if (current_x + 1 < row_size && isPossiblePath(current_state, map_c.get(current_x + 1).get(current_y), max_steep)) {
                map_c.get(current_x + 1).get(current_y).cost = current_state.cost + 10 + map_c.get(current_x + 1).get(current_y).muddiness + Math.abs(map_c.get(current_x + 1).get(current_y).height- current_state.height);
                children.offer(map_c.get(current_x + 1).get(current_y));
            }
            //add north-west
            if (current_x - 1 >= 0 && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x - 1).get(current_y - 1), max_steep)) {
                map_c.get(current_x - 1).get(current_y - 1).cost = current_state.cost + 14 + map_c.get(current_x - 1).get(current_y - 1).muddiness + Math.abs(map_c.get(current_x - 1).get(current_y - 1).height- current_state.height);
                children.offer(map_c.get(current_x - 1).get(current_y - 1));
            }
            //add north-east
            if (current_x - 1 >= 0 && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x - 1).get(current_y + 1), max_steep)) {
                map_c.get(current_x - 1).get(current_y + 1).cost = current_state.cost + 14 + map_c.get(current_x - 1).get(current_y + 1).muddiness + Math.abs(map_c.get(current_x - 1).get(current_y + 1).height- current_state.height);
                children.offer(map_c.get(current_x - 1).get(current_y + 1));
            }
            //add south-west
            if (current_x + 1 < row_size && current_y - 1 >= 0 && isPossiblePath(current_state, map_c.get(current_x + 1).get(current_y - 1), max_steep)) {
                map_c.get(current_x + 1).get(current_y - 1).cost = current_state.cost + 14 + map_c.get(current_x + 1).get(current_y - 1).muddiness + Math.abs(map_c.get(current_x + 1).get(current_y - 1).height- current_state.height);
                children.offer(map_c.get(current_x + 1).get(current_y - 1));
            }
            //add south-east
            if (current_x + 1 < row_size && current_y + 1 < col_size && isPossiblePath(current_state, map_c.get(current_x + 1).get(current_y + 1), max_steep)) {
                map_c.get(current_x + 1).get(current_y + 1).cost = current_state.cost + 14 + map_c.get(current_x + 1).get(current_y + 1).muddiness + Math.abs(map_c.get(current_x + 1).get(current_y + 1).height- current_state.height);
                children.offer(map_c.get(current_x + 1).get(current_y + 1));
            }
            while (!children.isEmpty()) {
                Cell child = children.poll();
                if (!child.openQ && !child.visited) {
                    map_c.get(child.coordinate.x).get(child.coordinate.y).openQ = true;
                    map_c.get(child.coordinate.x).get(child.coordinate.y).predecessor = current_state;
                    open.offer(new Cell(child));
                } else if (child.openQ) {
                    Iterator<Cell> value = open.iterator();
                    while (value.hasNext()) {
                        Cell temp = value.next();
                        if (child.coordinate.isEqual(temp.coordinate) && child.cost < temp.cost) {
                            value.remove();
                            map_c.get(child.coordinate.x).get(child.coordinate.y).predecessor = current_state;
                            open.offer(new Cell(child));
                            break;
                        }
                    }
                } else
                    continue;  //?

            }
            close.offer(current_state);
        }
        return null;

    }



    //main method
    public static void main(String[] args){
        search solution = new search();
        ArrayList<ArrayList<ArrayList<Cell>>> result = new ArrayList<>();
        Pair start_point = new Pair();
        ArrayList<Pair> endPoint = new ArrayList<>();
        ArrayList<ArrayList<Cell>> map = new ArrayList<>();
        try {
            File myInput = new File("input.txt");
            Scanner myReader = new Scanner(myInput);
            String search_algo = myReader.nextLine();
            String size = myReader.nextLine();
            String start = myReader.nextLine();
            String max_steep_s = myReader.nextLine();
            String n = myReader.nextLine();
            int n_int = Integer.parseInt(n);
            //covert endpoint

            for(int i = 0; i<n_int; i++){
                String temp = myReader.nextLine();
                String[] end = temp.split(" ");
                Pair end_point = new Pair(Integer.parseInt(end[1]),Integer.parseInt(end[0]));
                endPoint.add(end_point);
            }
            //convert size
            String[] sizes = size.split(" ");
            int row = Integer.parseInt(sizes[1]);
            int col = Integer.parseInt(sizes[0]);
            //convert start point
            String[] starts = start.split(" ");
            start_point = new Pair(Integer.parseInt(starts[1]),Integer.parseInt(starts[0]));
            //convert max_steep
            int max_steep = Integer.parseInt(max_steep_s);
            //load map

            for(int i = 0; i < row; i++){
                ArrayList<Cell> temp_row = new ArrayList<>();
                String s = myReader.nextLine();
                String[] ss = s.split(" ");
                for(int j = 0; j < col; j++){
                    Cell temp_cell = new Cell(new Pair(i,j));
                    int value = Integer.parseInt(ss[j]);
                    if(value < 0){
                        temp_cell.height = Math.abs(value);
                    }
                    else{
                        temp_cell.muddiness = value;
                    }
                    temp_row.add(temp_cell);
                }
                map.add(temp_row);
            }

            if(search_algo.equals("BFS")){
                for(int i = 0; i < endPoint.size(); i++){
                    result.add(solution.BFS(map,col,row,start_point,endPoint.get(i),max_steep));
                }

            }
            else if(search_algo.equals("UCS")){
                for(int i = 0; i < endPoint.size(); i++){
                    result.add(solution.UCS(map,col,row,start_point,endPoint.get(i),max_steep));
                }
            }
            else if(search_algo.equals("A*")){
                for(int i = 0; i < endPoint.size(); i++){
                    result.add(solution.aStar(map,col,row,start_point,endPoint.get(i),max_steep));
                }
            }





            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            int counter = 0;
            FileWriter myWriter = new FileWriter("output.txt");

            while(counter < endPoint.size()){
                if(result.get(counter) == null){
                    myWriter.write("FAIL");
                }
                else{
                    myWriter.write(solution.traceBack(result.get(counter),endPoint.get(counter),start_point).toString());
                }
                if(counter != endPoint.size()-1){
                    myWriter.write("\n");
                }
                counter++;
            }




            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
