import static java.lang.System.out;
public class Main{
    public static void main(String[] args){
        out.println("ルール説明");
	    out.println("【EAT=数字と位置が合っている】");
	    out.println("【BITE=数字のみ合っている(位置は違う)】");
	    out.println("先に4EATを達成した方の勝ちです");
        out.println("アイテムを使う場合は「アイテム」または「item」と入力してください");

        try{
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
            out.println("プレイヤー" + p2.getPlayerNumber() + "の勝利です");
            Thread.sleep(1000);
        }catch(Exception e){
            out.println("想定外のエラーです");
            e.getMessage();
            e.printStackTrace();
        }
    }
}