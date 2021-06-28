import java.io.*;
import java.util.*;
import static java.lang.System.out;
public class Player extends NumeronPlayer{
    private boolean ok;
    private char[] input;
    private Item item;
    private boolean[] itemUsable = new boolean[1];
    private NumeronPlayer enemy;

    public void set() throws Exception{
        Console cons = System.console();
        out.println("【" + this.getName() + "】4桁の数字を設定してください");
        while(!this.isOk()){
            // get console line
            this.setInput(cons.readPassword("→"));

            // check input
            String str = new String(this.getInput());
            this.setOk(CheckNumber.check(this.input, this.ansNumber));
        }
    }

    public int attack(int[] enemyNumber) throws Exception{
        this.setOk(false);
        while(!this.isOk()){
            out.println(this.getName() + "の攻撃ターン");
            // get console line
            String atk = new Scanner(System.in).nextLine();

            if(atk.equals("アイテム")||atk.equals("item")){
                this.useItem(enemyNumber);
                continue;
            }
            this.setInput(atk.toCharArray());  //convert to character array
            this.setOk(CheckNumber.check(this.getInput(), this.getAttackNumber()));
        }
        int[] eatBite = CheckNumber.discrimination(enemyNumber, this.getAttackNumber());
        out.print("　  →　");
        Thread.sleep(1000);
        out.print(eatBite[0] + "-EAT ");
        Thread.sleep(1000);
        out.println(eatBite[1] + "-BITE");
        Thread.sleep(1000);
        this.setItemUsable(true);
        return eatBite[0];
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
        /* 
        *  @param:
        *   itemNumber: int
        *
        *   0: show explanations of items
        *   1: use Shuffle (diffensive item) 
        *   2: use Target (offensive item)
        *   3: use High&Low (offensive item)
        *   4: use Slash (offensive item)
        */
        switch(itemNumber){
            case 0:
                this.getItem().explanation();
                this.setItemUsable(false);  //説明の表示をアイテムの使用に含まないため
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

    public void shuffled()throws Exception{throw new Exception();}

    public boolean isOk(){return this.ok;}
    public char[] getInput(){return this.input;}
    public Item getItem(){return this.item;}
    public boolean[] isItemUsable(){return this.itemUsable;}
    public NumeronPlayer getEnemy(){return this.enemy;}
    public void setOk(boolean ok){this.ok = ok;}
    public void setInput(char[] input){this.input = input;}
    public void setItemUsable(boolean iu){this.itemUsable[0] = iu;}

    public Player(int p){
        this.name = "プレイヤー" + p;
        this.setAnsNumber(new int[4]);
        this.setAttackNumber(new int[4]);
        this.setOk(false);
        this.setItemUsable(true);
        this.item = new Item(this.isItemUsable());
    }
    public Player(int p, NumeronPlayer enemy){ 
        //　mode2でShuffleを使用した時にenemyのshuffledメソッドを呼ぶため
        this(p);
        this.enemy = enemy;
        this.item = new Item(this.isItemUsable(), this.getEnemy());
    }
}