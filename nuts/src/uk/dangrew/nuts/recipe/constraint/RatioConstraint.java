package uk.dangrew.nuts.recipe.constraint;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;

public class RatioConstraint implements RecipeConstraint {
   
   private final ObjectProperty< String > description;
   private final ObjectProperty< Food > firstIngredient;
   private final ObjectProperty< Double > howMuchOfFirst;
   private final ObjectProperty< Food > secondIngredient;
   private final ObjectProperty< Double > howMuchOfSecond;
   private final ObjectProperty< Relationship > relationship;
   
   public RatioConstraint() {
      this( null, null, null, null, null );
   }//End Constructor
   
   public RatioConstraint( 
            Food firstIngredient, 
            Double howMuchOfFirst,
            Food secondIngredient, 
            Double howMuchOfSecond,
            Relationship relationship
   ) {
      this.firstIngredient = new SimpleObjectProperty<>( firstIngredient );
      this.howMuchOfFirst = new SimpleObjectProperty<>( howMuchOfFirst );
      this.secondIngredient = new SimpleObjectProperty<>( secondIngredient );
      this.howMuchOfSecond = new SimpleObjectProperty<>( howMuchOfSecond );
      this.relationship = new SimpleObjectProperty<>( relationship );
      this.description = new SimpleObjectProperty<>();
      this.updateDescription();
      
      this.firstIngredient.addListener( ( s, o, n ) -> updateDescription() );
      this.howMuchOfFirst.addListener( ( s, o, n ) -> updateDescription() );
      this.secondIngredient.addListener( ( s, o, n ) -> updateDescription() );
      this.howMuchOfSecond.addListener( ( s, o, n ) -> updateDescription() );
      this.relationship.addListener( ( s, o, n ) -> updateDescription() );
   }//End Constructor
   
   private void updateDescription(){
      description.set( 
               new StringJoiner( ", " )
                  .add( firstIngredient.get() == null ? "No First Ingredient" : firstIngredient.get().properties().nameProperty().get() )
                  .add( howMuchOfFirst.get() == null ? "No First Amount" : howMuchOfFirst.get().toString() )
                  .add( secondIngredient.get() == null ? "No Second Ingredient" : "" + secondIngredient.get().properties().nameProperty().get() )
                  .add( howMuchOfSecond.get() == null ? "No Second Amount" : "" + howMuchOfSecond.get().toString() )
                  .add( relationship.get() == null ? "No Relationship" : "" + relationship.get().toString() )
                  .toString()
      );
   }//End Method
   
   @Override public ObjectProperty< String > description() {
      return description;
   }//End Method
   
   @Override public ConstraintType type() {
      return ConstraintType.Ratio;
   }//End Method
   
   public ObjectProperty< Food > firstIngredient(){
      return firstIngredient;
   }//End Method
   
   public ObjectProperty< Food > secondIngredient(){
      return secondIngredient;
   }//End Method

   public ObjectProperty< Double > howMuchOfFirst(){
      return howMuchOfFirst;
   }//End Method
   
   public ObjectProperty< Double > howMuchOfSecond(){
      return howMuchOfSecond;
   }//End Method
   
   public ObjectProperty< Relationship > relationship(){
      return relationship;
   }//End Method

   @Override public Optional< LinearConstraint > generate( List< Food > foods ) {
      if ( !hasSufficientParameters( foods ) ) {
         return Optional.empty();
      }
      double[] coeffecients = new double[ foods.size() ];
      for ( int i = 0; i < foods.size(); i++ ) {
         Food food = foods.get( i );
         if ( food.properties().id().equals( firstIngredient.get().properties().id() ) ) {
            coeffecients[ i ] = howMuchOfSecond.get();
         } else if ( food.properties().id().equals( secondIngredient.get().properties().id() ) ) {
            coeffecients[ i ] = -howMuchOfFirst.get();
         } else {
            coeffecients[ i ] = 0.0;
         }
      }
      
      return Optional.of( new LinearConstraint( coeffecients, relationship.get(), 0.0 ) );
   }//End Method
   
   private boolean hasSufficientParameters( List< Food > foods ){
      if ( !Optional.ofNullable( firstIngredient.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( secondIngredient.get() ).isPresent() ) {
         return false;
      }
      
      if ( !Optional.ofNullable( howMuchOfFirst.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( howMuchOfSecond.get() ).isPresent() ) {
         return false;
      }
      
      if ( !Optional.ofNullable( relationship.get() ).isPresent() ) {
         return false;
      }
      
      if ( !foods.contains( firstIngredient.get() ) || !foods.contains( secondIngredient.get() ) ) {
         return false;
      }
      
      return true;
   }//End Method

}//End Class
