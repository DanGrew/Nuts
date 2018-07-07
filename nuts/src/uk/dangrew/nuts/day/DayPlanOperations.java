package uk.dangrew.nuts.day;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.template.Template;

public class DayPlanOperations {

   private final Map< LocalDate, DayPlan > mappedPlans;
   private final DayPlanController dayPlanController;
   
   public DayPlanOperations( DayPlanStore dayPlans, DayPlanController dayPlanController ) {
      this.dayPlanController = dayPlanController;
      this.mappedPlans = dayPlans.objectList().stream()
               .collect( Collectors.toMap( DayPlan::date, Function.identity() ) );
   }//End Constructor

   public void applyTemplate( DayPlan day, Template template ) {
      clearDayPlan( day );
      addFromTemplate( day, template );
   }//End Method

   public void addFromTemplate( DayPlan dayPlan, Template template ) {
      for ( FoodPortion portion : template.portions() ) {
         dayPlanController.add( portion, dayPlan );
      }
   }//End Method

   public void clearDayPlan( DayPlan dayPlan ) {
      while ( !dayPlan.portions().isEmpty() ) {
         dayPlanController.remove( dayPlan.portions().get( 0 ), dayPlan );
      }
   }//End Method

   public void copyToDay( DayPlan fromDay, LocalDate toDate ) {
      DayPlan toPlan = mappedPlans.get( toDate );
      if ( toPlan == null ) {
         return;
      }
      
      addFromTemplate( toPlan, fromDay );
   }//End Method
   
}//End Class
