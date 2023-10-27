import GaFr.GFGame;
import GaFr.GFStamp;
import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import GaFr.GFFont;
import GaFr.GFKey;
import GaFr.GFTexture;
import GaFr.Gfx;
import GaFr.GFPixels;
import GaFr.GFKeyboard;
import GaFr.GFSound;

public class Game extends GFGame
{
  GFStamp[] imgMap = new GFStamp[100];
  GFStamp[] world;
  boolean start = true;
  GFSound carSound = new GFSound("sounds/car.mp3");
  Timer carTimer = new Timer(12500);
  GFSound jumpSound = new GFSound("sounds/jump.mp3");
  GFSound splashSound = new GFSound("sounds/splash.mp3");
  GFSound carCrash = new GFSound("sounds/car_crash.wav");
  GFStamp restartBtn = new GFStamp("assets/restart.png").resize(300,170);
  ArrayList<Strip> map;
  GFFont font = new GFFont("gafr/fonts/spleen/spleen-16x32.ffont.json");
  GFFont font1 = new GFFont("gafr/fonts/spleen/spleen-32x64.ffont.json");

  int score = 0;

  void initialize_map()
  {
    map = new ArrayList<>();
    for(int i=0;i<2;i++){
      map.add(new Road());
      map.add(new Grass());
      map.add(new Water());
      map.add(new Road());
      map.add(new Grass());
      map.add(new Road());
    }
  }

  void initialize_world()
  {
    world = new GFStamp[map.size()];
    for(int i=0;i<map.size();i++){
      world[i] = map.get(i).background;
    }
  }

  {
    initialize_map();
    initialize_world();
  }

  Player player = new Player(400,340,100,10,0);
  int playerRow =1;

  @Override
  public void onMouseUp(int x, int y, int buttons, int flags, int button)
  {
    if(!start && x >= 270 && x <= 270 + 300  && y >= 150 && y <= 150 + 170)
    {
      for(int i=0;i<map.size();i++){
        map.get(i).randomize();
        map.get(i).y = 0;
      }
      startOver();
      start = true;
      score = 0;
    }
  }

  @Override
  public void onKeyDown (String key, int code, int flags){
    switch (code)
    {
      case GFKey.ArrowRight:
      {
        if(start){
          player.direction = 3;
          player.jumpMotion=true;
        }
        break;
      }
      case GFKey.ArrowLeft:
      {
        if(start){
          player.direction = 2;
          player.jumpMotion=true;
        }
        break;
      }
      case GFKey.ArrowUp:
      {
        if(start){
          player.direction = 0;
          player.jumpMotion=true;
        }
        break;
      }
      case GFKey.ArrowDown:
      {
        if(start){
          player.direction = 1;
          player.jumpMotion =true;
          break;
        }
      }
    }
  }

  @Override
  public void onKeyUp (String key, int code, int flags){
    if(start){
      if(code ==GFKey.ArrowRight ){
        player.jumpMotion = false;
        player.jump = true;
        player.move(3);
        jumpSound.play();
      }else if(code==GFKey.ArrowLeft){
        player.jumpMotion = false;
        player.jump = true;
        player.move(2);
        jumpSound.play();
      }else if(code==GFKey.ArrowUp){
        player.jumpMotion = false;
        player.jump = true;
        offset +=75;
        jumpSound.play();
        score ++;
      }else if(code==GFKey.ArrowDown){
        if(offset>0) {
          player.jumpMotion = false;
          player.jump = true;
          offset -=75;
          score --;
        }
        jumpSound.play();
      }
    }
  }

  void playCarSound(){
    if(carTimer.time == 12000){
      carSound.play();
    }
      carTimer.tick();
  }

  void startOver()
  {
    offset = 0;
    j = 0;
    startSlice = 0;
    bgHeight = map.get(j).height;
    temp = offset;
    player.direction = 0;
    player.x = 400;
    player.y = 340;
    start = false;
  }

  int offset = 0;

  int j;
  int startSlice;
  int bgHeight;
  int temp;

  @Override
  public void onDraw (int frameCount)
  {
    if(start){
      j=0;
      startSlice=0;
      bgHeight = map.get(j).height;
      temp = offset;
      while(temp/bgHeight>0){
        startSlice++;
        temp -= bgHeight;
        j = (j+1) % map.size();
        bgHeight = map.get(j).height;
      }
      int currSlice;
      int off = temp % bgHeight;
      int totalH = 0;
      for(int i=0;i<7;i++){
        if(startSlice+i>=world.length) currSlice = (startSlice+i) % world.length;
        else if(startSlice+i < 0 ) continue;
        else currSlice = startSlice+i;
        totalH += map.get(currSlice).height;
        world[currSlice].moveTo(0,500+off-totalH).stamp();
        checkCollision(500+off-totalH,500+off-totalH+map.get(currSlice).height,currSlice,frameCount);
        int drawIdx = currSlice-1;
        if(currSlice==0) drawIdx = world.length-1;
        Strip drawObj = map.get(drawIdx);
        drawObj.draw();
        drawObj.y = 500+off-totalH+map.get(currSlice).height;
      }
      player.draw(frameCount);
      drawTrees();
      font.draw(60, 20, "Score: "+ (score));
    }else{
      restartBtn.moveTo(270,150).stamp();
      font1.draw(280, 50, "Game Over");
      font.draw(220, 330, "Click on button to restart ");
      font.draw(350, 370, "Score: "+ (score));
    }
  }

  public void drawTrees(){
    for(int i =map.size()-1;i>=0;i--){
      Strip obj = map.get(i);
      if(obj.isGrass && obj.y<480 && obj.y>0){
        obj.draw();
      }
    }
  }

  public void checkCollision(int upperBound, int lowerBound, int currSlice, int frameCount){
    Strip currMap = map.get(1);
    if(upperBound<player.y && lowerBound>=player.y) currMap = map.get(currSlice);
    if(currMap.isWater){
      if(currMap.checkCollision(player.x, player.y-currMap.y)){
        Log l = currMap.getCollidedLog();
        player.moveWithLog(l.isRightToLeft, l.velocity, frameCount);
      }else{
        splashSound.play();
        startOver();
      }
    }else{
      if(currMap.checkCollision(player.x, player.y-currMap.y)){
        carCrash.play();
        startOver();
      }
    }
  }

}