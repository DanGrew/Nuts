package uk.dangrew.nuts.recipe.constraint;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.Relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.recipe.constraint.raw.IngredientRawConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraints;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraints;

public class RecipeConfiguration implements Concept {

   private final Properties properties;
   private final ObservableList< Food > ingredients;
   private final ObjectProperty< RecipeFunction > function;
   private final ObservableList< RecipeConstraint > contraints;
   private final ObjectProperty< FoodHolder > solution;
   
   public RecipeConfiguration() {
      this.properties = new Properties( "Recipe Configuration" );
      this.ingredients = FXCollections.observableArrayList();
      this.function = new SimpleObjectProperty<>( new RecipeFunction() );
      this.contraints = FXCollections.observableArrayList();
      this.solution = new SimpleObjectProperty<>();
      
      this.contraints.add( new NutritionalUnitConstraints() );
      this.contraints.add( new IngredientConstraints() );
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Concept duplicate() {
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
         .flatMap( List::stream )
         .collect( Collectors.toList() );
      
      ingredients.stream()
         .map( i -> new IngredientRawConstraint( i, Relationship.GEQ, 0.0 ) )
         .map( c -> c.generate( ingredients ) )
         .flatMap( List::stream )
         .collect( Collectors.toList() )
         .forEach( generated::add );
      return new LinearConstraintSet( generated );
   }//End Method
   
   public ObjectProperty< FoodHolder > solution(){
      return solution;
   }//End Method
   
}//End Class
