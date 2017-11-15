package uk.dangrew.nuts.day;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.template.Template;

public class DayPlanOperations {

   public void applyTemplateAndDuplicate( DayPlan day, Template template ) {
      day.portions().clear();
      
      for ( FoodPortion portion : template.portions() ) {
         day.portions().add( portion.duplicate( 
                  " ( " + day.properties().nameProperty().get() + " )" 
         ) );
      }
   }//End Method
   
   public void applyTemplate( DayPlan day, Template template ) {
      day.portions().clear();
      
      for ( FoodPortion portion : template.portions() ) {
         FoodPortion copy = new FoodPortion( portion.food().get(), portion.portion().get() );
         day.portions().add( copy );
      }
   }//End Method

}//End Class
