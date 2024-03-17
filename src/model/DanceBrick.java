package model;

public class SpecialBrick extends Brick{
    private boolean isDance;
    public SpecialBrick(int x, int y, int weight, boolean isDance) {
        super(x, y, weight);
        this.isDance = isDance;
    }
    @Override
    public void explode(){
        super.explode();
        if(isDance){
            
        }else{

        }
    };
}
