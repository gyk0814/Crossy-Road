import GaFr.GFStamp;
import GaFr.GFTexture;
import GaFr.Gfx;

public class Log {
    int x;
    int y;
    int velocity;
    int time = 0;
    boolean isRightToLeft;
    GFStamp log = new GFStamp("assets/log.png").rescale(0.18,0.18);

    public Log(int x, int y, int velocity,boolean isRightToLeft){
      this.isRightToLeft = isRightToLeft;
      this.x = x;
      this.y = y;
      this.velocity = velocity;
    }

    public void draw(){
      if( this.isRightToLeft){
        if(this.x >= -100){
          this.x -= this.velocity;
        }
        else{
          this.x = 800;
        }
      }
      if(!this.isRightToLeft){
        if(this.x <= 800){
          this.x += this.velocity;
        }
        else{
          this.x = -100;
        }
      }
      this.log.moveTo(this.x, this.y).stamp();

    }

}

