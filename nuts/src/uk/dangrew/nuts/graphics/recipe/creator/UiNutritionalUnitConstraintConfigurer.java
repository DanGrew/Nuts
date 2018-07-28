package uk.dangrew.nuts.graphics.recipe.creator;

import org.apache.commons.math3.optim.linear.Relationship;

import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;

public class UiNutritionalUnitConstraintConfigurer extends BorderPane {

   public UiNutritionalUnitConstraintConfigurer( UiRecipeConstraintController controller, NutritionalUnitRawConstraint constraint ) {
      setCenter( new PropertiesPane( "Bound Constraint Properties", 
               new PropertyRowBuilder()
                  .withLabelName( "Bound Unit" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           FXCollections.observableArrayList( NutritionalUnit.values() ), 
                           constraint.subject().get(), 
                           ( s, o, n ) -> {
                              constraint.subject().set( n );
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
                  .withLabelName( "Bound Value (such as grams, mls, etc)" )
                  .withBinding( new ResponsiveDoubleAsTextProperty( 
                           constraint.bound().get(), 
                           ( o, n ) -> {
                              constraint.bound().set( n );
                              controller.recalculate();
                           },
                           true 
                  )
               )
         )
      );
   }//End Constructor
   
}//End Class
