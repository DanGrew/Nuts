package uk.dangrew.nuts.store;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.system.ConceptStore;

public class FoodReferenceChecker {

   private final Database database;
   private final Set< Food > references;
   
   private Food searchedFor;
   
   public FoodReferenceChecker( Database database ) {
      this.database = database;
      this.references = new LinkedHashSet<>();
   }//End Constructor

   public void searchFor( Food searchFor ) {
      this.searchedFor = searchFor;
      this.references.clear();
      findReferencesInStore( database.meals() );
      findReferencesInStore( database.templates() );
      findReferencesInStore( database.dayPlans() );
      findReferencesInStore( database.shoppingLists() );
   }//End Method
   
   private void findReferencesInStore( ConceptStore< ? extends Meal > store ) {
      references.addAll( 
               store.objectList().stream()
                  .filter( m -> mealDoesNotContainFoodInPortion( m, searchedFor ) )
                  .collect( Collectors.toSet() ) 
      );
   }//End Method
   
   private boolean mealDoesNotContainFoodInPortion( Meal meal, Food food ) {
      return meal.portions().stream()
         .filter( p -> p.food().get() == food )
         .count() > 0;
   }//End Method

   public Collection< Food > lastSearchResult() {
      return references;
   }//End Method

   public void removeReferences() {
      if ( searchedFor == null ) {
         return;
      }
      
      references.stream()
         .filter( f -> ( f instanceof Meal ) )
         .map( f -> ( Meal )f )
         .forEach( this::removeFoodFromMeal );
      references.clear();
   }//End Method
   
   private void removeFoodFromMeal( Meal meal ) {
      meal.portions().removeIf( p -> p.food().get() == searchedFor );
   }//End Method

   public void replaceWith( Food replacement ) {
      if ( searchedFor == null ) {
         return;
      }
      
      references.stream()
         .filter( f -> ( f instanceof Meal ) )
         .map( f -> ( Meal )f )
         .forEach( m -> replaceFoodInMeal( m, replacement ) );
      references.clear();
   }//End Method
   
   private void replaceFoodInMeal( Meal meal, Food replacement ) {
      meal.portions().stream()
         .filter( p -> p.food().get() == searchedFor )
         .forEach( p -> p.setFood( replacement ) );
   }//End Method

}//End Class
