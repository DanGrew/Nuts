package uk.dangrew.nuts.recipe.constraint;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;

public class IngredientConstraint implements RecipeConstraint {

   private final ObjectProperty< String > description;
   private final ObjectProperty< Food > ingredient;
   private final ObjectProperty< Relationship > relationship;
   private final ObjectProperty< Double > amountOfIngredient;
   
   public IngredientConstraint() {
      this( null, null, null );
   }//End Constructor
   
   public IngredientConstraint( Food ingredient, Relationship relationship, Double amountOfIngredient ) {
      this.ingredient = new SimpleObjectProperty<>( ingredient );
      this.relationship = new SimpleObjectProperty<>( relationship );
      this.amountOfIngredient = new SimpleObjectProperty<>( amountOfIngredient );
      this.description = new SimpleObjectProperty<>();
      this.updateDescription();
      
      this.ingredient.addListener( ( s, o, n ) -> updateDescription() );
      this.relationship.addListener( ( s, o, n ) -> updateDescription() );
      this.amountOfIngredient.addListener( ( s, o, n ) -> updateDescription() );
   }//End Constructor
   
   private void updateDescription(){
      description.set( 
               new StringJoiner( ", " )
                  .add( ingredient.get() == null ? "No Ingredient" : ingredient.get().properties().nameProperty().get() )
                  .add( relationship.get() == null ? "No Relationship" : relationship.get().toString() )
                  .add( amountOfIngredient.get() == null ? "No Amount" : "" + amountOfIngredient.get() )
                  .toString()
      );
   }//End Method
   
   @Override public ObjectProperty< String > description() {
      return description;
   }//End Method

   @Override public ConstraintType type() {
      return ConstraintType.Ingredient;
   }//End Method
   
   public ObjectProperty< Food > ingredient(){
      return ingredient;
   }//End Method
   
   public ObjectProperty< Relationship > relationship(){
      return relationship;
   }//End Method
   
   public ObjectProperty< Double > amountOfIngredient(){
      return amountOfIngredient;
   }//End Method

   @Override public Optional< LinearConstraint > generate( List< Food > foods ) {
      if ( !hasSufficientParameters( foods ) ) {
         return Optional.empty();
      }
      double[] coeffecients = new double[ foods.size() ];
      for ( int i = 0; i < foods.size(); i++ ) {
         Food food = foods.get( i );
         if ( food.properties().id().equals( ingredient.get().properties().id() ) ) {
            coeffecients[ i ] = 1.0;
         } else {
            coeffecients[ i ] = 0.0;
         }
      }
      
      return Optional.of( new LinearConstraint( coeffecients, relationship.get(), amountOfIngredient.get() ) );
   }//End Method
   
   private boolean hasSufficientParameters( List< Food > foods ){
      if ( !Optional.ofNullable( ingredient.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( relationship.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( amountOfIngredient.get() ).isPresent() ) {
         return false;
      }
      if ( !foods.contains( ingredient.get() ) ) {
         return false;
      }
      
      return true;
   }//End Method

}//End Class
