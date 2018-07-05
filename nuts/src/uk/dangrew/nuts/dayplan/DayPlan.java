package uk.dangrew.nuts.dayplan;

import java.util.Iterator;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.system.Properties;
import uk.dangrew.nuts.template.Template;

public class DayPlan implements FoodHolder {
   
   private final Template structure;
   
   public DayPlan( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   public DayPlan( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   private DayPlan( Properties properties ) {
      this( new Template( properties ) );
   }//End Constructor
   
   DayPlan( Template structure ) {
      this.structure = structure;
   }//End Constructor

   @Override public Properties properties() {
      return structure.properties();
   }//End Method

   @Override public Nutrition nutrition() {
      return structure.nutrition();
   }//End Method

   @Override public FoodAnalytics foodAnalytics() {
      return structure.foodAnalytics();
   }//End Method
   
   public GoalAnalytics goalAnalytics(){
      return structure.goalAnalytics();
   }//End Method

   @Override public Food duplicate( String referenceId ) {
      return this;
   }//End Method
   
   @Override public ObservableList< FoodPortion > portions(){
      return structure.portions();
   }//End Method
   
   @Override public void swap( FoodPortion portion1, FoodPortion portion2 ) {
      structure.swap( portion1, portion2 );
   }//End Method
   
   void remove( FoodPortion toRemove ) {
      remove( toRemove, structure );
   }//End Method
   
   void remove( FoodPortion toRemove, Meal from ) {
      for ( Iterator< FoodPortion > iterator = from.portions().iterator(); iterator.hasNext(); ) {
         FoodPortion next = iterator.next();
         if ( next == toRemove ) {
            iterator.remove();
            return;
         }
         FoodTypes.ofType( next.food().get(), Meal.class ).ifPresent( meal -> remove( toRemove, meal ) );
      }
   }//End Method

}//End Class
