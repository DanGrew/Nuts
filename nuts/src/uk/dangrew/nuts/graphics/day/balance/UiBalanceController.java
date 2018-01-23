package uk.dangrew.nuts.graphics.day.balance;

import uk.dangrew.nuts.day.DayPlan;

public class UiBalanceController {

   public void syncCalories( DayPlan plan ) {
      plan.consumedCalories().set( plan.properties().calories().get() );
   }//End Method

   public void resetBalance( DayPlan plan ) {
      plan.isBalanceReset().set( !plan.isBalanceReset().get() );
   }//End Method

}//End Class
