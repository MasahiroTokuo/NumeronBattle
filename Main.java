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
            out.println("\nプレイヤー数を決めてください");
            out.println("①　→　1 player\n②　→　2 players");
            int number = 0;
            while(true){
                try{
                    number = new Scanner(System.in).nextInt();
                    if(number > 2 || number < 1){
                        throw new InputMismatchException();
                    }
                }catch(InputMismatchException ime){
                    out.println("1か2を入力してください");
                    continue;
                }
                break;
            }
            if(number == 1){
                player();
            }else{
                players();
            }
        }catch(Exception e){
            out.println("想定外のエラーです");
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void player() throws Exception {
        Player p1 = new Player(1);
        Player enemy = new Player(2);
        enemy.setRandom();
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

    public static void players() throws Exception {
        Player p1 = new Player(1);
        Player p2 = new Player(2);
        p1.set();
        p2.set();
        int eat;
        Player player;
        do{
            eat = p1.attack(p2.getAnsNumber());
            player = p1;
            p1 = p2;
            p2 = player;
        }while(eat != 4);
        out.println(p2.getName() + "の勝利です");
        Thread.sleep(1000);
    }
}