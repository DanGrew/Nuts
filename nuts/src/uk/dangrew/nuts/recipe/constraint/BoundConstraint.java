package uk.dangrew.nuts.recipe.constraint;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class BoundConstraint implements RecipeConstraint {

   private final ObjectProperty< String > description;
   private final ObjectProperty< NutritionalUnit > unit;
   private final ObjectProperty< Relationship > relationship;
   private final ObjectProperty< Double > bound;
   
   public BoundConstraint() {
      this( null, null, null );
   }//End Constructor
   
   public BoundConstraint( NutritionalUnit unit, Relationship relationship, Double bound ) {
      this.unit = new SimpleObjectProperty<>( unit );
      this.relationship = new SimpleObjectProperty<>( relationship );
      this.bound = new SimpleObjectProperty<>( bound );
      this.description = new SimpleObjectProperty<>();
      this.updateDescription();
      
      this.unit.addListener( ( s, o, n ) -> updateDescription() );
      this.relationship.addListener( ( s, o, n ) -> updateDescription() );
      this.bound.addListener( ( s, o, n ) -> updateDescription() );
   }//End Constructor
   
   private void updateDescription(){
      description.set( 
               new StringJoiner( ", " )
                  .add( unit.get() == null ? "No Unit" : unit.get().displayName() )
                  .add( relationship.get() == null ? "No Relationship" : relationship.get().toString() )
                  .add( bound.get() == null ? "No Bound" : "" + bound.get() )
                  .toString()
      );
   }//End Method
   
   public ObjectProperty< NutritionalUnit > unit(){
      return unit;
   }//End Method
   
   public ObjectProperty< Relationship > relationship(){
      return relationship;
   }//End Method
   
   public ObjectProperty< Double > bound(){
      return bound;
   }//End Method
   
   @Override public ObjectProperty< String > description() {
      return description;
   }//End Method
   
   @Override public ConstraintType type() {
      return ConstraintType.Bound;
   }//End Method

   @Override public Optional< LinearConstraint > generate( List< Food > foods ) {
      if ( !hasSufficientParameters() ) {
         return Optional.empty();
      }
      double[] coeffecients = new double[ foods.size() ];
      for ( int i = 0; i < foods.size(); i++ ) {
         coeffecients[ i ] = foods.get( i ).nutrition().of( unit.get() ).get();
      }
      
      return Optional.of( new LinearConstraint( coeffecients, relationship.get(), bound.get() ) );
   }//End Method
   
   private boolean hasSufficientParameters(){
      if ( !Optional.ofNullable( unit.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( relationship.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( bound.get() ).isPresent() ) {
         return false;
      }
      
      return true;
   }//End Method

}//End Class
