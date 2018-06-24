package uk.dangrew.nuts.graphics.day.balance;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class UiBalanceController {

   public void syncCalories( DayPlan plan ) {
      plan.consumedCalories().set( NutritionalUnit.Calories.of( plan ).get() );
   }//End Method

   public void resetBalance( DayPlan plan ) {
      plan.isBalanceReset().set( !plan.isBalanceReset().get() );
   }//End Method

}//End Class
