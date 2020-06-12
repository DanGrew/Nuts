package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.javafx.table.options.ConceptOptions;
import uk.dangrew.kode.javafx.table.options.ConceptOptionsImpl;
import uk.dangrew.kode.javafx.table.options.FilteredConceptOptions;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;

public class FilteredConceptOptionsTest {

   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   
   private FoodItemStore store;
   private ConceptOptions< Food > options;
   private FilteredConceptOptions< Food > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      store = new FoodItemStore();
      options = new ConceptOptionsImpl<>( store );
      systemUnderTest = new FilteredConceptOptions<>( options );
      
      chicken = store.createConcept( "Chicken" );
      beans = store.createConcept( "Beans" );
      sausages = store.createConcept( "Sausages" );
   }//End Method

   @Test public void shouldProvideOptions() {
      assertThat( systemUnderTest.options(), is( options.options() ) );
      
      store.createConcept( "Almonds" );
      assertThat( systemUnderTest.options(), is( options.options() ) );
      
      store.removeConcept( sausages );
      assertThat( systemUnderTest.options(), is( options.options() ) );
   }//End Method
   
   @Test public void shouldProvideReversedSorting() {
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
      assertThat( systemUnderTest.options().get( 2 ), is( sausages ) );
      
      systemUnderTest.invertedSorting().set( true );
      
      assertThat( systemUnderTest.options().get( 0 ), is( sausages ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
      assertThat( systemUnderTest.options().get( 2 ), is( beans ) );
      
      systemUnderTest.invertedSorting().set( false );
      
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
      assertThat( systemUnderTest.options().get( 2 ), is( sausages ) );
   }//End Method
   
   @Test public void shouldNotFilerByDefault() {
      systemUnderTest.filterString().set( "chi" );
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
      assertThat( systemUnderTest.options().get( 2 ), is( sausages ) );
      
      systemUnderTest.filterString().set( "s" );
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
      assertThat( systemUnderTest.options().get( 2 ), is( sausages ) );
   }//End Method
   
   @Test public void shouldReverseSortAndfilter() {
      systemUnderTest.applyFilter( f -> {
         String filter = systemUnderTest.filterString().get();
         if ( filter == null ) {
            return false;
         }
         filter = filter.toLowerCase();
         String name = f.properties().nameProperty().get().toLowerCase();
         return !name.contains( filter );
      } );
      
      systemUnderTest.filterString().set( "s" );
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( sausages ) );
      
      systemUnderTest.invertedSorting().set( true );
      assertThat( systemUnderTest.options().get( 0 ), is( sausages ) );
      assertThat( systemUnderTest.options().get( 1 ), is( beans ) );
      
      systemUnderTest.filterString().set( "chick" );
      assertThat( systemUnderTest.options().get( 0 ), is( chicken ) );
      
      systemUnderTest.filterString().set( "s" );
      assertThat( systemUnderTest.options().get( 0 ), is( sausages ) );
      assertThat( systemUnderTest.options().get( 1 ), is( beans ) );
   }//End Method
   
   @Test public void shouldProvideSortingProperty(){
      assertThat( systemUnderTest.invertedSorting().get(), is( false ) );
      systemUnderTest.invertedSorting().set( true );
      assertThat( systemUnderTest.invertedSorting().get(), is( true ) );
      systemUnderTest.invertedSorting().set( false );
      assertThat( systemUnderTest.invertedSorting().get(), is( false ) );
   }//End Method 
   
   @Test public void shouldProvidefilterProperty(){
      assertThat( systemUnderTest.filterString().get(), is( nullValue() ) );
      systemUnderTest.filterString().set( "anything" );
      assertThat( systemUnderTest.filterString().get(), is( "anything" ) );
      systemUnderTest.filterString().set( null );
      assertThat( systemUnderTest.filterString().get(), is( nullValue() ) );
   }//End Method 
   
   @Test public void shouldRefreshWhenSorted(){
      options.customSort( Comparators.reverseComparator( options.comparator() ) );
      assertThat( systemUnderTest.options(), is( options.options() ) );
   }//End Method
   
   @Test public void shouldApplyFilter(){
      systemUnderTest.applyFilter( f -> f == chicken );
      assertThat( systemUnderTest.options(), is( Arrays.asList( beans, sausages ) ) );
      
      systemUnderTest.clearFilters();
      systemUnderTest.applyFilter( f -> f.properties().nameProperty().get().contains( "s" ) );
      assertThat( systemUnderTest.options(), is( Arrays.asList( chicken ) ) );
   }//End Method
   
   @Test public void shouldApplyMultipleFilters(){
      systemUnderTest.applyFilter( f -> f == chicken );
      assertThat( systemUnderTest.options(), is( Arrays.asList( beans, sausages ) ) );

      systemUnderTest.applyFilter( f -> f.properties().nameProperty().get().contains( "g" ) );
      assertThat( systemUnderTest.options(), is( Arrays.asList( beans ) ) );
   }//End Method
   
   @Test public void shouldIgnoreEmptyStringForFilter(){
      systemUnderTest.filterString().set( " " );
      assertThat( systemUnderTest.options().get( 0 ), is( beans ) );
      assertThat( systemUnderTest.options().get( 1 ), is( chicken ) );
   }//End Method
}//End Class
