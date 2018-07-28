package uk.dangrew.nuts.recipe.constraint;

import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.optim.linear.LinearConstraint;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;

public abstract class RecipeConstraintBase implements RecipeConstraint {

   private final ConstraintType type;
   private final ObjectProperty< Boolean > enabled;
   private final ObjectProperty< String > description;
   
   public RecipeConstraintBase( ConstraintType type ) {
      this.type = type;
      this.enabled = new SimpleObjectProperty<>( true );
      this.description = new SimpleObjectProperty<>();
   }//End Constructor
   
   @Override public ObjectProperty< Boolean > enabled() {
      return enabled;
   }//End Method
   
   @Override public ObjectProperty< String > description() {
      return description;
   }//End Method
   
   @Override public ConstraintType type(){
      return type;
   }//End Method

   @Override public List< LinearConstraint > generate( List< Food > foods ) {
      if ( !canGenerate( foods ) ) {
         return Collections.emptyList();
      }
      return unconditionalGenerate( foods );
   }//End Method
   
   protected abstract List< LinearConstraint > unconditionalGenerate( List< Food > foods );
   
   private boolean canGenerate( List< Food > foods ) {
      if ( enabled.get() == null || !enabled.get() ) {
         return false;
      }
      
      return hasSufficientParameters( foods );
   }//End Method
   
   protected boolean hasSufficientParameters( List< Food > foods ) {
      return true;
   }//End Method
   
}//End Class
