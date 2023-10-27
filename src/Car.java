import GaFr.GFStamp;
import GaFr.GFTexture;
import GaFr.Gfx;

public class Car{
    int x;
    int y;
    int velocity;
    GFStamp carImg;
    GFStamp purple_left;
    GFStamp purple_right;
    int time = 0;
    boolean isRightToLeft;

    public Car(int x, int y, int velocity,boolean isGreen, boolean isRightToLeft){
      this.isRightToLeft = isRightToLeft;
      this.x = x;
      this.y = y;
      this.velocity = velocity;
      if(isGreen){
        if(isRightToLeft)  carImg = new GFStamp("assets/greenCar_left.png").rescale(0.3,0.3);
        else carImg = new GFStamp("assets/greenCar_right.png").rescale(0.3,0.3);
      }else{
        if(isRightToLeft)  carImg = new GFStamp("assets/purpleCar_left.png").rescale(0.35,0.35);
        else carImg = new GFStamp("assets/purpleCar_right.png").rescale(0.35,0.35);
      }
    }

    public void move(){
      if(time % 6 == 0 && this.isRightToLeft){
        if(this.x >= -100){
          this.x -= this.velocity;
        }
        else{
          this.x = 800;
        }
      }
      if(time % 6 == 0 && !this.isRightToLeft){
        if(this.x <= 800){
          this.x += this.velocity;
        }
        else{
          this.x = 0;
        }
      }
    }

    public void draw(){
      this.carImg.moveTo(this.x, this.y).stamp();
      this.move();
      this.time += 1;
    }
  }
