package uk.dangrew.nuts.graphics.recipe.creator;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboProperty;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.RecipeFunction;

public class UiFunctionConfigurer extends BorderPane {

   public UiFunctionConfigurer( UiRecipeConstraintController controller, RecipeFunction function ) {
      setCenter( new PropertiesPane( "Function Properties", 
               new PropertyRowBuilder()
                  .withLabelName( "Nutritional Unit" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           FXCollections.observableArrayList( NutritionalUnit.values() ), 
                           function.unit().get(), 
                           ( s, o, n ) -> {
                              function.unit().set( n );
                              controller.recalculate();
                           } ) 
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "Goal Type" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           FXCollections.observableArrayList( GoalType.values() ), 
                           function.goalType().get(), 
                           ( s, o, n ) -> {
                              function.goalType().set( n );
                              controller.recalculate();
                           } ) 
                  )
         )
      );
   }//End Constructor
   
}//End Class
