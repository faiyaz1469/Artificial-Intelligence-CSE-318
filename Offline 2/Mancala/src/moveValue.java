public class moveValue {
    private int value;
    private int bin;

    public moveValue(int value, int bin){
        this.value = value;
        this.bin = bin;
    }
    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public void setBin(int bin){
        this.bin = bin;
    }

    public int getBin(){
        return this.bin;
    }
}