package uk.dangrew.nuts.graphics.recipe.integration;

import java.util.Optional;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.store.Database;

public class RecipeGeneratorControllerImpl {

   private final Database database;
   private final RecipeGeneratorWindow recipeWindow;
   
   public RecipeGeneratorControllerImpl( Database database ) {
      this( new RecipeGeneratorWindow(), database );
   }//End Constructor
   
   RecipeGeneratorControllerImpl( RecipeGeneratorWindow window, Database database ) {
      this.recipeWindow = window;
      this.database = database;
   }//End Method

   public Optional< FoodHolder > generateFor( Food selection ) {
      if ( selection == null ) {
         return Optional.empty();
      }
      
      if ( selection instanceof FoodHolder ) {
         return recipeWindow.generate( database, ( FoodHolder )selection );
      }
      return Optional.empty();
   }//End Method

}//End Class
