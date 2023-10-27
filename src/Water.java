import GaFr.GFStamp;
import java.util.ArrayList;
import GaFr.GFU;

public class Water extends Strip{
    ArrayList<Log[]> logs = new ArrayList<>();
    Integer randint1 =  GaFr.GFU.randint(1,3);
    Integer randint2 = GaFr.GFU.randint(1,3);
    Integer randint3 = GaFr.GFU.randint(1,3);

    void randomize(){
        randint1 =  GaFr.GFU.randint(1,3);
        randint2 = GaFr.GFU.randint(1,3);
        randint3 = GaFr.GFU.randint(1,3);
        for(int i=0;i<logs1.length;i++){
            logs1[i].velocity = randint1;
        }
        for(int i=0;i<logs2.length;i++){
            logs2[i].velocity = randint2;
        }
        for(int i=0;i<logs3.length;i++){
            logs3[i].velocity = randint3;
        }
    }

    Log[] logs1 = {
        // new Log(0, this.y, 20, false),
        new Log(200, this.y,randint1, false),
        new Log(400, this.y,randint1, false),

    };

    Log[] logs2 = {
        new Log(100, this.y, randint2, true),
        // new Log(300, this.y, 20, true),
        new Log(500, this.y, randint2 , true),
        // new Log(700, this.y, 20, true),
    };

    Log[] logs3 = {
        new Log(0, this.y, randint3 , false),
        new Log(200, this.y, randint3 , false),
        new Log(400, this.y, randint3 , false),
    };

    public Water(){
        isWater = true;
        y = 0;
        height = 225;
        background = new GFStamp("assets/water.png").resize(800,height);
        logs.add(logs1);
        logs.add(logs2);
        logs.add(logs3);
    }
    Log collided;
    public boolean checkCollision(int x,int y_off){
        if(y_off>height) y_off = height-1;
        if(y_off<height){
          int arrayIdx = y_off/75;
          for(Log log: logs.get(arrayIdx)){
            if((log.x-10<=(x-10)) && (log.x+150>=(x+10))) {collided = log; return true;}
          }
        }

        return false;
      }

    public Log getCollidedLog(){
        return collided;
    }


    public void draw(){
       for(int i = 0; i < logs1.length; i++){
            logs.get(0)[i].y = y + 34;
            logs.get(0)[i].draw();
        }

        for(int i = 0; i < logs2.length; i++){
             logs.get(1)[i].y = y + 108;
             logs.get(1)[i].draw();
         }

        for(int i = 0; i < logs3.length; i++){
            logs.get(2)[i].y = y + 180;
            logs.get(2)[i].draw();
        }
    }
}