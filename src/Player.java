
import GaFr.GFStamp;
import GaFr.GFTexture;
import GaFr.Gfx;
import GaFr.Easings;
import GaFr.GFU;
import GaFr.GFKeyboard;
import GaFr.GFKey;

public class Player{
    int x;
    int y;
    int speed =75;
    int health;
    int damage;
    int direction;
    int[] inventory = new int[10];
    GFStamp playerUp = new GFStamp("assets/chicken_back.png").rescale(0.18).centerPin();
    GFStamp playerDown = new GFStamp("assets/chicken_front.png").rescale(0.18).centerPin();
    GFStamp playerLeft = new GFStamp("assets/chicken_left.png").rescale(0.18).centerPin();
    GFStamp playerRight = new GFStamp("assets/chicken_right.png").rescale(0.18).centerPin();
    GFStamp currentSprite = playerUp;
    {
      playerUp.pinY = 1f;
      playerDown.pinY = 1f;
      playerLeft.pinY = 1f;
      playerRight.pinY = 1f;
    }
    public Player(int x, int y, int health, int damage, int direction){
      this.x = x;
      this.y = y;
      this.health = health;
      this.damage = damage;
      this.direction = direction;
    }

    public void move(int direction){
      if(direction == 0 && this.y >= 3){
        this.y -= this.speed;
      }
      if(direction == 1 && this.y <= 400){
        this.y += this.speed;
      }
      if(direction == 2 && (this.x - this.speed)>= 20){
        this.x -= this.speed;
      }
      if(direction == 3 && this.x+this.speed <= 780){
        this.x += this.speed;
      }
    }
    public void moveWithLog(Boolean RtoL, int speed, int time){
      if(RtoL && (this.x-speed) >= 20){
        this.x -= speed;
      }
      if(!RtoL && (this.x+speed) <= 780){
        this.x += speed;
      }
    }

    boolean jumpMotion = false;
    boolean jump = false;
    double jumpHeight = 0;
    int rescaleCounter =1;
    int counter =1;
    float jump_Xoff = 75;

    public void draw(int frameCount){
      float screenX = this.x;
      float screenY = (float)this.y - (float)jumpHeight;
      float scaleEase = 0;

      if(jumpMotion){
        Easings.EasingPair jumpRescale = new Easings.EasingPair(Easings.easeInQuad, new Easings.Reverse(Easings.easeInQuad));
        scaleEase = Easings.easeOutCubic.easef(rescaleCounter/15.0);
        rescaleCounter++;
      }else if(jump){
        Easings.EasingPair jumpEasing = new Easings.EasingPair(Easings.easeOutQuad, new Easings.Reverse(Easings.easeOutQuad));
        double Yease = jumpEasing.ease( counter / 30.0 );
        float Xease = Easings.easeOutQuad.easef(counter/30.0);
        scaleEase = Easings.easeOutQuad.easef(rescaleCounter/30.0);
        jumpHeight = GFU.scale( Yease, 0, 50 );
        jump_Xoff = GFU.scale(Xease,0,75);
        counter++;
      }
      if(counter % 31 == 0 ){jump = false; counter = 1;}
      if(jumpMotion ==false) rescaleCounter = 1;
      if(direction == 0){
        currentSprite = playerUp;
      }
      if(direction == 1){
        currentSprite = playerDown;
      }
      if(direction == 2){
        currentSprite = playerLeft;
        screenX = this.x + speed - jump_Xoff;
      }
      if(direction == 3){
        currentSprite = playerRight;
        screenX = this.x - speed + jump_Xoff;
      }
      // if(jumpMotion){
        currentSprite.pinY = 1f;
        currentSprite.rescale(GFU.scale(scaleEase,0.18,0.15));
        currentSprite.moveTo(screenX,screenY).stamp();
      // }else{
        // currentSprite.pinY = 0.5f;
        // currentSprite.moveTo(screenX,screenY).stamp();
      // }
    }
  }