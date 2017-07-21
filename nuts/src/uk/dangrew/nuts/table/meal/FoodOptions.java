/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodOptions} provides the combined {@link Food}s available in the {@link Database}.
 */
class FoodOptions {

   private final Comparator< Food > comparator;
   private final ObservableList< Food > options;
   
   /**
    * Constructs a new {@link FoodOptions}.
    * @param database the {@link Database}.
    */
   FoodOptions( Database database ) {
      this.options = FXCollections.observableArrayList();
      this.comparator = Comparators.stringExtractionComparater( f -> f.properties().nameProperty().get() );
      
      database.foodItems().objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::add, this::remove 
      ) );
      database.meals().objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::add, this::remove 
      ) );
   }//End Constructor
   
   /**
    * Method to add a {@link Food} to the options.
    * @param food the {@link Food} to add.
    */
   private void add( Food food ) {
      options.add( food );
      Collections.sort( options, comparator );
   }//End Method

   /**
    * Method to remove a {@link Food} to the options.
    * @param food the {@link Food} to remove.
    */
   private void remove( Food food ) {
      options.remove( food );
   }//End Method

   /**
    * Access to the {@link ObservableList} options.
    * @return the {@link ObservableList}.
    */
   ObservableList< Food > options() {
      return options;
   }//End Method

   /**
    * Method to find the first {@link Food} with the given name.
    * @param name the name to look for.
    * @return the found {@link Food}.
    */
   Food find( String name ) {
      Optional< Food > found = options().stream().filter( 
               f -> f.properties().nameProperty().get().equals( name ) 
      ).findFirst();
      
      if ( found.isPresent() ) {
         return found.get();
      } else {
         return null;
      }
   }//End Method

}//End Class
