package uk.dangrew.nuts.recipe.constraint;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class RecipeConfiguration implements Concept {

   private final Properties properties;
   private final ObservableList< Food > ingredients;
   private final ObjectProperty< RecipeFunction > function;
   private final ObservableList< RecipeConstraint > contraints;
   
   public RecipeConfiguration() {
      this.properties = new Properties( "Recipe Configuration" );
      this.ingredients = FXCollections.observableArrayList();
      this.function = new SimpleObjectProperty<>( new RecipeFunction() );
      this.contraints = FXCollections.observableArrayList();
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method
   
   public ObservableList< Food > ingredients(){
      return ingredients;
   }//End Method
   
   public ObjectProperty< RecipeFunction > function(){
      return function;
   }//End Method
   
   public ObservableList< RecipeConstraint > constraints() {
      return contraints;
   }//End Method
   
   public LinearConstraintSet generateConstraints(){
      Collection< LinearConstraint > generated = contraints.stream()
         .map( c -> c.generate( ingredients ) )
         .filter( Optional::isPresent )
         .map( Optional::get )
         .collect( Collectors.toList() );
      
      ingredients.stream()
         .map( i -> new IngredientConstraint( i, Relationship.GEQ, 0.0 ) )
         .map( c -> c.generate( ingredients ) )
         .filter( Optional::isPresent )
         .map( Optional::get )
         .forEach( generated::add );
      return new LinearConstraintSet( generated );
   }//End Method
   
}//End Class
