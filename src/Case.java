

public class Case {
   String input;
   int[] inputs;
   int keyMap;

   public Case(int[] inputs, int keyMap){
      this.inputs = inputs;
      this.keyMap = keyMap;
   }

   public int getKeyMap(){
      return keyMap;
   }

   public String getInput(){
      return input;
   }

}
