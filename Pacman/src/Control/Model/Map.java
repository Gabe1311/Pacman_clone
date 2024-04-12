package Control.Model;

import static Utility.Constants.CellsConst.*;

public class Map {
    private Cell[][] cells;

    public Map(){
        randomMapSize();
        makeMap();
        //printMap();
    }

    public Cell[][] getCells(){
        return cells;
    }
    public void printMap(){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                System.out.print(cells[i][j].getType()+ "    ");
            }
            System.out.println("index-----" + i);
        }

    }
    public void randomMapSize(){
        cells = new Cell[(int) (Math.random() * 90 )+10 ][(int) (Math.random() * 90 )+10 ]; // OK
    }
    public void makeMap(){
        //MAKES UPPER WALL
        int length = cells[0].length;
        int height = cells.length;
        for(int i = 0 ; i < length; i++){
            cells[0][i] = new Cell(WALL,i,0);
        }
        int previousRow =WALL;
        for(int i = 1; i < height -1; i ++){
            if(previousRow == WALL) {
                makeFreeRow(i);
                previousRow = FREE;
                continue;
            }
            if(previousRow == FREE && height - i <= 5) {
                int heightOfObstacles = height - i -2;
                previousRow = WALL;
                makeWallRow(heightOfObstacles, i);
                i += heightOfObstacles -1;

                continue;
            }
            if(previousRow == FREE && height - i > 5) {
                int heightOfObstacles = (int) (Math.random() * (4) + 1);
                makeWallRow(heightOfObstacles, i);
                previousRow = WALL;
                i += heightOfObstacles-1;
            }

        }
        for(int i = 0; i <length; i ++){
            cells[height-1][i] = new Cell(WALL,i,height-1);
        }
        correction();

    }
    public void correction(){
        for (int i = 0; i < cells.length; i ++){
            if(cells[i][cells[0].length -3].getType() == 0 && cells[i][cells[0].length -4].getType() == 1 ){
                cells[i][cells[0].length -3].setType(WALL);
            }

        }
        for(int i = 0; i < cells[0].length; i++){
            if(cells[cells.length -3][i].getType()==0 && cells[cells.length-4][i].getType() ==1){
                cells[cells.length -3][i].setType(WALL);
            }
        }

    }
    public void makeWallRow(int heightObstacles, int indexY){
        for(int x = 0; x < cells[0].length; x++){
            if(x > 2 && cells[indexY][x-1] == null){
                x--;
            }
            if(x == 0 || x == cells[0].length - 1){
                cells[indexY][x] = new Cell(WALL,x,indexY);
                continue;
            }
            if(cells[indexY][x-1].getType() == 1 || x == cells[0].length - 2){
                cells[indexY][x] = new Cell(FREE,x,indexY);
                continue;
            }
            if(cells[indexY][x-1].getType() == 0 && cells[0].length - x > 5){
                int wallLength = (int)(Math.random() * 5 + 1);
                createWall(indexY,x,wallLength);
                x+= wallLength -1;

            }else{
                createWall(indexY,x,cells[0].length-x -2);
                x += cells[0].length-x-2;
            }

        }
        for(int i = indexY; i < indexY + heightObstacles; i++){ //was indexY + 1
            for (int j = 0; j < cells[0].length;j++){

                cells[i][j]= new Cell(cells[indexY][j].getType(),j,indexY);
//                if(cells[i][j].getType() !=cells[indexY][j].getType()){
//                    System.out.println(cells[i][j].getType() + "    "+ cells[indexY][j].getType());
//                }

            }
        }

    }
    private void createWall(int y, int x,int wallLength){
        for(int i = x;i < wallLength+x; i++){
            cells[y][i] = new Cell(WALL,i,y);
        }

    }

    private void makeFreeRow(int y){
        for(int i =0; i <cells[0].length; i ++){
            if(i == 0 || i == cells[0].length - 1){
                cells[y][i] = new Cell(WALL,y,i);
            } else {
                cells[y][i]=new Cell(FREE,y,i);
            }
        }

    }
}
