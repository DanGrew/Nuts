/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link FoodOptions} provides the combined {@link Food}s available in the {@link Database}.
 */
public class FoodOptions< FoodTypeT extends Food > {

   private final Comparator< FoodTypeT > comparator;
   private final ObservableList< FoodTypeT > options;

   /**
    * Constructs a new {@link FoodOptions}.
    * @param foodStore the {@link FoodStore} to consider.
    */
   public FoodOptions( FoodStore< ? extends FoodTypeT > foodStore ) {
      this( Collections.singletonList( foodStore ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodOptions}.
    * @param foodStores the {@link Collection} of {@link FoodStore}s to consider.
    */
   public FoodOptions( Collection< FoodStore< ? extends FoodTypeT > > foodStores ) {
      this.options = FXCollections.observableArrayList();
      this.comparator = Comparators.stringExtractionComparater( f -> f.properties().nameProperty().get() );
      
      for ( FoodStore< ? extends FoodTypeT > store : foodStores ) {
         store.objectList().forEach( this::add );
         store.objectList().addListener( new FunctionListChangeListenerImpl<>( 
                  this::add, this::remove 
         ) );
      }
   }//End Constructor
   
   /**
    * Method to add a {@link Food} to the options.
    * @param food the {@link Food} to add.
    */
   private void add( FoodTypeT food ) {
      options.add( food );
      Collections.sort( options, comparator );
   }//End Method

   /**
    * Method to remove a {@link Food} to the options.
    * @param food the {@link Food} to remove.
    */
   private void remove( FoodTypeT food ) {
      options.remove( food );
   }//End Method

   /**
    * Access to the {@link ObservableList} options.
    * @return the {@link ObservableList}.
    */
   public ObservableList< FoodTypeT > options() {
      return options;
   }//End Method

   /**
    * Method to find the first {@link Food} with the given name.
    * @param name the name to look for.
    * @return the found {@link Food}.
    */
   public FoodTypeT find( String name ) {
      return options().stream().filter( 
               f -> f.properties().nameProperty().get().equals( name ) 
      ).findFirst().orElse( null );
   }//End Method

   public FoodTypeT first() {
      if ( options.isEmpty() ) {
         return null;
      }
      
      return options.get( 0 );
   }//End Method

}//End Class
