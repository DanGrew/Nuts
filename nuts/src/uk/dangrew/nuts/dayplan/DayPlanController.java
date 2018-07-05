package uk.dangrew.nuts.dayplan;

import static uk.dangrew.nuts.graphics.database.FoodTypes.ofType;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealStore;

public class DayPlanController {

   private final FoodItemStore foodItems;
   private final MealStore meals;
   
   public DayPlanController() {
      this( 
               new FoodItemStore(), 
               new MealStore() 
      );
   }//End Constructor
   
   DayPlanController(
            FoodItemStore foodItems, 
            MealStore meals
   ) {
      this.foodItems = foodItems;
      this.meals = meals;
   }//End Constructor
   
   public void add( FoodPortion portion, FoodHolder subject ) {
      ofType( portion.food().get(), FoodItem.class ).ifPresent( foodItem -> {
         add( foodItem, portion.portion().get(), subject );
      } );
      ofType( portion.food().get(), Meal.class ).ifPresent( meal -> {
         add( meal, portion.portion().get(), subject );
      } );
   }//End Method
   
   private void add( 
            FoodItem food, 
            double portion, 
            FoodHolder subject
   ) {
      FoodItem copy = copy( food );
      subject.portions().add( new FoodPortion( copy, portion ) );
   }//End Method
   
   private void add( 
            Meal food, 
            double portion, 
            FoodHolder subject
   ) {
      Meal copy = copy( food );
      subject.portions().add( new FoodPortion( copy, portion ) );
   }//End Method
   
   private FoodItem copy( FoodItem item ) {
      FoodItem copy = item.duplicate( "" );
      foodItems.store( copy );
      return copy;
   }//End Method
   
   private Meal copy( Meal food ) {
      Meal copy = meals.createConcept( food.properties().nameProperty().get() );
      for ( FoodPortion mealPortion : food.portions() ) {
         ofType( mealPortion.food().get(), FoodItem.class ).ifPresent( foodItem -> {
            FoodItem itemCopy = copy( foodItem );
            copy.portions().add( new FoodPortion( itemCopy, mealPortion.portion().get() ) );
         } );
         ofType( mealPortion.food().get(), Meal.class ).ifPresent( meal -> {
            Meal mealCopy = copy( meal );
            copy.portions().add( new FoodPortion( mealCopy, mealPortion.portion().get() ) );
         } );
      }
      return copy;
   }

   public void remove( FoodPortion toRemove, DayPlan dayPlan ) {
      dayPlan.remove( toRemove );
      ofType( toRemove.food().get(), FoodItem.class ).ifPresent( foodItems::removeConcept );
      ofType( toRemove.food().get(), Meal.class ).ifPresent( meals::removeConcept );
   }//End Method

}//End Class
