package game.utils;

import game.utils.Health;

public class Resource extends Health {
    protected int amount;
    protected int cost;
    public Resource(int pool,int amount,int cost){
        super(pool);
        this.cost=cost;
        this.amount=amount;
    }
    public void reduce(){
        this.amount -=this.cost;
        this.amount = Math.max(amount,0);
    }
    public void increase(int inc){
        this.amount += inc;
        this.amount = Math.min(this.amount,this.pool);
    }
    public void setPool(int pool) {
        this.pool = pool;
        this.amount = pool;
    }
    public boolean AbleToCast(){
        return this.amount>=this.cost;
    }
    public boolean WAbleToCast(){
        return this.amount<this.cost;
    }
    @Override
    public int getAmount() {
        return amount;
    }
    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
