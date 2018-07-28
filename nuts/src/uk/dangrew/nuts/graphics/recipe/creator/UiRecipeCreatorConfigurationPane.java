package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.meal.MealTableController;
import uk.dangrew.nuts.graphics.table.ConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.store.Database;

public class UiRecipeCreatorConfigurationPane extends GridPane {

   private final ConceptTableWithControls< RecipeConstraint > table;
   private final UiRecipeConstraintController controller;
   private final UiRecipeConstraintSelector selector;
   private final MealTableController resultController;
   
   public UiRecipeCreatorConfigurationPane( MealTableController resultController, RecipeConfiguration recipeConfiguration ) {
      this.resultController = resultController;
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 20, 80 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );

      this.controller = new UiRecipeConstraintController( recipeConfiguration );
      
      add( 
               table = new TableComponents< RecipeConstraint >()
                  .withDatabase( new Database() )
                  .withCheckBoxController( new UiRecipeConstraintCheckBoxController( controller ) )
                  .applyColumns( UiRecipeConstraintColumns::new )
                  .withController( controller )
                  .withControls( new ConceptControls( controller ) )
                  .buildTableWithControls( "Recipe Configurations" ),
      0, 0 );
      
      add( selector = new UiRecipeConstraintSelector( controller ), 1, 0 );
      controller.associate( table.table() );
      controller.associate( selector, resultController );
   }//End Constructor
   
}//End Class
