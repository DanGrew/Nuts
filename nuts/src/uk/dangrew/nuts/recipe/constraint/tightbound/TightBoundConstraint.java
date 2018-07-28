package uk.dangrew.nuts.recipe.constraint.tightbound;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraintBase;
import uk.dangrew.nuts.recipe.constraint.raw.RawBoundConstraint;

public class TightBoundConstraint< SubjectT > extends RecipeConstraintBase implements RecipeConstraint {

   private final ObjectProperty< SubjectT > subject;
   private final RawBoundConstraint< SubjectT > lowerBound;
   private final RawBoundConstraint< SubjectT > upperBound;
   
   protected TightBoundConstraint( 
            ConstraintType type,
            Supplier< RawBoundConstraint< SubjectT > > constraintGenerator
   ) {
      super( type );
      this.subject = new SimpleObjectProperty<>();
      this.lowerBound = constraintGenerator.get();
      this.lowerBound.relationship().set( Relationship.GEQ );
      this.upperBound = constraintGenerator.get();
      this.upperBound.relationship().set( Relationship.LEQ );
      
      this.subject.addListener( ( s, o, n ) -> {
         this.lowerBound.subject().set( n );
         this.upperBound.subject().set( n );
         description().set( lowerBound.subjectDisplayName() + " Tight Bound" );
      } );
   }//End Constructor
   
   public ObjectProperty< SubjectT > subject() {
      return subject;
   }//End Method

   public ObjectProperty< Double > lowerBound() {
      return lowerBound.bound();
   }//End Method

   public ObjectProperty< Double > upperBound() {
      return upperBound.bound();
   }//End Method

   @Override public List< LinearConstraint > unconditionalGenerate( List< Food > foods ) {
      List< LinearConstraint > constraints = new ArrayList<>();
      lowerBound.generate( foods ).forEach( constraints::add );
      upperBound.generate( foods ).forEach( constraints::add );
      return constraints;
   }//End Method

}//End Class
