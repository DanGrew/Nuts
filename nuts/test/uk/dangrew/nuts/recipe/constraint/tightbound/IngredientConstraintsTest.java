package uk.dangrew.nuts.recipe.constraint.tightbound;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;

public class IngredientConstraintsTest {

   private RecipeConfiguration configuration;
   private IngredientConstraints systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      configuration = new RecipeConfiguration();
      systemUnderTest = new IngredientConstraints();
   }//End Method

   @Test public void shouldUpdateConstraintsWhenIngredientsChange() {
      systemUnderTest.configure( configuration );
      
      FoodItem item = new FoodItem( "Item" );
      assertThat( systemUnderTest.constraintFor( item ), is( nullValue() ) );
      configuration.ingredients().add( item );
      assertThat( systemUnderTest.constraintFor( item ), is( notNullValue() ) );
      configuration.ingredients().remove( item );
      assertThat( systemUnderTest.constraintFor( item ), is( nullValue() ) );
   }//End Method

}//End Class
