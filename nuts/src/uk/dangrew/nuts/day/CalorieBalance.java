package uk.dangrew.nuts.day;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;

public class CalorieBalance {

   private final DayPlanStore plans;
   private final ChangeListener< Double > recalculatorForDouble;
   private final ChangeListener< Boolean > recalculatorForBoolean;
   
   public CalorieBalance( DayPlanStore plans ) {
      this.plans = plans;
      this.recalculatorForDouble = ( s, o, n ) -> recalculateBalances();
      this.recalculatorForBoolean = ( s, o, n ) -> recalculateBalances();
      
      plans.objectList().forEach( this::add );
      plans.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::add, this::remove 
      ) );
   }//End Constructor
   
   private void add( DayPlan plan ) {
      plan.consumedCalories().addListener( recalculatorForDouble );
      plan.allowedCalories().addListener( recalculatorForDouble );
      plan.isBalanceReset().addListener( recalculatorForBoolean );
      
      recalculateBalances();
   }//End Method
   
   private void remove( DayPlan plan ) {
      plan.consumedCalories().removeListener( recalculatorForDouble );
      plan.allowedCalories().removeListener( recalculatorForDouble );
      plan.isBalanceReset().removeListener( recalculatorForBoolean );
      
      recalculateBalances();
   }//End Method
   
   private void recalculateBalances(){
      double balance = 0;
      
      TreeMap< LocalDate, DayPlan > sortedPlans = new TreeMap<>( 
               plans.objectList().stream()
                  .collect( Collectors.toMap( 
                           DayPlan::date, Function.identity() 
                  ) ) 
      );
      for ( DayPlan plan : sortedPlans.values() ) {
         if ( plan.isBalanceReset().get() ) {
            balance = 0;
         } else {
            double consumed = plan.consumedCalories().get();
            double allowed = plan.allowedCalories().get();
            double caloriesSpent = consumed - allowed;
            balance += caloriesSpent;
         }
         plan.calorieBalance().set( balance );
      }
   }//End Method

}//End Class
