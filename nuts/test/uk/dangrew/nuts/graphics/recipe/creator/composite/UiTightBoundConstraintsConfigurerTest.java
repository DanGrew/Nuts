package uk.dangrew.nuts.graphics.recipe.creator.composite;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.recipe.creator.UiRecipeConstraintController;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraints;

public class UiTightBoundConstraintsConfigurerTest {

   private NutritionalUnitConstraints constraint;
   @Mock private UiRecipeConstraintController controller;
   private UiTightBoundConstraintsConfigurer< NutritionalUnit > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      constraint = new NutritionalUnitConstraints();
      systemUnderTest = new UiTightBoundConstraintsConfigurer<>( 
               constraint, 
               controller 
      );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 999999 );
   }//End Method
   
}//End Class
