package uk.dangrew.nuts.recipe.constraint.tightbound;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.linear.LinearConstraint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraintBase;

public class TightBoundConstraints< SubjectT > extends RecipeConstraintBase implements RecipeConstraint {

   private final Supplier< TightBoundConstraint< SubjectT > > constraintGenerator;
   private final Map< SubjectT, TightBoundConstraint< SubjectT > > constraints;
   private final ObservableList< TightBoundConstraint< SubjectT > > constraintsList;
   private final ObservableList< TightBoundConstraint< SubjectT > > publicConstraintsList;
   
   public TightBoundConstraints(
            ConstraintType type,
            Supplier< TightBoundConstraint< SubjectT > > constraintGenerator 
   ) {
      super( type );
      this.constraintGenerator = constraintGenerator;
      this.constraints = new LinkedHashMap<>();
      this.constraintsList = FXCollections.observableArrayList(); 
      this.publicConstraintsList = new PrivatelyModifiableObservableListImpl<>( constraintsList );
   }//End Constructor
   
   protected void putConstraintFor( SubjectT subject ){
      this.constraints.put( subject, constraintGenerator.get() );
      this.constraints.get( subject ).subject().set( subject );
      this.constraintsList.add( this.constraints.get( subject ) );
   }//End Method
   
   protected void removeConstraintFor( SubjectT subject ){
      TightBoundConstraint< SubjectT > constraint = this.constraints.get( subject );
      this.constraints.remove( subject );
      this.constraintsList.remove( constraint );
   }//End Method

   public ObservableList< TightBoundConstraint< SubjectT > > constraints(){
      return publicConstraintsList;
   }//End Method

   @Override public List< LinearConstraint > unconditionalGenerate( List< Food > ingredients ) {
      return constraints.values().stream()
         .map( c -> c.generate( ingredients ) )
         .flatMap( List::stream )
         .collect( Collectors.toList() );
   }//End Method

   public TightBoundConstraint< SubjectT > constraintFor( SubjectT subject ) {
      return constraints.get( subject );
   }//End Method
   
}//End Class
