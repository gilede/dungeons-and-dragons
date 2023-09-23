package game.utils;
//class that help us represent the stats and used it to print in level up case
public class Stats {
    public int before_int1;
    public int before_int2;
    public int before_int3;
    public int before_int4;
    public int before_int5;
    public int _int1;
    public int _int2;
    public int _int3;
    public int _int4;
    public int _int5;

    public Stats(int int1,int int2,int int3){
        this.before_int1 =int1;
        this.before_int2=int2;
        this.before_int3=int3;
    }
    public Stats(int int1,int int2,int int3,int int4,int int5){
        this.before_int1 =int1;
        this.before_int2=int2;
        this.before_int3=int3;
        this.before_int4=int4;
        this.before_int5=int5;
    }

    public void setAfterStats(int int1,int int2,int int3){
        _int1 = int1 -before_int1;
        _int2 = int2- before_int2;
        _int3 = int3- before_int3;
    }

    public void setAfterStats(int int1,int int2,int int3,int int4,int int5){
        _int1 = int1 -before_int1;
        _int2 = int2- before_int2;
        _int3 = int3- before_int3;
        _int4 = int4- before_int4;
        _int5 = int5- before_int5;
    }
    public int getint1(){return _int1;}
    public int getint2(){return _int2;}
    public int getint3(){return _int3;}
    public int getint4(){return _int4;}
    public int getint5(){return _int5;}
}
