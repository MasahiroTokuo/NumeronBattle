import java.util.*;
import static java.lang.System.out;
public class AIplayer extends NumeronPlayer{
    private ArrayList<int[]> candidate;
    private int[] eatBite;

    public int attack(int[] enemyNumber)throws Exception{
        out.println(this.getName() + "の攻撃ターン");
        int random = new Random().nextInt(this.getCandidate().size());
        // choose a number randomly from candidates
        this.setAttackNumber(this.getCandidate().get(random));

        Thread.sleep(1000);
        for(int i : attackNumber){
            out.print(i);
        }
        out.println("");
        this.setEatBite(CheckNumber.discrimination(enemyNumber, this.getAttackNumber()));
        //  show results
        out.print("　  →　");
        Thread.sleep(1000);
        out.print(eatBite[0] + "-EAT ");
        Thread.sleep(1000);
        out.print(eatBite[1] + "-BITE");
        Thread.sleep(1000);

        this.remove();  //remove inappropriate numbers from candidates
        if(this.getCandidate().size() != 1){
            out.println("   (候補数:" + this.getCandidate().size() + ")");
            Thread.sleep(1000);
        }else{ out.println(""); }
        return eatBite[0];
    }

    public void set()throws Exception{this.setRandom();}

    public void remove()throws Exception{
        Iterator<int[]> it = this.getCandidate().iterator();
        while(it.hasNext()){
            int[] array = it.next();
            //  コールした数字に対するEAT,BITE数が一致しない候補を取り除く
            int[] check = CheckNumber.discrimination(array, this.getAttackNumber());
            if(check[0] != this.getEatBite()[0] || check[1] != this.getEatBite()[1]){it.remove();}
        }
    }

    public void shuffled() throws Exception{    //  mode2でプレイヤーがShuffleを使用した時に候補を増やすためのメソッド
        out.print("(候補数:" + this.getCandidate().size() + "　→　");
        HashSet<String> candidateSet = new HashSet<String>();
        for(int[] array : this.getCandidate()){
            /*  候補の数字を昇順に並び替え、文字列に変換してHashSetに入れる
                候補に残っている数字の「組み合わせ」を取得する*/
            Arrays.sort(array);
            String[] strArray = new String[4];
            for(int i = 0; i < array.length; i++){
                strArray[i] = String.valueOf(array[i]);
            }
            String str = String.join("", strArray);
            candidateSet.add(str);
        }
        ArrayList<int[]> newCandidate = new ArrayList<int[]>();
        for(String str : candidateSet){
            char[] charArray = str.toCharArray();   //   convert to character array
            int[] array = new int[4];
            for(int i = 0; i < array.length; i++){  //  convert to integer array
                array[i] = Character.getNumericValue(charArray[i]);
            }
            this.setShuffledCandidate(array, newCandidate, 1);
        }
        this.setCandidate(newCandidate);
        out.println(this.getCandidate().size() + ")");
    }

    public void setShuffledCandidate(int[] array, ArrayList<int[]> newCandidate, int count)throws Exception{
        //  取得した数字の「組み合わせ」に対する順列24個を新しい候補に入れる
        for(int i = 0; i < array.length; i++){
            //newArrayを用意してarrayをコピーし、newCandidateに入れる
            int[] newArray = new int[4];
            for(int j = 0; j < 4; j++){
                newArray[j] = array[j];
            }
            newCandidate.add(newArray);
            //  arrayの数字を1つずつずらす　　例：1234→2341→3412→4123
            int number = array[0];
            for(int j = 0; j < 3; j++){
                array[j] = array[j+1];
            }
            array[3] = number;
        }
        switch(count){  //  数字の並び順を入れ替える  例：1234→2134
            case 1:
                this.exchange(array, 0, 1);
                break;
            case 2:
                this.exchange(array, 2, 3);
                break;
            case 3:
            case 5:
                this.exchange(array, 0, 3);
                break;
            case 4:
                this.exchange(array, 1, 3);
                break;
            case 6:
                return;
        }
        count++;
        this.setShuffledCandidate(array, newCandidate, count);  //countが6になるまでこのメソッドを繰り返す
    }

    public void exchange(int[] array, int a, int b)throws Exception{
        int number = array[a];
        array[a] = array[b];
        array[b] = number;
    }

    public ArrayList<int[]> getCandidate(){return this.candidate;}
    public int[] getEatBite(){return this.eatBite;}
    public void setCandidate(ArrayList<int[]> candidate){this.candidate = candidate;}
    public void setEatBite(int[] eatBite){this.eatBite = eatBite;}

    public AIplayer()throws Exception{
        this.name = "COM1";
        this.ansNumber = new int[4];
        this.setAttackNumber(new int[4]);
        this.setCandidate(new ArrayList<int[]>());
        this.setEatBite(new int[2]);

        //  0~9999の数字のうち、各桁に被りがない5040個をcandidateにセット
        for(int i = 0; i < 10000; i++){
            String str = String.format("%04d", i);
            String[] strArray = str.split("");
            int[] intArray = new int[4];
            for(int j = 0; j < intArray.length; j++){
                intArray[j] = Integer.parseInt(strArray[j]);
            }
            boolean overlap = false;
            for(int j = 0; j < intArray.length; j++){
                for(int k = j+1; k < intArray.length; k++){
                    if(intArray[j] == intArray[k]){
                        overlap = true;
                        break;
                    }
                }
                if(overlap == true){break;}
            }
            if(overlap == false){
                this.getCandidate().add(intArray);
            }
        }
    }
}