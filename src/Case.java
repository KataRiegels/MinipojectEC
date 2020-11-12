

public class Case {
   String input;
   int[] inputs;
   int keyMap;

   public Case(String input, int keyMap){
      this.input = input;
      this.keyMap = keyMap;
   }


   public int getKeyMap(){
      return keyMap;
   }

   public String getInput(){
      return input;
   }

}


