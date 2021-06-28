import static java.lang.System.out;
import java.util.*;
public class Main{
    public static void main(String[] args){
        try{
            out.println("ルール説明");
            out.println("【EAT=数字と位置が合っている】");
            out.println("【BITE=数字のみ合っている(位置は違う)】");
            out.println("先に4EATを達成した方の勝ちです");
            out.println("アイテムを使う場合は「アイテム」または「item」と入力してください");
            out.println("\nプレイモードを決めてください");
            out.println("①　→　1 player\n②　→　2 players(Player vs COM)\n③　→　2 players(Player vs Player)");
            int number = 0;
            while(true){
                try{
                    number = new Scanner(System.in).nextInt();
                    if(number > 3 || number < 1){
                        throw new InputMismatchException();
                    }
                }catch(InputMismatchException ime){
                    out.println("1~3を入力してください");
                    continue;
                }
                break;
            }
            switch (number){
                case 1:
                    mode1();
                    break;
                case 2:
                    mode2();
                    break;
                case 3:
                    mode3();
                    break;
            }
        }catch(Exception e){
            out.println("想定外のエラーです");
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void mode1() throws Exception { 
        NumeronPlayer enemy = new AIplayer();
        Player p1 = new Player(1);
        enemy.set();
        p1.getItem().setDefense(false);
        int count = 0;
        int eat;
        do{
            eat = p1.attack(enemy.getAnsNumber());
            count++;
        }while(eat != 4);
        out.println(count + "回目で正解しました");
        Thread.sleep(1000);
    }

    public static void mode2() throws Exception {
        NumeronPlayer p2 = new AIplayer();
        NumeronPlayer p1 = new Player(1, p2);
        loopAction(p1, p2);
    }

    public static void mode3() throws Exception {
        NumeronPlayer p1 = new Player(1);
        NumeronPlayer p2 = new Player(2);
        loopAction(p1, p2);
    }
    public static void loopAction(NumeronPlayer p1, NumeronPlayer p2) throws Exception{
        p1.set();
        p2.set();
        int eat;
        NumeronPlayer player;
        do{        //どちらかが4EATするまでattackメソッドを交互に繰り返す
            eat = p1.attack(p2.getAnsNumber());
            player = p1;
            p1 = p2;
            p2 = player;
        }while(eat != 4);
        out.println(p2.getName() + "の勝利です");
        Thread.sleep(1000);

        out.print(p2.getName() + "の設定ナンバーは ");
        for(int i : p2.getAnsNumber()){
            out.print(i);
        }
        out.println(" でした");
        Thread.sleep(1000);
        out.println("ゲームを終了します");
        Thread.sleep(1000);
    }
}