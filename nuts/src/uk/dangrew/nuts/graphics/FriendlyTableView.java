/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionModel;

/**
 * {@link FriendlyTableView} provides an interface for a friendlier version of {@link javafx.scene.control.TableView}.
 */
public interface FriendlyTableView< RowTypeT > {

   /**
    * Friendly access to the {@link javafx.scene.control.TableView#getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< RowTypeT > getRows();
   
   /**
    * Friendly access to the {@link javafx.scene.control.TableView#getSelectionModel()}.
    * @return the {@link SelectionModel}.
    */
   public SelectionModel< RowTypeT > getSelectionModel();
   
}//End Interface

