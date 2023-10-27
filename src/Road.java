import GaFr.GFStamp;
import java.util.*;

public class Road extends Strip{
    ArrayList<Car[]> cars = new ArrayList<>();
    Integer randint1 =  GaFr.GFU.randint(9,15);
    Integer randint2 = GaFr.GFU.randint(9,15);
    int randXpos = GaFr.GFU.randint(0,200);

    void randomize(){
      randint1 =  GaFr.GFU.randint(9,15);
      randint2 = GaFr.GFU.randint(9,15);
      for(int i=0;i<lane1.length;i++){
        lane1[i].velocity = randint1;
      }
      for(int i=0;i<lane2.length;i++){
          lane2[i].velocity = randint2;
      }
    }

    Car[] lane1  = {
      new Car(randXpos, this.y, randint1, true, true),
      // new Car(375, this.y, 10, false, true),
      new Car(randXpos + GaFr.GFU.randint(150,400), this.y, randint1, true, true),
      // new Car(50, this.y, 10, false, true)
    };

    Car[] lane2 = {new Car(GaFr.GFU.randint(0,600), this.y, randint2,false, false),
      // new Car(305, this.y, 10, true, false),
      // new Car(460, this.y, 10, false,false),
    };
    {
      cars.add(lane1);
      cars.add(lane2);
    }

    public boolean checkCollision(int x,int y_off){
      if(y_off>height) y_off = height-1;
      if(y_off<height){
        int arrayIdx = y_off/75;
        for(Car car : cars.get(arrayIdx)){
          if((car.x<=(x+20)) && (car.x+120>x-20)) return true;
        }
      }
      return false;
    }

    public Road(){
      height = 150;
      background = new GFStamp("assets/road.png").resize(800,height);
    }

    public void draw(){

      for (int i = 0; i < lane1.length; i++){
        lane1[i].y = y-10;
        lane1[i].draw();
      }

      for (int i = 0;i < lane2.length ; i++){
        lane2[i].y = y +70;
        lane2[i].draw();
      }


    }
  }
