package game.utils;

public class Health {
    protected int pool;
    protected int amount;
    public Health(int pool){
        this.pool = pool;
        this.amount=pool;
    }
    public int getPool() {
        return pool;
    }
    public void setPool(int pool) {
        this.pool = pool;
        this.amount = pool;
    }
    public void reduce(int damage){
        this.amount-=damage;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String toString(){
        return String.format("%s/%s",getAmount() ,getPool());
    }
}
