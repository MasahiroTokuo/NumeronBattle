import java.util.InputMismatchException;
import static java.lang.System.out;
public class CheckNumber{
    public static boolean check(char[] answer, int[] numbers) throws Exception{
        boolean ok = true;
        try{
            // length must be 4 characters.
            if(answer.length != 4) throw new InputMismatchException();

            // digit check 
            for(char i : answer){
                if(!(Character.isDigit(i))) throw new InputMismatchException();
            }

            // convert to integer array
            for(int i = 0; i < numbers.length; i++){
                numbers[i] = Character.getNumericValue(answer[i]);
            }

            //  same number check
            for(int i = 0; i < numbers.length; i++){
                for(int j = i+1; j < numbers.length; j++){
                    if(numbers[i] == numbers[j]) throw new InputMismatchException();
                }
            }
        }catch(InputMismatchException ime){
            //  show error message and have player tryed again if the input is NOT appropriate
            ok = false;
            out.println("|| 不正な文字列が入力されました");
            out.println("||  ①　数字が4桁ではない");
            out.println("||  ②　数字以外の文字が含まれている");
            out.println("||  ③　同じ数字が含まれている");
            out.println("|| 上の3点のいずれかに該当しています");
            out.println("再入力してください");
            Thread.sleep(1000);
            return ok;
        }
        return ok;
    }
    
    public static int[] discrimination(int[] ans, int[] numbers) throws Exception {
        //  count EAT & BITE
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
        int[] eatBite = {eat, (bite - eat)};
	    return eatBite;
	}
}