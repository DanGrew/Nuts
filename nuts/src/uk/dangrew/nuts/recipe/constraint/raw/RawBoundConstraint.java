package uk.dangrew.nuts.recipe.constraint.raw;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraintBase;

public abstract class RawBoundConstraint< SubjectT > extends RecipeConstraintBase implements RecipeConstraint {

   private final ObjectProperty< SubjectT > subject;
   private final ObjectProperty< Relationship > relationship;
   private final ObjectProperty< Double > bound;
   
   protected RawBoundConstraint( ConstraintType type, SubjectT subject, Relationship relationship, Double bound ) {
      super( type );
      this.subject = new SimpleObjectProperty<>( subject );
      this.relationship = new SimpleObjectProperty<>( relationship );
      this.bound = new SimpleObjectProperty<>( bound );
      this.updateDescription();
      
      this.subject.addListener( ( s, o, n ) -> updateDescription() );
      this.relationship.addListener( ( s, o, n ) -> updateDescription() );
      this.bound.addListener( ( s, o, n ) -> updateDescription() );
   }//End Constructor
   
   private void updateDescription(){
      description().set( 
               new StringJoiner( ", " )
                  .add( subjectDisplayName() )
                  .add( relationship.get() == null ? "No Relationship" : relationship.get().toString() )
                  .add( bound.get() == null ? "No Bound" : "" + bound.get() )
                  .toString()
      );
   }//End Method
   
   public abstract String subjectDisplayName();
   
   protected abstract double coefficientFor( Food food );
   
   public ObjectProperty< SubjectT > subject(){
      return subject;
   }//End Method
   
   public ObjectProperty< Relationship > relationship(){
      return relationship;
   }//End Method
   
   public ObjectProperty< Double > bound(){
      return bound;
   }//End Method
   
   @Override public List< LinearConstraint > unconditionalGenerate( List< Food > foods ) {
      if ( !hasSufficientParameters( foods ) ) {
         return Collections.emptyList();
      }
      double[] coeffecients = new double[ foods.size() ];
      for ( int i = 0; i < foods.size(); i++ ) {
         coeffecients[ i ] = coefficientFor( foods.get( i ) );
      }
      
      return Arrays.asList( new LinearConstraint( coeffecients, relationship.get(), bound.get() ) );
   }//End Method
   
   @Override protected boolean hasSufficientParameters( List< Food > foods ){
      if ( !Optional.ofNullable( subject.get() ).isPresent() ) {
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
