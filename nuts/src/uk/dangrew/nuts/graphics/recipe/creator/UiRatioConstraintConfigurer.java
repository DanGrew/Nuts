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
import uk.dangrew.nuts.recipe.constraint.RatioConstraint;

public class UiRatioConstraintConfigurer extends BorderPane {

   private final ComboBox< Food > firstIngredientBox;
   private final ComboBox< Food > secondIngredientBox;
   
   public UiRatioConstraintConfigurer( UiRecipeConstraintController controller, RatioConstraint constraint ) {
      setCenter( new PropertiesPane( "Ratio Constraint Properties", 
               new PropertyRowBuilder()
                  .withLabelName( "First Ingredient" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           firstIngredientBox = new ComboBox<>( controller.configuration().ingredients() ), 
                           constraint.firstIngredient().get(), 
                           ( s, o, n ) -> {
                              constraint.firstIngredient().set( n );
                              controller.recalculate();
                           } ) 
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "How Much of First Ingredient (eg '3' as in 3:2)" )
                  .withBinding( new ResponsiveDoubleAsTextProperty( 
                           constraint.howMuchOfFirst().get(), 
                           ( o, n ) -> {
                              constraint.howMuchOfFirst().set( n );
                              controller.recalculate();
                           },
                           true 
                        )
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "Second Ingredient" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           secondIngredientBox = new ComboBox<>( controller.configuration().ingredients() ), 
                           constraint.secondIngredient().get(), 
                           ( s, o, n ) -> {
                              constraint.secondIngredient().set( n );
                              controller.recalculate();
                           } ) 
                  ),
               new PropertyRowBuilder()
                  .withLabelName( "How Much of Second Ingredient (eg '2' as in 3:2)" )
                  .withBinding( new ResponsiveDoubleAsTextProperty( 
                           constraint.howMuchOfSecond().get(), 
                           ( o, n ) -> {
                              constraint.howMuchOfSecond().set( n );
                              controller.recalculate();
                           },
                           true 
                        )
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
                  )
         )
      );
      
      firstIngredientBox.setConverter( new FoodStringConverter( controller.configuration().ingredients() ) );
      secondIngredientBox.setConverter( new FoodStringConverter( controller.configuration().ingredients() ) );
   }//End Constructor
   
}//End Class
