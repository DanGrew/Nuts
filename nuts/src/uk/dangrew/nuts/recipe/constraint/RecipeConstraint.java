package uk.dangrew.nuts.recipe.constraint;

import java.util.List;
import java.util.Optional;

import org.apache.commons.math3.optim.linear.LinearConstraint;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.Food;

public interface RecipeConstraint {
   
   public ObjectProperty< String > description();
   
   public ConstraintType type();
   
   public Optional< LinearConstraint > generate( List< Food > foods );

}//End Interface

