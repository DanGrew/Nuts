package uk.dangrew.nuts.day;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.template.Template;
import uk.dangrew.nuts.template.TemplateStore;

public class DayPlanOperations {

   private final Map< LocalDate, DayPlan > mappedPlans;
   private final TemplateStore templates;
   
   public DayPlanOperations( TemplateStore templates, DayPlanStore dayPlans ) {
      this.templates = templates;
      this.mappedPlans = dayPlans.objectList().stream()
               .collect( Collectors.toMap( DayPlan::date, Function.identity() ) );
   }//End Constructor

   public void applyTemplate( DayPlan day, Template template ) {
      clearDayPlan( day );
      addFromTemplate( day, template );
   }//End Method

   public void addFromTemplate( DayPlan dayPlan, Template template ) {
      for ( FoodPortion portion : template.portions() ) {
         FoodPortion copy = new FoodPortion( portion.food().get(), portion.portion().get() );
         dayPlan.portions().add( copy );
      }
   }//End Method

   public void clearDayPlan( DayPlan dayPlan ) {
      dayPlan.portions().clear();
   }//End Method

   public void saveAsTemplate( String name, DayPlan dayPlan ) {
      Template template = templates.createConcept( name );
      template.portions().addAll( dayPlan.portions() );
   }//End Method

   public void copyToDay( DayPlan fromDay, LocalDate toDate ) {
      DayPlan toPlan = mappedPlans.get( toDate );
      if ( toPlan == null ) {
         return;
      }
      
      addFromTemplate( toPlan, fromDay );
   }//End Method
   
}//End Class
