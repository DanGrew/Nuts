package uk.dangrew.nuts.day;

import static uk.dangrew.nuts.graphics.database.FoodTypes.ofType;
import static uk.dangrew.nuts.graphics.database.FoodTypes.ofTypeInPortion;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
   
   public FoodItemStore foodItems(){
      return foodItems;
   }//End Method
   
   public MealStore meals(){
      return meals;
   }//End Method
   
   public FoodPortion add( FoodPortion portion, FoodHolder subject ) {
      ObjectProperty< FoodPortion > createdPortion = new SimpleObjectProperty<>();
      ofType( portion.food().get(), FoodItem.class ).ifPresent( foodItem -> {
         createdPortion.set( add( foodItem, portion.portion().get(), subject ) );
      } );
      ofType( portion.food().get(), Meal.class ).ifPresent( meal -> {
         createdPortion.set( add( meal, portion.portion().get(), subject ) );
      } );
      return createdPortion.get();
   }//End Method
   
   private FoodPortion add( 
            FoodItem food, 
            double portion, 
            FoodHolder subject
   ) {
      FoodItem copy = copy( food );
      FoodPortion newPortion = new FoodPortion( copy, portion );
      subject.portions().add( newPortion );
      return newPortion;
   }//End Method
   
   private FoodPortion add( 
            Meal food, 
            double portion, 
            FoodHolder subject
   ) {
      Meal copy = copy( food );
      FoodPortion newPortion = new FoodPortion( copy, portion );
      subject.portions().add( newPortion );
      return newPortion;
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
      ofTypeInPortion( toRemove, FoodItem.class ).ifPresent( foodItems::removeConcept );
      ofTypeInPortion( toRemove, Meal.class ).ifPresent( meals::removeConcept );
   }//End Method

}//End Class
