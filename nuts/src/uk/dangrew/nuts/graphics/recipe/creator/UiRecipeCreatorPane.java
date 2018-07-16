package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.meal.MealControls;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;
import uk.dangrew.nuts.store.Database;

public class UiRecipeCreatorPane extends GridPane {
   
   private final ConceptTableWithControls< FoodPortion > foodTable;
   private final MealTableControllerImpl resultController;
   
   public UiRecipeCreatorPane( Database database, Meal recipe ) {
      new JavaFxStyle().configureConstraintsForRowPercentages( this, 30, 40, 30 );
      new JavaFxStyle().configureConstraintsForEvenColumns( this, 1 );
      
      RecipeConfiguration recipeConfiguration = new RecipeConfiguration();
      recipe.portions().stream()
         .map( p -> p.food().get() )
         .forEach( f -> recipeConfiguration.ingredients().add( f ) );
      recipe.portions().addListener( new FunctionListChangeListenerImpl<>( 
               p -> recipeConfiguration.ingredients().add( p.food().get() ), 
               p -> recipeConfiguration.ingredients().remove( p.food().get() ) 
      ) );
      
      MealTableControllerImpl ingredientsController = new MealTableControllerImpl();
      add( foodTable = new TableComponents< FoodPortion >()
                        .withDatabase( database )
                        .applyColumns( FoodTableColumns< FoodPortion >::new ) 
                        .withController( ingredientsController )
                        .withControls( new MealControls( ingredientsController ) )
                        .buildTableWithControls( "Ingredients" ),
      0, 0 );
      ingredientsController.showMeal( recipe );
      
      add( new TableComponents< FoodPortion >()
               .withDatabase( new Database() )
               .applyColumns( MealTableColumns::new )
               .withController( resultController = new MealTableControllerImpl() )
               .buildTable(),
      0, 2 );
      add( new UiRecipeCreatorConfigurationPane( resultController, recipeConfiguration ), 0, 1 );
   }//End Constructor

}//End Class
