package J1030414313;
import java.io.*;
public class Map {

    private final int canCoverBrick = 0;//可以通过的砖
    private final int notMoveBrick = 1;//不能通过且不能推动的砖
    private final int box = 2;//箱子
    private final int targetBrick = 3;//目标砖
    private final int successfulBox = 4;//已经移动到目标地点的砖
    private final int people = 5;//人
    private final int blackBrick = 6;//地图外的砖
    private final int peopleAndTargetBrick = 7;//人与目标重合的砖

    private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;

    public String fileAddress;
    private int column = 0;
    private int row = 0;
    private int[][] map;
    private int[][] emptyMap;//除去人的初始地图
    public int[][] dynamicMap;//每次移动后得到的地图
    public int[][] tempDynamicMap;//临时动态地图，与变动一步之后的地图对比，得到变动的砖块

    Map(String fileAaddress) throws IOException{
        this.fileAddress = fileAaddress;
        countColumn();
        countRow();
        obtainMap();
        obtainEmptyMap();
        obtainDynamicMap();
    }
    public void reSetMap(){
        obtainEmptyMap();
        obtainDynamicMap();
        obtainTempDynamicMap();
        return;
    }
    private int countColumn() throws IOException{
        FileReader mapFile = new FileReader(this.fileAddress);
        do{
            int c = mapFile.read();
            if(c != -1 && c!= 13){
                this.column++;
            }
            else break;
        }while(true);
        mapFile.close();
        return this.column;
    }
    private int countRow() throws IOException{
        FileReader mapFile = new FileReader(this.fileAddress);
        do{
            int c = mapFile.read();
            if(c == -1){
                this.row++;
//                this.mapFile.
                mapFile.close();
                return this.row;
            }
            if(c == 13 || c ==10) {
                this.row++;
                c = mapFile.read();
            }
        }while(true);
    }
    public int getColumn(){
        return this.column;
    }
    public int getRow(){
        return this.row;
    }
    private void obtainMap() throws IOException{
        FileReader mapFile = new FileReader(this.fileAddress);
        map = new int[this.row][this.column];
        for( int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                int c = mapFile.read();
                if(c == 13){
                    c = mapFile.read();
                    c = mapFile.read();
                }
                this.map[i][j] = c-48;
            }
        }
        mapFile.close();
        return ;
    }
    private void setDynamicMapPoint(int x,int y,int type){
        this.dynamicMap[x][y] = type;
        return ;
    }
    private int getPeopleX(){
        for(int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                if(this.dynamicMap[i][j] == 5 || this.dynamicMap[i][j] == 7){
                    return j;
                }
            }
        }
        return -1;
    }
    private int getPeopleY(){
        for(int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                if(this.dynamicMap[i][j] == 5 || this.dynamicMap[i][j] == 7){
                    return i;
                }
            }
        }
        return -1;
    }
    private void obtainEmptyMap(){
        this.emptyMap = new int[getRow()][getColumn()];
        for(int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                if(this.map[i][j] == 2 || this.map[i][j] == 5){
                    this.emptyMap[i][j] = 0;
                }
                else this.emptyMap[i][j] = this.map[i][j];
            }
        }
    }
    private void obtainDynamicMap(){
        this.dynamicMap = new int[this.row][this.column];
        for(int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                 this.dynamicMap[i][j] = this.map[i][j];
            }
        }
    }
    private void obtainTempDynamicMap(){
        this.tempDynamicMap = new int[this.row][this.column];
        for(int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                this.tempDynamicMap[i][j] = this.dynamicMap[i][j];
            }
        }
    }
    private void movePeopleUp(){
        int peopleX = this.getPeopleX();
        int peopleY = this.getPeopleY();
        if(peopleY > 0){
            switch(this.dynamicMap[peopleY-1][peopleX]){
                case 0:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY-1,peopleX,people);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 3:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY-1,peopleX,peopleAndTargetBrick);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 2:
                case 4:
                    obtainTempDynamicMap();
                    if(this.dynamicMap[peopleY-2][peopleX] == 0 ){
                        setDynamicMapPoint(peopleY-2,peopleX,box);
                        setDynamicMapPoint(peopleY-1,peopleX,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }

                    }
                    else if(this.dynamicMap[peopleY-2][peopleX] == 3){
                        setDynamicMapPoint(peopleY-2,peopleX,successfulBox);
                        setDynamicMapPoint(peopleY-1,peopleX,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }
                    }
                    break;
            }
        }
    }
    private void movePeopleDown(){
        int peopleX = this.getPeopleX();
        int peopleY = this.getPeopleY();
        if(peopleY < this.row){
            switch(this.dynamicMap[peopleY+1][peopleX]){
                case 0:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY+1,peopleX,people);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 3:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY+1,peopleX,peopleAndTargetBrick);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 2:
                case 4:
                    obtainTempDynamicMap();
                    if(this.dynamicMap[peopleY+2][peopleX] == 0 ){
                        setDynamicMapPoint(peopleY+2,peopleX,box);
                        setDynamicMapPoint(peopleY+1,peopleX,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }

                    }
                    else if(this.dynamicMap[peopleY+2][peopleX] == 3){
                        setDynamicMapPoint(peopleY+2,peopleX,successfulBox);
                        setDynamicMapPoint(peopleY+1,peopleX,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleX,peopleX,targetBrick);
                        }
                    }
                    break;
            }
        }
    }
    private void movePeopleLeft(){
        int peopleX = this.getPeopleX();
        int peopleY = this.getPeopleY();
        if(peopleX > 0){
            switch(this.dynamicMap[peopleY][peopleX-1]){
                case 0:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY,peopleX-1,people);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 3:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY,peopleX-1,peopleAndTargetBrick);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 2:
                case 4:
                    obtainTempDynamicMap();

                    if(this.dynamicMap[peopleY][peopleX-2] == 0 ){
                        setDynamicMapPoint(peopleY,peopleX-2,box);
                        setDynamicMapPoint(peopleY,peopleX-1,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }

                    }
                    else if(this.dynamicMap[peopleY][peopleX-2] == 3){
                        setDynamicMapPoint(peopleY,peopleX-2,successfulBox);
                        setDynamicMapPoint(peopleY,peopleX-1,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }
                    }
                    break;
            }
        }
    }
    private void movePeopleRight(){
        int peopleX = this.getPeopleX();
        int peopleY = this.getPeopleY();
        if(peopleX < this.column){
            switch(this.dynamicMap[peopleY][peopleX+1]){
                case 0:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY,peopleX+1,people);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 3:
                    obtainTempDynamicMap();
                    setDynamicMapPoint(peopleY,peopleX+1,peopleAndTargetBrick);
                    if(this.emptyMap[peopleY][peopleX] == 0){
                        setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                    }
                    else if(this.emptyMap[peopleY][peopleX] == 3){
                        setDynamicMapPoint(peopleY,peopleX,targetBrick);
                    }
                    break;
                case 2:
                case 4:
                    obtainTempDynamicMap();
                    if(this.dynamicMap[peopleY][peopleX+2] == 0 ){
                        setDynamicMapPoint(peopleY,peopleX+2,box);
                        setDynamicMapPoint(peopleY,peopleX+1,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }

                    }
                    else if(this.dynamicMap[peopleY][peopleX+2] == 3){
                        setDynamicMapPoint(peopleY,peopleX+2,successfulBox);
                        setDynamicMapPoint(peopleY,peopleX+1,people);
                        if(this.emptyMap[peopleY][peopleX] == 0){
                            setDynamicMapPoint(peopleY,peopleX,canCoverBrick);
                        }
                        else if(this.emptyMap[peopleY][peopleX] == 3){
                            setDynamicMapPoint(peopleY,peopleX,targetBrick);
                        }
                    }
                    break;
            }
        }
    }
    public void movePeople(int direction){
        int peopleX = this.getPeopleX();
        int peopleY = this.getPeopleY();
        switch(direction){
            case UP:
                this.movePeopleUp();
                break;
            case DOWN:
                this.movePeopleDown();
                break;
            case LEFT:
                this.movePeopleLeft();
                break;
            case RIGHT:
                this.movePeopleRight();
                break;
        }
    }
    public boolean isSucceed(){
        for (int i = 0;i<this.row;i++){
            for(int j = 0;j<this.column;j++){
                if(this.dynamicMap[i][j] == 2) return false;
            }
        }
        return true;
    }
}
