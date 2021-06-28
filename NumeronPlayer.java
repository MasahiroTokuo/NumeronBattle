import java.util.*;
public abstract class NumeronPlayer{
    String name;
    int[] ansNumber;
    int[] attackNumber;

    public void setRandom() throws Exception{
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            intList.add(i);
        }
	    for(int i = 0; i < this.getAnsNumber().length; i++){
            int random = new java.util.Random().nextInt(intList.size());
	        this.getAnsNumber()[i] = intList.get(random);
            intList.remove(random);
	    }
    }
    public abstract void set()throws Exception;
    public abstract int attack(int[] enemyNumber)throws Exception;
    public abstract void shuffled()throws Exception;

    public String getName(){return this.name;}
    public int[] getAnsNumber(){return this.ansNumber;}
    public int[] getAttackNumber(){return this.attackNumber;}
    public void setAnsNumber(int[] ansNumber){this.ansNumber = ansNumber;}
    public void setAttackNumber(int[] attackNumber){this.attackNumber = attackNumber;}
}