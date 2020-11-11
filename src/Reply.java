


public class Reply {
   String reply;
   int memory;
   String tempTrig;

   public Reply(String reply, int memory, String tempTrig){
      this.reply = reply;
      this.memory = memory;
      this.tempTrig = tempTrig;
   }

   public int getMap(){
      return this.memory;
   }

   public void printReply(){
      System.out.println(reply);
   }

   public String chooseReply(CaseReplyMap CRM) {
      if (CRM.idk() == memory){
         return reply;
      }
      return "no";
   }

}
