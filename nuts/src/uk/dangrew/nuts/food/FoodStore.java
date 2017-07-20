/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.collections.ObservableList;

/**
 * {@link FoodStore} provides an interface for storing {@link Food}s, being a little more general
 * for storage mechanisms other that {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}s.
 */
public interface FoodStore< FoodTypeT extends Food > {

   /**
    * Provides the {@link ObservableList} of {@link Food} held.
    * @return the {@link ObservableList}.
    */
   public ObservableList< FoodTypeT > objectList();
   
   /**
    * Method to create a new {@link Food} of the associated type with the given name.
    * @param name the name to create for.
    * @return the created {@link Food}.
    */
   public FoodTypeT createFood( String name );
   
   /**
    * Method to remove the given {@link Food} from the store.
    * @param food the {@link Food} to remove.
    */
   public void removeFood( FoodTypeT food );
   
}//End Interface
