package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.beans.value.ObservableValue;
import uk.dangrew.nuts.graphics.common.CheckBoxControllerImpl;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class UiRecipeConstraintCheckBoxController extends CheckBoxControllerImpl< RecipeConstraint > {
   
   private final UiRecipeConstraintController controller;
   
   public UiRecipeConstraintCheckBoxController( UiRecipeConstraintController controller ) {
      this.controller = controller;
   }//End Constructor
   
   @Override protected boolean getModelValue( RecipeConstraint concept ) {
      return concept.enabled().get();
   }//End Method
   
   @Override protected void apply( ObservableValue< ? extends Boolean > property, boolean o, boolean included ) {
      this.conceptFor( property ).enabled().set( included );
      this.controller.recalculate();
   }//End Method
   
}//End Class
