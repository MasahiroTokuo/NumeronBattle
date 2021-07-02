import java.io.*;
import java.util.*;
import static java.lang.System.out;
public class Item{
    private boolean offense, defense;
    private boolean[] itemUsable = new boolean[1];
    private NumeronPlayer enemy;

    public void explanation(){
        out.println("|| 各プレイヤーはシャッフルとその他3つの内から1つの計2個のアイテムを使用できます");
        out.println("|| アイテムは自分の攻撃ターン毎に1つ使用できます");
        out.println("|| 【シャッフル】自分の設定した数字の桁をシャッフルできる(変えなくてもいい)");
        out.println("|| 　　例：1974 → 7941");
        out.println("||");
        out.println("|| 【ターゲット】0~9の数字を指定して、相手の数字に指定した数字が含まれているかを知ることができる");
        out.println("||             　指定した数字が含まれている時は、その数字の位置もわかる");
        out.println("|| 　　例：相手の数字が1974の時に7を指定 → 7は数字の3番目に含まれています");
        out.println("||");
        out.println("|| 【ハイ＆ロー】相手の数字の全ての桁のHigh(5~9),Low(0~4)を知ることができる");
        out.println("|| 　　例：1974 → Low, High, High, Low");
        out.println("||");
        out.println("|| 【スラッシュ】相手の数字のスラッシュナンバー(最大の数字-最小の数字)を知ることができる");
        out.println("|| 　　例：1974 → スラッシュナンバーは8(=9-1)");
    }

    public int[] shuffle(int[] ansNumber) throws Exception{
        if(!this.cannotUse(this.isDefense())) return ansNumber;
        Console cons = System.console();
        char[] input = new char[4];
        int[] newNumber = new int[4];
        boolean ok = false;
        int check = 0;
        while(check != 4){
            while(!ok){
                out.println("入れ替えた数字を入力してください");
                input = cons.readPassword("→");
                ok = CheckNumber.check(input, newNumber);
            }
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    if(ansNumber[i] == newNumber[j]) check++;
                }
            }
            if(check != 4){
                out.println("設定した数字に含まれない数字が入っています");
                ok = !ok;
                check = 0;
                continue;
            }
        }
        ansNumber = newNumber;
        if(this.getEnemy() != null) this.getEnemy().shuffled(); //  mode2の場合のみAIの候補を増やすメソッドを実行
        this.setDefense(false);
        return ansNumber;
    }

    public void target(int[] enemyNumber) throws Exception {
        if(!cannotUse(this.isOffense())) return;
        int targetNumber = 0;
        while(true){
            out.println("数字を1つ指定してください");
            try{
                targetNumber = new Scanner(System.in).nextInt();
                if(targetNumber > 9 || targetNumber < 0) throw new IllegalArgumentException();
            }catch(Exception e){
                out.println("0~9の数字を入力してください");
                targetNumber = 0;
                continue;
            }
            break;
        }
        List<Integer> list = new ArrayList<>(enemyNumber.length);
        for(int i : enemyNumber){
            list.add(i);
        }
        if(list.contains(targetNumber)){
            out.println(targetNumber + "は相手の数字の" + (list.indexOf(targetNumber)+1) + "番目に含まれています");
        }else{
            out.println(targetNumber + "は相手の数字に含まれていません");
        }
        Thread.sleep(1000);
        this.setOffense(false);
    }

    public void highLow(int[] enemyNumber) throws Exception {
        if(!cannotUse(this.isOffense())) return;
        out.print("結果は　");
        Thread.sleep(1000);
        for(int i : enemyNumber){
            if(i < 5){
                out.print("Low  ");
            }else{
                out.print("High  ");
            }
        }
        out.println("です");
        Thread.sleep(1000);
        this.setOffense(false);
    }
    
    public void slash(int[] enemyNumber) throws Exception {
        if(!cannotUse(this.isOffense())) return;
        int max = enemyNumber[0];
        int min = enemyNumber[0];
        for(int i : enemyNumber){
            if(i < min){
                min = i;
            }else if(i > max){
                max = i;
            }
        }
        int slashNumber = max - min;
        out.println("スラッシュナンバーは" + slashNumber + "です");
        Thread.sleep(1000);
        this.setOffense(false);
    }
    public boolean cannotUse(boolean b) throws Exception {
        if(b == false){
            out.println("このアイテムは使用できません");
            this.itemUsable[0] = false;
        }
        return b;
    }

    public boolean isOffense(){return this.offense;}
    public boolean isDefense(){return this.defense;}
    public boolean isItemUsable(){return this.itemUsable[0];}
    public NumeronPlayer getEnemy(){return this.enemy;}
    public void setOffense(boolean off){this.offense = off;}
    public void setDefense(boolean def){this.defense = def;}
    public void setItemUsable(boolean[] iu){this.itemUsable = iu;}

    public Item(boolean[] iu){
        this.setOffense(true);
        this.setDefense(true);
        this.setItemUsable(iu);
    }
    public Item(boolean[] iu, NumeronPlayer enemy){
        //　mode2でShuffleを使用した時にenemyのshuffledメソッドを呼ぶため
        this(iu);
        this.enemy = enemy;
    }
}