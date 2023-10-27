public class Timer {
    int time = 0; 
    int limit;

    public Timer(int limit){
        this.limit = limit;
    }

    public void tick(){
        this.time += 1;
        if (this.time >= this.limit){
            this.time = 0;
        }
    }
}
