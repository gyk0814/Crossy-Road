import GaFr.GFStamp;

public class Grass extends Strip{
  GFStamp smallTree = new GFStamp("assets/tree1.png").rescale(0.7).centerPin();
  GFStamp bigTree = new GFStamp("assets/tree2.png").rescale(0.7).centerPin();
  int randint1;
  int randint2;
  int randint3;
  {
    smallTree.pinY = 1f;
    bigTree.pinY = 1f;
    isGrass = true;
    randint1 = GaFr.GFU.randint(0,10);
    randint2 = GaFr.GFU.randint(50,760);
    randint3 = GaFr.GFU.randint(80,750);
  }
    public Grass(){
      height = 75;
      background = new GFStamp("assets/grass.png").resize(800,height);
    }

    void randomize(){
      randint1 = GaFr.GFU.randint(0,10);
      randint2 = GaFr.GFU.randint(50,760);
      randint3 = GaFr.GFU.randint(80,750);
    }

    public void draw(){
      while((randint3<randint2+60 && randint3>randint2-60)){ randint3 = GaFr.GFU.randint(50,750);}
      if(randint1>7){
        smallTree.moveTo(randint2,this.y+40).stamp();
        smallTree.moveTo(randint3,this.y+40).stamp();
      }else if(randint1>3){
        bigTree.moveTo(randint3,this.y+40).stamp();
        smallTree.moveTo(randint2,this.y+50).stamp();
      }else{
        smallTree.moveTo(randint2,this.y+40).stamp();
      }
    }
  }