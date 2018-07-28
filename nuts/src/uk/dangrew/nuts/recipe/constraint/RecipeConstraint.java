package uk.dangrew.nuts.recipe.constraint;

import java.util.List;

import org.apache.commons.math3.optim.linear.LinearConstraint;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.Food;

public interface RecipeConstraint {
   
   public ObjectProperty< Boolean > enabled();
   
   public ObjectProperty< String > description();
   
   public ConstraintType type();

   public default void configure( RecipeConfiguration configuration ){}
   
   public List< LinearConstraint > generate( List< Food > foods );

}//End Interface

