/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.system;

import javafx.collections.ObservableList;

/**
 * {@link ConceptStore} provides an interface for storing {@link Concept}s, being a little more general
 * for storage mechanisms other that {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}s.
 */
public interface ConceptStore< TypeT > {

   /**
    * Provides the {@link ObservableList} of {@link Concept} held.
    * @return the {@link ObservableList}.
    */
   public ObservableList< TypeT > objectList();
   
   /**
    * Method to create a new {@link Concept} of the associated type with the given name.
    * @param name the name to create for.
    * @return the created {@link Concept}.
    */
   public TypeT createConcept( String name );
   
   /**
    * Method to create a new {@link Concept} of the associated type with the given name.
    * @param id the fixed id to created with.
    * @param name the name to create for.
    * @return the created {@link Concept}.
    */
   public TypeT createConcept( String id, String name );
   
   /**
    * Method to store the {@link Concept} in the {@link ConceptStore}.
    * @param food the {@link Concept} to store.
    */
   public void store( TypeT food );
   
   /**
    * Getter for the {@link Concept} with the given id.
    * @param id the id of the {@link Concept}.
    * @return the matching {@link Concept}, can be null.
    */
   public TypeT get( String id );
   
   /**
    * Method to remove the given {@link Concept} from the store.
    * @param food the {@link Concept} to remove.
    */
   public void removeConcept( TypeT food );
   
}//End Interface
