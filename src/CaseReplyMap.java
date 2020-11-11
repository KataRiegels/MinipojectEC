


public class CaseReplyMap {
   int caseMap;
   int replyMap;
   int some;

   public CaseReplyMap(int caseMap, int some, int replyMap){
      this.caseMap = caseMap;
      this.replyMap = replyMap;
      this.some = some;
   }

   public int idk(){
      if (this.caseMap == this.some){
         return replyMap;
      }
      return -1;
   }


}
