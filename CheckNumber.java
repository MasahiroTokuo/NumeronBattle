import java.util.InputMismatchException;
import static java.lang.System.out;
public class CheckNumber{
    public static boolean check(char[] answer, int[] numbers) throws Exception{
        boolean ok = true;
        try{
            if(answer.length != 4){
                throw new InputMismatchException();
            }
            for(char i : answer){
                if(!(Character.isDigit(i))){
                    throw new InputMismatchException();
                }
            }
            for(int i = 0; i < numbers.length; i++){
                numbers[i] = Character.getNumericValue(answer[i]);
            }
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3-i; j++){
                    if(numbers[i] == numbers[i+j+1]){
                        throw new InputMismatchException();
                    }
                }
            }
        }catch(InputMismatchException ime){
            ok = false;
            out.println("|| 不正な文字列が入力されました");
            out.println("||  ①　数字が4桁ではない");
            out.println("||  ②　数字以外の文字が含まれている");
            out.println("||  ③　同じ数字が含まれている");
            out.println("|| 上の3点のいずれかに該当しています");
            out.println("再入力してください");
            return ok;
        }
        return ok;
    }
    public static int discrimination(int[] ans, int[] numbers) throws Exception {
	    int eat = 0;
	    int bite = 0;
	    for(int i = 0; i < 4; i++){
	        for(int j = 0; j < 4; j++){
	            if(numbers[i] == ans[j]){
	                bite++;
	            }
	        }
	    }
	    for(int i = 0; i < 4; i++){
	        if(numbers[i] == ans[i]){
	            eat++;
	        }
	    }
	    out.print("　  →　");
        Thread.sleep(1000);
        out.print(eat + "-EAT ");
        Thread.sleep(1000);
        out.println((bite - eat) + "-BITE");
        Thread.sleep(1000);
	    return eat;
	}
}