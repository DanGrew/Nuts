package uk.dangrew.nuts.apis.tesco.model;

public class GuidelineDailyAmounts {

   private final GuidelineDailyAmountOfEnergy energyGda;
   private final GuidelineDailyAmount fatGda;
   private final GuidelineDailyAmount saturatesGda;
   private final GuidelineDailyAmount sugarsGda;
   private final GuidelineDailyAmount saltGda;
   
   public GuidelineDailyAmounts() {
      this.energyGda = new GuidelineDailyAmountOfEnergy();
      this.fatGda = new GuidelineDailyAmount();
      this.saturatesGda = new GuidelineDailyAmount();
      this.sugarsGda = new GuidelineDailyAmount();
      this.saltGda = new GuidelineDailyAmount();
   }//End Constructor

   public GuidelineDailyAmountOfEnergy energyGda() {
      return energyGda;
   }//End Method

   public GuidelineDailyAmount fatGda() {
      return fatGda;
   }//End Method

   public GuidelineDailyAmount saturatesGda() {
      return saturatesGda;
   }//End Method

   public GuidelineDailyAmount sugarsGda() {
      return sugarsGda;
   }//End Method

   public GuidelineDailyAmount saltGda() {
      return saltGda;
   }//End Method
   
}//End Class
