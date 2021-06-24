import java.io.*;
import java.util.*;
import static java.lang.System.out;
public class Player{
    private String name;
    private boolean ok;
    private char[] input;
    private int[] ansNumber;
    private int[] attackNumber;
    private Item item;
    private boolean[] itemUsable = new boolean[1];

    public void set() throws Exception{
        Console cons = System.console();
        out.println("【" + this.getName() + "】4桁の数字を設定してください");
        while(!this.isOk()){
            this.setInput(cons.readPassword("→"));
            this.setOk(CheckNumber.check(this.input, this.ansNumber));
        }
    }

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

    public int attack(int[] enemyNumber) throws Exception{
        this.setOk(false);
        while(!this.isOk()){
            out.println(this.getName() + "の攻撃ターン");
            String atk = new Scanner(System.in).nextLine();
            if(atk.equals("アイテム")||atk.equals("item")){
                this.useItem(enemyNumber);
                continue;
            }
            this.setInput(atk.toCharArray());
            this.setOk(CheckNumber.check(this.getInput(), this.getAttackNumber()));
        }
        int eat = CheckNumber.discrimination(enemyNumber, this.getAttackNumber());
        this.setItemUsable(true);
        return eat;
    }

    public void useItem(int[] enemyNumber) throws Exception{
        if(this.isItemUsable()[0] == false){
            out.println("このターンすでにアイテムを使用しています");
            return;
        }
        out.println("どのアイテムを使用しますか？");
        out.println("⓪ アイテムの説明　① シャッフル　② ターゲット　③ ハイ&ロー　④ スラッシュ");
        int itemNumber = 0;
        while(true){
            try{
                itemNumber = new Scanner(System.in).nextInt();
                if(itemNumber > 4 || itemNumber < 0){
                    throw new InputMismatchException();
                }
            }catch(InputMismatchException ime){
                out.println("0~4の数字を入力してください");
                itemNumber = 0;
                continue;
            }
            break;
        }
        switch(itemNumber){
            case 0:
                this.getItem().explanation();
                this.setItemUsable(false);
                break;
            case 1:
                this.setAnsNumber(this.getItem().shuffle(this.getAnsNumber()));
                break;
            case 2:
                this.getItem().target(enemyNumber);
                break;
            case 3:
                this.getItem().highLow(enemyNumber);
                break;
            case 4:
                this.getItem().slash(enemyNumber);
                break;
        }
        this.setItemUsable(!this.isItemUsable()[0]);
    }

    public String getName(){return this.name;}
    public boolean isOk(){return this.ok;}
    public char[] getInput(){return this.input;}
    public int[] getAnsNumber(){return this.ansNumber;}
    public int[] getAttackNumber(){return this.attackNumber;}
    public Item getItem(){return this.item;}
    public boolean[] isItemUsable(){return this.itemUsable;}
    public void setName(String name){this.name = name;}
    public void setOk(boolean ok){this.ok = ok;}
    public void setInput(char[] input){this.input = input;}
    public void setAnsNumber(int[] ansNumber){this.ansNumber = ansNumber;}
    public void setAttackNumber(int[] attackNumber){this.attackNumber = attackNumber;}
    public void setItem(Item item){this.item = item;}
    public void setItemUsable(boolean iu){this.itemUsable[0] = iu;}

    public Player(int p){
        this.setName("プレイヤー" + p);
        this.setAnsNumber(new int[4]);
        this.setAttackNumber(new int[4]);
        this.setOk(false);
        this.setItemUsable(true);
        this.setItem(new Item(this.isItemUsable()));
    }
}