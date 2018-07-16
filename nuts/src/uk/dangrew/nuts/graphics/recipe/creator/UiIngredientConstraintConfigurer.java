package uk.dangrew.nuts.graphics.recipe.creator;

import org.apache.commons.math3.optim.linear.Relationship;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.FoodStringConverter;
import uk.dangrew.nuts.recipe.constraint.IngredientConstraint;

public class UiIngredientConstraintConfigurer extends BorderPane {

   private final ComboBox< Food > ingredientBox;
   
   public UiIngredientConstraintConfigurer( UiRecipeConstraintController controller, IngredientConstraint constraint ) {
      setCenter( new PropertiesPane( "Ingredient Constraint Properties", 
               new PropertyRowBuilder()
                  .withLabelName( "Ingredient" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           ingredientBox = new ComboBox<>( controller.configuration().ingredients() ), 
                           constraint.ingredient().get(), 
                           ( s, o, n ) -> {
                              constraint.ingredient().set( n );
                              controller.recalculate();
                           } ) 
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "Relationship" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           FXCollections.observableArrayList( Relationship.values() ), 
                           constraint.relationship().get(), 
                           ( s, o, n ) -> {
                              constraint.relationship().set( n );
                              controller.recalculate();
                           } ) 
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "Amount of Ingredient (number of portions, not %)" )
                  .withBinding( new ResponsiveDoubleAsTextProperty( 
                           constraint.amountOfIngredient().get(), 
                           ( o, n ) -> {
                              constraint.amountOfIngredient().set( n );
                              controller.recalculate();
                           },
                           true 
                  )
               )
         )
      );
      
      ingredientBox.setConverter( new FoodStringConverter( controller.configuration().ingredients() ) );
   }//End Constructor
   
}//End Class
