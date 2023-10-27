import GaFr.GFStamp;
import GaFr.GFTexture;
import GaFr.Gfx;

public class Strip {
  GFStamp background;
  int height;
  int y;
  boolean isWater= false;
  boolean isGrass = false;
  Log collided;

  public void draw(){}
  public boolean checkCollision(int x, int y){return false;}
  public Log getCollidedLog(){
    return collided;
  }
  void randomize(){}
}
