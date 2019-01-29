package uk.dangrew.nuts.graphics.recipe.creator.composite;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.recipe.creator.UiRecipeConstraintController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraints;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraint;

public class UiTightBoundConstraintsControllerTest {

   private RecipeConfiguration configuration;
   private IngredientConstraints selected;
   
   private ConceptTable< TightBoundConstraint< Food > > table;
   @Mock private UiRecipeConstraintController controller;
   private UiTightBoundConstraintsController< Food > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      configuration = new RecipeConfiguration();
      configuration.ingredients().add( new FoodItem( "Item1" ) );
      configuration.ingredients().add( new FoodItem( "Item2" ) );
      configuration.ingredients().add( new FoodItem( "Item3" ) );
      
      selected = new IngredientConstraints();
      selected.configure( configuration );
      
      table = new TableComponents< TightBoundConstraint< Food > >()
               .applyColumns( TightBoundsConstraintColumns< Food >::new )
               .withController( mock( UiTightBoundConstraintsController.class ) )
               .buildTable();
      systemUnderTest = new UiTightBoundConstraintsController<>( controller );
      systemUnderTest.associate( table );
   }//End Method

   @Test public void shouldListenForSelectedConstraintChanges() {
      systemUnderTest.select( selected );
      assertTableMatchesConstraints( 3 );
      
      configuration.ingredients().add( new FoodItem( "Item4" ) );
      assertTableMatchesConstraints( 4 );
   }//End Method
   
   @Test public void shouldAddEachConstraintWhenSelected() {
      systemUnderTest.select( selected );
      assertTableMatchesConstraints( 3 );
   }//End Method
   
   @Test public void shouldRecalculateOnSelection() {
      systemUnderTest.select( selected );
      verify( controller ).recalculate();
   }//End Method
   
   @Test public void shouldUnregisterOnAlternateSelection() {
      systemUnderTest.select( selected );
      verify( controller, times( 1 ) ).recalculate();
      systemUnderTest.select( null );
      verify( controller, times( 1 ) ).recalculate();
      
      selected.constraints().get( 0 ).upperBound().set( 23.0 );
      verify( controller, times( 1 ) ).recalculate();
      
      selected.constraints().get( 0 ).upperBound().set( 42.0 );
      verify( controller, times( 1 ) ).recalculate();
   }//End Method
   
   @Test public void shouldRecalculateOnUpperBoundChanges() {
      systemUnderTest.select( selected );
      verify( controller ).recalculate();
      
      selected.constraints().get( 0 ).upperBound().set( 23.0 );
      verify( controller, times( 2 ) ).recalculate();
      
      selected.constraints().get( 0 ).upperBound().set( 42.0 );
      verify( controller, times( 3 ) ).recalculate();
   }//End Method
   
   @Test public void shouldRecalculateOnLowerBoundChanges() {
      systemUnderTest.select( selected );
      verify( controller ).recalculate();
      
      selected.constraints().get( 0 ).lowerBound().set( 23.0 );
      verify( controller, times( 2 ) ).recalculate();
      
      selected.constraints().get( 0 ).lowerBound().set( 42.0 );
      verify( controller, times( 3 ) ).recalculate();
   }//End Method

   private void assertTableMatchesConstraints( int expectedRows ){
      assertThat( table.getRows().size(), is( expectedRows ) );
      assertThat( table.getRows(), hasSize( selected.constraints().size() ) );
      for ( int i = 0; i < table.getRows().size(); i++ ) {
         assertThat( table.getRows().get( i ).concept(), is( selected.constraints().get( i ) ) );
      }
   }//End Method

}//End Class
